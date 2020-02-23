import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private auth: AuthenticationService, private cookieService: CookieService, private router: Router) { }

  ngOnInit() {
    if(this.cookieService.get('userDetail') != null|| this.cookieService.get('userDetail') != undefined){
      console.log("userdata->",this.cookieService.get('userDetail'));
    }
  }

  loginPage(){
    this.router.navigate(["/login"]);
  }

}
