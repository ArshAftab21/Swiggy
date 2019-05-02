import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';
import { AuthserviceService } from './modules/authentication/authservice.service';
import { Router } from '@angular/router';



@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate{

  constructor(private authService: AuthserviceService, private router: Router) { }
  canActivate() {

    if(!this.authService.isTokenExpired()) {
      console.log(("token not expired"));
      
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }

}
