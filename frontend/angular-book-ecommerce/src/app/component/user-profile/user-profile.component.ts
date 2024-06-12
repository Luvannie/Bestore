import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TokenService } from '../../service/token.service';
import { UserService } from '../../service/user.service';
import { UserResponse } from '../../response/user/user.response';
import { HttpErrorResponse } from '@angular/common/http';
import { UpdateUserDTO } from '../../common/update-user-dto';
import { ApiResponse } from '../../response/api.response';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})
export class UserProfileComponent implements OnInit{
  userResponse?: UserResponse;
  userProfileForm !: FormGroup; 
  user_id: number = 0;
  token: string = '';

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private userService: UserService,
              private tokenService: TokenService,
              private route: ActivatedRoute,
  ) {}

  ngOnInit(){
    this.userProfileForm = this.formBuilder.group({
      username : new FormControl(''),
      email : new FormControl('',[Validators.email]),
      phone_number: new FormControl(''),
      facebook_account_id: new FormControl(''),
      google_account_id: new FormControl(''),
      password: new FormControl('',[Validators.minLength(6)]),
      retype_password: new FormControl('',[Validators.minLength(6)]),
    },
    {
      validators: this.passwordMatchValidator// Custom validator function for password match
    }
  );

    this.token = this.tokenService.getToken();
    
    this.userService.getUserDetail(this.token).subscribe({
      next: (response: ApiResponse) => {
        // debugger
        this.userResponse = {
          ...response.data,
        };    
        this.userProfileForm.patchValue({
          username: this.userResponse?.username ?? '',
          phoneNumber: this.userResponse?.phone_number ?? '',
          
        });        
        this.userService.saveUserResponseToLocalStorage(this.userResponse);         
      },
      complete: () => {
        // debugger;
      },
      error: (error: HttpErrorResponse) => {
        // debugger;
        console.error(error?.error?.message ?? '');
      }
    })
  }

  passwordMatchValidator(): ValidatorFn {
    return (formGroup: AbstractControl): ValidationErrors | null => {
      const password = formGroup.get('password')?.value;
      const retypedPassword = formGroup.get('retype_password')?.value;
      console.log('mật khẩu gõ lại',retypedPassword)
      if (password !== retypedPassword) {
        return { passwordMismatch: true };
      }
  
      return null;
    };
  }

  save(): void {
    // debugger
    if (this.userProfileForm.valid) {
      const updateUserDTO: UpdateUserDTO = {
        username: this.userProfileForm.get('username')?.value,
        phone_number: this.userProfileForm.get('phone_number')?.value,
        password: this.userProfileForm.get('password')?.value,
        retype_password: this.userProfileForm.get('retype_password')?.value,
        facebook_account_id: this.userProfileForm.get('facebook_account_id')?.value,
        google_account_id: this.userProfileForm.get('google_account_id')?.value,
        
      };
      console.log('Update user DTO:', updateUserDTO);
      
      this.token = this.tokenService.getToken();
      
      this.userService.updateUserDetail(this.token, updateUserDTO)
        .subscribe({
          next: (response: any) => {
            console.log('update response:',response);
            this.userService.removeUserFromLocalStorage();
            this.tokenService.removeToken();
            this.router.navigateByUrl("/login");
          },
          error: (error: HttpErrorResponse) => {
            // debugger;
            console.log('Error updating user profile:')
            console.error(error?.error?.message ?? '');
          } 
        });
    } else {
      if (this.userProfileForm.hasError('passwordMismatch')) {        
        console.error('Mật khẩu và mật khẩu gõ lại chưa chính xác')
      }
    }
  }    

  get username() {
    return this.userProfileForm.get('username');
  }

  get email() {
    return this.userProfileForm.get('email');
  }

  get phoneNumber() {
    return this.userProfileForm.get('phone_number');
  }

  get facebookAccountId() {
    return this.userProfileForm.get('facebook_account_id');
  }

  get googleAccountId() {
    return this.userProfileForm.get('google_account_id');
  }

  get password() {
    console.log('mật khẩu',this.userProfileForm.get('password'))
    return this.userProfileForm.get('password');
  }

  get retypePassword() {
    console.log('mật khẩu gõ lại',this.userProfileForm.get('retype_password'))
    return this.userProfileForm.get('retype_password');
  }

}
