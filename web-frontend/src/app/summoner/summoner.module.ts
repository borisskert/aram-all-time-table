import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Effects } from './+state/effects';
import { EffectsModule } from '@ngrx/effects';
import { SummonerSelectComponent } from './components/summoner-select/summoner-select.component';
import { ReactiveFormsModule } from '@angular/forms';
import { SummonerContainerComponent } from './containers/summoner-container/summoner-container.component';
import { SummonerDetailsComponent } from './components/summoner-details/summoner-details.component';
import { QueueStatisticsComponent } from './components/queue-statistics/queue-statistics.component';
import { QueueRecordsComponent } from './components/queue-records/queue-records.component';
import { SummonerUpdateComponent } from './components/summoner-update/summoner-update.component';

@NgModule({
  declarations: [
    SummonerSelectComponent,
    SummonerContainerComponent,
    SummonerDetailsComponent,
    QueueStatisticsComponent,
    QueueRecordsComponent,
    SummonerUpdateComponent,
  ],
  exports: [
    SummonerContainerComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    EffectsModule.forFeature([ Effects ]),
  ]
})
export class SummonerModule {}
