import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LocalserverComponent } from './localserver/localserver.component';
import { CertificateComponent } from './certificate/certificate.component';


const routes: Routes = [
  { path: 'localserver', component: LocalserverComponent },
  { path: 'certificate', component: CertificateComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
