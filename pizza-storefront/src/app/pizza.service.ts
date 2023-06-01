import { HttpClient } from '@angular/common/http';
import { inject } from '@angular/core';
import { Order } from './order.model';

const uploadAPIURL: string = 'api/';

export class PizzaService {
  // TODO: Task 3 //should this be task 4?
  // You may add any parameters and return any type from placeOrder() method
  // Do not change the method name
  http = inject(HttpClient);
  placeOrder(order: Order) {
    console.info('Posting', order);
    return this.http.post<any>(uploadAPIURL + 'order', JSON.stringify(order));
  }

  // TODO: Task 5
  // You may add any parameters and return any type from getOrders() method
  // Do not change the method name
  getOrders() {}

  // TODO: Task 7
  // You may add any parameters and return any type from delivered() method
  // Do not change the method name
  delivered() {}
}
