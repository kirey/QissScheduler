import { RouterModule, Routes } from '@angular/router';

// App Modules
import { LoginModule } from './login/login.module';
import { SchedulerModule } from './scheduler/scheduler.module';

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