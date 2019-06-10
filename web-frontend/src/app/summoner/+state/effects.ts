import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, switchMap, tap } from 'rxjs/operators';
import { SummonerService } from '../summoner.service';
import {
  GetSummonerName,
  LoadQueueStatistics,
  LoadQueueStatisticsFailure,
  LoadQueueStatisticsSuccessful,
  LoadSummoner,
  LoadSummonerFailure,
  LoadSummonerSuccessful,
  SummonerAction,
  SummonerActionType
} from './actions';
import { Observable, of } from 'rxjs';

@Injectable()
export class Effects {

  loadSummoner$: Observable<SummonerAction> = createEffect(() => this.actions$.pipe(
    ofType<LoadSummoner>(SummonerActionType.LoadSummoner),
    switchMap(({ payload }) => {
      return this.summonerService.loadSummoner(payload.summonerName)
        .pipe(
          map(summoner => new LoadSummonerSuccessful({ summoner })),
          catchError(() => of(new LoadSummonerFailure()))
        );
    })
  ));

  getSummonerName$: Observable<SummonerAction> = createEffect(() => this.actions$.pipe(
    ofType<GetSummonerName>(SummonerActionType.GetSummonerName),
    switchMap(() => {
      return this.summonerService.getSummonerName()
        .pipe(
          map(summonerName => new LoadSummoner({ summonerName }))
        );
    })
  ));

  setSummonerName$ = createEffect(() => this.actions$.pipe(
    ofType<LoadSummonerSuccessful>(SummonerActionType.LoadSummonerSuccessful),
    tap(summoner => {
      this.summonerService.setSummonerName(summoner.payload.summoner.name);
    })
  ), {
    dispatch: false
  });

  loadQueueStatistics$: Observable<SummonerAction> = createEffect(() => this.actions$.pipe(
    ofType<LoadSummonerSuccessful>(SummonerActionType.LoadSummonerSuccessful),
    switchMap(({ payload }) => {
      return this.summonerService.loadQueueStatistics(payload.summoner.name)
        .pipe(
          map(queueStatistics => new LoadQueueStatisticsSuccessful({ queueStatistics })),
          catchError(() => of(new LoadQueueStatisticsFailure()))
        );
    })
  ));

  constructor(
    private actions$: Actions,
    private summonerService: SummonerService,
  ) {}
}
