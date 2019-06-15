import { TestBed } from '@angular/core/testing';

import { SummonerService } from './summoner.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { APP_CONFIG_PROVIDER, AppConfig } from '../app-config';

describe('SummonerService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientTestingModule,
      RouterTestingModule,
    ],
    providers: [
      AppConfig, APP_CONFIG_PROVIDER
    ]
  }));

  it('should be created', () => {
    const service: SummonerService = TestBed.get(SummonerService);
    expect(service).toBeTruthy();
  });
});
