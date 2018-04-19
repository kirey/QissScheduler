import { RouterModule, Routes } from '@angular/router';

// App Modules
import { LoginModule } from './login/login.module';

//App Components
import { HomeComponent } from './home/home.component';

//Routes
const routes: Routes = [
    { path: 'login', loadChildren: () => LoginModule },

    { path: '**', component: HomeComponent }
];

export { routes };