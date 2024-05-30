import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  private readonly TOKEN ="access_token";
  constructor() {
   

   }
   getToken(): string | null {
    return localStorage.getItem(this.TOKEN);
   }

   setToken(token: string): void {
    localStorage.setItem(this.TOKEN, token);
   }

   removeToken(): void {
    localStorage.removeItem(this.TOKEN);
   }
}
