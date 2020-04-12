import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  username : string;
  appName : string;

  constructor(private cookieService:CookieService) { }

  ngOnInit() {
    this.username = '';
    this.appName = this.cookieService.get("appName");
  }

  toggle(){
    
  }

  cart(){
    
  }
}
