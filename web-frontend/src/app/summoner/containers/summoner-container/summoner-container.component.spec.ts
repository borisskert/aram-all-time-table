import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SummonerContainerComponent } from './summoner-container.component';

describe('SummonerContainerComponent', () => {
  let component: SummonerContainerComponent;
  let fixture: ComponentFixture<SummonerContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SummonerContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SummonerContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
