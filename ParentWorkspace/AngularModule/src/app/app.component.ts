import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'AngularModule';

  constructor(private http: HttpClient, private router: Router){}
  
  ngOnInit(){
    this.http.get(environment.backendHost+"/base/ping")
        .subscribe((data)=>{
          console.log("Connection to Backend successfull"+data);
          this.router.navigate(['/home']);
        },
        (error)=>{
          alert("something went wrong!");
          console.log("Connection to Backend Failed");
        })
  }
}
