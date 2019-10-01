import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SummonerContainerComponent } from './summoner-container.component';
import { SummonerSelectComponent } from '../../components/summoner-select/summoner-select.component';
import { SummonerDetailsComponent } from '../../components/summoner-details/summoner-details.component';
import { QueueStatisticsComponent } from '../../components/queue-statistics/queue-statistics.component';
import { ReactiveFormsModule } from '@angular/forms';
import { AppStateModule } from '../../../app-state.module';
import { QueueRecordsComponent } from '../../components/queue-records/queue-records.component';

describe('SummonerContainerComponent', () => {
  let component: SummonerContainerComponent;
  let fixture: ComponentFixture<SummonerContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        AppStateModule,
      ],
      declarations: [
        SummonerContainerComponent,
        SummonerSelectComponent,
        SummonerDetailsComponent,
        QueueStatisticsComponent,
        QueueRecordsComponent,
      ]
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
