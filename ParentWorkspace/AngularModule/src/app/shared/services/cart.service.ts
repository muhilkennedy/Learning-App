import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  getCartItems = "/user/getCartItems";
  createCartItems = "/user/insertItemInCart";
  removeCartItems = "/user/removeCartItem";

  constructor(private http: HttpClient, private userService: UserService) { }

  getCartForUser() : Observable<any>{
    const httpOptions = {
      headers: { 'Authorization': this.userService.token },
      params: {'email': this.userService.emailId} // need to be removed kept for ease of dev only else
    };
    return this.http.get(environment.backendHost+this.getCartItems, httpOptions);
  }

  addCartForUser(body:any) : Observable<any>{
    const httpOptions = {
      headers: { 'Authorization': this.userService.token },
      params: {'email': this.userService.emailId} // need to be removed kept for ease of dev only else
    };
    return this.http.post(environment.backendHost+this.createCartItems, body, httpOptions);
  }

  removeCartItem(itemId) : Observable<any>{
    const httpOptions = {
      headers: { 'Authorization': this.userService.token },
      params: {'email': this.userService.emailId,// need to be removed kept for ease of dev only else
               'itemId' : itemId}
    };
    return this.http.get(environment.backendHost+this.getCartItems, httpOptions);
  }
}
