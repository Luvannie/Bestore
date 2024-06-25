import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookListComponent } from './component/book-list/book-list.component';
import { BookDetailsComponent } from './component/book-details/book-details.component';
import { CartDetailComponent } from './component/cart-detail/cart-detail.component';
import { CheckoutComponent } from './component/checkout/checkout.component';
import { HomeComponent } from './component/home/home.component';
import { UserProfileComponent } from './component/user-profile/user-profile.component';




import { LoginComponent } from './component/login/login.component';
import { RegisterComponent } from './component/register/register.component';
import { AdminComponent } from './component/admin/admin.component';
import { AdminGuardFn } from './guard/admin.guard';
import { AuthGuardFn } from './guard/auth.guard';
import { UserOrderComponent } from './component/user-order/user-order.component';
import { BooksAdminComponent } from './component/admin/books-admin/books-admin.component';
import { CategoryAdminComponent } from './component/admin/category-admin/category-admin.component';
import { DetailOrderAdminComponent } from './component/admin/detail-order-admin/detail-order-admin.component';
import { UpdateBookAdminComponent } from './component/admin/books-admin/update-book-admin/update-book-admin.component';
import { InsertBookAdminComponent } from './component/admin/books-admin/insert-book-admin/insert-book-admin.component';
import { UpdateCategoryAdminComponent } from './component/admin/category-admin/update-category-admin/update-category-admin.component';
import { InsertCategoryAdminComponent } from './component/admin/category-admin/insert-category-admin/insert-category-admin.component';
import { UserAdminComponent } from './component/admin/detail-order-admin/user-admin/user-admin.component';
import { ComplainComponent } from './component/complain/complain.component';
import { ComplainAdminComponent } from './component/admin/complain-admin/complain-admin.component';
import { OrdersAdminComponent } from './component/admin/orders-admin/orders-admin.component';



const routes: Routes = [
  { path: 'register',component:RegisterComponent},
  { path: 'login',component:LoginComponent},
  { path: 'checkout', component: CheckoutComponent,canActivate:[AuthGuardFn] },
  { path: 'cart-detail', component: CartDetailComponent},
  { path: 'books/:id', component: BookDetailsComponent},
  { path: 'search/:keyword', component: BookListComponent},
  { path: 'book-category/:id', component: BookListComponent },
  { path: 'book-category', component: BookListComponent },
  { path: 'books', component: HomeComponent },
  { path: 'user-profile', component: UserProfileComponent,canActivate:[AuthGuardFn]  },
  { path: 'admin',component:AdminComponent,canActivate:[AdminGuardFn],
    children: [           
      {
          path: 'books',
          component: BooksAdminComponent
      },
      {
          path: 'categories',
          component: CategoryAdminComponent
      },
      //sub path
      {
          path: 'orders/:id',
          component: DetailOrderAdminComponent
      },
      {
          path: 'books/update/:id',
          component: UpdateBookAdminComponent
      },
      {
          path: 'books/insert',
          component: InsertBookAdminComponent
      },
      //categories            
      {
          path: 'categories/update/:id',
          component: UpdateCategoryAdminComponent
      },
      {
          path: 'categories/insert',
          component: InsertCategoryAdminComponent
      },
      {
          path: 'users',
          component: UserAdminComponent
      },  
      {
        path:'complains',
        component: ComplainAdminComponent
        },
        {
            path:'orders',
            component:OrdersAdminComponent
        }    
  ]
   },
  { path: 'user-orders',component:UserOrderComponent},
  {path: 'complain', component:ComplainComponent},
  { path: '', redirectTo: '/books', pathMatch: 'full' },
  { path: '**', redirectTo: '/books', pathMatch: 'full' }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  
})
export class AppRoutingModule { }
