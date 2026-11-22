
import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login';
import { PedidosComponent } from './pedidos/pedidos';
import { MsalGuard } from '@azure/msal-angular';

export const routes: Routes = [
  {
    path: '',
    component: LoginComponent
  },
  {
    path: 'pedidos',
    component: PedidosComponent,
    canActivate: [MsalGuard]
  },
  {
    path: '**',
    redirectTo: ''
  }
];
