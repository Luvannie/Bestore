import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../../service/user.service';
import { TokenService } from '../../../service/token.service';
import { DOCUMENT } from '@angular/common';
import { ComplainService } from '../../../service/complain.service';
import { Complain } from '../../../common/model/complain';
import { HttpErrorResponse } from '@angular/common/http';
import { ApiResponse } from '../../../response/api.response';

@Component({
  selector: 'app-complain-admin',
  templateUrl: './complain-admin.component.html',
  styleUrl: './complain-admin.component.css'
})
export class ComplainAdminComponent implements OnInit{

  complains: Complain[] = [];
  currentPage: number = 0;
  itemsPerPage: number = 12;
  pages: number[] = [];
  totalPages:number = 0;
  visiblePages: number[] = [];
  keyword:string = "";
  localStorage?:Storage;
  isExpanded: boolean = false;
  token: string = '';
  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private router: Router,
    private tokenService: TokenService,
    private complainService: ComplainService,
    @Inject(DOCUMENT) private document: Document
  ) {
    this.localStorage = document.defaultView?.localStorage;
  }
  ngOnInit(): void {
    this.currentPage = Number(this.localStorage?.getItem('currentComplainAdminPage')) || 0;
    this.token = this.tokenService.getToken();
    this.getComplains(this.currentPage, this.itemsPerPage);
  }

  getComplains( page: number, limit: number) {
    this.complainService.getUnfinishedComplains(this.token, page, limit).subscribe({
      next: (apiResponse: ApiResponse) => {
        console.log(apiResponse);
        const complains = apiResponse?.data.complains as Complain[]
        complains.forEach((complain: Complain) => {
          // if (complain) {
          //  console.log(complain.id);
          // }
        });
        this.complains = complains;
        this.totalPages = apiResponse?.data.totalPages;
        this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
      },
      complete: () => {
        // console.log('complete');
        console.log(this.complains);
      },
      error: (error: HttpErrorResponse) => {
        console.error(error?.error?.message ?? '');
      }
    });
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

  onPageChange(page: number) {
    this.currentPage = page < 0 ? 0 : page;
    this.localStorage?.setItem('currentComplainAdminPage', String(this.currentPage));
    this.getComplains(this.currentPage, this.itemsPerPage);
  }

  doneComplain(complain: Complain) {
    this.complainService.doneComplain(this.token, complain).subscribe({
      next: (apiResponse: ApiResponse) => {
        console.log(apiResponse);
        this.getComplains(this.currentPage, this.itemsPerPage);
      },
      complete: () => {
        console.log('complete');
      },
      error: (error: HttpErrorResponse) => {
        console.error(error?.error?.message ?? '');
      }
    });
  }
  

}
