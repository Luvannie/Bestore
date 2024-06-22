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
    order_id: 0,
    user_id: 0,
    total_price: 0,
    order_date: new Date(),
    status: '',
    shipping_method: '',
    shipping_address: '',
    shipping_date: new Date(),
    payment_method: '',
    customer_id: 0,
    tracking_number: '',
    customer_name: '',
    customer_email: '',
    customer_phone: '',
    shipping_country: '',
    shipping_city: '',
    shipping_district: '',
    order_items: [],
    date_created: new Date(),
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
            order_id: order.order_id,
            user_id: order.user_id,
            total_price: order.total_price,
            order_date: order.order_date ? new Date(order.order_date) : null,
            status: order.status,
            shipping_method: order.shipping_method,
            shipping_address: order.shipping_address,
            shipping_date: order.shipping_date ? new Date(order.shipping_date[0], order.shipping_date[1] - 1, order.shipping_date[2]) : null,
            payment_method: order.payment_method,
            customer_id: order.customer_id,
            tracking_number: order.tracking_number,
            customer_name: order.customer_name,
            customer_email: order.customer_email,
            customer_phone: order.customer_phone,
            shipping_country: order.shipping_country,
            shipping_city: order.shipping_city,
            shipping_district: order.shipping_district,
            order_items: order.order_items.map((item: any) => {
              return {
                ...item,
                bookId: item.book_id,
                unitPrice: item.unit_price,
                quantity: item.quantity,
              };
            }),
            date_created: order.date_created ? new Date(order.date_created) : null,
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
