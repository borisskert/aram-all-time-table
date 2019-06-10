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

    default:
      return state;
  }
}
