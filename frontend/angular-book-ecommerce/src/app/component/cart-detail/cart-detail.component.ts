import { Component, OnInit } from '@angular/core';
import { CartItem } from '../../common/model/cart-item';
import { CartService } from '../../service/cart.service';
import { Book } from 'lucide-angular';
import { CartItemDTO } from '../../common/cart-item-dto';
import { ApiResponse } from '../../response/api.response';

@Component({
  selector: 'app-cart-detail',
  templateUrl: './cart-detail.component.html',
  styleUrl: './cart-detail.component.css'
})
export class CartDetailComponent implements OnInit{
  cartItems: CartItem[] = [];
  totalPrice: number = 0;
  totalQuantity: number = 0;

    constructor(private cartService: CartService) { }
  
    ngOnInit(): void {
      this.listCartDetails();
    }
  listCartDetails() {
    this.cartItems = this.cartService.cartItems;
    this.cartService.totalPrice.subscribe(
      data => this.totalPrice = data
    );
    this.cartService.totalQuantity.subscribe(
      data => this.totalQuantity = data
    );
    this.cartService.computeCartTotals();
  }

  incrementQuantity(theCartItem: CartItem){
    theCartItem.quantity++;
    const updateCartItem : CartItem ={
      id: theCartItem.id,
      user_id: theCartItem.user_id,
      book_id: theCartItem.book_id,
      thumbnail: theCartItem.thumbnail,
      unit_price: theCartItem.unit_price,
      quantity: theCartItem.quantity
    }
    this.cartService.updateCartItem(updateCartItem).subscribe({
      next: (apiresponse:ApiResponse) => {
        console.log(apiresponse);
        this.cartService.computeCartTotals();
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }
  decrementQuantity(theCartItem: CartItem){
    this.cartService.decrementQuantity(theCartItem);
  }
  remove(theCartItem: CartItem){
    this.cartService.remove(theCartItem);
  }

}
