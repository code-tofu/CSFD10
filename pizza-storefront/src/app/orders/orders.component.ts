import { Component, OnInit, inject } from '@angular/core';
import { PizzaService } from '../pizza.service';
import { firstValueFrom } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { PendingOrder } from '../order.model';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css'],
})
export class OrdersComponent implements OnInit {
  pizzaSvc = inject(PizzaService);
  actRoute = inject(ActivatedRoute);
  email: string = '';
  orders: PendingOrder[] = [];

  ngOnInit() {
    this.email = this.actRoute.snapshot.params['email'];
    firstValueFrom(this.pizzaSvc.getOrders(this.email))
      .then((resp) => {
        for (let i = 0; i < resp.length; i++) {
          let order: PendingOrder = {
            date: new Date(+resp[i]['date']),
            orderId: resp[i]['orderId'],
            total: resp[i]['total'],
          };
          this.orders.push(order);
        }
      })
      .catch((err) => {
        console.log(err);
        alert('Email Does Not Exist');
      });
  }
}
