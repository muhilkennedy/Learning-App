import { Component, OnInit, Inject, ViewChild, ElementRef } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { LoginService } from '../../services/login.service';
import { UserService } from '../../services/user.service';
import { CookieService } from 'ngx-cookie-service';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-dialog-overview',
  templateUrl: './dialog-overview.component.html',
  styleUrls: ['./dialog-overview.component.css']
})
export class DialogOverviewComponent implements OnInit {

  @ViewChild('username') uname: ElementRef;
  @ViewChild('password') pass: ElementRef;

  @ViewChild('reg_fname') fname: ElementRef;
  @ViewChild('reg_lname') lname: ElementRef;
  @ViewChild('reg_email') email: ElementRef;
  @ViewChild('reg_pass1') pass1: ElementRef;
  @ViewChild('reg_pass2') pass2: ElementRef;
  @ViewChild('reg_mobile') mobile: ElementRef;

  @ViewChild('forget_email') forget_email: ElementRef;
  @ViewChild('forget_code') forget_code: ElementRef;
  @ViewChild('forgot_password') forgot_password: ElementRef;

  errorMsg:string;
  tempVariable:string;
  hasError:boolean = false;
  loading:boolean = false;
  isSignInDialog:boolean = true;
  isSignUpDialog:boolean = false;
  isForgotDialog1:boolean = false;
  isForgotDialog2:boolean = false;
  
  constructor(
    public loginService: LoginService,
    private user:UserService,
    private cookieService: CookieService,
    private cartService: CartService,
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
              let userData:any = response.dataList[0];
              this.cookieService.set("userName", userData.firstName);
              this.cookieService.set("userId", userData.userId);
              this.user.setToken(response.data.token);
              this.user.setUserDetails(userData.userId, userData.emailId, userData.mobile,
                  userData.role, userData.firstName, userData.lastName, userData.loginVia, userData.active);
              this.cartService.getCartForUser()
                  .subscribe((response:any)=>{
                    if(response.statusCode == 200){
                      this.user.cartItems = response.dataList;
                    }
                  },
                  (error)=>{
                    alert("Failed to load cart Items");
                  });
              this.dialogRef.close();
            }
            else{
              this.errorMsg = response.errorMessages[0];
              this.hasError = true;
            }
            this.loading = false;
          },
          (error)=>{
            alert("something went wrong!");
            console.log("Connection to Backend Failed");
            this.dialogRef.close();
          });
    }
  }

  redirectToGoogleLogin(){
    this.loginService.getGoogleRedirectUrl()
        .subscribe((response:any)=>{
          if(response.statusCode == 200){
            window.location.href = response.url;
          }
        },
        (error)=>{
          alert("somthing went wrong");
        });
  }

  redirectToFaceBookLogin(){
    
  }

  registerUser(){
    this.isSignUpDialog = true;
    this.isSignInDialog = false;
    this.isForgotDialog1 = false;
    this.isForgotDialog2 = false;
  }

  forgotUser(){
    this.isSignUpDialog = false;
    this.isSignInDialog = false;
    this.isForgotDialog1 = true;
    this.isForgotDialog2 = false;
  }

  register(){
    //proper form checks has to be implemented
    this.hasError = false;
    this.errorMsg = '';
    if(this.fname.nativeElement.value === ''){
      this.hasError = true;
      this.errorMsg = "First name cannot be Empty";
      return;
    }
    if(this.lname.nativeElement.value === ''){
      this.hasError = true;
      this.errorMsg = "Last name cannot be Empty";
      return;
    }
    if(this.email.nativeElement.value === ''){
      this.hasError = true;
      this.errorMsg = "Email ID cannot be Empty";
      return;
    }
    if(this.pass1.nativeElement.value === '' || this.pass2.nativeElement.value === ''){
      this.hasError = true;
      this.errorMsg = "Password cannot be Empty";
      return;
    }
    if(this.pass1.nativeElement.value != this.pass2.nativeElement.value){
      this.hasError = true;
      this.errorMsg = "Passwords Do Not Match";
      return;
    }
    let body = {
      emailId : this.email.nativeElement.value,
      password : this.pass1.nativeElement.value,
      mobile : this.mobile.nativeElement.value,
      firstName : this.fname.nativeElement.value,
      lastName : this.lname.nativeElement.value
    }
    this.loading = true;
    this.loginService.createuser(body)
        .subscribe((response:any)=>{
          if(response.statusCode == 200){
            //snack-bars from angular material has to be implemented.
            alert("user creation successfull!")
          }
          else{
            alert("user creation failed!");
          }
          this.loading = false;
          this.isSignUpDialog = false;
          this.isSignInDialog = true;
        },
        (error)=>{
          alert("user creation failed!");
        });
  }

  sendCode(){
    this.hasError = false;
    if(this.forget_email.nativeElement.value === ''){
      this.hasError = true;
      this.errorMsg = "Email ID cannot be Empty";
      return;
    }
    this.loading = true;
    this.loginService.sendCode(this.forget_email.nativeElement.value)
      .subscribe((response:any)=>{
        if(response.statusCode == 200){
          this.isForgotDialog1 = false;
          this.isForgotDialog2 = true;
          this.tempVariable = this.forget_email.nativeElement.value;
        }
        else{
          alert("sending email failed");
        }
        this.loading = false;
      },
      (error)=>{
        alert("somthing went wrong");
      });
  }

  updatePassword(){
    this.hasError = false;
    if(this.forget_code.nativeElement.value === ''){
      this.hasError = true;
      this.errorMsg = "Verification Code cannot be Empty";
      return;
    }
    if(this.forgot_password.nativeElement.value === ''){
      this.hasError = true;
      this.errorMsg = "Password cannot be Empty";
      return;
    }
    this.loading = true;
    let body = {
      emailId : this.tempVariable,
      password : this.forgot_password.nativeElement.value,
      verification : { code : this.forget_code.nativeElement.value }
    }
    this.loginService.updatePassword(body)
      .subscribe((response:any)=>{
        if(response.statusCode == 200){
          this.isForgotDialog1 = false;
          this.isForgotDialog2 = false;
          this.isSignInDialog = true;
        }
        else{
          alert("verification failed");
        }
        this.loading = false;
      },
      (error)=>{
        alert("something went wrong");
      });
  }

}
