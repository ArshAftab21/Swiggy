import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Food} from './food';


@Injectable({
  providedIn: 'root'
})
export class SwiggyServiceService {

  constructor(private http: HttpClient) { }
  getFoodItems(): Observable<any> {
  return this.http.get('http://localhost:8080/getFoodItems');
}
 addtoCart(food)
 {
 
   return this.http.post("http://localhost:8080/add",food,{responseType: 'text'});
 }
 getCartItems(): Observable<any> {
  return this.http.get('http://localhost:8080/getCartItems');
}
getTotalCost(): Observable<any> {
  return this.http.get('http://localhost:8080/totalCost');

}
deleteFromCart(cart)
 {
 
   return this.http.post("http://localhost:8080/delete",cart,{responseType: 'text'});
 }
 placeOrder(): Observable<any> {
  return this.http.post('http://localhost:8080/placeOrder',{responseType: 'text'});

}
}
