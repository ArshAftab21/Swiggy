import { NgModule } from '@angular/core';
import { RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
const authRouter: Routes = [
  {
    path:'',
    children: [
      {
        path: '',
        redirectTo: '/home',
        pathMatch: 'full'
      },
      {
        path: 'register',
        component: RegisterComponent,
      },
      {
        path: 'login',
        component: LoginComponent,
      }
    ]
  }
];
@NgModule({
  imports: [
    RouterModule.forChild(authRouter)
  ],
  exports: [
    RouterModule
  ]
})
export class AuthenticationRouterModule { }
