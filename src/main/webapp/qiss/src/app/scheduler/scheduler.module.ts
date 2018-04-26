import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HttpClient } from '@angular/common/http';

import { DialogModule } from 'primeng/dialog';
import { ModalDirective } from 'ngx-bootstrap/modal';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { SchedulerComponent } from './scheduler.component';
import { SchedulerService } from './scheduler.service';

@NgModule({
    declarations: [
        SchedulerComponent
    ],
    imports: [
        CommonModule,
        BrowserAnimationsModule,
        DialogModule
    ],
    providers: [
        HttpClient,
        SchedulerService
    ]
})
export class SchedulerModule { }
