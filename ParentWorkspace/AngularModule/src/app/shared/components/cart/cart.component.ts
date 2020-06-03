import { Component, OnInit } from '@angular/core';
import { MatBottomSheet, MatBottomSheetRef } from '@angular/material/bottom-sheet';
import { UserService } from '../../services/user.service';
import { CartService } from '../../services/cart.service';
import { CardsService } from '../../services/cards.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  constructor(private _bottomSheet: MatBottomSheet,
    public userSerive: UserService, private cartService: CartService) { }

  openBottomSheet(): void {
    this._bottomSheet.open(cartBottomSheet);
  }

  ngOnInit() {
  }

}

//cart content in bottom of the page
@Component({
  selector: 'cart-bottom-sheet',
  templateUrl: 'cartBottomSheet.html',
})
export class cartBottomSheet {

  cartItems: any;
  itemDetails = [];
  loading: boolean = false;

  constructor(private _bottomSheetRef: MatBottomSheetRef<cartBottomSheet>,
    public user: UserService,
    private cartService: CartService,
    private cardService: CardsService,
    private snackBar: MatSnackBar,
    private router: Router) {
    this.cartItems = this.user.cartItems;
    this.loading = true;
    let itemIds = [];
    this.cartItems.forEach(element => {
      itemIds.push(element.itemId);
    });
    this.cartService.getItemsFromId(itemIds)
      .subscribe((response: any) => {
        if (response.statusCode == 200) {
          if(response.data != null)
          {
            this.itemDetails.push(response.data);
          }
          else{
            this.itemDetails = response.dataList;
          }
          this.updateCost();
        }
        this.loading = false;
      },
        (error) => {
          this.snackBar.open("Failed to load Cart Items", "ERROR", {
            duration: 10000,
          });
          this.loading = false;
        });
  }

  closeBottomSheet(): void {
    this._bottomSheetRef.dismiss();
  }

  //Complexity is high of n2. need to be modified for performance improvement later.
  updateCost(){
    this.user.cartItems.forEach(citem => {
      let c=0;
      this.itemDetails.forEach(ele => {
        if(ele.itemId === citem.itemId){
          let pos = this.getPosition(ele.itemId);
          if(pos > -1)
            this.user.cartItems[pos].cost = ele.cost*this.user.cartItems[pos].quantity;
        }
        c++;
      });
    });
  }

  getPosition(itemId){
    let i=0;
    let pos=-1;
    this.user.cartItems.forEach(ele=>{
      if(itemId === ele.itemId){
        pos = i;
      }
      i++;
    });
    return pos;
  }

  toggleDelete(item: any) {
    return item.quantity > 1 ? true : false;
  }

  incrementItem(item: any) {
    let i = 0;
    let modifiedItem;
    this.user.cartItems.forEach(element => {
      if (item.itemId === element.itemId) {
        this.user.cartItems[i].quantity++;
        modifiedItem = this.user.cartItems[i];
      }
      i++;
    });
    this.addOrMinusCartItems(modifiedItem);
    this.updateCost();
  }

  decrementItem(item: any) {
    let i = 0;
    let modifiedItem;
    this.user.cartItems.forEach(element => {
      if (item.itemId === element.itemId) {
        this.user.cartItems[i].quantity--;
        modifiedItem = this.user.cartItems[i];
      }
      i++;
    });
    this.addOrMinusCartItems(modifiedItem);
    this.updateCost();
  }

  addOrMinusCartItems(item: any) {
    this.cartService.addCartForUser(item)
      .subscribe((response: any) => {
        if (response.statusCode == 200) {
          this.cartItems = this.user.cartItems;
        }
      },
        (error) => {
          this.snackBar.open("Failed to load Cart Items", "ERROR", {
            duration: 10000,
          });
        });
  }

  removeItem(item: any) {
    let i = 0;
    this.user.cartItems.forEach(element => {
      if (item.itemId === element.itemId) {
        this.user.cartItems.splice(i, 1);
      }
      i++;
    });
    this.cartService.removeCartItem(item.itemId)
      .subscribe((response: any) => {
        if (response.statusCode == 200) {
          this.cartItems = this.user.cartItems;
        }
      },
        (error) => {
          this.snackBar.open("Failed to load Cart Items", "ERROR", {
            duration: 10000,
          });
        });
    this.updateCost();
  }

  clearCart() {
    if(this.user.cartItems.length > 0){
      this.cartService.clearCart()
        .subscribe((response: any) => {
          console.log('user cart cleared!');
        },
          (error) => {
            this.snackBar.open("Failed to clear Items", "ERROR", {
              duration: 10000,
            });
          });
      //clear user cart
      this.user.cartItems = [];
      this.cartItems = [];
    }
    else{
      this.snackBar.open("Cart is Empty", "WARN", {
        duration: 3000
      });
    }
  }

  checkOutCart(){
    if(this.user.cartItems.length > 0){
      this.closeBottomSheet();
      this.router.navigate(['/orderItem']);
    }
    else{
      this.snackBar.open("Cart is Empty", "WARN", {
        duration: 3000,
      });
    }
  }
}