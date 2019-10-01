import { Component, OnInit } from '@angular/core';
import { State } from '../../../+state/contract';
import { Store } from '@ngrx/store';
import { GetSummonerName, LoadSummoner } from '../../+state/actions';
import { Observable } from 'rxjs';
import { Summoner } from '../../summoner';
import { getQueueRecords, getQueueStatistics, getSummoner } from '../../+state/selectors';
import { QueueStatistics } from '../../components/queue-statistics';
import { QueueRecords } from '../../components/queue-records';

@Component({
  selector: 'app-summoner-container',
  templateUrl: './summoner-container.component.html',
  styleUrls: [ './summoner-container.component.scss' ]
})
export class SummonerContainerComponent implements OnInit {

  public summoner$: Observable<Summoner>;
  public queueStatistics$: Observable<QueueStatistics>;
  public queueRecords$: Observable<QueueRecords>;

  constructor(private readonly store: Store<State>) { }

  ngOnInit() {
    this.summoner$ = this.store.select(getSummoner);
    this.queueStatistics$ = this.store.select(getQueueStatistics);
    this.queueRecords$ = this.store.select(getQueueRecords);

    this.store.dispatch(new GetSummonerName());
  }

  onSummonerSelected(summonerName: string) {
    this.store.dispatch(new LoadSummoner({ summonerName }));
  }
}
