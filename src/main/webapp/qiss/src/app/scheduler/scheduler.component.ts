import { Component, OnInit, TemplateRef, ViewEncapsulation } from '@angular/core';

import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';

import { SchedulerService } from './scheduler.service';
import { Button } from 'primeng/button';

@Component({
    selector: 'app-scheduler',
    templateUrl: './scheduler.component.html',
    styleUrls: ['./scheduler.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class SchedulerComponent implements OnInit {

    constructor(public schedulerService: SchedulerService, private _modalService: BsModalService) { }

    jobs: any;
    modalRef: BsModalRef;
    config = {
        animated: true,
        keyboard: true,
        class: 'history-modal'
    };
    jobHistoryArray = [];

    // Start Job
    start(id) {
        this.schedulerService.startJob(id)
            .subscribe(
                res => console.log(res),
                err => console.log(err)
            )
    }

    // Stop Job
    stop(id) {
        this.schedulerService.stopJob(id)
            .subscribe(
                res => console.log(res),
                err => console.log(err)
            )
    }

    //History Modal
    openHistoryModal(history: TemplateRef<any>, id) {
        this.modalRef = this._modalService.show(history, this.config);

        this.schedulerService.getHisory(id)
            .subscribe(
                res => {
                    console.log(res);
                    this.jobHistoryArray = res;
                },
                err => console.log(err)
            );
    }

    ngOnInit() {
        // Get All Jobs
        this.schedulerService.getJobs()
            .subscribe(
                res => {
                    this.jobs = res;
                    console.log(res);
                },
                err => console.log(err)
            );
    }

}
