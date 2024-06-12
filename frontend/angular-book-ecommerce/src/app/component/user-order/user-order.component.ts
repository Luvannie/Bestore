import { Component, Inject, OnInit } from '@angular/core';
import { OrderResponse } from '../../response/order/order.response';
import { OrderService } from '../../service/order.service';
import { ActivatedRoute, Router } from '@angular/router';
import { TokenService } from '../../service/token.service';
import { ApiResponse } from '../../response/api.response';
import { HttpErrorResponse } from '@angular/common/http';
import { DOCUMENT } from '@angular/common';

@Component({
  selector: 'app-user-order',
  templateUrl: './user-order.component.html',
  styleUrl: './user-order.component.css',
})
export class UserOrderComponent implements OnInit {
  orderResponse: OrderResponse = {
    orderId: 0,
    userId: 0,
    totalPrice: 0,
    orderDate: new Date(),
    status: '',
    shippingMethod: '',
    shippingAddress: '',
    shippingDate: new Date(),
    paymentMethod: '',
    customerId: 0,
    trackingNumber: '',
    customerName: '',
    customerEmail: '',
    customerPhone: '',
    shippingCountry: '',
    shippingCity: '',
    shippingDistrict: '',
    orderItems: [],
    dateCreated: new Date(),
  };

  orderResponses: OrderResponse[] = [];
  localStorage?:Storage;

  userId: number = 0;
  constructor(
    private orderService: OrderService,
    private route: ActivatedRoute,
    private router: Router,
    private tokenService: TokenService,
    @Inject(DOCUMENT) private document: Document
  ) {this.localStorage = document.defaultView?.localStorage;}
  ngOnInit() {
    this.userId = this.tokenService.getUserId();
    this.getOrders();
  
  }

  getOrders(): void {
    // debugger;
    this.orderService.getOrdersByUserId(this.userId).subscribe({
      next: (apiResponse: ApiResponse) => {
        
        const response = apiResponse.data;
        this.orderResponses = response.map((order: any) => {
          return {
            orderId: order.order_id,
            userId: order.user_id,
            totalPrice: order.total_price,
            orderDate: order.order_date ? new Date(order.order_date) : null,
            status: order.status,
            shippingMethod: order.shipping_method,
            shippingAddress: order.shipping_address,
            shippingDate: order.shipping_date ? new Date(order.shipping_date[0], order.shipping_date[1] - 1, order.shipping_date[2]) : null,
            paymentMethod: order.payment_method,
            customerId: order.customer_id,
            trackingNumber: order.tracking_number,
            customerName: order.customer_name,
            customerEmail: order.customer_email,
            customerPhone: order.customer_phone,
            shippingCountry: order.shipping_country,
            shippingCity: order.shipping_city,
            shippingDistrict: order.shipping_district,
            orderItems: order.order_items.map((item: any) => {
              return {
                ...item,
                bookId: item.book_id,
                unitPrice: item.unit_price,
                quantity: item.quantity,
              };
            }),
            dateCreated: order.date_created ? new Date(order.date_created) : null,
          };
        });
        console.log(this.orderResponses); // Kiểm tra dữ liệu nhận được
      },
      complete: () => {
        // debugger;
        console.log('Orders fetched successfully');
      },
      error: (error: HttpErrorResponse) => {
        // debugger;
        console.error(error?.error?.message ?? '');
      },
    });
  }

  trackByBookId(index: number, item: any): number {
    return item.bookId;
  }
  complain(orderId: number) {
    localStorage.setItem('complain_order_id', orderId.toString());
    this.router.navigateByUrl("/complain");
  }


}
