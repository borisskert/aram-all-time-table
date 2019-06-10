import { Action } from '@ngrx/store';
import { Summoner } from '../summoner';

export enum SummonerActionType {
  LoadSummoner = '[Summoner] Load Summoner',
  LoadSummonerSuccessful = '[Summoner] Load Summoner Successful',
  LoadSummonerFailure = '[Summoner] Load Summoner Failure',
  GetSummonerName = '[Summoner] Get Summoner Name',
}

export type SummonerAction =
  | LoadSummoner
  | LoadSummonerSuccessful
  | LoadSummonerFailure
  | GetSummonerName
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

export class GetSummonerName implements Action {
  readonly type = SummonerActionType.GetSummonerName;
}
