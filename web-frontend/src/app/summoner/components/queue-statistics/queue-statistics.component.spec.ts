import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QueueStatisticsComponent } from './queue-statistics.component';

describe('QueueStatisticsComponent', () => {
  let component: QueueStatisticsComponent;
  let fixture: ComponentFixture<QueueStatisticsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QueueStatisticsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QueueStatisticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
