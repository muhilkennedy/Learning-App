import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'AngularModule';

  constructor(private http: HttpClient, private router: Router, private cookieService:CookieService,
              private snackBar: MatSnackBar){}
  
  ngOnInit(){
    this.http.get(environment.backendHost+"/base/ping")
        .subscribe((response:any)=>{
          console.log("Connection to Backend successfull");
          this.cookieService.set("appName",response.data);
          this.router.navigate(['/home']);
        },
        (error)=>{
          this.snackBar.open("Connection to Backend Failed", "ERROR", {
            duration: 20000,
          });
          console.log("Connection to Backend Failed");
        })
  }
}
