import { Inject, Injectable, inject } from '@angular/core';
import { CartItem } from '../common/model/cart-item';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { DOCUMENT } from '@angular/common';
import { Book } from '../common/model/book';
import { TokenService } from './token.service';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { ApiResponse } from '../response/api.response';
import { environment } from '../../environments/environment';
import { HttpUtilService } from './http.util.service';
import { CartItemDTO } from '../common/cart-item-dto';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  
  localStorage?:Storage;
  refreshCart() {
    throw new Error('Method not implemented.');
  }
  
  user_id: number = 0;
  cartItems: CartItem[] = [];
  totalPrice: Subject<number> = new BehaviorSubject<number>(0);
  totalQuantity: Subject<number> = new BehaviorSubject<number>(0);
  token: string = '';
  private cartItemrUrl = `${environment.apiBaseUrl}/cart_item`;
  private http = inject(HttpClient);
  private httpUtilService = inject(HttpUtilService);  
  private userCartItem = `${environment.apiBaseUrl}/cart_item/user`;

  constructor(
    private tokenService: TokenService,
    @Inject(DOCUMENT) private document: Document  
  ) {
    this.localStorage = document.defaultView?.localStorage;
    let user = this.localStorage?.getItem('user');
    this.user_id = user ? Number(JSON.parse(user).id) : 0;
    this.token = this.tokenService.getToken();
    this.loadCartItems();
   }


  addToCart(theBook: Book) {
    
    let alreadyExistsInCart: boolean = false;
    let existingCartItem: CartItem = undefined!;

    if(this.cartItems.length > 0){
      //tim san pham trong gio hang
      existingCartItem = this.cartItems.find(tempCartItem => tempCartItem.book_id === theBook.id)!;

      // kiem tra xem san pham co trong gio hang chua
      alreadyExistsInCart = (existingCartItem != undefined);
    }

    if(alreadyExistsInCart){
      // tang so luong
      existingCartItem.quantity++;
      this.updateCartItem(existingCartItem).subscribe({
        next: (apiResponse: ApiResponse) => {
          existingCartItem = apiResponse.data as CartItem;
          console.log(apiResponse);
          this.computeCartTotals();
        }
      });
    } else {
      // them san pham vao gio hang
      const theCartItem :  CartItemDTO ={
        book_id: theBook.id,
        quantity: 1,
        unit_price: theBook.unitPrice,
        user_id: this.user_id,
        thumbnail: theBook.thumbnail
      }
      this.createCartItem(theCartItem).subscribe({
        next: (apiResponse: ApiResponse) => {
          this.cartItems.push(apiResponse.data as CartItem);
          this.computeCartTotals();
        },
        error: (error: HttpErrorResponse) => {
          console.error(error?.error?.message ?? '');
        }
      });
    }

    // tinh lai tong tien va tong so luong

  }

  decrementQuantity(theCartItem: CartItem) {
  theCartItem.quantity--;

  const updateCartItem : CartItem = {
    id: theCartItem.id,
    user_id: theCartItem.user_id,
    book_id: theCartItem.book_id,
    thumbnail: theCartItem.thumbnail,
    unit_price: theCartItem.unit_price,
    quantity: theCartItem.quantity
  }

  if(theCartItem.quantity === 0){
    this.remove(theCartItem);
  } else {
    this.updateCartItem(updateCartItem).subscribe({
      next: (apiResponse: ApiResponse) => {
        console.log(apiResponse);
        this.computeCartTotals();
      },
      error: (error: HttpErrorResponse) => {
        console.error(error?.error?.message ?? '');
      }
    });
  }
}
  remove(theCartItem: CartItem) {
    // get index of item in the array
    const itemIndex = this.cartItems.findIndex(tempCartItem => tempCartItem.id === theCartItem.id);
  
    // if found, remove the item from the array at the given index
    if(itemIndex > -1){
      this.cartItems.splice(itemIndex, 1);
  
      this.deleteCartItem(theCartItem).subscribe({
        next: (apiResponse: ApiResponse) => {
          console.log(apiResponse);
          this.computeCartTotals();
        },
        error: (error: HttpErrorResponse) => {
          console.error(error?.error?.message ?? '');
        }
      });
    }
  }

  computeCartTotals() {
    let totalPriceValue: number = 0;
    let totalQuantityValue: number = 0;

    for(let currentCartItem of this.cartItems){
      totalPriceValue += currentCartItem.quantity * currentCartItem.unit_price;
      totalQuantityValue += currentCartItem.quantity;
    }

    // publish the new values ... all subscribers will receive the new data
    this.totalPrice.next(totalPriceValue);
    this.totalQuantity.next(totalQuantityValue);

    // log cart data just for debugging purposes
    this.logCartData(totalPriceValue, totalQuantityValue);
  }

  logCartData(totalPriceValue: number, totalQuantityValue: number) {
    console.log('Trong gio hang cua ban co:');
    for(let tempCartItem of this.cartItems){
      const subTotalPrice = tempCartItem.quantity * tempCartItem.unit_price;
      console.log(`tên: ${tempCartItem.book_id}, số lượng=${tempCartItem.quantity}, giá 1 sản phẩm=${tempCartItem.unit_price}, giá tổng =${subTotalPrice}`);
    }

    console.log(`tổng giá: ${totalPriceValue.toFixed(2)}, tổng số lượng: ${totalQuantityValue}`);
    console.log('-----------------');
  }

  
  loadCartItems() {
  const userId = this.user_id;
  if (userId > 0) {
    this.getCartItemsByUser().subscribe({
      next: (apiResponse: ApiResponse) => {
        console.log("api cart", apiResponse?.data)
        this.cartItems = apiResponse?.data as CartItem[];
        this.computeCartTotals();
        console.log(this.cartItems);
      },
      error: (error: HttpErrorResponse) => {
        console.error(error?.error?.message ?? '');
      }
    });
  }
}

  

  getCartItemsByUser(): Observable<ApiResponse> {
    
    return this.http.get<ApiResponse>(`${this.userCartItem}/${this.user_id}`);
  }

  updateCartItem(cartItem: CartItem): Observable<ApiResponse> {
    const id = cartItem.id;
    const url = `${this.cartItemrUrl}/${id}`;
    return this.http.put<ApiResponse>(url, cartItem);
  }

  deleteCartItem(cartItem: CartItem): Observable<ApiResponse> {
    const id = cartItem.id;
    const url = `${this.cartItemrUrl}/${id}`;
    return this.http.delete<ApiResponse>(url);
  }

  createCartItem(cartItem: CartItemDTO): Observable<ApiResponse> {
    const headers ={
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`
    }
    return this.http.post<ApiResponse>(this.cartItemrUrl, cartItem,{headers: headers});
  }

  removeAllItems() {
  // Assuming cartItems is an array of CartItem objects
  this.cartItems.forEach(item => {
    this.deleteCartItem(item).subscribe({
      next: (apiResponse: ApiResponse) => {
        console.log(apiResponse);
        this.computeCartTotals();
      },
      error: (error: HttpErrorResponse) => {
        console.error(error?.error?.message ?? '');
      }
    });
  });

  // Clear the local cartItems array after all delete operations have been initiated
  this.cartItems = [];
}
  begin(){
    this.localStorage = document.defaultView?.localStorage;
    let user = this.localStorage?.getItem('user');
    this.user_id = user ? Number(JSON.parse(user).id) : 0;
    this.token = this.tokenService.getToken();
    this.loadCartItems();
  }
  
}
