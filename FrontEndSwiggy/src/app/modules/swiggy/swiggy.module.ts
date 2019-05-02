import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import {SwiggyRouterModule} from './swiggy-router.module';
import {SwiggyServiceService} from './swiggy-service.service';
import { CommonModule } from '@angular/common';
import { FoodItemsComponent } from './food-items/food-items.component';
import { CartComponent } from './cart/cart.component';
import { CorouselComponent } from './corousel/corousel.component';
import { HomeComponent } from './home/home.component';
import {TokenInterceptorService} from './token-interceptor.service';
import { FormsModule, } from '@angular/forms';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { OrderplacedComponent } from './orderplaced/orderplaced.component';
@NgModule({
  declarations: [ 
    FoodItemsComponent, 
    CartComponent, 
    CorouselComponent, 
    HomeComponent, OrderplacedComponent],

  imports: [
    CommonModule,
    HttpClientModule,
    SwiggyRouterModule,
    FormsModule,
    NgbModule
  ],

  exports: [
   SwiggyRouterModule
  ],

  providers: [SwiggyServiceService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    }
  ]
})

export class SwiggyModule { }
