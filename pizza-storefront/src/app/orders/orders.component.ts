import { Component, OnInit, inject } from '@angular/core';
import { PizzaService } from '../pizza.service';
import { firstValueFrom } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { PendingOrder } from '../order.model';

const months: String[] = [
  'Jan',
  'Feb',
  'Mar',
  'Apr',
  'May',
  'Jun',
  'Jul',
  'Aug',
  'Sep',
  'Oct',
  'Nov',
  'Dec',
];

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css'],
})
export class OrdersComponent implements OnInit {
  pizzaSvc = inject(PizzaService);
  actRoute = inject(ActivatedRoute);
  router = inject(Router);
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

  selectDelivered(e: any) {
    console.info('>>Selected:' + e.target.value);
    firstValueFrom(this.pizzaSvc.delivered(e.target.value))
      .then((resp) => {
        alert('Order ' + e.target.value + ' marked as Delivered');
        this.router.navigate(['/orders', this.email]);
      })
      .catch((err) => {
        console.log(err);
        alert('Order ' + e.target.value + ' Not Found');
      });
  }

  getMonths(month: number) {
    return months[month - 1];
  }
}
