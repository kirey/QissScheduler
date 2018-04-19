import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable'

@Injectable()
export class LoginService {

    constructor(public http: HttpClient) { }

    public url: string = 'authentication';

    public login(data): Observable<any> {
        return this.http.post(this.url, data);
    }
}
