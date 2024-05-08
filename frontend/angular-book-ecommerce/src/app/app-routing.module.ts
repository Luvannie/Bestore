import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookListComponent } from './component/book-list/book-list.component';
import { BookDetailsComponent } from './component/book-details/book-details.component';
import { CartDetailComponent } from './component/cart-detail/cart-detail.component';
import { CheckoutComponent } from './component/checkout/checkout.component';
import { HomeComponent } from './component/home/home.component';

const routes: Routes = [
  { path: 'checkout', component: CheckoutComponent},
  { path: 'cart-detail', component: CartDetailComponent},
  { path: 'books/:id', component: BookDetailsComponent},
  { path: 'search/:keyword', component: BookListComponent},
  { path: 'book-category/:id', component: BookListComponent },
  { path: 'book-category', component: BookListComponent },
  { path: 'books', component: HomeComponent },
  { path: '', redirectTo: '/books', pathMatch: 'full' },
  { path: '**', redirectTo: '/books', pathMatch: 'full' }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
