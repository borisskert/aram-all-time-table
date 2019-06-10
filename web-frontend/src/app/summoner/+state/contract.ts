import { Summoner } from '../summoner';

export interface SummonerState {
  summoner: Summoner;
}

export const initialState: SummonerState = {
  summoner: null,
};
