import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiResponse } from '../response/api.response';
import { CategoryDTO } from '../common/category-dto';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private token: string = '';
  private apiBaseUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient,
              private tokenService: TokenService
  ) {
    this.token = this.tokenService.getToken();
   }
  getCategories(page:number,limit:number ):Observable<ApiResponse> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`
    });
    const params ={
      page: page.toString(),
      limit: limit.toString()
    }
      return this.http.get<ApiResponse>(`${environment.apiBaseUrl}/book_categories_admin`, { headers: headers, params: params});           
  }
  getDetailCategory(id: number): Observable<ApiResponse> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`
    });
    return this.http.get<ApiResponse>(`${this.apiBaseUrl}/book_categories_admin/${id}`, { headers: headers});
  }
  deleteCategory(id: number): Observable<ApiResponse> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`
    });
    debugger
    return this.http.delete<ApiResponse>(`${this.apiBaseUrl}/book_categories_admin/${id}`, { headers: headers});
  }
  updateCategory(id: number, updatedCategory: CategoryDTO): Observable<ApiResponse> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`
    });
    return this.http.put<ApiResponse>(`${this.apiBaseUrl}/book_categories_admin/${id}`, updatedCategory, { headers: headers});
  }  
  insertCategory(insertCategoryDTO: CategoryDTO): Observable<ApiResponse> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`
    });
    // Add a new category
    return this.http.post<ApiResponse>(`${this.apiBaseUrl}/book_categories_admin`, insertCategoryDTO, { headers: headers});
  }
}

