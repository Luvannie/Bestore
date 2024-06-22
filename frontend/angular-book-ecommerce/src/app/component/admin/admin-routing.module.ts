import { RouterModule, Routes } from "@angular/router";
import { AdminComponent } from "./admin.component";
import { BooksAdminComponent } from "./books-admin/books-admin.component";
import { InsertBookAdminComponent } from "./books-admin/insert-book-admin/insert-book-admin.component";
import { UpdateBookAdminComponent } from "./books-admin/update-book-admin/update-book-admin.component";
import { CategoryAdminComponent } from "./category-admin/category-admin.component";
import { InsertCategoryAdminComponent } from "./category-admin/insert-category-admin/insert-category-admin.component";
import { UpdateCategoryAdminComponent } from "./category-admin/update-category-admin/update-category-admin.component";
import { DetailOrderAdminComponent } from "./detail-order-admin/detail-order-admin.component";
import { UserAdminComponent } from "./detail-order-admin/user-admin/user-admin.component";
import { NgModule } from "@angular/core";
import { ComplainAdminComponent } from "./complain-admin/complain-admin.component";
import { OrdersAdminComponent } from "./orders-admin/orders-admin.component";

const routes: Routes = [
    {
        path: 'admin',
        component: AdminComponent,
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
    }
];

@NgModule({
    imports: [
        RouterModule.forChild(routes)
    ],
    exports: [RouterModule]
})
export class AdminRoutingModule { }