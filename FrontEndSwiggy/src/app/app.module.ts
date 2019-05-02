import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import {AuthenticationModule} from './modules/authentication/authentication.module';
import {SwiggyModule} from './modules/swiggy/swiggy.module';


import { HttpClientModule } from '@angular/common/http';
import { AuthGuardService } from './auth-guard.service';



const appRoutes: Routes =[
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  }
]


@NgModule({
  declarations: [
    AppComponent,
  
  ],
  imports: [
    BrowserModule,
    AuthenticationModule,
    SwiggyModule,
      HttpClientModule,
      RouterModule.forRoot(appRoutes)
  ],
  providers: [AuthGuardService],
  bootstrap: [AppComponent]
})
export class AppModule { }
