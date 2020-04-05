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
  userDeails = "userDetail";
  
  constructor(private http: HttpClient, private router: Router) { }

  userLogin(email, pass) : Observable<any>{
    const headers = { 'Content-Type' : 'application/json' };
    const body = { emailId: email, password: pass };
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };
    return this.http.post(environment.backendHost+this.userAuth, body, httpOptions);  
  }
    

}
