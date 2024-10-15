import { TestBed } from '@angular/core/testing';

import { TaskImageServiceService } from './task-image-service.service';

describe('TaskImageServiceService', () => {
  let service: TaskImageServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TaskImageServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
