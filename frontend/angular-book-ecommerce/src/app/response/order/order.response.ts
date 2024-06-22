import { OrderItem } from '../../common/model/order-item';


export interface OrderResponse {
    order_id: number;
    user_id: number;
    total_price: number;
    order_date: Date;
    status: string;
    shipping_method: string;
    shipping_address: string;
    shipping_date: Date;
    payment_method: string;
    customer_id: number;
    tracking_number: string;
    customer_name: string;
    customer_email: string;
    customer_phone: string;
    shipping_country: string;
    shipping_city: string;
    shipping_district: string;
    order_items: OrderItem[];
    date_created: Date;
}