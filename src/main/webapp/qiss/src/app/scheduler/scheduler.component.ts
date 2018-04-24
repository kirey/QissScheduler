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

    start(id) {
        this.schedulerService.startJob(id)
            .subscribe(
                res => console.log(res),
                err => console.log(err)
            )
    }
    stop(id) {
        this.schedulerService.stopJob(id)
            .subscribe(
                res => console.log(res),
                err => console.log(err)
            )
    }

    ngOnInit() {
        // Get All Jobs
        this.schedulerService.getJobs()
            .subscribe(
                res => {
                    this.jobs = res;
                    // console.log(res);
                },
                err => console.log(err)
            );
    }

}
