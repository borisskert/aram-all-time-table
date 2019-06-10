import { initialState as summonerInitialState, SummonerState } from '../summoner/+state/contract';

export interface State {
  summoner: SummonerState;
}

export const initialState: State = {
  summoner: summonerInitialState,
};
