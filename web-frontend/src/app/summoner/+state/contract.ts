import { Summoner } from '../summoner';
import { QueueStatistics } from '../components/queue-statistics';

export interface SummonerState {
  summoner: Summoner;
  queueStatistics: QueueStatistics;
}

export const initialState: SummonerState = {
  summoner: null,
  queueStatistics: null,
};
