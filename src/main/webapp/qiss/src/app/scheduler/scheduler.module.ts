import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HttpClient } from '@angular/common/http';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { SchedulerComponent } from './scheduler.component';
import { SchedulerService } from './scheduler.service';

@NgModule({
    declarations: [
        SchedulerComponent
    ],
    imports: [
        CommonModule,
        BrowserAnimationsModule
    ],
    providers: [
        HttpClient,
        SchedulerService
    ]
})
export class SchedulerModule { }
