import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/shared/services/login.service';
import { CookieService } from 'ngx-cookie-service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-redirect',
  templateUrl: './redirect.component.html',
  styleUrls: ['./redirect.component.css']
})
export class RedirectComponent implements OnInit {

  loading:boolean = true;

  constructor(private loginService:LoginService, private user:UserService,
    private cookieService: CookieService) { }

  ngOnInit() {
    console.log("inside redirect");
    let url: String = window.location.href;
    let token:string = this.getParamValueQueryString('token',url);
    this.loginService.getUserDetailFromToken(token)
        .subscribe(
          (response)=>{
            if(response.statusCode == 200){
              console.log("Success");
              let userData:any = response.data;
              this.cookieService.set("userName", userData.firstName);
              this.cookieService.set("userId", userData.userId);
              this.user.setToken(this.cookieService.get("token"));
              this.user.setUserDetails(userData.userId, userData.emailId, userData.mobile,
                  userData.role, userData.firstName, userData.lastName, userData.loginVia, userData.active);
            }
            this.loading = false;
          },
          (error) => {
            this.loading = false;
            alert("something went wrong!");
          }
        );
        this.sleep(3000);
  }

  getParamValueQueryString( paramName , url) {
    let paramValue = new URL(url).searchParams;
    return paramValue.get(paramName);
  }

  sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }

}
