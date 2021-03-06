import { Component, OnInit, HostListener, ViewChild, ElementRef } from '@angular/core';
import { CardsService } from 'src/app/shared/services/cards.service';
import { CookieService } from 'ngx-cookie-service';
import { UserService } from 'src/app/shared/services/user.service';
import { CartService } from 'src/app/shared/services/cart.service';
import { environment } from 'src/environments/environment';
import {MatSnackBar} from '@angular/material/snack-bar';
import {MatDialog} from '@angular/material/dialog';
import { DialogOverviewComponent } from '../../shared/components/dialog-overview/dialog-overview.component'

@Component({
  selector: 'app-cards',
  templateUrl: './cards.component.html',
  styleUrls: ['./cards.component.css']
})
export class CardsComponent implements OnInit {

  cards:any[];

  itemsLoaded : number;

  modalImageContent:string;
  modalImageCaption:string;
  @ViewChild('image') img: ElementRef;
  @ViewChild('parent') parent: ElementRef;

  constructor( private cookieService: CookieService,
               private cardService: CardsService,
               private user: UserService,
               private cartService: CartService,
               private snackBar: MatSnackBar,
               public dialog: MatDialog) { }

  ngOnInit() {

    this.cardService.getItemsForHomePage(environment.homeCardsToLoadPerCall, 0)
        .subscribe((response:any)=>{
          if(response.statusCode == 200){
            console.log("Success");
            this.cardService.items = response.dataList;
            console.log(this.cardService.items);
            this.cards = this.cardService.items;
            this.itemsLoaded = this.cardService.items.length;
          }
        },
        (error)=>{
          this.snackBar.open("Failed To Load Cards Content", "ERROR", {
            duration: 5000,
          });
          console.log("Failed To Load Cards Content");
        })

  }

  viewFullImage(card){
    this.img.nativeElement.src = card.image;
    this.modalImageCaption = card.itemName;
    let tag = this.parent.nativeElement;
    tag.classList.remove("modal");
    tag.classList.add("modal-open");
  }

  closeImageOverview(){
    this.img.nativeElement.src = null;
    this.modalImageCaption = null;
    this.parent.nativeElement.classList.remove("modal-open");
    this.parent.nativeElement.classList.add("modal");
  }

  addToCart(item:any){
    if(this.user.token != null && this.cookieService.get("userId") != null){
      let cartItem = { itemId: item.itemId, itemName: item.itemName, quantity: 1 , cost: item.cost};
      let itemExisting = false;
      // identify if the item is present in cart already and increment values likewise.
      this.user.cartItems.forEach(function(itemValue){
        if(itemValue.itemId === item.itemId){
          itemValue.quantity += 1;
          itemExisting = true;
          cartItem = itemValue;
        }
      });
      if(!itemExisting)
        this.user.cartItems.push(cartItem);
        let removeFromCart = false;
        this.cartService.addCartForUser(cartItem)
            .subscribe((response:any)=>{
              if(response.statusCode === 200){
                console.log("added to cart successfully!");
                this.snackBar.open(cartItem.itemName + " added to Cart Successfully", "OK", {
                  duration: 2000,
                });
              }
              else{
                this.displayLoginSnackBar();
                //reset cart item
                this.removeFromCartArray(cartItem);
              }
            },
            (error)=>{
              this.snackBar.open("Failed to add " + cartItem.itemName + " to Cart", "ERROR", {
                duration: 2000,
              });
              this.removeFromCartArray(cartItem);
            });
    }
    else{
      this.displayLoginSnackBar();
    }
  }

  displayLoginSnackBar(){
    this.snackBar.open("Please Login to add Item into Cart", "Click here to LOGIN", {
      duration: 5000,
    });
    this.snackBar._openedSnackBarRef.onAction().subscribe(()=>{
      this.openLoginDialog();
    });
  }

  openLoginDialog(): void {
    const dialogRef = this.dialog.open(DialogOverviewComponent, {
      width: '250px',
      data: {}
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  removeFromCartArray(cartItem){
    for(let i=0; i < this.user.cartItems.length; i++){
      if(this.user.cartItems[i].itemId === cartItem.itemId){
        if((this.user.cartItems[i].quantity - 1) === 0){
          this.user.cartItems.splice(i,1);
        }
        else{
          this.user.cartItems[i].quantity -= 1;
        }
      }
    }
  }

  @HostListener("window:scroll", ["$event"])
  onWindowScroll() {
  //In chrome and some browser scroll is given to body tag
  let pos = (document.documentElement.scrollTop || document.body.scrollTop) + document.documentElement.offsetHeight;
  let max = document.documentElement.scrollHeight;
  // pos/max will give you the distance between scroll bottom and and bottom of screen in percentage.
  if(pos == max )   {
    //incremental call to push cards on lazy load
    this.cardService.getItemsForHomePage(environment.homeCardsToLoadPerCall, this.itemsLoaded)
        .subscribe((response:any)=>{
          if(response.statusCode == 200){
            this.cardService.items = response.dataList;
            this.cards = this.cards.concat(this.cardService.items);
            this.itemsLoaded += response.dataList.length;
          }
        },
        (error)=>{
          this.snackBar.open("Failed to lazy load Cards", "ERROR", {
            duration: 2000,
          });
          console.log("Failed to lazy load Cards");
        })
  }
}

}
