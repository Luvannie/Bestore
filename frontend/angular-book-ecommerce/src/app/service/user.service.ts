import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Inject, Injectable, inject } from '@angular/core';
import { User } from '../common/model/user';
import { LoginDTO } from '../common/loginDTO';
import { Observable, catchError } from 'rxjs';
import { environment } from '../../environments/environment';
import { HttpUtilService } from './http.util.service';
import { ApiResponse } from '../response/api.response';
import { UserResponse } from '../response/user/user.response';
import { UpdateUserDTO } from '../common/update-user-dto';
import { DOCUMENT } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userUrl = `${environment.apiBaseUrl}/users`;
  private http = inject(HttpClient);
  private httpUtilService = inject(HttpUtilService);  
  private apiUserDetail = `${environment.apiBaseUrl}/users/details`;

  localStorage?:Storage;
  
  private apiConfig={
    headers: this.httpUtilService.createHeaders(),
  }
  constructor(private httpClient:HttpClient,
    @Inject(DOCUMENT) private document: Document
  ) {this.localStorage = document.defaultView?.localStorage; }

  //register a new user
  registerUser(newUser: User){
    const registerUrl=this.userUrl+"/register";
    return this.httpClient.post(registerUrl, newUser, this.apiConfig);
  }


  login(loginDTO: LoginDTO):Observable<any>  {
    const loginUrl = this.userUrl + "/login";
    return this.httpClient.post(loginUrl, loginDTO,this.apiConfig);
  }

  // getUserDetail(token: string): Observable<ApiResponse> {
  //   return this.http.post<ApiResponse>(this.apiUserDetail, {
  //     headers: new HttpHeaders({
  //       'Content-Type': 'application/json',
  //       Authorization: `Bearer ${token}`
  //     })
  //   })
  // }

  getUserDetail(token: string): Observable<ApiResponse> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  
    return this.http.post<ApiResponse>(this.apiUserDetail, {}, { headers: headers });
  }

  // updateUserDetail(token: string, updateUserDTO: UpdateUserDTO): Observable<ApiResponse>  {
  //   // debugger
  //   const headers = new HttpHeaders({
  //     'Content-Type': 'application/json',
  //     'Authorization': `Bearer ${token}`
  //   });
  //   let userResponse = this.getUserResponseFromLocalStorage();        
  //   return this.http.put<ApiResponse>(`${this.apiUserDetail}/${userResponse?.id}`,updateUserDTO, { headers: headers });
  // }

  updateUserDetail(token: string, updateUserDTO: UpdateUserDTO): Observable<ApiResponse>  {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
    let userResponse = this.getUserResponseFromLocalStorage(); 
    console.log('User response for updateusser:', userResponse);
    if (!userResponse || !userResponse.id) {
        console.error('User response or user ID is not defined');
        throw new Error('User response or user ID is not defined');
    }
        
    return this.http.put<ApiResponse>(`${this.apiUserDetail}/${userResponse.id}`, updateUserDTO, { headers: headers })
      .pipe(
        catchError(err => {
          console.error(err);
          throw err;
        })
      );
}

  getUserResponseFromLocalStorage():UserResponse | null {
    // debugger
    try {
      // Retrieve the JSON string from local storage using the key
      const userResponseJSON = this.localStorage?.getItem('user'); 
      if(userResponseJSON == null || userResponseJSON == undefined) {
        console.log('User response not found in local storage.');
        return null;
      }
      // Parse the JSON string back to an object
      const userResponse = JSON.parse(userResponseJSON!);  
      console.log('User response retrieved from local storage.');
      return userResponse;
    } catch (error) {
      console.error('Error retrieving user response from local storage:', error);
      return null; // Return null or handle the error as needed
    }
  }

  saveUserResponseToLocalStorage(userResponse?: UserResponse) {
    try {
      // debugger
      if(userResponse == null || !userResponse) {
        return;
      }
      // Convert the userResponse object to a JSON string
      const userResponseJSON = JSON.stringify(userResponse);  
      // Save the JSON string to local storage with a key (e.g., "userResponse")
      this.localStorage?.setItem('user', userResponseJSON);  
      console.log('User response saved to local storage.');
      console.log( this.localStorage?.getItem('user'));
    } catch (error) {
      console.error('Error saving user response to local storage:', error);
    }
  }

  removeUserFromLocalStorage():void {
    try {
      // Remove the user data from local storage using the key
      this.localStorage?.removeItem('user');
      console.log('User data removed from local storage.');
    } catch (error) {
      console.error('Error removing user data from local storage:', error);
      // Handle the error as needed
    }
  }
  getUsers(params: { page: number, limit: number, keyword: string },token:string): Observable<ApiResponse> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
    const url = `${environment.apiBaseUrl}/users`;
    return this.http.get<ApiResponse>(url,  { params: params, headers: headers, responseType: 'json' });
  }

  resetPassword(userId: number): Observable<ApiResponse> {
    const url = `${environment.apiBaseUrl}/users/reset-password/${userId}`;
    return this.http.put<ApiResponse>(url, null, this.apiConfig);
  }

  toggleUserStatus(params: { userId: number, enable: boolean },token:string): Observable<ApiResponse> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
    const url = `${environment.apiBaseUrl}/users/block/${params.userId}/${params.enable ? '1' : '0'}`;
    return this.http.put<ApiResponse>(url, null, { headers: headers });
  }
}
