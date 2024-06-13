import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RelevantComponent } from './relevant.component';

describe('RelevantComponent', () => {
  let component: RelevantComponent;
  let fixture: ComponentFixture<RelevantComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RelevantComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RelevantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
