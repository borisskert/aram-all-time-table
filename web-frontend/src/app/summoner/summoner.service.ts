import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Summoner } from './summoner';
import { AppConfig } from '../app-config';
import { HttpClient } from '@angular/common/http';
import { filter, map } from 'rxjs/operators';
import { ActivatedRoute, Router } from '@angular/router';
import { QueueStatistics } from './components/queue-statistics';
import { QueueRecords } from './components/queue-records';

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

  public loadQueueStatistics(name: string): Observable<QueueStatistics> {
    return this.httpClient.get<QueueStatisticsResponse>(
      `${this.appConfig.getBackendUrl()}/lol/queuestatistics?summoner=${name}`
    ).pipe(
      map(response => response.queueStatistics.ARAM)
    );
  }

  public loadQueueRecords(name: string): Observable<QueueRecords> {
    return this.httpClient.get<QueueRecords>(
      `${this.appConfig.getBackendUrl()}/lol/records?summoner=${name}`
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

interface QueueStatisticsResponse {
  queueStatistics: {
    ARAM: {
      victories: number;
      defeats: number;
      games: number;
      winRate: number;
    };
  };
}
