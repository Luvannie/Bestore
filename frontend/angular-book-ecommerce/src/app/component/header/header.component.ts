import { Component, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';
import { UserResponse } from '../../response/user/user.response';
import { TokenService } from '../../service/token.service';
import { Router } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RouterModule } from '@angular/router';  
import { CommonModule } from '@angular/common';
import { CartService } from '../../service/cart.service';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent implements OnInit{
  userResponse:UserResponse | null = null;
  isPopoverOpen = false;
  activeNavItem: number = 0;
  isLoggedIn: boolean = false;
  constructor(
    private userService: UserService,
    private tokenService: TokenService,    
    private router: Router,
    private cartService: CartService
  ) {}

  ngOnInit() {
     this.userResponse = this.userService.getUserResponseFromLocalStorage();
     this.isLoggedIn = !!this.userResponse;
    //  console.log(this.userResponse);
    
  }

  togglePopover(event: Event): void {
    event.preventDefault();
    this.isPopoverOpen = !this.isPopoverOpen;
  }

  handleItemClick(index: number): void {
    //console.error(`Clicked on "${index}"`);
    if(index === 0) {
      // debugger
      this.router.navigate(['/user-profile']);                      
    } else if (index === 2) {
      this.userService.removeUserFromLocalStorage();
      this.tokenService.removeToken();
      this.userResponse = this.userService.getUserResponseFromLocalStorage();  
      this.cartService.cartItems = [];
      this.cartService.totalPrice.next(0);
      this.cartService.totalQuantity.next(0);  
      this.isLoggedIn = false;
    }
    else if (index === 1) {
      this.router.navigate(['/user-orders']);
    }
    this.isPopoverOpen = false; // Close the popover after clicking an item    
  }

  
  setActiveNavItem(index: number) {    
    this.activeNavItem = index;
    //console.error(this.activeNavItem);
  }  

 
}
