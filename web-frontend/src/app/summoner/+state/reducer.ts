import { initialState, SummonerState } from './contract';
import { SummonerAction, SummonerActionType } from './actions';

export function reducer(state = initialState, action: SummonerAction): SummonerState {
  switch (action.type) {

    case SummonerActionType.LoadSummonerSuccessful: {
      return {
        ...state,
        summoner: action.payload.summoner,
      };
    }

    case SummonerActionType.LoadQueueStatisticsSuccessful: {
      return {
        ...state,
        queueStatistics: action.payload.queueStatistics,
      };
    }

    case SummonerActionType.LoadQueueRecordsSuccessful: {
      return {
        ...state,
        queueRecords: action.payload.queueRecords,
      };
    }

    default:
      return state;
  }
}
