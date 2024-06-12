import { DOCUMENT } from '@angular/common';
import { Inject, Injectable, inject } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HttpUtilService } from './http.util.service';
import { ApiResponse } from '../response/api.response';
import { Observable } from 'rxjs';
import { complain } from '../common/model/complain';

@Injectable({
  providedIn: 'root'
})
export class ComplainService {
  private complainUrl = `${environment.apiBaseUrl}/complains/create`;

  private http = inject(HttpClient);
  private httpUtilService = inject(HttpUtilService);  
  localStorage?: Storage;
  constructor(@Inject(DOCUMENT) private document:Document) {
    this.localStorage = document.defaultView?.localStorage;
   }

   createComplain(token:string,complain: complain): Observable<ApiResponse> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
    console.log(complain);
  
    return this.http.post<ApiResponse>(this.complainUrl, complain, { headers: headers });
   }
}
