import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { MainComponent } from './components/main.component';

import { ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { OrdersComponent } from './orders/orders.component';

const routes: Routes = [
  { path: '', component: MainComponent },
  { path: '/orders/:email', component: OrdersComponent },
  { path: '**', redirectTo: '/', pathMatch: 'full' },
];

@NgModule({
  declarations: [AppComponent, MainComponent, OrdersComponent],
  imports: [BrowserModule, ReactiveFormsModule, RouterModule, HttpClientModule],

  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
