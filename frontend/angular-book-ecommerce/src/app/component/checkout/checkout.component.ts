import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { BeFormService } from '../../service/be-form.service';
import { CartService } from '../../service/cart.service';
import { Country } from '../../common/model/country';
import { City } from '../../common/model/city';
import { BeValidate } from '../../validator/be-validate';
import { CheckoutService } from '../../service/checkout.service';
import { Router } from '@angular/router';
import { Order } from '../../common/model/order';
import { OrderItem } from '../../common/model/order-item';
import { Purchase } from '../../common/model/purchase';
import { environment } from '../../../environments/environment';
import { PaymentInfo } from '../../common/model/payment-info';
import { TokenService } from '../../service/token.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css'
})
export class CheckoutComponent implements OnInit{

    checkoutFormGroup!: FormGroup;
    totalPrice: number = 0;
    totalQuantity: number = 0;

    creditCardYears: number[] = [];
    creditCardMonths: number[] = [];

    countries: Country[] = [];
    cities: City[] = [];

    shippingAddressCities: City[] = [];
    selectedPaymentMethod: string = '';
  
    paymentInfo: PaymentInfo = new PaymentInfo();

    cardElement: any;
    displayError: any="";
    user_id: number = 0;
    
    
    constructor(private formBuilder: FormBuilder,
                private beFormService: BeFormService,
                private cartService: CartService,
                private checkoutService: CheckoutService,
                private router: Router,
                private tokenService : TokenService

    ) { }
  
    ngOnInit(): void {
      //get the user_id
      this.user_id = this.tokenService.getUserId();
      this.reviewCartDetails();

      this.checkoutFormGroup = this.formBuilder.group({
        customer: this.formBuilder.group({
          fullName: new FormControl('',[Validators.required, Validators.minLength(3),BeValidate.allWhitespaceValidator]),
          phoneNumber: new FormControl('', [Validators.required, Validators.minLength(10),BeValidate.allWhitespaceValidator]),
          email: new FormControl('', [Validators.required, Validators.email,BeValidate.allWhitespaceValidator])
        }),
        shippingAddress: this.formBuilder.group({
          street:  new FormControl('',[Validators.required, Validators.minLength(3),BeValidate.allWhitespaceValidator]),
          district:  new FormControl('',[Validators.required, Validators.minLength(3),BeValidate.allWhitespaceValidator]),
          city:  new FormControl('',[Validators.required]),
          country:  new FormControl('',[Validators.required]),
          zipCode:  new FormControl('',[Validators.required, Validators.minLength(5),BeValidate.allWhitespaceValidator]),
          specificAddress:  new FormControl('',[Validators.required, Validators.minLength(3),BeValidate.allWhitespaceValidator])
        }),
        
        method: this.formBuilder.group({
          paymentMethod: new FormControl('',),
          shippingMethod: new FormControl('',),
          coupon_code: new FormControl('',)
        })        
      }
      
    );

      // populate countries
      this.beFormService.getCountries().subscribe(
        data => {
          this.countries = data;
          // console.log("Retrieved countries: " + JSON.stringify(data));
        }
      );

      console.log("totalPrice: " + this.totalPrice + ", totalQuantity: " + this.totalQuantity);
    }
  
  reviewCartDetails() {
    // subscribe to cartService.totalQuantity
    this.cartService.totalQuantity.subscribe(
      totalQuantity => this.totalQuantity = totalQuantity
    );

    // subscribe to cartService.totalPrice
    this.cartService.totalPrice.subscribe(
      totalPrice => this.totalPrice = totalPrice
      
    );
  }

    onSubmit(){
      console.log("Handling the submit button");
      if(this.checkoutFormGroup.invalid){
        this.checkoutFormGroup.markAllAsTouched();
      }
      console.log(this.checkoutFormGroup.get('customer')?.value);
      console.log(this.checkoutFormGroup.get('shippingAddress')?.value);

      let order = new Order(this.totalQuantity,this.totalPrice);

      const cartItems = this.cartService.cartItems;
      let orderItems :OrderItem[] = cartItems.map(tempCartItem => new OrderItem(tempCartItem));

      let purchase = new Purchase();
      purchase.shippingAddress = this.checkoutFormGroup.controls['shippingAddress'].value;
      const shippingAddressCity: City = JSON.parse(JSON.stringify(purchase?.shippingAddress?.city));
      const shippingAddressCountry: Country = JSON.parse(JSON.stringify(purchase?.shippingAddress?.country));
      if (purchase.shippingAddress) {
        purchase.shippingAddress.city  = shippingAddressCity.name;
      }
      if (purchase.shippingAddress) {
        purchase.shippingAddress.country = shippingAddressCountry.name;
      }

      purchase.customer = this.checkoutFormGroup.controls['customer'].value;

      purchase.order = order;
      purchase.orderItems = orderItems;
      // purchase.shipping_method = this.checkoutFormGroup.controls['method'].value.shippingMethod;
      // purchase.payment_method = this.checkoutFormGroup.controls['method'].value.paymentMethod;

      purchase.user_id = this.user_id;
      // purchase.coupon_code = this.checkoutFormGroup.controls['method'].value.coupon_code;
      purchase.coupon_code = 'HEAVEN';
      purchase.payment_method = 'VNPAY';
      purchase.shipping_method = 'NORMAL';
      //compute payment info

      console.log(purchase);
      this.checkoutService.placeOrder(purchase).subscribe({
        next: response => {
          // alert(`Your order has been received.\nOrder tracking number: ${response.orderTrackingNumber}`);
          console.log(`Your order has been received.\nOrder tracking number: ${response.orderTrackingNumber}`);
          this.resetCart();
        },
        error: err => {
          alert(`There was an error: ${err.message}`);
        
       }
      });



      this.checkoutService.createPayment(this.totalPrice).subscribe(
        {next: (response) => {
          if (response.status === 'ok') {
            window.location.href = response.url;
          } else {
            // Xử lý trường hợp response không thành công
            console.error('Payment creation failed:', response.message);
          }
        },
        error: (error) => {
          // Xử lý lỗi từ API
          console.error('Error creating payment:', error);
        }
        }
      );

      

    }
  resetCart() {
    // reset cart data
    this.cartService.removeAllItems();
    this.cartService.cartItems = [];
    this.cartService.totalPrice.next(0);
    this.cartService.totalQuantity.next(0);
    

    // reset the form data
    this.checkoutFormGroup.reset();
  }

    

    handleMonthsAndYears(){
      const creditCardFormGroup = this.checkoutFormGroup.get('creditCard');
      const currentYear: number = new Date().getFullYear();
      const selectedYear: number = Number(creditCardFormGroup?.value.expirationYear);

      let startMonth: number;

      if(currentYear === selectedYear){
        startMonth = new Date().getMonth() + 1;
      } else {
        startMonth = 1;
      }

      this.beFormService.getCreditCardMonths(startMonth).subscribe(
        data => {
          this.creditCardMonths = data;
        }
      );
    }

    getCities(formGroupName: string){
      
      const formGroup = this.checkoutFormGroup.get(formGroupName);
      const countryCode = formGroup?.value.country.code;
      console.log(`Country code: ${countryCode}`);
      this.beFormService.getCities(countryCode).subscribe(
        data => {
          if(formGroupName === 'shippingAddress'){
            this.shippingAddressCities = data;
          } 
          // select first item by default
          formGroup?.get('city')?.setValue(data[0]);
        }
      );
    }

    get fullName(){
      return this.checkoutFormGroup.get('customer.fullName');
    }

    get phoneNumber(){
      return this.checkoutFormGroup.get('customer.phoneNumber');
    }

    get email(){
      return this.checkoutFormGroup.get('customer.email');
    }

    get shippingAddressStreet(){
      return this.checkoutFormGroup.get('shippingAddress.street');
    }

    get shippingAddressDistrict(){
      return this.checkoutFormGroup.get('shippingAddress.district');
    }

    get shippingAddressCity(){
      return this.checkoutFormGroup.get('shippingAddress.city');
    }

    get shippingAddressCountry(){
      return this.checkoutFormGroup.get('shippingAddress.country');
    }

    get shippingAddressZipCode(){
      return this.checkoutFormGroup.get('shippingAddress.zipCode');
    }

    get shippingAddressSpecificAddress(){
      return this.checkoutFormGroup.get('shippingAddress.specificAddress');
    }

    get cardType(){
      return this.checkoutFormGroup.get('creditCard.cardType');
    }

    get nameOnCard(){
      return this.checkoutFormGroup.get('creditCard.nameOnCard');
    }

    get cardNumber(){
      return this.checkoutFormGroup.get('creditCard.cardNumber');
    }

    get securityCode(){
      return this.checkoutFormGroup.get('creditCard.securityCode');
    }

    get shippingMethod(){
      return this.checkoutFormGroup.get('method.shippingMethod');
    }

    get paymentMethod(){
      return this.checkoutFormGroup.get('method.paymentMethod');
    }

    get coupon_code(){
      return this.checkoutFormGroup.get('method.coupon_code');
    }
}
