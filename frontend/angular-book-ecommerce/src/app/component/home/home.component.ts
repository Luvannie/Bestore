import { Component, OnInit } from '@angular/core';
import { BookService } from '../../service/book.service';
import { Book } from '../../common/book';
import { ActivatedRoute } from '@angular/router';
import { CartService } from '../../service/cart.service';
import { CartItem } from '../../common/cart-item';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
  books: Book[] = [];
  thePageNumber: number = 1;
  thePageSize: number = 10;
  theTotalElements: number = 0;

  constructor(private bookService: BookService,
              private route: ActivatedRoute,
              private cartService:CartService
    ) { }

  ngOnInit(): void {
    this.listBooks();
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

    const theCartItem = new CartItem(theBook);
    this.cartService.addToCart(theCartItem);
  }
}
