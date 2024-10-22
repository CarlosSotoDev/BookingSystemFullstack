import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'; // HttpClient directamente
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Flight } from '../models/flight.model';  // Modelo

@Injectable({
  providedIn: 'root',
})
export class FlightService {
  private apiUrl = 'http://localhost:8762/fligthservice/api/v1/flights'; 

  constructor(private http: HttpClient) {}  // HttpClient inyectado

  getFlights(): Observable<Flight[]> {
    return this.http.get<Flight[]>(this.apiUrl).pipe(
      catchError((error) => {
        console.error('Error fetching flights:', error);
        return of([]);  // Retornar un array vacío en caso de error
      })
    );
  }

  deleteFlight(id: number): Observable<any> {
    const deleteUrl = `${this.apiUrl}/${id}`;
    return this.http.delete(deleteUrl).pipe(
      catchError((error) => {
        console.error('Error deleting flight:', error);
        return of({ error: true, message: 'Error deleting flight' }); 
      })
    );
  }


}
