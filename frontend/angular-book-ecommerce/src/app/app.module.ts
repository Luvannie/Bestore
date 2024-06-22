import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BookListComponent } from './component/book-list/book-list.component';
import { HttpClient } from '@angular/common/http';
import { HttpClientModule,HTTP_INTERCEPTORS } from '@angular/common/http';
import { BookService } from './service/book.service';
import { Router, RouterModule, Routes } from '@angular/router';
import { BookCategoryMenuComponent } from './component/book-category-menu/book-category-menu.component';
import { SearchComponent } from './component/search/search.component';
import { BookDetailsComponent } from './component/book-details/book-details.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CartStatusComponent } from './component/cart-status/cart-status.component';
import { CartDetailComponent } from './component/cart-detail/cart-detail.component';
import { CheckoutComponent } from './component/checkout/checkout.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HomeComponent } from './component/home/home.component';
import { LoginComponent } from './component/login/login.component';
import { OKTA_CONFIG, OktaAuthModule } from '@okta/okta-angular';
import myAppConfig from './config/my-app-config';
import { OktaAuth } from '@okta/okta-auth-js';
import { RegisterComponent } from './component/register/register.component';
import { TokenInterceptor } from './interceptor/token.interceptor';
import { Form } from '@okta/okta-signin-widget/types/packages/@okta/courage-dist/types';
import { HeaderComponent } from './component/header/header.component';
import { UserProfileComponent } from './component/user-profile/user-profile.component';
import { AdminComponent } from './component/admin/admin.component';
import { UserOrderComponent } from './component/user-order/user-order.component';
import { BooksAdminComponent } from './component/admin/books-admin/books-admin.component';
import { CategoryAdminComponent } from './component/admin/category-admin/category-admin.component';
import { DetailOrderAdminComponent } from './component/admin/detail-order-admin/detail-order-admin.component';
import { UpdateBookAdminComponent } from './component/admin/books-admin/update-book-admin/update-book-admin.component';
import { UpdateCategoryAdminComponent } from './component/admin/category-admin/update-category-admin/update-category-admin.component';
import { InsertCategoryAdminComponent } from './component/admin/category-admin/insert-category-admin/insert-category-admin.component';
import { InsertBookAdminComponent } from './component/admin/books-admin/insert-book-admin/insert-book-admin.component';
import { UserAdminComponent } from './component/admin/detail-order-admin/user-admin/user-admin.component';
import { AdminModule } from './component/admin/admin.module';
import { ComplainComponent } from './component/complain/complain.component';
import { RelevantComponent } from './component/relevant/relevant.component';
import { FooterComponent } from './component/footer/footer.component';
import { AsideComponent } from './component/aside/aside.component';


const oktaConfig = myAppConfig.oidc;

const oktaAuth = new OktaAuth(oktaConfig);


@NgModule({
  declarations: [
      AppComponent,
      BookListComponent,
      BookCategoryMenuComponent,
      SearchComponent,
      BookDetailsComponent,
      CartStatusComponent,
      CartDetailComponent,
      CheckoutComponent,
      HomeComponent,
      LoginComponent,
      RegisterComponent,
      HeaderComponent,
      UserOrderComponent,
      UserProfileComponent,
      ComplainComponent,
      RelevantComponent,
      FooterComponent,
      AsideComponent,
    ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    HttpClientModule,
    NgbModule,
    ReactiveFormsModule,
    OktaAuthModule,
    FormsModule,
    AdminModule
   
    
    
  ],
  providers: [BookService,{provide: OKTA_CONFIG, useValue: {oktaAuth}}],
  bootstrap: [AppComponent]
})
export class AppModule { }
