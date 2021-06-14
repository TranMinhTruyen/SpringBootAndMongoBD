import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
    { path: '', redirectTo: '/home', pathMatch: 'full'},    
    { path: 'admin', redirectTo: '/admin', pathMatch: 'full'}
];
export const routing = RouterModule.forRoot(routes)