import { TestBed } from '@angular/core/testing';

import { VerificarApiService } from './verificar-api.service';

describe('VerificarApiService', () => {
  let service: VerificarApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VerificarApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
