import { Routes } from '@angular/router';
import { LandingpageComponent } from './pages/landingpage/landingpage.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ForgotpasswordComponent } from './pages/forgotpassword/forgotpassword.component';

export const routes: Routes = [
    { path: '', redirectTo: 'home', pathMatch: 'full' },
    { path: 'home', component: LandingpageComponent },
    { path: 'login', component: LoginComponent },
    { path: 'resetpassword', component: ForgotpasswordComponent },
    { path: 'register', component: RegisterComponent }
];
