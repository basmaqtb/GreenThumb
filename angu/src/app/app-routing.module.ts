import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './Components/login/login.component';
import { RegisterComponent } from './Components/register/register.component';
import { DashboardComponent } from './Components/dashboard/dashboard.component';
import { AllEquipementComponent } from './Components/all-equipement/all-equipement.component';
import { AddEquipementComponent } from './Components/add-equipement/add-equipement.component';
import { AllTachesComponent } from './Components/all-taches/all-taches.component';
import { AddTacheComponent } from './Components/add-tache/add-tache.component';
import { AllRendezvousComponent } from './Components/all-rendezvous/all-rendezvous.component';
import { AddrendezVousComponent } from './Components/addrendez-vous/addrendez-vous.component';
import { AllUsersComponent } from './Components/all-users/all-users.component';
import { AddUserComponent } from './Components/add-user/add-user.component';




// Import Equipement Detail Component

const routes: Routes = [  
  { path: 'register', component: RegisterComponent }, 
  { path: 'login', component: LoginComponent }, 
  { path: 'dashboard' , component: DashboardComponent},
  { path: 'equipements' , component: AllEquipementComponent},
  { path: 'taches' , component: AllTachesComponent},
  { path: 'equipements/add', component: AddEquipementComponent }, 
  { path: 'taches/add', component: AddTacheComponent }, 
  { path: 'taches/update/:id', component: AddTacheComponent},
  { path: 'taches/details/:id', component: AddTacheComponent},
  { path: 'rendezvous' , component: AllRendezvousComponent},
  { path: 'rendezvous/add', component: AddrendezVousComponent },
  { path: 'rendezvous/update/:id', component: AddrendezVousComponent},
  { path: 'rendezvous/details/:id', component: AddrendezVousComponent},
  { path: 'users' , component: AllUsersComponent},
  { path: 'users/add', component: AddUserComponent },
  { path: 'users/update/:id', component: AddUserComponent },



  

  


  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }