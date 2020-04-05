import { Component, OnInit, Inject, ViewChild, ElementRef } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { LoginService } from '../../services/login.service';
import { UserService } from '../../services/user.service';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-dialog-overview',
  templateUrl: './dialog-overview.component.html',
  styleUrls: ['./dialog-overview.component.css']
})
export class DialogOverviewComponent implements OnInit {

  @ViewChild('username') uname: ElementRef;
  @ViewChild('password') pass: ElementRef;

  errorMsg:string;
  hasError:boolean = false;
  loading:boolean = false;
  
  constructor(
    public loginService: LoginService,
    private user:UserService,
    private cookieService: CookieService,
    public dialogRef: MatDialogRef<DialogOverviewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: null) {}

  ngOnInit() {
  }

  hasErrorMsg(){
    return this.hasError;
  }

  login(){
    console.log("inside login");
    this.hasError = false;
    this.errorMsg = '';
    if(this.uname == undefined || this.uname.nativeElement.value === ''){
      this.errorMsg = "Email is Required";
    }
    if(this.pass == undefined || this.pass.nativeElement.value === ''){
      this.errorMsg = "Password is Required";
    }
    if(this.errorMsg != undefined && this.errorMsg.length > 1){
      this.hasError = true;
    }
    else{
      this.loading = true;
      this.loginService.userLogin(this.uname.nativeElement.value, this.pass.nativeElement.value)
          .subscribe((response:any)=>{
            if(response.statusCode == 200){
              console.log("Success");
              let userData:any = response.dataList[0];
              this.cookieService.set("userName", userData.firstName);
              this.cookieService.set("userId", userData.userId);
              this.user.setToken(response.data.token);
              this.user.setUserDetails(userData.userId, userData.emailId, userData.mobile,
                  userData.role, userData.firstName, userData.lastName, userData.loginVia, userData.active);
            }
            else{
              this.errorMsg = response.errorMessages[0];
              this.hasError = true;
            }
            this.loading = false;
            this.dialogRef.close();
          },
          (error)=>{
            alert("something went wrong!");
            console.log("Connection to Backend Failed");
            this.dialogRef.close();
          });
    }
  }

  redirectToGoogleLogin(){

  }

}
