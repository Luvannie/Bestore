import { DOCUMENT } from '@angular/common';
import { Inject, Injectable, inject } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HttpUtilService } from './http.util.service';
import { ApiResponse } from '../response/api.response';
import { Observable } from 'rxjs';
import { ComplainDTO } from '../common/complain-dto';
import { Complain } from '../common/model/complain';

@Injectable({
  providedIn: 'root'
})
export class ComplainService {
  private complainUrl = `${environment.apiBaseUrl}/complains`;

  private http = inject(HttpClient);
  private httpUtilService = inject(HttpUtilService);  
  localStorage?: Storage;
  constructor(@Inject(DOCUMENT) private document:Document) {
    this.localStorage = document.defaultView?.localStorage;
   }

   createComplain(token:string,complain: ComplainDTO): Observable<ApiResponse> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
    console.log(complain);
    const url = `${this.complainUrl}/create`;
    return this.http.post<ApiResponse>(url, complain, { headers: headers });
   }

   getUnfinishedComplains(token: string,page:number, limit:number): Observable<ApiResponse> {
    const params = {
      page:page.toString(),
      limit:limit.toString()
    }
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<ApiResponse>(`${this.complainUrl}/unfinished`, { headers: headers, params: params });
   }

   doneComplain(token:string, complain: Complain): Observable<ApiResponse> {
    complain.is_finish = true;
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
    return this.http.put<ApiResponse>(`${this.complainUrl}/${complain.id}`, complain, { headers: headers });
   }

   
}
