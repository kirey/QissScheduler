import { Component, OnInit } from '@angular/core';

import { SchedulerService } from './scheduler.service';
import { Button } from 'primeng/button';

@Component({
    selector: 'app-scheduler',
    templateUrl: './scheduler.component.html',
    styleUrls: ['./scheduler.component.scss']
})
export class SchedulerComponent implements OnInit {

    constructor(public schedulerService: SchedulerService) { }

    jobs: any;

    ngOnInit() {
        // Get All Jobs
        this.schedulerService.getJobs()
            .subscribe(
                res => this.jobs = res,
                err => console.log(err)
            );
    }

}
