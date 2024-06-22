import { Component, OnInit } from '@angular/core';
import { TokenService } from '../../service/token.service';
import { UserService } from '../../service/user.service';
import { UserResponse } from '../../response/user/user.response';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent implements OnInit {
  userResponse?: UserResponse | null ; // Update the type to allow for null values
  constructor(
    private tokenService: TokenService,
    private userService: UserService,
    private router: Router

  ) { }
  ngOnInit(): void {
      this.userResponse = this.userService.getUserResponseFromLocalStorage();
      debugger
    // if (this.router.url === '/admin') {
    //   this.router.navigate(['/admin/orders']);
    // }
  }
  logout(){
    this.userService.removeUserFromLocalStorage();
      this.tokenService.removeToken();
      this.userResponse = this.userService.getUserResponseFromLocalStorage(); 
      this.router.navigate(['/login']);   

  }

  showAdminComponent(componentName: string): void {
    debugger
    if (componentName === 'orders') {
      this.router.navigate(['/admin/orders']);
    } else if (componentName === 'categories') {
      this.router.navigate(['/admin/categories']);
    } else if (componentName === 'books') {
      this.router.navigate(['/admin/books']);
    } else if (componentName === 'users') {
      this.router.navigate(['/admin/users']);
    } else if (componentName === 'complains') {
      this.router.navigate(['/admin/complains']);
    }
  }

}
