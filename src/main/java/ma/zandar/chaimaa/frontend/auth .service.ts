import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { AuthRequest, AuthResponse } from '../models/models';

@Injectable({ providedIn: 'root' })
export class AuthService {
    private baseUrl = 'http://localhost:8080/api/auth';

    constructor(private http: HttpClient, private router: Router) {}

    login(credentials: AuthRequest): Observable<AuthResponse> {
        return this.http.post<AuthResponse>(`${this.baseUrl}/login`, credentials).pipe(
            tap(response => {
                localStorage.setItem('token', response.token);
                localStorage.setItem('username', response.username);
                localStorage.setItem('roles', JSON.stringify(response.roles));
            })
        );
    }

    logout(): void {
        localStorage.clear();
        this.router.navigate(['/login']);
    }

    getToken(): string | null {
        return localStorage.getItem('token');
    }

    getUsername(): string | null {
        return localStorage.getItem('username');
    }

    getRoles(): string[] {
        const roles = localStorage.getItem('roles');
        return roles ? JSON.parse(roles) : [];
    }

    isLoggedIn(): boolean {
        return !!this.getToken();
    }

    hasRole(role: string): boolean {
        return this.getRoles().includes(role);
    }

    isAdmin(): boolean {
        return this.hasRole('ROLE_ADMIN');
    }

    isEmploye(): boolean {
        return this.hasRole('ROLE_EMPLOYE') || this.isAdmin();
    }
}