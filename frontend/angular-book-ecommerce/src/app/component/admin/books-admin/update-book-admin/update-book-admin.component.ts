import { Component, OnInit } from '@angular/core';
import { Book } from '../../../../common/model/book';
import { BookCategory } from '../../../../common/model/book-category';
import { BookService } from '../../../../service/book.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiResponse } from '../../../../response/api.response';
import { HttpErrorResponse } from '@angular/common/http';
import { CategoryService } from '../../../../service/category.service';
import { UpdateBookDTO } from '../../../../common/update-book-dto';


@Component({
  selector: 'app-update-book-admin',
  templateUrl: './update-book-admin.component.html',
  styleUrl: './update-book-admin.component.css'
})
export class UpdateBookAdminComponent implements OnInit{

  bookId: number;
  book: Book;
  updatedBook: Book;
  categories: BookCategory[] = [];
  
  images: File[] = [];

  constructor(
    private bookService: BookService,
    private route: ActivatedRoute,
    private router: Router,
    private categoryService: CategoryService,
    
  ) {
    this.bookId = 0;
    this.book = {} as Book;
    this.updatedBook = {} as Book;
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.bookId = Number(params.get('id'));
      console.log(this.bookId);
      this.getBookDetails();
    });
     this.getCategories();
  }

  getCategories() {
    this.categoryService.getCategories(1,100).subscribe({
      next: (apiResponse: ApiResponse) => {
        this.categories = apiResponse.data;
      },
      error: (error: HttpErrorResponse) => {
        console.error(error?.error?.message ?? '');
      } 
    });
  }

  getBookDetails(): void {
    this.bookService.getDetailBook(this.bookId).subscribe({
      next: (apiResponse: ApiResponse) => {
        this.book = apiResponse.data;
        this.updatedBook = { ...apiResponse.data };
        
      },
      error: (error: HttpErrorResponse) => {
        console.error(error?.error?.message ?? '');
      } 
    });     
  }

  updateBook() {
    const updateBookDTO: UpdateBookDTO = {
      title: this.updatedBook.title,
      unitPrice: this.updatedBook.unitPrice,
      description: this.updatedBook.description,
      // category_id: this.updatedBook.category_id
    };
    console.log("update object",updateBookDTO);
    
    this.bookService.updateBook(this.book.id, updateBookDTO).subscribe({
      next: (apiResponse: ApiResponse) => {  
        console.log(apiResponse);
      },
      complete: () => {
         this.router.navigate(['/admin']);        
      },
      error: (error: HttpErrorResponse) => {
        console.error(error?.error?.message ?? '');
      } 
    });  
  }
}
