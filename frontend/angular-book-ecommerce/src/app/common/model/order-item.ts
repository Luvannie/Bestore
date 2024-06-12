import { CartItem } from "./cart-item";

export class OrderItem {
    thumbnail: string;
    unitPrice: number;
    quantity: number;
    bookId: number;

    constructor(data:any) {
        this.thumbnail = data.thumbnail;
        this.unitPrice = data.unitPrice;
        this.quantity = data.quantity;
        this.bookId = data.id;
       
    }
}
