import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SummonerUpdateComponent } from './summoner-update.component';

describe('SummonerUpdateComponent', () => {
  let component: SummonerUpdateComponent;
  let fixture: ComponentFixture<SummonerUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SummonerUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SummonerUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
