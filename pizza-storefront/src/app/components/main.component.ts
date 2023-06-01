import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PizzaService } from '../pizza.service';
import { firstValueFrom } from 'rxjs';
import { Order } from '../order.model';
import { Router } from '@angular/router';

const SIZES: string[] = [
  'Personal - 6 inches',
  'Regular - 9 inches',
  'Large - 12 inches',
  'Extra Large - 15 inches',
];

const PIZZA_TOPPINGS: string[] = [
  'chicken',
  'seafood',
  'beef',
  'vegetables',
  'cheese',
  'arugula',
  'pineapple',
];

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css'],
})
export class MainComponent {
  pizzaSize = SIZES[0];

  constructor() {}
  fb = inject(FormBuilder);
  pizzaSvc = inject(PizzaService);
  router = inject(Router);

  orderForm!: FormGroup;
  toppings!: FormGroup;

  ngOnInit(): void {
    this.orderForm = this.createForm();
  }

  createForm(): FormGroup {
    return this.fb.group({
      name: this.fb.control<string>('', [Validators.required]),
      email: this.fb.control<string>('', [
        Validators.required,
        Validators.email,
      ]),
      size: this.fb.control<number>(0, [Validators.required]),
      base: this.fb.control<string>('', [Validators.required]),
      sauce: this.fb.control<string>('', [Validators.required]),
      comments: this.fb.control<string>(''),
      toppings: this.createToppings(), //default no selection
    });
  }

  createToppings(): FormGroup {
    return this.fb.group({
      chicken: this.fb.control<boolean>(false),
      seafood: this.fb.control<boolean>(false),
      beef: this.fb.control<boolean>(false),
      vegetables: this.fb.control<boolean>(false),
      cheese: this.fb.control<boolean>(false),
      arugula: this.fb.control<boolean>(false),
      pineapple: this.fb.control<boolean>(false),
    });
  }

  updateSize(size: string) {
    this.pizzaSize = SIZES[parseInt(size)];
  }

  order() {
    console.info('>>OrderForm:', this.orderForm.value);
    let newOrder: Order = {
      name: this.orderForm.value.name,
      email: this.orderForm.value.email,
      sauce: this.orderForm.value.sauce,
      size: this.orderForm.value.size,
      comments: this.orderForm.value.comments,
      toppings: this.parseToppings(this.orderForm.value.toppings),
      crust: this.orderForm.value.base,
    };

    console.info('>>NewOrder:', newOrder);
    firstValueFrom(this.pizzaSvc.placeOrder(newOrder))
      .then((resp) => {
        console.log(resp);
        alert('Order Successful' + JSON.stringify(resp));
        this.orderForm.reset();
        this.router.navigate(['/orders', newOrder.email]);
      })
      .catch((err) => {
        console.log(err);
        alert('Order Failed:' + err);
      });
  }

  parseToppings(toppingsObj: any): string[] {
    let toppingsArr: string[] = [];
    let toppings = Object.keys(toppingsObj);
    for (let i = 0; i < toppings.length; i++) {
      if (toppingsObj[toppings[i]]) {
        toppingsArr.push(toppings[i]);
      }
    }
    console.info(toppingsArr);
    return toppingsArr;
  }
}
