import { Component, OnInit } from '@angular/core';
import { Book } from '../../common/book';
import { BookService } from '../../service/book.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-book-details',
  templateUrl: './book-details.component.html',
  styleUrl: './book-details.component.css'
})
export class BookDetailsComponent implements OnInit {
    book!:Book;
    constructor(private bookService: BookService,
                private route: ActivatedRoute
                
    ) { }
  
    ngOnInit(): void {
      this.route.paramMap.subscribe(() => {
        this.handleBookDetails();
      });
    }

    handleBookDetails() {
      // get the "id" param string. convert string to a number using the "+" symbol
      const theBookId: number = +this.route.snapshot.paramMap.get('id')!;
      this.bookService.getBook(theBookId).subscribe(
        data => {
          this.book = data;
        }
      )
    }
}
