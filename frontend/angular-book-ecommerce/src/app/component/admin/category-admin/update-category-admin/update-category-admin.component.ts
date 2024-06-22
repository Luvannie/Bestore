import { Component, OnInit } from '@angular/core';
import { BookCategory } from '../../../../common/model/book-category';
import { CategoryService } from '../../../../service/category.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiResponse } from '../../../../response/api.response';
import { HttpErrorResponse } from '@angular/common/http';
import { CategoryDTO } from '../../../../common/category-dto';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-update-category-admin',
  templateUrl: './update-category-admin.component.html',
  styleUrl: './update-category-admin.component.css',
  

})
export class UpdateCategoryAdminComponent implements OnInit {

  categoryId: number;
  category: BookCategory;
  updatedCategory: BookCategory;

  constructor(
    private categoryService: CategoryService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.categoryId = 0;
    this.category = {} as BookCategory;
    this.updatedCategory = {} as BookCategory;
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.categoryId = Number(params.get('id'));
      console.log(this.categoryId);
      this.getCategoryDetails();
    });
  }

  getCategoryDetails(): void {
    this.categoryService.getDetailCategory(this.categoryId).subscribe({
      next: (apiResponse: ApiResponse) => {
        this.category = apiResponse.data;
        this.updatedCategory = { ...apiResponse.data };
      },
      error: (error: HttpErrorResponse) => {
        console.error(error?.error?.message ?? '');
      }
    });
  }

  updateCategory() {
    const updateCategoryDTO: CategoryDTO = {
      categoryName: this.updatedCategory.categoryName,
    };
    console.log("update object", updateCategoryDTO);

    this.categoryService.updateCategory(this.category.id, updateCategoryDTO).subscribe({
      next: (apiResponse: ApiResponse) => {
        console.log(apiResponse);
      },
      complete: () => {
        this.router.navigate(['/admin/categories']);
      },
      error: (error: HttpErrorResponse) => {
        console.error(error?.error?.message ?? '');
      }
    });
  }
}