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


    //Format Date
    formatDate(ts) {
        let date = new Date(ts);
        let d = date.getDate();
        let m:any = date.getMonth()+1;
        let y = date.getFullYear();
        let h = date.getHours();
        let min = date.getMinutes();
        let s = date.getSeconds();

        switch(m) {
            case 1:
            m = 'January';
            break;
            case 2:
            m = 'February';
            break;
            case 3:
            m = 'March';
            break;
            case 4:
            m = 'April';
            break;
            case 5:
            m = 'May';
            break;
            case 6:
            m = 'June';
            break;
            case 7:
            m = 'July';
            break;
            case 8:
            m = 'August';
            break;
            case 9:
            m = 'September';
            break;
            case 10:
            m = 'October';
            break;
            case 11:
            m = 'November';
            break;
            case 12:
            m = 'December';
            break;
        }

        return m + ' ' + d + ', ' + y + ' ' + h + ':' + min + ':' + s;
    }    

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
                res=> {
                    console.log(res);
                    this.jobs = res.data;
                    this.successMessage(res.message);
                    this.addJobModal.hide();
                },
                err => {
                    this.errorMessage(err);
                    this.addJobModal.hide();
                }
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
                    this.jobs = res.data;
                    this.successMessage(res.message);
                    this.editJobModal.hide();
                },
                err => {
                    console.log(err);
                    this.errorMessage(err);
                    this.editJobModal.hide();
                }
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
                    this.jobs = res.data;
                    this.successMessage(res.message);
                    this.deleteModal.hide();
                },
                err => {
                    console.log(err);
                    this.errorMessage(err);
                    this.deleteModal.hide();
                }
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
                err => {
                    console.log(err);
                    this.errorMessage(err);
                }
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
                err => {
                    console.log(err);
                    this.errorMessage(err);
                } 
            )
    }

    compare(a,b) {
        if (a.id < b.id)
            return -1;
        if (a.id > b.id)
            return 1;
        return 0;
    }

    //History Modal
    openHistoryModal(id) {
        this.historyModal.show();
        this.schedulerService.getHisory(id)
            .subscribe(
                res => {
                    console.log(res);
                    this.jobHistoryArray = res.data.sort(this.compare);
                },
                err => {
                    console.log(err)
                    this.errorMessage(err);
                }
            );
    }

    // Growl Messages
    successMessage(message) {
        this.messageService.add({severity:'success', summary:'Success', detail: message});
    }
    errorMessage(err) {
        this.messageService.add({severity:'error', summary:'Error', detail: err.error.message});
    }

    ngOnInit() {
        // Get All Jobs
        this.schedulerService.getJobs()
            .subscribe(
                res => {
                    this.jobs = res.data.sort(this.compare);
                    console.log(this.jobs);
                },
                err => console.log(err)
            );
    }
    //Getters for Form Contorls
    get jobName() { return this.jobForm.get('jobName'); }
    get cronExpression() { return this.jobForm.get('cronExpression'); }
}
