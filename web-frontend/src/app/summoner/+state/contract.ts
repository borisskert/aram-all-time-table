import { Summoner } from '../summoner';
import { QueueStatistics } from '../components/queue-statistics';
import { QueueRecords } from '../components/queue-records';

export interface SummonerState {
  summoner: Summoner;
  queueStatistics: QueueStatistics;
  queueRecords: QueueRecords;
}

export const initialState: SummonerState = {
  summoner: null,
  queueStatistics: null,
  queueRecords: null,
};
