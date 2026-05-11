import { Component } from '@angular/core';
import { AuthService } from './services/auth.service';

@Component({
    selector: 'app-root',
    template: `
    <app-navbar *ngIf="authService.isLoggedIn()"></app-navbar>
    <div [class]="authService.isLoggedIn() ? 'container-fluid mt-4' : ''">
      <router-outlet></router-outlet>
    </div>
  `
})
export class AppComponent {
    constructor(public authService: AuthService) {}
}