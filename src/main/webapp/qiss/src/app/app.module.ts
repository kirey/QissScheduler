import { BrowserModule } from '@angular/platform-browser';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';

import { routes } from './app.routes';

import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// PrimeNG
import { ButtonModule } from 'primeng/primeng';
import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import { GrowlModule } from 'primeng/growl';
import { MessageService } from 'primeng/components/common/messageservice';

// NGX BOOTSTRAP
import { TooltipModule } from 'ngx-bootstrap';
import { ModalModule } from 'ngx-bootstrap';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { SchedulerComponent } from './scheduler/scheduler.component';
import { RouterModule } from '@angular/router';
import { LoginService } from './login/login.service';
import { SchedulerService } from './scheduler/scheduler.service';

@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        SchedulerComponent
    ],
    imports: [
        RouterModule.forRoot(routes, { useHash: true }),
        BrowserModule,
        HttpClientModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        FormsModule,
        ButtonModule,
        TableModule,
        DialogModule,
        GrowlModule,
        TooltipModule.forRoot(),
        ModalModule.forRoot()
    ],
    providers: [
        HttpClientModule,
        LoginService,
        SchedulerService,
        MessageService
    ],
    schemas: [
        CUSTOM_ELEMENTS_SCHEMA
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
