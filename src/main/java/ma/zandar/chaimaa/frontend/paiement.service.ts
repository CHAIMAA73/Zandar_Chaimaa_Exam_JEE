import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Paiement } from '../models/models';

@Injectable({ providedIn: 'root' })
export class PaiementService {
    private baseUrl = 'http://localhost:8080/api/paiements';

    constructor(private http: HttpClient) {}

    getAllPaiements(): Observable<Paiement[]> {
        return this.http.get<Paiement[]>(this.baseUrl);
    }

    getPaiement(id: number): Observable<Paiement> {
        return this.http.get<Paiement>(`${this.baseUrl}/${id}`);
    }

    getPaiementsByContrat(contratId: number): Observable<Paiement[]> {
        return this.http.get<Paiement[]>(`${this.baseUrl}/contrat/${contratId}`);
    }

    createPaiement(paiement: Paiement): Observable<Paiement> {
        return this.http.post<Paiement>(this.baseUrl, paiement);
    }

    deletePaiement(id: number): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/${id}`);
    }
}