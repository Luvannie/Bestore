import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComplainAdminComponent } from './complain-admin.component';

describe('ComplainAdminComponent', () => {
  let component: ComplainAdminComponent;
  let fixture: ComponentFixture<ComplainAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ComplainAdminComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ComplainAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
