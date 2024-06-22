import { CartItemDTO } from "./cart-item-dto";

export class OrderDTO {
    order_id: number;
  user_id: number;
  total_price: number;
  order_date: Date;
  status: string;
  cart_items: CartItemDTO[];
  shipping_method: string;
  shipping_address: string;
  shipping_date: Date;
  payment_method: string;
  coupon_code: string;

  constructor(data: any) {
    this.order_id = data.order_id;
    this.user_id = data.user_id;
    this.total_price = data.total_price;
    this.order_date = data.order_date;
    this.status = data.status;
    this.cart_items = data.cart_items;
    this.shipping_method = data.shipping_method;
    this.shipping_address = data.shipping_address;
    this.shipping_date = data.shipping_date;
    this.payment_method = data.payment_method;
    this.coupon_code = data.coupon_code;
  }
}