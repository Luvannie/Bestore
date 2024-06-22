import { Component, OnInit } from '@angular/core';
import { OrderResponse } from '../../../response/order/order.response';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderService } from '../../../service/order.service';
import { ApiResponse } from '../../../response/api.response';
import { HttpErrorResponse } from '@angular/common/http';
import { OrderDTO } from '../../../common/order-dto';
import { TokenService } from '../../../service/token.service';

@Component({
  selector: 'app-detail-order-admin',
  templateUrl: './detail-order-admin.component.html',
  styleUrl: './detail-order-admin.component.css'
})
export class DetailOrderAdminComponent implements OnInit{
  order_id : number = 0;
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
  }
  token: string = '';
  constructor(
    private route: ActivatedRoute,
    private orderService: OrderService,
    private router: Router,
    private tokenService: TokenService
  ) {
   
  }
  ngOnInit(): void {
    this.getOrderDetails();
    this.token = this.tokenService.getToken();

  }
  getOrderDetails() {
    this.order_id = this.route.snapshot.params['id'];
    this.orderService.getOrderById(this.order_id).subscribe({
      next: (apiresponse: ApiResponse) => {
        const response = apiresponse.data;
        console.log('Order fetched successfully:', response);
        this.orderResponse.order_id = response.order_id;
        this.orderResponse.user_id = response.user_id;
        this.orderResponse.total_price = response.total_price;
        this.orderResponse.order_date = response.order_date ;
        this.orderResponse.status = response.status;
        this.orderResponse.shipping_method = response.shipping_method;
        this.orderResponse.shipping_address = response.shipping_address;
        this.orderResponse.shipping_date = response.shipping_date;
        this.orderResponse.payment_method = response.payment_method;
        this.orderResponse.customer_id = response.customer_id;
        this.orderResponse.tracking_number = response.tracking_number;
        this.orderResponse.customer_name = response.customer_name;
        this.orderResponse.customer_email = response.customer_email;
        this.orderResponse.customer_phone = response.customer_phone;
        this.orderResponse.shipping_country = response.shipping_country;
        this.orderResponse.shipping_city = response.shipping_city;
        this.orderResponse.shipping_district = response.shipping_district;
        this.orderResponse.order_items = response.order_items;
       
      },
      error: (error: any) => {
        console.error('Error fetching order:', error);
      }
    });
  }

  saveOrder(): void {    
    debugger        
    this.orderService
      .updateOrder(this.token,this.order_id, new OrderDTO(this.orderResponse))
      .subscribe({
      next: (response: ApiResponse) => {
        debugger

        console.log('Order updated successfully:', response);
        // Navigate back to the previous page
        //this.router.navigate(['/admin/orders']);       
        this.router.navigate(['../'], { relativeTo: this.route });
      },
      complete: () => {
        debugger;        
      },
      error: (error: HttpErrorResponse) => {
        debugger;
        console.error(error?.error?.message ?? '');
        this.router.navigate(['../'], { relativeTo: this.route });
      }       
    });   
  }

}
