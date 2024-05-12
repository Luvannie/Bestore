import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../common/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userUrl = 'http://localhost:8080/api/users';
  constructor(private httpClient:HttpClient) { }

  //register a new user
  registerUser(newUser: User){
    const registerUrl=this.userUrl+"/register";
    return this.httpClient.post(registerUrl, newUser);
  }

  //login a user
  loginUser(user: User){
    return this.httpClient.post(this.userUrl, user);
  }

}
