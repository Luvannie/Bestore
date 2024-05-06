import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { BeFormService } from '../../service/be-form.service';
import { CartService } from '../../service/cart.service';
import { Country } from '../../common/country';
import { City } from '../../common/city';

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
    billingAddressCities: City[] = [];
    
    constructor(private formBuilder: FormBuilder,
                private beFormService: BeFormService,
                private cartService: CartService
    ) { }
  
    ngOnInit(): void {
      this.checkoutFormGroup = this.formBuilder.group({
        customer: this.formBuilder.group({
          fullName: [''],
          phoneNumber: [''],
          email: ['']
        }),
        shippingAddress: this.formBuilder.group({
          street: [''],
          district: [''],
          city: [''],
          country: [''],
          zipCode: [''],
          shippingAddress: ['']
        }),
        billingAddress: this.formBuilder.group({
          street: [''],
          district: [''],
          city: [''],
          country: [''],
          zipCode: [''],
          billingAddress: ['']
        }),
        creditCard: this.formBuilder.group({
          cardType: [''],
          nameOnCard: [''],
          cardNumber: [''],
          securityCode: [''],
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

    onSubmit(){
      console.log("Handling the submit button");
      console.log(this.checkoutFormGroup.get('customer')?.value);
      // console.log("The shipping address is " + this.checkoutFormGroup.get('shippingAddress')?.value.street);
    }

    copyShippingAddressToBillingAddress(event:any){
      if(event.target.checked){
        this.checkoutFormGroup.controls['billingAddress']
        .setValue(this.checkoutFormGroup.controls['shippingAddress'].value);
      } else {
        this.checkoutFormGroup.controls['billingAddress'].reset();
      }
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
          } else {
            this.billingAddressCities = data;
          }
          // select first item by default
          formGroup?.get('city')?.setValue(data[0]);
        }
      );
    }

}
