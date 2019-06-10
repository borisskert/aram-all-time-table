import { State } from './contract';
import { ActionReducerMap } from '@ngrx/store/src/models';
import { reducer as summonerReducer } from '../summoner/+state/reducer';

export const reducers: ActionReducerMap<State> = {
  summoner: summonerReducer,
};
