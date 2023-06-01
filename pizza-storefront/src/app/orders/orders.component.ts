import { Component, OnInit, inject } from '@angular/core';
import { PizzaService } from '../pizza.service';
import { firstValueFrom } from 'rxjs';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css'],
})
export class OrdersComponent implements OnInit {
  pizzaSvc = inject(PizzaService);
  actRoute = inject(ActivatedRoute);
  email: string = '';

  ngOnInit() {
    this.email = this.actRoute.snapshot.params['email'];
    firstValueFrom(this.pizzaSvc.getOrders(this.email))
      .then((resp) => {})
      .catch((err) => {
        console.log(err);
        alert('Email Does Not Exist');
      });
  }
}
