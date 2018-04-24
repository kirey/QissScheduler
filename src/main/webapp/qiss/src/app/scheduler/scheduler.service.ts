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
}
