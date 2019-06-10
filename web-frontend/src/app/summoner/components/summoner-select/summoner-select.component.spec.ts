import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SummonerSelectComponent } from './summoner-select.component';

describe('SummonerSelectComponent', () => {
  let component: SummonerSelectComponent;
  let fixture: ComponentFixture<SummonerSelectComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SummonerSelectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SummonerSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
