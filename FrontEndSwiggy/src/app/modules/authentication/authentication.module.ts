import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import {AuthserviceService} from './authservice.service';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AuthenticationRouterModule } from './/authentication-router.module';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';

@NgModule({
  declarations: [LoginComponent, RegisterComponent],
  imports: [
      CommonModule,
    HttpClientModule,
    AuthenticationRouterModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [AuthserviceService],
  exports: [
    AuthenticationRouterModule,
  ]

})
export class AuthenticationModule { }
