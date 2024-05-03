import { Component, OnInit } from '@angular/core';
import { BookService } from '../../service/book.service';
import { Book } from '../../common/book';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list-grid.component.html',
  styleUrl: './book-list.component.css'
})
export class BookListComponent implements OnInit{
  books: Book[] = [];
  currentCategoryId: number = 1;

  searchMode: boolean = false;
  constructor(private bookService: BookService,
              private route: ActivatedRoute) { }

  ngOnInit(){
    this.route.paramMap.subscribe(() => {
      this.listBooks();
    });
    
  }
  listBooks() {
    this.searchMode = this.route.snapshot.paramMap.has('keyword');

    if(this.searchMode){
      this.handleSearchBooks();
    } else {
      this.handleListBooks();
    }
    
  }
  handleSearchBooks() {
    const theKeyword: string = this.route.snapshot.paramMap.get('keyword')!;
    this.bookService.searchBooks(theKeyword).subscribe(
      data => {
        this.books = data;
      }
    );
  }

  handleListBooks() {
    // check if "id" parameter is available
    const hasCategoryId: boolean = this.route.snapshot.paramMap.has('id');
    if(hasCategoryId){
      // get the "id" param string. convert string to a number using the "+" symbol
      this.currentCategoryId = +this.route.snapshot.paramMap.get('id')!;
    } else {
      // not category id available ... default to category id 1
      this.currentCategoryId = 1;
    }
    this.bookService.getBookList(this.currentCategoryId).subscribe(
      data => {
        this.books = data;
      }
    )
  }
}
