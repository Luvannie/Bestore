import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Purchase } from '../common/model/purchase';
import { Observable } from 'rxjs';
import { PaymentInfo } from '../common/model/payment-info';

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {
  private purchaseUrl = 'http://localhost:8080/api/checkout/purchase';
  private paymentIntentUrl = 'http://localhost:8080/api/checkout/payment-intent';
  private paymentUrl = 'http://localhost:8080/api/payment/create_payment';

  constructor(private httpClient: HttpClient) { }

  placeOrder(purchase: Purchase):Observable<any> {
    return this.httpClient.post(this.purchaseUrl, purchase);
  }

  createPaymentIntent(paymentInfo: PaymentInfo):Observable<any> {
    return this.httpClient.post(this.paymentIntentUrl, paymentInfo);
  }

  createPayment(totalPrice: number):Observable<any> {
    const url = `${this.paymentUrl}/${totalPrice}`;
    return this.httpClient.get(url);
  }
}
