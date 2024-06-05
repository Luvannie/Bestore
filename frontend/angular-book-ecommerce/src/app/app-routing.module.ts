import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookListComponent } from './component/book-list/book-list.component';
import { BookDetailsComponent } from './component/book-details/book-details.component';
import { CartDetailComponent } from './component/cart-detail/cart-detail.component';
import { CheckoutComponent } from './component/checkout/checkout.component';
import { HomeComponent } from './component/home/home.component';
import { UserProfileComponent } from './component/user-profile/user-profile.component';



import myAppConfig from './config/my-app-config';
import { LoginComponent } from './component/login/login.component';
import { LoginStatusComponent } from './component/login-status/login-status.component';
import { RegisterComponent } from './component/register/register.component';
import { AdminComponent } from './component/admin/admin.component';
import { AdminGuardFn } from './guard/admin.guard';



const routes: Routes = [
  { path: 'register',component:RegisterComponent},
  { path: 'login',component:LoginComponent},
  { path: 'checkout', component: CheckoutComponent},
  { path: 'cart-detail', component: CartDetailComponent},
  { path: 'books/:id', component: BookDetailsComponent},
  { path: 'search/:keyword', component: BookListComponent},
  { path: 'book-category/:id', component: BookListComponent },
  { path: 'book-category', component: BookListComponent },
  { path: 'books', component: HomeComponent },
  { path: 'user-profile', component: UserProfileComponent },
  { path: 'admin',component:AdminComponent },
  { path: '', redirectTo: '/books', pathMatch: 'full' },
  { path: '**', redirectTo: '/books', pathMatch: 'full' }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  
})
export class AppRoutingModule { }
