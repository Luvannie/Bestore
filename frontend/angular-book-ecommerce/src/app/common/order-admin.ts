import { OrderItem } from "./model/order-item";

export interface OrderAdmin {
    orderId: number;
    userId: number;
    totalPrice: number;
    orderDate: Date;
    status: string;
    shippingMethod: string;
    shippingAddress: string;
    shippingDate: Date;
    paymentMethod: string;
    customerId: number;
    trackingNumber: string;
    customerName: string;
    customerEmail: string;
    customerPhone: string;
    shippingCountry: string;
    shippingCity: string;
    shippingDistrict: string;
    orderItems: OrderItem[];
    dateCreated: Date;
    is_active: boolean;
}