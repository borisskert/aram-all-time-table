import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { APP_CONFIG_PROVIDER, AppConfig } from './app-config';
import { AppStateModule } from './app-state.module';
import { SummonerModule } from './summoner/summoner.module';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    AppStateModule,
    SummonerModule,
  ],
  providers: [
    AppConfig, APP_CONFIG_PROVIDER
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule {}
