import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-summoner-update',
  templateUrl: './summoner-update.component.html',
  styleUrls: [ './summoner-update.component.scss' ]
})
export class SummonerUpdateComponent {

  @Output()
  update = new EventEmitter<void>();

  constructor() { }

  public onUpdate() {
    this.update.emit();
  }
}
