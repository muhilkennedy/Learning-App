import { Component, OnInit, Inject } from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { DialogOverviewComponent } from '../dialog-overview/dialog-overview.component';
import { UserService } from '../../services/user.service';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-login-dialog',
  templateUrl: './login-dialog.component.html',
  styleUrls: ['./login-dialog.component.css']
})
export class LoginDialogComponent implements OnInit {

  username:string = '';

  constructor(public dialog: MatDialog, public user:UserService, private cookieService: CookieService) { }

  openDialog(): void {

      const dialogRef = this.dialog.open(DialogOverviewComponent, {
        width: '250px',
        data: {}
      });
      dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
        if(this.user != undefined && this.user.firstName != undefined){
          this.username = this.user.firstName;
        }
        else{
          this.username = '';
        }
      });

    
  }

  ngOnInit() {
    this.username = '';
  }

  isLoggedIn(){
    let test = this.cookieService.get("userId");
    if(this.cookieService.get("userId") != null && this.cookieService.get("userId") != ''){
      this.username = this.cookieService.get("userName");
      return true;
    }
    return false;
  }

  logout(){
    this.username = '';
    this.user = null;
    this.cookieService.deleteAll();
    location.reload();
  }

}
