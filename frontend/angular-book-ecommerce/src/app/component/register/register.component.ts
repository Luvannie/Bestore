import { Component, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { BeFormService } from '../../service/be-form.service';
import { BeValidate } from '../../validator/be-validate';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit{

    registerFormGroup!: FormGroup;
  
    constructor(private userService:UserService,
                private formBuilder: FormBuilder,
                private beFormService: BeFormService,
                private router: Router,
    ) { }
  
    ngOnInit(): void {
      this.registerFormGroup = this.formBuilder.group({
        user: this.formBuilder.group({
          username: new FormControl('',[Validators.required, Validators.minLength(3),BeValidate.allWhitespaceValidator]),
          account: new FormControl('',[Validators.required, Validators.minLength(6),BeValidate.allWhitespaceValidator]),
          password:new FormControl('',[Validators.required, Validators.minLength(6),BeValidate.allWhitespaceValidator]),
          email: new FormControl('', [Validators.required, Validators.email,BeValidate.allWhitespaceValidator]),
          phone_number: new FormControl('',[Validators.required, Validators.minLength(10),BeValidate.allWhitespaceValidator]),
          retype_password: new FormControl('',[Validators.required, Validators.minLength(6),BeValidate.allWhitespaceValidator]),
          facebook_account_id: new FormControl(0),
          google_account_id: new FormControl(0),
          role_id: new FormControl(1)

        })
      });
    }

    onSubmit(){
      console.log("Handling the submit button");
      console.log(this.registerFormGroup.get('user')?.value);
      this.userService.registerUser(this.registerFormGroup.get('user')?.value).subscribe(
        {
          next: response => {
            alert(`Your account has been created successfully.`);
            this.returnToHome();
           
          },
          error: err => {
            alert(`There was an error: ${err.message}`);
          
         }
        }
      )
    }
  returnToHome() {
    this.router.navigateByUrl("/books");
  }

  get username(){
    return this.registerFormGroup.get('user.username');
  }

  get account(){
    return this.registerFormGroup.get('user.account');
  }

  get password(){
    return this.registerFormGroup.get('user.password');
  }

  get email(){
    return this.registerFormGroup.get('user.email');
  }

  get phoneNumber(){
    return this.registerFormGroup.get('user.phone_number');
  }

  get retype_password(){
    return this.registerFormGroup.get('user.retype_password');
  }

  checkPasswordsMatch() {    
    if (this.registerFormGroup.get('user.password')?.value !== this.registerFormGroup.get('user.retype_password')?.value) {
        this.registerFormGroup.get('user.retype_password')?.setErrors({ 'passwordMismatch': true });
    } else {
        this.registerFormGroup.get('user.retype_password')?.setErrors(null);
    }
}


}
