import { Component } from '@angular/core';
import { AppConfig } from './app-config';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: [ './app.component.scss' ]
})
export class AppComponent {
  public constructor(private appConfig: AppConfig) {
  }

  public get backendUrl() {
    return this.appConfig.getBackendUrl();
  }
}
