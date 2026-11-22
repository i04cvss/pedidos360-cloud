
import { Component } from '@angular/core';
import { MsalService } from '@azure/msal-angular';
import { loginRequest } from '../../auth.config';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent {

  constructor(private msalService: MsalService) {}

  iniciarSesion(): void {
    this.msalService.loginRedirect(loginRequest).subscribe({
      error: (error) => {
        console.error('Error al iniciar sesión:', error);
      }
    });
  }
}
