import { Component, OnInit } from '@angular/core';
import { SwiggyServiceService } from '../swiggy-service.service';
import {Food} from '../food';

@Component({
  selector: 'app-food-items',
  templateUrl: './food-items.component.html',
  styleUrls: ['./food-items.component.css']
})
export class FoodItemsComponent implements OnInit {
  fooditems: Array<any>;
  food:Food=new Food();
  quantity:any;
  inputNumber:Array<Number>;
  quan:any;
  constructor(private foodservice: SwiggyServiceService) {
    this.inputNumber=new Array<Number>();
   }

  ngOnInit() {
    this.foodservice.getFoodItems().subscribe(data => {
      this.fooditems = data;
    });
  }
addtoCart(foodSelected)
{
 this.quantity=this.inputNumber[foodSelected.id];
 console.log(this.quantity);
 this.food.quantity=this.quantity;
 this.food.id=foodSelected.id;
 this.food.description=foodSelected.description;
 this.food.name=foodSelected.name;
 this.food.price=foodSelected.price;
 this.food.image=foodSelected.image;
 console.log(this.food);
 this.foodservice.addtoCart(this.food).subscribe(data=>{
  this.ngOnInit();
  console.log(data);
 
 });

}

disableButton(food)
{
  this.quan=this.inputNumber[food.id]; 
  if(this.quan>food.quantity)
  return false;
  else if(this.quan>0)
  return true;
  else
  return false;


}


}
