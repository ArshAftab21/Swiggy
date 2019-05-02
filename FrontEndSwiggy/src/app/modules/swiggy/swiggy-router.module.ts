import { NgModule } from '@angular/core';
import { RouterModule, Routes} from '@angular/router';
import { FoodItemsComponent } from './food-items/food-items.component';
import { CartComponent } from './cart/cart.component';
import {HomeComponent} from './home/home.component';
import {AuthGuardService} from '../../auth-guard.service';
import {OrderplacedComponent} from './orderplaced/orderplaced.component'
const swiggyRouter: Routes = [
   { path: 'fooditems', component: FoodItemsComponent,canActivate:[AuthGuardService], },
  { path: 'cart', component: CartComponent,canActivate:[AuthGuardService] },
    { path: 'home', component: HomeComponent },
    {path: 'orderplaced',component: OrderplacedComponent},
  { path: '', redirectTo: '/home', pathMatch: 'full' },
];
@NgModule({
  imports: [
    RouterModule.forChild(swiggyRouter)
  ],
  exports: [
    RouterModule
  ]
})
export class SwiggyRouterModule { }
