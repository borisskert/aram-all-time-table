import { APP_INITIALIZER, Injectable, Provider } from '@angular/core';
import { environment } from '../environments/environment';
import { throwError } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class AppConfig {

  private env: {
    [key: string]: string;
  } = null;

  constructor(private http: HttpClient) {}

  public getBackendUrl(): string {
    return this.getValue('NG_BACKEND_URL');
  }

  /**
   * Use to get a property of the env file
   */
  private getValue(key: string) {
    return this.env[key];
  }

  /**
   * This method loads "env.json" to get the current working environment variables
   */
  public load() {
    return new Promise((resolve, reject) => {
      this.tryToGetEnvJson(resolve);
    });
  }

  private tryToGetEnvJson(resolve) {
    this.http.get<any>(environment.envJson)
      .subscribe(
        envResponse => {
          this.env = envResponse;
          resolve(true);
          return true;
        },
        error => {
          console.log(`Configuration file "${environment.envJson}" could not be read`);
          resolve(true);
          return throwError(error);
        }
      );
  }
}

export const APP_CONFIG_PROVIDER: Provider = {
  provide: APP_INITIALIZER,
  useFactory: (config: AppConfig) => () => config.load(),
  deps: [ AppConfig ], multi: true
};

