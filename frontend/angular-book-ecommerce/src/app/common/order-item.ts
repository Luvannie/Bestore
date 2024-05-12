import { CartItem } from "./cart-item";

export class OrderItem {
    thumbnail: string;
    unitPrice: number;
    quantity: number;
    bookId: number;

    constructor(cartItem: CartItem) {
        this.thumbnail = cartItem.thumbnail;
        this.unitPrice = cartItem.unitPrice;
        this.quantity = cartItem.quantity;
        this.bookId = cartItem.id;
       
    }
}
