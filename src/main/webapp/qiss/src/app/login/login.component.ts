import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router'

import { LoginService } from './login.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

    constructor(public formBuilder: FormBuilder, public loginService: LoginService, public router: Router) { }

    loginForm: FormGroup;

    loginUser() {
        let obj = {
            username: this.username.value,
            password: this.password.value
        }

        // this.loginService.login(obj)
        //     .subscribe(
        //         res => this.router.navigate(['/home']),
        //         err => console.log(err)
        //     );
    }

    ngOnInit() {
        this.loginForm = this.formBuilder.group({
            username: ['', Validators.required],
            password: ['', Validators.required]
        })

    }

    //Getters for Form Contorls
    get username() { return this.loginForm.get('username'); }
    get password() { return this.loginForm.get('password'); }

}
