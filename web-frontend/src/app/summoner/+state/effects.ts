import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, switchMap, tap } from 'rxjs/operators';
import { SummonerService } from '../summoner.service';
import {
  GetSummonerName,
  LoadQueueRecords,
  LoadQueueRecordsFailure,
  LoadQueueRecordsSuccessful,
  LoadQueueStatistics,
  LoadQueueStatisticsFailure,
  LoadQueueStatisticsSuccessful,
  LoadSummoner,
  LoadSummonerFailure,
  LoadSummonerNotFound,
  LoadSummonerSuccessful,
  SummonerAction,
  SummonerActionType,
  UpdateSummoner,
  UpdateSummonerFailure,
  UpdateSummonerSuccessful
} from './actions';
import { Observable, of } from 'rxjs';

@Injectable()
export class Effects {

  loadSummoner$: Observable<SummonerAction> = createEffect(() => this.actions$.pipe(
    ofType<LoadSummoner | UpdateSummonerSuccessful>(
      SummonerActionType.LoadSummoner,
      SummonerActionType.UpdateSummonerSuccessful
    ),
    switchMap(({ payload }) => {
      return this.summonerService.loadSummoner(payload.summonerName)
        .pipe(
          map(summoner => new LoadSummonerSuccessful({ summoner })),
          catchError(error => {
              if (error.status === 404) {
                return of(new LoadSummonerNotFound({ summonerName: payload.summonerName }));
              } else {
                return of(new LoadSummonerFailure());
              }
            }
          )
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
    ofType<LoadQueueStatistics>(SummonerActionType.LoadQueueStatistics),
    switchMap(({ payload }) => {
      return this.summonerService.loadQueueStatistics(payload.summonerName)
        .pipe(
          map(queueStatistics => new LoadQueueStatisticsSuccessful({ queueStatistics })),
          catchError(error => {
            return of(new LoadQueueStatisticsFailure());
          })
        );
    })
  ));

  loadQueueRecords$: Observable<SummonerAction> = createEffect(() => this.actions$.pipe(
    ofType<LoadQueueRecords>(SummonerActionType.LoadQueueRecords),
    switchMap(({ payload }) => {
      return this.summonerService.loadQueueRecords(payload.summonerName)
        .pipe(
          map(queueRecords => new LoadQueueRecordsSuccessful({ queueRecords })),
          catchError(error => {
            return of(new LoadQueueRecordsFailure());
          })
        );
    })
  ));

  loadQueueStatisticsAfterSummonerLoad$: Observable<SummonerAction> = createEffect(() => this.actions$.pipe(
    ofType<LoadSummonerSuccessful>(SummonerActionType.LoadSummonerSuccessful),
    switchMap(({ payload }) => {
      return of(new LoadQueueStatistics({ summonerName: payload.summoner.name }));
    })
  ));

  loadQueueRecordsAfterSummonerLoad$: Observable<SummonerAction> = createEffect(() => this.actions$.pipe(
    ofType<LoadSummonerSuccessful>(SummonerActionType.LoadSummonerSuccessful),
    switchMap(({ payload }) => {
      return of(new LoadQueueRecords({ summonerName: payload.summoner.name }));
    })
  ));

  updateSummoner$: Observable<SummonerAction> = createEffect(() => this.actions$.pipe(
    ofType<UpdateSummoner | LoadSummonerNotFound>(SummonerActionType.UpdateSummoner, SummonerActionType.LoadSummonerNotFound),
    switchMap(({ payload }) => {
      return this.summonerService.updateSummoner(payload.summonerName)
        .pipe(
          switchMap(() => [
            new UpdateSummonerSuccessful({ summonerName: payload.summonerName }),
          ]),
          catchError(() => of(new UpdateSummonerFailure()))
        );
    })
  ));

  constructor(
    private actions$: Actions,
    private summonerService: SummonerService,
  ) {}
}
