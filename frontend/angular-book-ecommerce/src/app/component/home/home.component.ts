import { Component, OnInit } from '@angular/core';
import { BookService } from '../../service/book.service';
import { Book } from '../../common/model/book';
import { ActivatedRoute } from '@angular/router';
import { CartService } from '../../service/cart.service';
import { CartItem } from '../../common/model/cart-item';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
  books: Book[] = [];
  thePageNumber: number = 1;
  thePageSize: number = 12;
  theTotalElements: number = 0;

  constructor(private bookService: BookService,
              private route: ActivatedRoute,
              private cartService:CartService
    ) { }

  ngOnInit(): void {
    this.listBooks();
    this.cartService.begin();
  }

  listBooks() {
    this.bookService.getPagedBooks(this.thePageNumber - 1, this.thePageSize).subscribe(
      data => {
        this.books = data._embedded.books;
        this.thePageNumber = data.page.number + 1;
        this.thePageSize = data.page.size;
        this.theTotalElements = data.page.totalElements;
      }
    );
  }

  addToCart(theBook: Book){
   
    this.cartService.addToCart(theBook);
  }
}
