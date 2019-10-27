import { Action } from '@ngrx/store';
import { Summoner } from '../summoner';
import { QueueStatistics } from '../components/queue-statistics';
import { QueueRecords } from '../components/queue-records';

export enum SummonerActionType {
  LoadSummoner = '[Summoner] Load Summoner',
  LoadSummonerSuccessful = '[Summoner] Load Summoner Successful',
  LoadSummonerFailure = '[Summoner] Load Summoner Failure',
  LoadSummonerNotFound = '[Summoner] Load Summoner Not Found',
  GetSummonerName = '[Summoner] Get Summoner Name',
  LoadQueueStatistics = '[Summoner] Load Queue Statistics',
  LoadQueueStatisticsSuccessful = '[Summoner] Load Queue Statistics Successful',
  LoadQueueStatisticsFailure = '[Summoner] Load Queue Statistics Failure',
  LoadQueueRecords = '[Summoner] Load Queue Records',
  LoadQueueRecordsSuccessful = '[Summoner] Load Queue Records Successful',
  LoadQueueRecordsFailure = '[Summoner] Load Queue Records Failure',
  UpdateSummoner = '[Summoner] Update Summoner',
  UpdateSummonerSuccessful = '[Summoner] Update Summoner Successful',
  UpdateSummonerFailure = '[Summoner] Update Summoner Failure',
}

export type SummonerAction =
  | LoadSummoner
  | LoadSummonerSuccessful
  | LoadSummonerFailure
  | LoadSummonerNotFound
  | GetSummonerName
  | LoadQueueStatistics
  | LoadQueueStatisticsSuccessful
  | LoadQueueStatisticsFailure
  | LoadQueueRecords
  | LoadQueueRecordsSuccessful
  | LoadQueueRecordsFailure
  | UpdateSummoner
  | UpdateSummonerSuccessful
  | UpdateSummonerFailure
  ;

export class LoadSummoner implements Action {
  readonly type = SummonerActionType.LoadSummoner;

  constructor(public payload: { summonerName: string }) {}
}

export class LoadSummonerSuccessful implements Action {
  readonly type = SummonerActionType.LoadSummonerSuccessful;

  constructor(public payload: { summoner: Summoner }) {}
}

export class LoadSummonerFailure implements Action {
  readonly type = SummonerActionType.LoadSummonerFailure;
}

export class LoadSummonerNotFound implements Action {
  readonly type = SummonerActionType.LoadSummonerNotFound;

  constructor(public payload: { summonerName: string }) {}
}

export class GetSummonerName implements Action {
  readonly type = SummonerActionType.GetSummonerName;
}

export class LoadQueueStatistics implements Action {
  readonly type = SummonerActionType.LoadQueueStatistics;

  constructor(public payload: { summonerName: string }) {}
}

export class LoadQueueStatisticsSuccessful implements Action {
  readonly type = SummonerActionType.LoadQueueStatisticsSuccessful;

  constructor(public payload: { queueStatistics: QueueStatistics }) {}
}

export class LoadQueueStatisticsFailure implements Action {
  readonly type = SummonerActionType.LoadQueueStatisticsFailure;
}

export class LoadQueueRecords implements Action {
  readonly type = SummonerActionType.LoadQueueRecords;

  constructor(public payload: { summonerName: string }) {}
}

export class LoadQueueRecordsSuccessful implements Action {
  readonly type = SummonerActionType.LoadQueueRecordsSuccessful;

  constructor(public payload: { queueRecords: QueueRecords }) {}
}

export class LoadQueueRecordsFailure implements Action {
  readonly type = SummonerActionType.LoadQueueRecordsFailure;
}

export class UpdateSummoner implements Action {
  readonly type = SummonerActionType.UpdateSummoner;

  constructor(public payload: { summonerName: string }) {}
}

export class UpdateSummonerSuccessful implements Action {
  readonly type = SummonerActionType.UpdateSummonerSuccessful;

  constructor(public payload: { summonerName: string }) {}
}

export class UpdateSummonerFailure implements Action {
  readonly type = SummonerActionType.UpdateSummonerFailure;
}
