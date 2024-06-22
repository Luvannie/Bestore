// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-books-admin',
//   templateUrl: './books-admin.component.html',
//   styleUrl: './books-admin.component.css'
// })
// export class BooksAdminComponent {

// }

import { Component, OnInit, Inject, inject } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { BookService } from '../../../service/book.service';
import { Book } from '../../../common/model/book';
import { ApiResponse } from '../../../response/api.response';
import { HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-books-admin',
  templateUrl: './books-admin.component.html',
  styleUrls: ['./books-admin.component.css']
})
export class BooksAdminComponent implements OnInit {
  selectedCategoryId: number  = 0;
  books: Book[] = [];
  currentPage: number = 0;
  itemsPerPage: number = 12;
  pages: number[] = [];
  totalPages:number = 0;
  visiblePages: number[] = [];
  keyword:string = "";
  localStorage?:Storage;
  isExpanded: boolean = false;

  private bookService = inject(BookService);
  private router = inject(Router);
  private location = inject(Location);

  constructor(
    @Inject(DOCUMENT) private document: Document
  ) {
    this.localStorage = document.defaultView?.localStorage;
  }

  ngOnInit() {
    this.currentPage = Number(this.localStorage?.getItem('currentBookAdminPage')) || 0;
    this.getBooks(this.keyword, this.selectedCategoryId, this.currentPage, this.itemsPerPage);
  }

  searchBooks() {
    this.currentPage = 0;
    this.itemsPerPage = 12;
    this.getBooks(this.keyword.trim(), this.selectedCategoryId, this.currentPage, this.itemsPerPage);
  }

  getBooks(keyword: string, selectedCategoryId: number, page: number, limit: number) {
    this.bookService.getBooksAdmin(keyword, selectedCategoryId, page, limit).subscribe({
      next: (apiResponse: ApiResponse) => {
        console.log(apiResponse);
        const books = apiResponse?.data.books as Book[]
        books.forEach((book: Book) => {
          // if (book) {
          //  console.log(book.id);
          // }
        });
        this.books = books;
        this.totalPages = apiResponse?.data.totalPages;
        this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
      },
      complete: () => {
        // console.log('complete');
        console.log(this.books);
      },
      error: (error: HttpErrorResponse) => {
        console.error(error?.error?.message ?? '');
      }
    });
  }

  onPageChange(page: number) {
    this.currentPage = page < 0 ? 0 : page;
    this.localStorage?.setItem('currentBookAdminPage', String(this.currentPage));
    this.getBooks(this.keyword, this.selectedCategoryId, this.currentPage, this.itemsPerPage);
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

  insertBook() {
    this.router.navigate(['/admin/books/insert']);
  }

  updateBook(bookId: number) {
    
    this.router.navigate(['/admin/books/update', bookId]);
  }

  deleteBook(book: Book) {
    const confirmation = window.confirm('Are you sure you want to delete this book?');
    if (confirmation) {
      this.bookService.deleteBook(book.id).subscribe({
        next: (apiResponse: ApiResponse) => {
          console.error('Xóa thành công')
          location.reload();
        },
        error: (error: HttpErrorResponse) => {
          console.error(error?.error?.message ?? '');
        }
      });
    }
  }

  toggleDescription() {
    this.isExpanded = !this.isExpanded;
  }
}