import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InsertBookAdminComponent } from './insert-book-admin.component';

describe('InsertBookAdminComponent', () => {
  let component: InsertBookAdminComponent;
  let fixture: ComponentFixture<InsertBookAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [InsertBookAdminComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(InsertBookAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
