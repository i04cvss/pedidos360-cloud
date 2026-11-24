
import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MsalService } from '@azure/msal-angular';
import { loginRequest } from './auth.config';
import { Router, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {

  constructor(
    private msalService: MsalService,
    private http: HttpClient,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.msalService.handleRedirectObservable().subscribe({
      next: (result) => {

        if (result?.account) {
          this.msalService.instance.setActiveAccount(result.account);
          this.router.navigateByUrl('/pedidos');
        }

        const account =
          this.msalService.instance.getActiveAccount() ??
          this.msalService.instance.getAllAccounts()[0];

        if (!account) {
          console.log('No hay una sesión activa.');
          return;
        }

        this.msalService.instance.setActiveAccount(account);

        console.log('Usuario autenticado:', account.username);

        this.msalService.acquireTokenSilent({
          ...loginRequest,
          account
        }).subscribe({
          next: (tokenResult) => {
            console.log('Token de acceso obtenido correctamente.');
            console.log('Permisos:', tokenResult.scopes);

            const payloadBase64 = tokenResult.accessToken.split('.')[1]
              .replace(/-/g, '+')
              .replace(/_/g, '/');

            const payload = JSON.parse(
              atob(payloadBase64.padEnd(Math.ceil(payloadBase64.length / 4) * 4, '='))
            );

            console.log('AUDIENCIA:', payload.aud);
            console.log('ISSUER:', payload.iss);

            this.http.get<any[]>('http://localhost:8080/api/pedidos').subscribe({
              next: (response) => {
                console.log('Pedidos recibidos desde el BFF:', response);
              },
              error: (error) => {
                console.error('Error al obtener los pedidos:', error);
              }
            });
          },
          error: (error) => {
            console.error('Error al obtener el token:', error);
          }
        });
      },
      error: (error) => {
        console.error('Error de autenticación:', error);
      }
    });
  }
}
