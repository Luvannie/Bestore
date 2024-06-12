import { Component, Inject, OnInit } from '@angular/core';
import { Order } from '../../../common/model/order';
import { OrderAdmin } from '../../../common/order-admin';
import { OrderService } from '../../../service/order.service';
import { ActivatedRoute, Router } from '@angular/router';
import { DOCUMENT } from '@angular/common';
import { ApiResponse } from '../../../response/api.response';
import { HttpErrorResponse } from '@angular/common/http';
import { OrderResponse } from '../../../response/order/order.response';

@Component({
  selector: 'app-order-admin',
  templateUrl: './order-admin.component.html',
  styleUrl: './order-admin.component.css'
})
export class OrderAdminComponent implements OnInit{
  orders: OrderAdmin[] = [];
  currentPage: number = 1;
  itemsPerPage: number = 10;
  pages: number[] = [];
  totalPages: number = 0;
  keyword: string = "";
  visiblePages: number[] = [];
  localStorage?:Storage;
  constructor(private orderService: OrderService,
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    @Inject(DOCUMENT) private document: Document
  )
   {  this.localStorage = document.defaultView?.localStorage;}
  ngOnInit(): void {
    debugger
    this.currentPage = Number(this.localStorage?.getItem('currentOrderAdminPage')) || 0; 
    this.getAllOrders(this.keyword, this.currentPage, this.itemsPerPage);
  }

  searchOrders() {
    this.currentPage = 0;
    this.itemsPerPage = 12;
    //Mediocre Iron Wallet
    debugger
    this.getAllOrders(this.keyword.trim(), this.currentPage, this.itemsPerPage);
  }
  getAllOrders(keyword: string, page: number, limit: number) {
    debugger
    this.orderService.getAllOrders(keyword, page, limit).subscribe({
      next: (apiResponse: ApiResponse) => {
        debugger        
        this.orders = apiResponse.data.orders;
        this.totalPages = apiResponse.data.totalPages;
        this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
      },
      complete: () => {
        debugger;
      },
      error: (error: HttpErrorResponse) => {
        debugger;
        console.error(error?.error?.message ?? '');
      }
    });    
  }
  onPageChange(page: number) {
    debugger;
    this.currentPage = page < 0 ? 0 : page;
    this.localStorage?.setItem('currentOrderAdminPage', String(this.currentPage));         
    this.getAllOrders(this.keyword, this.currentPage, this.itemsPerPage);
  }

  generateVisiblePageArray(currentPage: number, totalPages: number): number[] {
    const maxVisiblePages = 5;
    const halfVisiblePages = Math.floor(maxVisiblePages / 2);

    let startPage = Math.max(currentPage - halfVisiblePages, 1);
    let endPage = Math.min(startPage + maxVisiblePages - 1, totalPages);

    if (endPage - startPage + 1 < maxVisiblePages) {
      startPage = Math.max(endPage - maxVisiblePages + 1, 1);
    }

    return new Array(endPage - startPage + 1).fill(0)
        .map((_, index) => startPage + index);
  }

  deleteOrder(id:number) {
    const confirmation = window
      .confirm('Are you sure you want to delete this order?');
    if (confirmation) {
      debugger
      this.orderService.deleteOrder(id).subscribe({
        next: (response: ApiResponse) => {
          debugger 
          location.reload();          
        },
        complete: () => {
          debugger;          
        },
        error: (error: HttpErrorResponse) => {
          debugger;
          console.error(error?.error?.message ?? '');
        }
      });    
    }
  }
  viewDetails(order:OrderResponse) {
    debugger
    this.router.navigate(['/admin/orders', order.orderId]);
  }



}
