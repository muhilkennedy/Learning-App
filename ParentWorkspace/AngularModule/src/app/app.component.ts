import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'AngularModule';

  constructor(private http: HttpClient){}
  
  ngOnInit(){
    this.http.get(environment.backendHost+"/init")
        .subscribe((data)=>{
          console.log("Connection to Backend successfull");
        },
        (error)=>{
          alert("something went wrong!");
          console.log("Connection to Backend Failed");
        })
  }

}
