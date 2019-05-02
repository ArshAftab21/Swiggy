import { Component, OnInit } from '@angular/core';
import { SwiggyServiceService } from '../swiggy-service.service';
import {Food} from '../food';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
cartitems:Array<any>=new Array<any>();
cost:Number;
food:Food=new Food();
inputNumber:Array<Number>;
quantity:any;
quan:any;

  constructor(private cartservice: SwiggyServiceService,private router:Router) {
    this.inputNumber=new Array<Number>();
   }

  ngOnInit() {
    this.cartservice.getCartItems().subscribe(data => {
      this.cartitems = data;

    });
    this.cartservice.getTotalCost().subscribe(tcost => {
      this.cost = tcost;
    });
  }

  displayCartEmpty()
  {
    if(this.cartitems.length==0)
    {
      return true;
    }
    else{
      false;
    }
  }

removeFromCart(foodSelected)
{
  this.quantity=this.inputNumber[foodSelected.id];
  this.food.quantity=this.quantity;
  this.food.id=foodSelected.id;
  this.food.name=foodSelected.name;
  this.food.price=foodSelected.price;
  this.food.description=foodSelected.description;
  this.food.image=foodSelected.image;
 
  console.log(this.food);
  this.cartservice.deleteFromCart(this.food).subscribe(data=>{
 console.log();
   this.ngOnInit();
 }); 
}

placeOrder()
{
  this.cartservice.placeOrder().subscribe(data=>{
    console.log(data);
    this.router.navigate(['/orderplaced']);   
    });
}

disableButton(cart)
{
  this.quan=this.inputNumber[cart.id]; 
  if(this.quan>cart.quantity)
  return false;
  else if(this.quan>0)
  return true;
  else
  return false;


}






}
