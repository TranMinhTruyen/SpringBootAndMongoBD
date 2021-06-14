import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { AdminComponent } from './admin.component';
import { AdminRouting } from './admin.routing';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { AdminBrandComponent } from './admin-brand/admin-brand.component';
import { AdminCategoryComponent } from './admin-category/admin-category.component';
import { AdminOrderComponent } from './admin-order/admin-order.component';
import { AdminProductsComponent } from './admin-products/admin-products.component';
import { AdminNavBarComponent } from './admin-navbar/admin-navbar.component';
import { AdminAccountComponent } from './admin-account/admin-account.component';
import { AdminCustomerAccountComponent } from './admin-customer-account/admin-customer-account.component';

@NgModule({
  declarations: [
    AdminComponent,
    AdminHomeComponent,
    AdminBrandComponent,
    AdminCategoryComponent,
    AdminOrderComponent,
    AdminProductsComponent,
    AdminNavBarComponent,
    AdminAccountComponent,
    AdminCustomerAccountComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    AdminRouting
  ],
  providers: [],
})
export class AdminModule { }