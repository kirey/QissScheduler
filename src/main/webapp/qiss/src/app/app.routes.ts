import { RouterModule, Routes } from '@angular/router';

//App Components
import { SchedulerComponent } from './scheduler/scheduler.component';
import { LoginComponent } from './login/login.component';

//Routes
const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'scheduler', component: SchedulerComponent },

    { path: '**', component: SchedulerComponent }
];

export { routes };