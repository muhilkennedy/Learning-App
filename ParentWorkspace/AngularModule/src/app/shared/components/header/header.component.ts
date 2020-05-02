import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  username : string;
  appName : string;

  itemCount : number ;

  constructor(private cookieService:CookieService, public userSerive: UserService) { }

  ngOnInit() {
    this.username = '';
    this.appName = this.cookieService.get("appName");
    this.itemCount = this.userSerive.cartItems.length;
  }

  toggle(){
    
  }

  cart(){
    
  }
}
