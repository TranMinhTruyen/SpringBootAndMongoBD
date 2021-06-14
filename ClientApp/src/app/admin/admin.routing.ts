import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminAccountComponent } from './admin-account/admin-account.component';
import { AdminBrandComponent } from './admin-brand/admin-brand.component';
import { AdminCategoryComponent } from './admin-category/admin-category.component';
import { AdminCustomerAccountComponent } from './admin-customer-account/admin-customer-account.component';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { AdminOrderComponent } from './admin-order/admin-order.component';
import { AdminProductsComponent } from './admin-products/admin-products.component';
import { AdminComponent } from './admin.component';

const routes: Routes = [
    { 
        path: 'admin',
        component: AdminComponent, 
        children: [
            { path: '', component: AdminHomeComponent },
            { path: 'brand', component: AdminBrandComponent },
            { path: 'category', component: AdminCategoryComponent },
            { path: 'order', component: AdminOrderComponent },
            { path: 'products', component: AdminProductsComponent },
            { path: 'account', component: AdminAccountComponent },
            { path: 'customeraccount', component: AdminCustomerAccountComponent },
        ]
    }
];
export const AdminRouting = RouterModule.forRoot(routes)