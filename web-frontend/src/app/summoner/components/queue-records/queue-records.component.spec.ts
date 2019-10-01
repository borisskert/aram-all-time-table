import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QueueRecordsComponent } from './queue-records.component';

describe('QueueRecordsComponent', () => {
  let component: QueueRecordsComponent;
  let fixture: ComponentFixture<QueueRecordsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QueueRecordsComponent ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QueueRecordsComponent);
    component = fixture.componentInstance;

    component.queueRecords = {
      maxWinStreak: 99,
      maxLoseStreak: 99,
      maxWinRate: 0.99,
      minWinRate: 0.99,
      maxFormWinRate: 0.99,
      minFormWinRate: 0.99,
    };

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
