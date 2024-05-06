import { Component, OnInit } from '@angular/core';
import { BookService } from '../../service/book.service';
import { Book } from '../../common/book';
import { ActivatedRoute } from '@angular/router';
import { CartItem } from '../../common/cart-item';
import { CartService } from '../../service/cart.service';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list-grid.component.html',
  styleUrl: './book-list.component.css'
})
export class BookListComponent implements OnInit{
  books: Book[] = [];
  currentCategoryId: number = 1;
  previousCategoryId: number=1;
  searchMode: boolean = false;

  //them dac diem cho pagination

  thePageNumber: number = 1;
  thePageSize: number = 10;
  theTotalElements: number = 0;
  
  previousKeyword: string = "";


  constructor(private bookService: BookService,
              private route: ActivatedRoute,
              private cartService:CartService) { }

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

    // neu keyword khac voi keyword truoc do
    // thi set pageNumber ve 1
    if(this.previousKeyword != theKeyword){
      this.thePageNumber = 1;
    }

    this.previousKeyword = theKeyword;

    console.log(`keyword=${theKeyword}, thePageNumber=${this.thePageNumber}`);
    this.bookService.searchBookListPaginate(this.thePageNumber - 1,
                                            this.thePageSize,
                                            theKeyword).subscribe(this.processResult());
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
      // this.bookService.getPagedBooks(this.thePageNumber - 1,
      //   this.thePageSize,).subscribe(
      //   this.processResult()
      // )
    }

    // neu co category id khac voi casi truoc do
    // thi set pageNumber ve 1  
    if(this.previousCategoryId != this.currentCategoryId){
      this.thePageNumber = 1;
    }

    this.previousCategoryId = this.currentCategoryId;

    console.log(`currentCategoryId=${this.currentCategoryId}, thePageNumber=${this.thePageNumber}`);

    this.bookService.getBookListPaginate(this.thePageNumber - 1,
                                          this.thePageSize,
                                          this.currentCategoryId)
                                          .subscribe(
                                            this.processResult()
                                          );
  }

  processResult() {
    return (data:any) => {
      this.books = data._embedded.books;
      this.thePageNumber = data.page.number + 1;
      this.thePageSize = data.page.size;
      this.theTotalElements = data.page.totalElements;
    };
  }

  addToCart(theBook: Book){

    const theCartItem = new CartItem(theBook);
    this.cartService.addToCart(theCartItem);
  }
}
