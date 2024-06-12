import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateBookAdminComponent } from './update-book-admin.component';

describe('UpdateBookAdminComponent', () => {
  let component: UpdateBookAdminComponent;
  let fixture: ComponentFixture<UpdateBookAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UpdateBookAdminComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UpdateBookAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
