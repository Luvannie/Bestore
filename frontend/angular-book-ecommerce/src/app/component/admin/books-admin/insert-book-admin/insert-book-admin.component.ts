import { Component, OnInit } from '@angular/core';
import { BookDTO } from '../../../../common/book-dto';
import { BookCategory } from '../../../../common/model/book-category';
import { ActivatedRoute, Router } from '@angular/router';
import { CategoryService } from '../../../../service/category.service';
import { BookService } from '../../../../service/book.service';
import { TokenService } from '../../../../service/token.service';
import { ApiResponse } from '../../../../response/api.response';
import { HttpErrorResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-insert-book-admin',
  templateUrl: './insert-book-admin.component.html',
  styleUrl: './insert-book-admin.component.css',
})
export class InsertBookAdminComponent implements OnInit{
  token: string = '';
  insertBookDTO: BookDTO = {
    title: '',
    unitPrice: 0,
    description: '',
    category_id: 0,
    author: '',
    thumbnail: ''
  }

  categories: BookCategory[] = [];
  selectedFile: File | null = null;


    constructor(
      private route: ActivatedRoute,
      private router: Router,
      private categoryService: CategoryService,
      private bookService: BookService,
      private tokenService: TokenService
    ) { }
  
    ngOnInit(): void {
      this.getCategories(0, 100);
      this.token = this.tokenService.getToken();
    }

    getCategories(page: number, limit: number) {
      this.categoryService.getCategories(page, limit).subscribe({
        next: (apiResponse: ApiResponse) => {
          console.log(apiResponse);
          this.categories = apiResponse.data.bookCategories as BookCategory[];
        },
        error: (error: HttpErrorResponse) => {
          console.error(error?.error?.message ?? '');
        }
      });
    }

    onFileSelected(event: Event): void {
      const input = event.target as HTMLInputElement;
      if (input.files && input.files.length > 0) {
        this.selectedFile = input.files[0];
      }
    }
  
    onUpload(): void {
    if (this.selectedFile) {
    this.bookService.uploadThumbnail(this.selectedFile, this.token).subscribe({
      next: (response:ApiResponse) => {
        this.insertBookDTO.thumbnail = response.data;
        console.log(this.insertBookDTO.thumbnail);
      },
      error: (error: HttpErrorResponse) => {
        console.error(error?.error?.message ?? '');
      }
    });
    }
    }

    insertBook() {
      console.log(this.insertBookDTO);
      this.bookService.createBook(this.insertBookDTO,this.token).subscribe({
        next: (apiResponse: ApiResponse) => {
          console.log(apiResponse);
          this.router.navigateByUrl('/admin/books');
          console.log(this.insertBookDTO);
        },
        error: (error: HttpErrorResponse) => {
          console.error(error?.error?.message ?? '');
        }
      });
    }

}
