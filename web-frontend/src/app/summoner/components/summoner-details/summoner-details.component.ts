import { Component, Input, OnInit } from '@angular/core';
import { Summoner } from '../../summoner';

@Component({
  selector: 'app-summoner-details',
  templateUrl: './summoner-details.component.html',
  styleUrls: [ './summoner-details.component.scss' ]
})
export class SummonerDetailsComponent implements OnInit {

  @Input()
  summoner: Summoner;

  constructor() { }

  ngOnInit() {
  }

}
