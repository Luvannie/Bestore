import { CartItem } from "./cart-item";

export class OrderItem {
    thumbnail: string;
    unitPrice: number;
    quantity: number;
    bookId: number;

    constructor(data:CartItem) {
        this.thumbnail = data.thumbnail;
        this.unitPrice = data.unit_price;
        this.quantity = data.quantity;
        this.bookId = data.book_id;
       
    }
}
