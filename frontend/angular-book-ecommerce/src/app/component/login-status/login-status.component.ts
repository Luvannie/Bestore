import { Component, Inject, OnInit } from '@angular/core';
import { OKTA_AUTH, OktaAuthStateService } from '@okta/okta-angular';
import { OktaAuth } from '@okta/okta-auth-js';

@Component({
  selector: 'app-login-status',
  templateUrl: './login-status.component.html',
  styleUrl: './login-status.component.css'
})
export class LoginStatusComponent implements OnInit{
  isAuthenticated: boolean = false ;
  userFullName: string = '';

  constructor(private oktaAhtuService:OktaAuthStateService,
    @Inject(OKTA_AUTH) private oktaAuth: OktaAuth) 
   { }

  ngOnInit(): void {
    this.oktaAhtuService.authState$.subscribe(
      (res) => {
        this.isAuthenticated = res.isAuthenticated ?? false;
        this.getUserDetails();
      }
    );
  }
  getUserDetails() {
    //Fetch the logged in user details
    if(this.isAuthenticated){
      this.oktaAuth.getUser().then(
        (res) => {
          this.userFullName = res.name as string;
        }
      );
    }
  }

  logout(){
    //terminate the session with Okta and remove current tokens
    this.oktaAuth.signOut();
  }
}
