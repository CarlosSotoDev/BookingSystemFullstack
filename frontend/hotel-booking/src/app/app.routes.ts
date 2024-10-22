import { Routes } from '@angular/router';
import { HotelsComponent } from './components/hotels/hotels.component';
import { FlightComponent } from './components/flights/flights.component';
import { WelcomeComponent } from './components/welcome/welcome.component';


export const routes: Routes = [
  { path: '', component: WelcomeComponent },
  { path: 'hotels', component: HotelsComponent },
  { path: 'flights', component: FlightComponent },
  { path: '**', redirectTo: '', pathMatch: 'full' },
];
