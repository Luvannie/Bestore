import { Component, Inject, OnInit } from '@angular/core';
import { BookCategory } from '../../../common/model/book-category';
import { CategoryService } from '../../../service/category.service';
import { ApiResponse } from '../../../response/api.response';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { DOCUMENT } from '@angular/common';

@Component({
  selector: 'app-category-admin',
  templateUrl: './category-admin.component.html',
  styleUrl: './category-admin.component.css'
})
export class CategoryAdminComponent implements OnInit {
  categories: BookCategory[] = [];
  currentPage: number = 0;
  itemsPerPage: number = 12;
  pages: number[] = [];
  totalPages:number = 0;
  visiblePages: number[] = [];
  localStorage?:Storage;

  constructor(private categoryService: CategoryService,
              private router: Router,
              @Inject(DOCUMENT) private document: Document
  ) { this.localStorage = document.defaultView?.localStorage;}

  ngOnInit(): void {
    this.getCategories(this.currentPage, this.itemsPerPage);
  }

  getCategories( page: number, limit: number) {
    this.categoryService.getCategories( page, limit).subscribe({
      next: (apiResponse: ApiResponse) => {
        console.log(apiResponse);
        const categories = apiResponse?.data.bookCategories as BookCategory[];
        this.categories = categories;
        this.totalPages = apiResponse?.data.totalPages;
        this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
      },
      complete: () => {
        console.log(this.categories);
      },
      error: (error: HttpErrorResponse) => {
        console.error(error?.error?.message ?? '');
      }
    });
  }

  onPageChange(page: number) {
    this.currentPage = page < 0 ? 0 : page;
    this.localStorage?.setItem('currentCategoryAdminPage', String(this.currentPage));
    this.getCategories( this.currentPage, this.itemsPerPage);
  }

  generateVisiblePageArray(currentPage: number, totalPages: number): number[] {
    const maxVisiblePages = 5;
    const halfVisiblePages = Math.floor(maxVisiblePages / 2);

    let startPage = Math.max(currentPage - halfVisiblePages, 1);
    let endPage = Math.min(startPage + maxVisiblePages - 1, totalPages);

    if (endPage - startPage + 1 < maxVisiblePages) {
      startPage = Math.max(endPage - maxVisiblePages + 1, 1);
    }

    return new Array(endPage - startPage + 1).fill(0)
      .map((_, index) => startPage + index);
  }

  insertCategory() {
    this.router.navigate(['/admin/categories/insert']);
  }

  updateCategory(categoryId: number) {
    this.router.navigate(['/admin/categories/update', categoryId]);
  }

  deleteCategory(category: BookCategory) {
    const confirmation = window.confirm('Are you sure you want to delete this category?');
    if (confirmation) {
      this.categoryService.deleteCategory(category.id).subscribe({
        next: (apiResponse: ApiResponse) => {
          console.error('Xóa thành công')
          location.reload();
        },
        error: (error: HttpErrorResponse) => {
          console.error(error?.error?.message ?? '');
        }
      });
    }
  }
}
