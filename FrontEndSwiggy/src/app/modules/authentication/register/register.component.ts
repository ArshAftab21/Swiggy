import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { AuthserviceService } from '../authservice.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  user: User;
  value:String='';
  constructor(private authService: AuthserviceService,private router: Router) {
    this.user=new User();
   }

  ngOnInit() {
  }

registerUser() {
    console.log("Register User data:",this.user);
    this.authService.registerUser(this.user).subscribe(data => {
      console.log("User registered", data);
      this.router.navigate(['/login']);
    });
  }
  onKey(event: any) { 
    this.authService.checkUser(this.user).subscribe(data => {
      
      this.value=data;
     
    });
  }

  displayError()
  {
    if(this.value=='User already exists')
    return true;
    else
    return false;
  }

  displayRegisterButton()
  {
    if(this.value=='User already exists')
    return false;
    else if((this.user.password==null)||(this.user.phoneNumber==null)||(this.user.email==null))
    return false;
    else
    return true;   
  }


}
