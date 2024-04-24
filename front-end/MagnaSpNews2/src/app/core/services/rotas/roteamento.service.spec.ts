import { TestBed } from '@angular/core/testing';

import { RoteamentoService } from './roteamento.service';

describe('RoteamentoService', () => {
  let service: RoteamentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RoteamentoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
