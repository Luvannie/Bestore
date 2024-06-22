import { Book } from './book';

export class CartItem {
    id: number;
    book_id: number;
    thumbnail: string;
    unit_price: number;
    
    quantity: number;
    user_id: number;
    
    constructor(data: any){
        this.id = data.id;
        this.book_id = data.id;
        
        this.thumbnail= data.thumbnail;
        this.unit_price = data.unitPrice;
        this.user_id = data.user_id;
        this.quantity = 1;
    }

    // id: number;
    // title: string;
    // thumbnail: string;
    // unitPrice: number;
    
    // quantity: number;
    // author: string;
    
    // constructor(book: Book){
    //     this.id = book.id;
    //     this.title = book.title;
    //     this.author = book.author;
    //     this.thumbnail= book.thumbnail;
    //     this.unitPrice = book.unitPrice;
    
    //     this.quantity = 1;
    // }

   
}
