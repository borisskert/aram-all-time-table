import { async, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { ReactiveFormsModule } from '@angular/forms';
import { APP_CONFIG_PROVIDER, AppConfig } from './app-config';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { AppStateModule } from './app-state.module';
import { SummonerModule } from './summoner/summoner.module';

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        ReactiveFormsModule,
        HttpClientTestingModule,
        AppStateModule,
        SummonerModule,
      ],
      declarations: [
        AppComponent,
      ],
      providers: [
        AppConfig, APP_CONFIG_PROVIDER,
      ]
    }).compileComponents();
  }));

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  });
});
