import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Summoner } from './summoner';
import { AppConfig } from '../app-config';
import { HttpClient } from '@angular/common/http';
import { filter, map } from 'rxjs/operators';
import { ActivatedRoute, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class SummonerService {

  constructor(private httpClient: HttpClient, private route: ActivatedRoute, private router: Router, private appConfig: AppConfig) { }

  public loadSummoner(name: string): Observable<Summoner> {
    return this.httpClient.get<SummonerResponse>(
      `${this.appConfig.getBackendUrl()}/lol/summoner?name=${name}`
    ).pipe(
      map(response => this.convert(response))
    );
  }

  public getSummonerName(): Observable<string> {
    return this.route.queryParams.pipe(
      filter(params => params.summoner),
      map(params => params.summoner),
    );
  }

  public setSummonerName(name: string): void {
    this.router.navigate([], {
      queryParams: {
        summoner: name,
      },
      relativeTo: this.route,
    });
  }

  private convert({ name, accountId, profileIconId, summonerLevel }: SummonerResponse): Summoner {
    return {
      name,
      accountId,
      profileIcon: `${this.appConfig.getBackendUrl()}/lol/profileIcon/${profileIconId}`,
      level: summonerLevel,
    };
  }
}

interface SummonerResponse {
  name: string;
  puuid: string;
  accountId: string;
  id: string;
  profileIconId: number;
  summonerLevel: number;
}
