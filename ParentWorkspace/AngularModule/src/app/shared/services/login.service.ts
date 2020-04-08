import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import { UserService } from './user.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  userAuth = "/base/userAuthentication";
  createUser = "/base/createUser";
  sendVerificationCode = "/base/sendVerification";
  updatePass = "/base/updatePassword";
  userDetails = "/user/userDetail";
  googleRedirectUrl = "/social/googleLogin";
  
  constructor(private http: HttpClient, private router: Router) { }

  userLogin(email, pass) : Observable<any>{
    const body = { emailId: email, password: pass };
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };
    return this.http.post(environment.backendHost+this.userAuth, body, httpOptions);  
  }

  getGoogleRedirectUrl() : Observable<any>{
    return this.http.get(environment.backendHost+this.googleRedirectUrl);
  }

  getUserDetailFromToken(token:string) : Observable<any>{
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': token,
      }),
    };
    return this.http.get(environment.backendHost+this.userDetails, httpOptions);
  }

  createuser(body:any) : Observable<any>{
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };
    return this.http.post(environment.backendHost+this.createUser, body, httpOptions);  
  }
    
  sendCode(email:string): Observable<any>{
    return this.http.get(environment.backendHost+this.sendVerificationCode, {params:{ emailId : email }});
  }
  
  updatePassword(body:any) : Observable<any>{
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };
    return this.http.post(environment.backendHost+this.updatePass, body, httpOptions);  
  }

}
