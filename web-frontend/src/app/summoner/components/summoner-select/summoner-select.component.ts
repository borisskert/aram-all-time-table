import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-summoner-select',
  templateUrl: './summoner-select.component.html',
  styleUrls: [ './summoner-select.component.scss' ]
})
export class SummonerSelectComponent implements OnInit {

  @Output()
  summonerSelected = new EventEmitter<string>();

  public summonerForm = new FormGroup({
    summonerName: new FormControl('', [ Validators.required ]),
  });

  constructor() { }

  ngOnInit() {
  }

  onSubmit() {
    this.summonerSelected.emit(this.summonerForm.value.summonerName);
  }
}
