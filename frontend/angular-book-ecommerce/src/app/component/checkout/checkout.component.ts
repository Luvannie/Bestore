import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { BeFormService } from '../../service/be-form.service';
import { CartService } from '../../service/cart.service';
import { Country } from '../../common/country';
import { City } from '../../common/city';
import { BeValidate } from '../../validator/be-validate';
import { CheckoutService } from '../../service/checkout.service';
import { Router } from '@angular/router';
import { Order } from '../../common/order';
import { OrderItem } from '../../common/order-item';
import { Purchase } from '../../common/purchase';

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
  
    
    constructor(private formBuilder: FormBuilder,
                private beFormService: BeFormService,
                private cartService: CartService,
                private checkoutService: CheckoutService,
                private router: Router,

    ) { }
  
    ngOnInit(): void {
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
        
        creditCard: this.formBuilder.group({
          cardType: new FormControl('',[Validators.required]),
          nameOnCard: new FormControl('',[Validators.required, Validators.minLength(2),BeValidate.allWhitespaceValidator]),
          cardNumber: new FormControl('',[Validators.required, Validators.pattern('[0-9]{16}')]),
          securityCode: new FormControl('',[Validators.required, Validators.pattern('[0-9]{3}')]),
          expirationMonth: [''],
          expirationYear: ['']
        })
      });

      // populate credit card months
      const startMonth: number = new Date().getMonth() + 1;
      this.beFormService.getCreditCardMonths(startMonth).subscribe(
        data => {
          this.creditCardMonths = data;
          console.log("Retrieved credit card months: " + JSON.stringify(data));
        }
      );

      // populate credit card years

      this.beFormService.getCreditCardYears().subscribe(
        data => {
          this.creditCardYears = data;
          console.log("Retrieved credit card years: " + JSON.stringify(data));
        }
      );

      // populate countries
      this.beFormService.getCountries().subscribe(
        data => {
          this.countries = data;
          console.log("Retrieved countries: " + JSON.stringify(data));
        }
      );
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

      let order = new Order(this.totalPrice,this.totalQuantity);

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

      this.checkoutService.placeOrder(purchase).subscribe({
        next: response => {
          alert(`Your order has been received.\nOrder tracking number: ${response.orderTrackingNumber}`);
          this.resetCart();
        },
        error: err => {
          alert(`There was an error: ${err.message}`);
        
       }
      })

    }
  resetCart() {
    // reset cart data
    this.cartService.cartItems = [];
    this.cartService.totalPrice.next(0);
    this.cartService.totalQuantity.next(0);

    // reset the form data
    this.checkoutFormGroup.reset();

    // navigate back to the products page
    this.router.navigateByUrl("/books");
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
}
