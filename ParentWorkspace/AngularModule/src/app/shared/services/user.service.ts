import { Injectable } from '@angular/core';

export interface cart{
  itemId:number,
  itemName:string,
  quantity:number,
  cost:number
}

@Injectable({
  providedIn: 'root'
})
export class UserService {

  userId:number;
  emailId:string;
  mobile:string;
  role:string;
  firstName:string;
  lastName:string;
  loginVia:string;
  active:boolean;
  token:string;

  cartItems: cart[] = [];

  constructor() { }

  public setToken(token:string){
    this.token = token;
  }

  public setUserDetails(userId, emailId, mobile, role, firstName, lastName, loginVia, active){
    this.userId = userId;
    this.emailId = emailId;
    this.mobile = mobile;
    this.role = role;
    this.firstName = firstName;
    this.lastName = lastName;
    this.loginVia = loginVia;
    this.active = active;
  }

}
