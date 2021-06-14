import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BrandComponent } from './brand/brand.component';
import { CartComponent } from './cart/cart.component';
import { CategoryComponent } from './category/category.component';
import { ProfileComponent } from './customer-profile/customerprofile.component';


import { HomeComponent } from './home/home.component';
import { OrderDetailComponent } from './orderdetail/orderdetail.component';
import { ProductByBrandComponent } from './product-by-brand/productbybrand.component';
import { ProductByCategoryComponent } from './product-by-category/productbycategory.component';
import { ProductComponent } from './product/product.component';
import { ReceiptComponent } from './receipt/receipt.component';
import { RegisterComponent } from './register/register.component';
import { UserComponent } from './user.component';

const routes: Routes = [
    { 
        path: '', 
        component: UserComponent, 
        children: [
            { path: '', component:HomeComponent},
            { path: 'brand', component: BrandComponent },
            { path: 'cart', component: CartComponent },
            { path: 'profile', component: ProfileComponent },
            { path: 'receipt', component: ReceiptComponent },
            { path: 'register', component: RegisterComponent },
            { path: 'product', component: ProductComponent },
            { path: 'productbybrand', component: ProductByBrandComponent },
            { path: 'category', component: CategoryComponent },
            { path: 'productbycategory', component: ProductByCategoryComponent },
            { path: 'orderdetail', component: OrderDetailComponent },
        ]
    }
];
export const UserRouting = RouterModule.forRoot(routes)