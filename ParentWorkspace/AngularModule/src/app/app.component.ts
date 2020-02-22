import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, RouterStateSnapshot, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'UI';
  username: string = null;
  email: string = null;
  status: string = '0';
  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(){
    console.log("onnit");
    let url: String = window.location.href;
    this.username = this.getParamValueQueryString('username',url);
    this.email = this.getParamValueQueryString('email',url);
    this.status = this.getParamValueQueryString('status',url);
    if(this.status === '0'){
      this.router.navigate(['/login']);
    }
    else if(this.status === '1'){
      this.router.navigate(['/home']);
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
