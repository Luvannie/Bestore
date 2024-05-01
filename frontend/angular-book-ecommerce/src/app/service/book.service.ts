import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Book } from '../common/book';
import { Observable, map } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class BookService {
  private baseUrl = 'http://localhost:8080/api/books';

  constructor(private httpClient:HttpClient) { }

  getBookList(theCategoryId:number): Observable<Book[]> {

    //Todo: need to build URL based on category id
    return this.httpClient.get<GetResponse>(this.baseUrl).pipe(
      map(response => response._embedded.books)
    );
  }
}

interface GetResponse {
  _embedded: {
    books: Book[];
  }
}
