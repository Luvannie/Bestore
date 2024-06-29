import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../service/user.service';
import { BeFormService } from '../../service/be-form.service';
import { Router } from '@angular/router';
import { BeValidate } from '../../validator/be-validate';
import { LoginDTO } from '../../common/loginDTO';
import { LoginResponse } from '../../response/user/login.response';
import { HttpErrorResponse } from '@angular/common/http';
import { TokenService } from '../../service/token.service';
import { RoleService } from '../../service/role.service';
import { ApiResponse } from '../../response/api.response';
import { Role } from '../../common/model/role';
import { UserResponse } from '../../response/user/user.response';
import { CartService } from '../../service/cart.service';
import { CartItem } from '../../common/model/cart-item';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{
  loginFormGroup!: FormGroup;
  roles: Role[] = []; // Mảng roles
  rememberMe: boolean = true;
  cartItems: CartItem[] = [];
  
  selectedRole: Role | undefined; // Biến để lưu giá trị được chọn từ dropdown
  userResponse?: UserResponse
  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder,
    private router: Router,
    private tokenService: TokenService,
    private roleService: RoleService,
    private cartService: CartService
  ) { }

  ngOnInit(): void {
    this.loginFormGroup = this.formBuilder.group({
      user: this.formBuilder.group({
        account: new FormControl('', [Validators.required, Validators.minLength(6), BeValidate.allWhitespaceValidator]),
        password: new FormControl('', [Validators.required, Validators.minLength(6), BeValidate.allWhitespaceValidator]),
      })
    });

    this.roleService.getRoles().subscribe({      
      next: (apiResponse: ApiResponse) => { // Sử dụng kiểu Role[]
        // debugger
        const roles = apiResponse.data
        this.roles = roles;
        this.selectedRole = roles.length > 0 ? roles[0] : undefined;
      },
      complete: () => {
        // debugger
      },  
      error: (error: HttpErrorResponse) => {
        // debugger;
        console.error(error?.error?.message ?? '');
      } 
    });
  }

  onSubmit(): void {
    const user = this.loginFormGroup.get('user')?.value;
    const loginDTO: LoginDTO = {
      account: user.account,
      password: user.password,
      role_id: this.selectedRole?.id ?? 1
    };
  
    this.userService.login(loginDTO).subscribe({
      next: (apiResponse: ApiResponse) => {
         debugger
        const { token } = apiResponse.data;
        this.cartService.loadCartItems
        if(this.rememberMe){
          this.tokenService.setToken(token);
           debugger
          this.userService.getUserDetail(token).subscribe({
            next: (apiResponse2: ApiResponse) => {
               debugger
              this.userResponse = {
                ...apiResponse2.data,
              };    
              this.userService.saveUserResponseToLocalStorage(this.userResponse); 
              console.log('User response:', this.userResponse);
              if(this.userResponse?.role.name == 'admin') {
                console.log('Admin login successfully');
                this.router.navigateByUrl("/admin");    
              } else if(this.userResponse?.role.name == 'user') {
                console.log('User login successfully');
                this.router.navigateByUrl("/");    
                
                // this.router.navigate(['/admin']);                     
              }
              
              
            },
            complete: () => {
              // this.resetCart();
               debugger;
            },
            error: (error: HttpErrorResponse) => {
              // debugger;
              console.error(error?.error?.message ?? '');
              console.error('Error getting user detail:', error);
            } 
          })
        }
        
      },
      complete: () => {
        debugger
      },
      error: (error: HttpErrorResponse) => {
        console.error(error?.error?.message ?? '');
      }
    });
  }

  get account() {
    return this.loginFormGroup.get('user.account');
  }

  get password() {
    return this.loginFormGroup.get('user.password');
  }
  
  resetCart() {
    // reset cart data
    this.cartService.cartItems = [];
    this.cartService.totalPrice.next(0);
    this.cartService.totalQuantity.next(0);

    // navigate back to the products page
   
  }
    

}
