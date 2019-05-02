import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { AuthserviceService } from '../authservice.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
message:String=null;
user:User = new User();

  constructor(private authService: AuthserviceService, private router: Router) { }

  ngOnInit() {
  }
  loginUser() {
    console.log("Login user", this.user);
    this.authService.loginUser(this.user).subscribe(data => {
        if(data['token']) {
        console.log("Login successful");
        this.authService.setToken(data['token']);
        this.router.navigate(['/fooditems']);
      }
      else{
        console.log(data);
        this.message=(data['message']);
      }
 });
  }
checkMessage()
{
  if(this.message==null)
  {
    return true;
  }
  else
  {
    return false;
  }
}

}
