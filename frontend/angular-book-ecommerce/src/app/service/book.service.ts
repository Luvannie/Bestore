import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Book } from '../common/model/book';
import { Observable, map } from 'rxjs';
import { BookCategory } from '../common/model/book-category';
import { ApiPageResponse } from '../response/api.response.page';
import { environment } from '../../environments/environment';
import { ApiResponse } from '../response/api.response';
import { UpdateBookDTO } from '../common/update-book-dto';
import { BookDTO } from '../common/book-dto';


@Injectable({
  providedIn: 'root'
})
export class BookService {
  
  
  private apiBaseUrl = environment.apiBaseUrl;
  
  private baseUrl = 'http://localhost:8080/api/books';

  private categoryUrl = 'http://localhost:8080/api/book-category?size=80';
  private uploadThumbnailUrl = 'http://localhost:8080/api/cloudinary/upload';
  constructor(private httpClient:HttpClient) { }

  
  // getAllBooks(): Observable<Book[]> {
  //   return this.getBooks(this.baseUrl);
  // }

  getPagedBooks(page: number, size: number): Observable<GetResponseBook> {
    const url = `${this.baseUrl}?page=${page}&size=${size}`;
    return this.httpClient.get<GetResponseBook>(url);
  }

  getBookList(theCategoryId:number): Observable<Book[]> {
    const searchUrl = `${this.baseUrl}/search/findByCategoryId?id=${theCategoryId}`;
    //Todo: need to build URL based on id
    return this.getBooks(searchUrl);
  }

  getBookListPaginate(thePage:number, thePageSize:number, theCategoryId:number): Observable<GetResponseBook> {
    const searchUrl = `${this.baseUrl}/search/findByCategoryId?id=${theCategoryId}`
                      + `&page=${thePage}&size=${thePageSize}`;
    //Todo: need to build URL based on id, page and size
    return this.httpClient.get<GetResponseBook>(searchUrl);
  }

  getProductCategories(): Observable<BookCategory[]> {
    return this.httpClient.get<GetResponseBookCategory>(this.categoryUrl).pipe(
      map(response => response._embedded.bookCategory)
    );
  }
 
  searchBooks(theKeyword: string): Observable<Book[]> {
    const searchTitleUrl = `${this.baseUrl}/search/findByTitleContaining?title=${theKeyword}`;
    //Todo: need to build URL based on category id
    return this.getBooks(searchTitleUrl);
  }

  searchBookListPaginate(thePage:number, thePageSize:number, theKeyword:string): Observable<GetResponseBook> {
    const searchUrl = `${this.baseUrl}/search/findByTitleContaining?title=${theKeyword}`
                      + `&page=${thePage}&size=${thePageSize}`;
    //Todo: need to build URL based on id, page and size
    return this.httpClient.get<GetResponseBook>(searchUrl);
  }

  private getBooks(searchUrl: string): Observable<Book[]> {
    return this.httpClient.get<GetResponseBook>(searchUrl).pipe(
      map(response => response._embedded.books)
    );
  }

  getBook(theBookId: number): Observable<Book> {
    // dua tren url id
    const bookUrl = `${this.baseUrl}/${theBookId}`;
    return this.httpClient.get<Book>(bookUrl);
  }

  getBooksAdmin(
    keyword: string,
    categoryId: number,
    page: number,
    limit: number
  ): Observable<ApiResponse> {
    const params = {
      keyword: keyword,
      category_id: categoryId.toString(),
      page: page.toString(),
      limit: limit.toString()
    };
    return this.httpClient.get<ApiResponse>(`${this.apiBaseUrl}/books_admin`, { params });
  }
  deleteBook(bookId: number): Observable<ApiResponse> {
    
    return this.httpClient.delete<ApiResponse>(`${this.apiBaseUrl}/books_admin/${bookId}`);
  }

  getDetailBook(bookId: number):Observable<ApiResponse> {
    return this.httpClient.get<ApiResponse>(`${this.apiBaseUrl}/books_admin/${bookId}`);
  }

  updateBook(bookId: number, updateBookDTO: UpdateBookDTO): Observable<ApiResponse> {
    return this.httpClient.put<ApiResponse>(`${this.apiBaseUrl}/books_admin/${bookId}`, updateBookDTO);
  }
  uploadThumbnail(file: File, token:string): Observable<ApiResponse> {
    const headers ={
      'Authorization': `Bearer ${token}`
    }
    const formData = new FormData();
    formData.append('file', file);
    return this.httpClient.post<ApiResponse>(this.uploadThumbnailUrl, formData, {headers: headers});
  }

  createBook(book: BookDTO,token:string): Observable<ApiResponse> {
    const headers ={
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    }
    return this.httpClient.post<ApiResponse>(`${this.apiBaseUrl}/books_admin`, book, {headers: headers});
  }

}

interface GetResponseBook {
  _embedded: {
    books: Book[];
  },
  page: {
    size: number,
    totalElements: number,
    totalPages: number,
    number: number
  }
}

interface GetResponseBookCategory {
  _embedded: {
    bookCategory: BookCategory[];
  }
}
