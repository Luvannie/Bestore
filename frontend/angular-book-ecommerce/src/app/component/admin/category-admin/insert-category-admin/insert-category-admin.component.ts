import { Component, OnInit } from '@angular/core';
import { CategoryDTO } from '../../../../common/category-dto';
import { TokenService } from '../../../../service/token.service';
import { CategoryService } from '../../../../service/category.service';
import { Router } from '@angular/router';
import { ApiResponse } from '../../../../response/api.response';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-insert-category-admin',
  templateUrl: './insert-category-admin.component.html',
  styleUrl: './insert-category-admin.component.css'
})
export class InsertCategoryAdminComponent implements OnInit {
  insertCategoryDTO: CategoryDTO = {
    categoryName: ''
  }
  constructor(
    private categoryService: CategoryService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  insertCategory() {
  console.log(this.insertCategoryDTO);
  this.categoryService.insertCategory(this.insertCategoryDTO).subscribe({
    next: (apiResponse: ApiResponse) => {
      console.log(apiResponse);
      // Chuyển hướng người dùng sau khi thêm thành công
      this.router.navigateByUrl('/admin/categories');
    },
    error: (error: HttpErrorResponse) => {
      console.error(error?.error?.message ?? '');
    }
  });
}

}
