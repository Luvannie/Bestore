import { Inject, Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { DOCUMENT } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  private readonly TOKEN ="token";
  private jwtHelperService = new JwtHelperService();
  localStorage?:Storage;
  constructor(@Inject(DOCUMENT) private document: Document) {
    this.localStorage = document.defaultView?.localStorage;
   

   }
   getToken(): string  {
    return this.localStorage?.getItem(this.TOKEN) ?? '';
   }

   setToken(token: string): void {
    this.localStorage?.setItem(this.TOKEN, token);      
   }

   removeToken(): void {
    this.localStorage?.removeItem(this.TOKEN);
   }

   getUserId(): number {
    let token = this.getToken();
    if (!token) {
        return 0;
    }
    let userObject = this.jwtHelperService.decodeToken(token);
    return 'userId' in userObject ? parseInt(userObject['userId']) : 0;

    }

    isTokenExpired(): boolean { 
      if(this.getToken() == null) {
          return false;
      }       
      return this.jwtHelperService.isTokenExpired(this.getToken()!);
  }
}
