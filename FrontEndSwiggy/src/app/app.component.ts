import { Component } from '@angular/core';
import {AuthserviceService} from '../app/modules/authentication/authservice.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'Swiggy';

  constructor(private authservice:AuthserviceService, private router: Router){}
 


logout() {
  console.log('In logout');
  this.authservice.deleteToken();

  this.router.navigate(['/home']);
}

  
}
