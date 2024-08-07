import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InsertCategoryAdminComponent } from './insert-category-admin.component';

describe('InsertCategoryAdminComponent', () => {
  let component: InsertCategoryAdminComponent;
  let fixture: ComponentFixture<InsertCategoryAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [InsertCategoryAdminComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(InsertCategoryAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
