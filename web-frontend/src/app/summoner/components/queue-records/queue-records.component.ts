import { Component, Input } from '@angular/core';
import { QueueRecords } from '../queue-records';

@Component({
  selector: 'app-queue-records',
  templateUrl: './queue-records.component.html',
  styleUrls: [ './queue-records.component.scss' ]
})
export class QueueRecordsComponent {

  @Input()
  queueRecords: QueueRecords;

  constructor() { }
}
