import { Book } from "./book";

export class CartItem {
    id: number;
    title: string;
    thumbnail: string;
    unitPrice: number;
    
    quantity: number;
    
    constructor(book: Book){
        this.id = book.id;
        this.title = book.title;
        this.thumbnail= book.thumbnail;
        this.unitPrice = book.unitPrice;
    
        this.quantity = 1;
    }
}
