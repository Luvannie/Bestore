import { Address } from "./address";
import { Customer } from "./customer";
import { Order } from "./order";
import { OrderItem } from "./order-item";

export class Purchase {
    customer: Customer | undefined;
    shippingAddress: Address | undefined;
    order: Order | undefined;
    orderItems: OrderItem[] | undefined;
    user_id: number | undefined;
    shipping_method: string| undefined;
    payment_method: string| undefined;
    // coupon_code: string| undefined;

    
}
