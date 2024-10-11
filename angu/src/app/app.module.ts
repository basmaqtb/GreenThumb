import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AuthenticationService } from './Services/auth.service';
import { LoginComponent } from './Components/login/login.component';
import { RegisterComponent } from './Components/register/register.component';
import { AddEquipementComponent } from './Components/add-equipement/add-equipement.component';
import { AllEquipementComponent } from './Components/all-equipement/all-equipement.component';
import { AllTachesComponent } from './Components/all-taches/all-taches.component';
import { AddTacheComponent } from './Components/add-tache/add-tache.component';
import { AllRendezvousComponent } from './Components/all-rendezvous/all-rendezvous.component';
import { AddrendezVousComponent } from './Components/addrendez-vous/addrendez-vous.component';
import { AllUsersComponent } from './Components/all-users/all-users.component';
import { AddUserComponent } from './Components/add-user/add-user.component';


@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    AddEquipementComponent,
    AllEquipementComponent,
    AllTachesComponent,
    AddTacheComponent,
    AllRendezvousComponent,
    AddrendezVousComponent,
    AllUsersComponent,
    AddUserComponent 
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule  // Make sure this is imported
  ],
  providers: [AuthenticationService], 
  bootstrap: [AppComponent]
})
export class AppModule { }