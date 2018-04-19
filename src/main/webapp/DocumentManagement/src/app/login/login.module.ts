import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';

import { LoginComponent } from './login.component';
import { routes } from './login.routes';
import { LoginService } from './login.service';

@NgModule({
    declarations: [
        LoginComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        HttpClientModule,
        ReactiveFormsModule
    ],
    providers: [
        LoginService
    ]
})
export class LoginModule { }
