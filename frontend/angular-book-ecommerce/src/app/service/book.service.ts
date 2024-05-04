import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Book } from '../common/book';
import { Observable, map } from 'rxjs';
import { BookCategory } from '../common/book-category';


@Injectable({
  providedIn: 'root'
})
export class BookService {
  
  
  private baseUrl = 'http://localhost:8080/api/books';

  private categoryUrl = 'http://localhost:8080/api/book-category';
  constructor(private httpClient:HttpClient) { }

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
