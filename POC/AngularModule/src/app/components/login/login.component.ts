import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, RouterStateSnapshot } from '@angular/router';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private http: HttpClient, private route: ActivatedRoute, private authService : AuthenticationService) { }

  ngOnInit() {
  }

  redirectToGoogleLogin(){
    return this.authService.getGoolgeURL()
                            .subscribe((data) => {
                                var response = JSON.parse(JSON.stringify(data));
                                if(response.statusCode === 200){
                                  window.location.href = response.url;
                                }
                              },
                              (error) => {
                                console.log("ERROR : ",error);
                            });
    // return this.http.get("http://localhost:8080/googleLogin")
    //                   .subscribe((data) => {
    //                     console.log("inside map",data);
    //                     var myJSON = JSON.stringify(data);
    //                     var obj = JSON.parse(myJSON);
    //                     if(obj.statusCode === 200){
    //                       window.location.href = obj.url;
    //                     }
    //                   },
    //                   (error) => {
    //                     console.log("inside error map",error);
    //                   })
                      
  }

}
