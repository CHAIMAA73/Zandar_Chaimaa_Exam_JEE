import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ContratAssurance } from '../models/models';

@Injectable({ providedIn: 'root' })
export class ContratService {
    private baseUrl = 'http://localhost:8080/api/contrats';

    constructor(private http: HttpClient) {}

    getAllContrats(): Observable<ContratAssurance[]> {
        return this.http.get<ContratAssurance[]>(this.baseUrl);
    }

    getContrat(id: number): Observable<ContratAssurance> {
        return this.http.get<ContratAssurance>(`${this.baseUrl}/${id}`);
    }

    getContratsByClient(clientId: number): Observable<ContratAssurance[]> {
        return this.http.get<ContratAssurance[]>(`${this.baseUrl}/client/${clientId}`);
    }

    getContratsByStatut(statut: string): Observable<ContratAssurance[]> {
        return this.http.get<ContratAssurance[]>(`${this.baseUrl}/statut/${statut}`);
    }

    createContrat(contrat: ContratAssurance): Observable<ContratAssurance> {
        return this.http.post<ContratAssurance>(this.baseUrl, contrat);
    }

    updateContrat(id: number, contrat: ContratAssurance): Observable<ContratAssurance> {
        return this.http.put<ContratAssurance>(`${this.baseUrl}/${id}`, contrat);
    }

    validerContrat(id: number): Observable<ContratAssurance> {
        return this.http.patch<ContratAssurance>(`${this.baseUrl}/${id}/valider`, {});
    }

    resilierContrat(id: number): Observable<ContratAssurance> {
        return this.http.patch<ContratAssurance>(`${this.baseUrl}/${id}/resilier`, {});
    }

    deleteContrat(id: number): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/${id}`);
    }
}
