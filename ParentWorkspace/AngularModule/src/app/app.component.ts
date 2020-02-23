import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, RouterStateSnapshot, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'UI';
  email: any = null;
  status: string = '0';
  constructor(private http: HttpClient, private router: Router, private cookieService: CookieService) { }

  ngOnInit(){
    console.log("onnit");
    let url: String = window.location.href;
    this.email = this.getParamValueQueryString('email',url);
    this.status = this.getParamValueQueryString('status',url);
    if(this.status === '0'){
      this.router.navigate(['/home']);
    }
    else if(this.status === '1'){
      this.cookieService.set('useremail',this.email);
      this.http.get(environment.userData+"email="+this.email)
                .subscribe((data)=>{
                  this.cookieService.set('userDetail',JSON.parse(JSON.stringify(data)));
                  this.router.navigate(['/home']);
                },
                (error)=>{
                  console.log(error);
                })
    }
    else if(this.status === '2'){
      this.router.navigate(['/notfound']);
    }
  }

  ngOnDestroy(){
    console.log("left app component");
  }

  getParamValueQueryString( paramName , url) {
    let paramValue = new URL(url).searchParams;
    return paramValue.get(paramName);
  }

}
