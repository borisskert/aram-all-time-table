import { Component, Input, OnInit } from '@angular/core';
import { QueueStatistics } from '../queue-statistics';

@Component({
  selector: 'app-queue-statistics',
  templateUrl: './queue-statistics.component.html',
  styleUrls: [ './queue-statistics.component.scss' ]
})
export class QueueStatisticsComponent implements OnInit {

  @Input()
  queueStatistics: QueueStatistics;

  constructor() { }

  ngOnInit() {
  }
}
