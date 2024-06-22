import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse } from '../response/api.response';
import { ApiPageResponse } from '../response/api.response.page';
import { OrderDTO } from '../common/order-dto';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private apiUrl = `${environment.apiBaseUrl}/orders`;
  private apiGetAllOrders = `${environment.apiBaseUrl}/orders/get-orders-by-keyword`;

  constructor(private http: HttpClient) {}

  getOrderById(orderId: number): Observable<ApiResponse> {
    const url = `${environment.apiBaseUrl}/orders/${orderId}`;
    return this.http.get<ApiResponse>(url);
  }
  getAllOrders(keyword:string,
    page: number, limit: number
  ): Observable<ApiPageResponse> {
      const params = new HttpParams()
      .set('keyword', keyword)      
      .set('page', page.toString())
      .set('limit', limit.toString());            
      return this.http.get<ApiPageResponse>(this.apiGetAllOrders, { params });
  }
  // updateOrder(orderId: number, orderData: OrderDTO): Observable<ApiResponse> {
  //   const url = `${environment.apiBaseUrl}/orders/${orderId}`;
  //   return this.http.put<ApiResponse>(url, orderData);
  // }

  getOrdersByUserId(userId: number): Observable<ApiResponse> {
    const url = `${this.apiUrl}/user/${userId}`;
    return this.http.get<ApiResponse>(url);
  }

  deleteOrder(orderId: number): Observable<ApiResponse> {
    const url = `${environment.apiBaseUrl}/orders/${orderId}`;
    return this.http.delete<ApiResponse>(url);
  }

  updateOrder(token:string,orderId: number, orderData: OrderDTO): Observable<ApiResponse> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
    const url = `${environment.apiBaseUrl}/orders/${orderId}`;
    return this.http.put<ApiResponse>(url, orderData, { headers: headers });
  }

  
}
