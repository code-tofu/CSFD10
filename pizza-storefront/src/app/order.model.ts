export interface Order {
  name: string;
  email: string;
  size: string;
  crust: string;
  sauce: string;
  toppings: string[];
  comments: string;
}
export interface PendingOrder {
  date: Date;
  orderId: string;
  total: number;
}
