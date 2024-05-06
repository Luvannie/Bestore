import { TestBed } from '@angular/core/testing';

import { BeFormService } from './be-form.service';

describe('BeFormService', () => {
  let service: BeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BeFormService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
