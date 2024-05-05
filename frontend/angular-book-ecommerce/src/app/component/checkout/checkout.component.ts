import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css'
})
export class CheckoutComponent implements OnInit{

    checkoutFormGroup!: FormGroup;
    
    constructor(private formBuilder: FormBuilder,) { }
  
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

}