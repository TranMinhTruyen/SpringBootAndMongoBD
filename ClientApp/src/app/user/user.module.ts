import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { UserComponent } from './user.component';
import { UserRouting } from './user.routing';
import { HomeComponent } from './home/home.component';
import { NavMenuComponent } from './nav-menu/nav-menu.component';
import { BrandComponent } from './brand/brand.component';
import { CartComponent } from './cart/cart.component';
import { ProfileComponent } from './customer-profile/customerprofile.component';
import { ReceiptComponent } from './receipt/receipt.component';
import { RegisterComponent } from './register/register.component';
import { ProductComponent } from './product/product.component';
import { ProductByBrandComponent } from './product-by-brand/productbybrand.component';
import { ProductByCategoryComponent } from './product-by-category/productbycategory.component';
import { CategoryComponent } from './category/category.component';
import { OrderDetailComponent } from './orderdetail/orderdetail.component';

@NgModule({
  declarations: [
    UserComponent,
    HomeComponent,
    NavMenuComponent,
    BrandComponent,
    CartComponent,
    ProfileComponent,
    ReceiptComponent,
    RegisterComponent,
    ProductComponent,
    ProductByBrandComponent,
    CategoryComponent,
    ProductByCategoryComponent,
    OrderDetailComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    UserRouting
  ],
  providers: [],
})
export class UserModule { }