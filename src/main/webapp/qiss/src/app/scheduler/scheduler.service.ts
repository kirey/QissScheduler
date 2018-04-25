import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';

@Injectable()
export class SchedulerService {

    constructor(private _http: HttpClient) { }

    baseUrl = '/QISS/rest/scheduler/';

    getJobs(): Observable<any> {
        return this._http.get(this.baseUrl + 'jobs');
    }

    startJob(id): Observable<any> {
        return this._http.post(this.baseUrl + 'startJob/' + id, null);
    }

    stopJob(id): Observable<any> {
        return this._http.post(this.baseUrl + 'stopJob/' + id, null);
    }

    getHisory(id): Observable<any> {
        return this._http.get(this.baseUrl + 'jobHistory/' + id);
    }

}
