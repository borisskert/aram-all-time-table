import { Component, OnInit } from '@angular/core';
import { State } from '../../../+state/contract';
import { Store } from '@ngrx/store';
import { GetSummonerName, LoadSummoner } from '../../+state/actions';
import { Observable } from 'rxjs';
import { Summoner } from '../../summoner';
import { getSummoner } from '../../+state/selectors';

@Component({
  selector: 'app-summoner-container',
  templateUrl: './summoner-container.component.html',
  styleUrls: [ './summoner-container.component.scss' ]
})
export class SummonerContainerComponent implements OnInit {

  public summoner$: Observable<Summoner>;

  constructor(private readonly store: Store<State>) { }

  ngOnInit() {
    this.summoner$ = this.store.select(getSummoner);
    this.store.dispatch(new GetSummonerName());
  }

  onSummonerSelected(summonerName: string) {
    this.store.dispatch(new LoadSummoner({ summonerName }));
  }
}
