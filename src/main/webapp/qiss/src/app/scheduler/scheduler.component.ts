import { Component, OnInit, TemplateRef, ViewEncapsulation, ViewChild } from '@angular/core';

import { BsModalService, ModalDirective } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';

import { Message } from 'primeng/components/common/api';
import { MessageService } from 'primeng/components/common/messageservice';

import { SchedulerService } from './scheduler.service';
import { Button } from 'primeng/button';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
    moduleId: module.id,
    selector: 'app-scheduler',
    templateUrl: './scheduler.component.html',
    styleUrls: ['./scheduler.component.scss'],
    encapsulation: ViewEncapsulation.None,
    providers: [BsModalService]
})
export class SchedulerComponent implements OnInit {

    constructor(
        public schedulerService: SchedulerService, 
        private _modalService: BsModalService, 
        public formBuilder: FormBuilder,  
        private messageService: MessageService) { }

    jobs: any;
    jobForm: FormGroup;
    jobHistoryArray = [];
    currentStatus: string;
    currentJob: any;
    @ViewChild('addJobModal') addJobModal: ModalDirective;
    @ViewChild('editJobModal') editJobModal: ModalDirective;
    @ViewChild('historyModal') historyModal: ModalDirective;
    @ViewChild('deleteModal') deleteModal: ModalDirective;

    // Add Job Modal
    openAddJobModal() {
        this.addJobModal.show();
        this.jobForm = this.formBuilder.group({
            jobName: ['', Validators.required],
            cronExpression: ['', Validators.required]
        });
    }

    // Add Job
    addJob() {
        let obj = {
            jobName: this.jobName.value,
            cronExpression: this.cronExpression.value,
            status: null
        }
        this.schedulerService.addJob(obj)
            .subscribe(
                res => {
                    console.log(res);
                    this.jobs = res;
                    this.successMessage('Job successfully added.');
                    this.addJobModal.hide();
                },
                err => console.log(err)
            );
    };

    // Edit Job Modal
    openEditJobModal(job) {
        this.editJobModal.show();
        this.jobForm = this.formBuilder.group({
            jobName: [job.jobName, Validators.required],
            cronExpression: [job.cronExpression, Validators.required]
        });
        this.currentJob = job;
        this.currentStatus = job.status;
    }

    // Edit Job
    editJob() {
        let obj = {
            jobName: this.jobName.value,
            cronExpression: this.cronExpression.value,
            status: this.currentStatus,
            id: this.currentJob.id
        }
        this.schedulerService.editJob(obj)
            .subscribe(
                res => {
                    console.log(res);
                    this.jobs = res;
                    this.successMessage('Job successfully edited.');
                    this.editJobModal.hide();
                },
                err => console.log(err)
            );
    };

    // Delete Job Modal
    openDeleteJobModal(job) {
        this.currentJob = job;
        this.deleteModal.show();
    }

    // Delete Job
    deleteJob() {
        this.schedulerService.deleteJob(this.currentJob.id)
            .subscribe(
                res => {
                    console.log(res);
                    this.jobs = res;
                    this.successMessage('Job successfully deleted.');
                    this.deleteModal.hide();
                },
                err => console.log(err)
            );
    }

    // Start Job
    start(job) {
        this.schedulerService.startJob(job.id)
            .subscribe(
                res => {
                    console.log(res);
                    this.successMessage(res.message);
                    return job.status = 'ACTIVE';
                },
                err => console.log(err)
            )
    }

    // Stop Job
    stop(job) {
        this.schedulerService.stopJob(job.id)
            .subscribe(
                res => {
                    console.log(res);
                    this.successMessage(res.message);
                    return job.status = 'INACTIVE';
                },
                err => console.log(err)
            )
    }

    //History Modal
    openHistoryModal(id) {
        this.historyModal.show();
        this.schedulerService.getHisory(id)
            .subscribe(
                res => {
                    console.log(res);
                    this.jobHistoryArray = res;
                },
                err => console.log(err)
            );
    }

    // Growl Messages
    successMessage(message) {
        this.messageService.add({severity:'success', summary:'Success', detail: message});
    }
    errorMessage(message) {
        this.messageService.add({severity:'error', summary:'Error', detail: message});
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
    //Getters for Form Contorls
    get jobName() { return this.jobForm.get('jobName'); }
    get cronExpression() { return this.jobForm.get('cronExpression'); }
}
