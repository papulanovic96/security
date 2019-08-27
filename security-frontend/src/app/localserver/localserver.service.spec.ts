import { TestBed } from '@angular/core/testing';

import { LocalserverService } from './localserver.service';

describe('LocalserverService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: LocalserverService = TestBed.get(LocalserverService);
    expect(service).toBeTruthy();
  });
});
