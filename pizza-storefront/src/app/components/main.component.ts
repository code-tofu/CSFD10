import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PizzaService } from '../pizza.service';
import { firstValueFrom } from 'rxjs';
import { Order } from '../order.model';

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

  orderForm!: FormGroup;

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
      toppings: this.fb.control<string>('', [Validators.required]),
      comments: this.fb.control<string>(''),
      //TODO: Change toppings value to value instead of boolean
    });
  }

  updateSize(size: string) {
    this.pizzaSize = SIZES[parseInt(size)];
  }

  order() {
    console.info('OrderForm:', this.orderForm.value);
    firstValueFrom(this.pizzaSvc.placeOrder(this.orderForm.value as Order))
      .then((resp) => {
        console.log(resp);
        alert('Order Successful');
        this.orderForm.reset();
        //navigate
      })
      .catch((err) => {
        console.log(err);
        alert('Order Failed:' + err);
      });
  }
}
function then(arg0: (resp: any) => void) {
  throw new Error('Function not implemented.');
}
