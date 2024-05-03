import { Component, OnInit } from '@angular/core';
import { BookCategory } from '../../common/book-category';
import { BookService } from '../../service/book.service';

@Component({
  selector: 'app-book-category-menu',
  templateUrl: './book-category-menu.component.html',
  styleUrl: './book-category-menu.component.css'
})
export class BookCategoryMenuComponent implements OnInit{
  bookCategories: BookCategory[] = [];

  constructor(private bookService: BookService) {}

  ngOnInit(){
    this.listBookCategories();
  }
  listBookCategories(){
    this.bookService.getProductCategories().subscribe(
      data => {
        console.log('Book Categories=' + JSON.stringify(data));
        this.bookCategories = data;
      }
    )
  }

}
