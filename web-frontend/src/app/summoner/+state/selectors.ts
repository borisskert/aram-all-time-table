import { createFeatureSelector, createSelector } from '@ngrx/store';
import { SummonerState } from './contract';

const getSummonerState = createFeatureSelector<SummonerState>('summoner');

export const getSummoner = createSelector(
  getSummonerState,
  state => state.summoner
);

export const getQueueStatistics = createSelector(
  getSummonerState,
  state => state.queueStatistics
);

export const getQueueRecords = createSelector(
  getSummonerState,
  state => state.queueRecords
);
