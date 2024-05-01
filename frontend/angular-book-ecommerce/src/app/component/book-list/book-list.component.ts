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
  constructor(private bookService: BookService,
              private route: ActivatedRoute) { }

  ngOnInit(): void{
    this.route.paramMap.subscribe(() => {
      this.listBooks();
    });
    
  }
  listBooks() {
    // check if "id" parameter is available
    const hasCategoryId: boolean = this.route.snapshot.paramMap.has('id');
    if(hasCategoryId){
      // get the "id" param string. convert string to a number using the "+" symbol
      this.currentCategoryId = +this.route.snapshot.paramMap.get('id')!;
    } else {
      // not category id available ... default to category id 1
      this.currentCategoryId = 1;
    }
    this.bookService.getBookList().subscribe(
      data => {
        this.books = data;
      }
    )
  }
}
