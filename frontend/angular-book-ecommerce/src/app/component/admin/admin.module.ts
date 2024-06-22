import { NgModule } from "@angular/core";
import { AdminComponent } from "./admin.component";
import { Order } from "../../common/model/order";
import { DetailOrderAdminComponent } from "./detail-order-admin/detail-order-admin.component";
import { Book } from "lucide-angular";
import { BooksAdminComponent } from "./books-admin/books-admin.component";
import { CategoryAdminComponent } from "./category-admin/category-admin.component";
import { InsertBookAdminComponent } from "./books-admin/insert-book-admin/insert-book-admin.component";
import { UpdateBookAdminComponent } from "./books-admin/update-book-admin/update-book-admin.component";
import { InsertCategoryAdminComponent } from "./category-admin/insert-category-admin/insert-category-admin.component";
import { UserAdminComponent } from "./detail-order-admin/user-admin/user-admin.component";
import { AdminRoutingModule, } from "./admin-routing.module";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { ComplainAdminComponent } from './complain-admin/complain-admin.component';
import { OrdersAdminComponent } from './orders-admin/orders-admin.component';
import { UpdateCategoryAdminComponent } from "./category-admin/update-category-admin/update-category-admin.component";

@NgModule({
    declarations:
    [
        AdminComponent,
        DetailOrderAdminComponent,
        BooksAdminComponent,
        CategoryAdminComponent,
        InsertBookAdminComponent,
        UpdateBookAdminComponent,
        InsertCategoryAdminComponent,
        UserAdminComponent,
        ComplainAdminComponent,
        OrdersAdminComponent,
        UpdateCategoryAdminComponent,
        InsertCategoryAdminComponent

        
    ],
    imports: [
        AdminRoutingModule,
        CommonModule,
        FormsModule
    ],
})

export class AdminModule{}