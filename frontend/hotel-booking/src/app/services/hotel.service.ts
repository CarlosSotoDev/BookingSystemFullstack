import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Hotel } from '../models/hotel.model';

@Injectable({
  providedIn: 'root' // Esto asegura que el servicio esté disponible en toda la aplicación
})
export class HotelService {
  
  // URL ajustada para interactuar con el API Gateway y Discovery Server
  private apiUrl = 'http://localhost:8762/hotelservice/api/v1/hotels';

  constructor(private http: HttpClient) { }

  // Obtener todos los hoteles
  getAllHotels(): Observable<Hotel[]> {
    return this.http.get<Hotel[]>(this.apiUrl);
  }

  // Crear un nuevo hotel
  createHotel(hotel: Hotel): Observable<Hotel> {
    return this.http.post<Hotel>(this.apiUrl, hotel);
  }

  // Obtener un hotel por ID
  getHotelById(id: number): Observable<Hotel> {
    return this.http.get<Hotel>(`${this.apiUrl}/${id}`);
  }

  // Actualizar un hotel existente
  updateHotel(id: number, hotel: Hotel): Observable<Hotel> {
    return this.http.put<Hotel>(`${this.apiUrl}/${id}`, hotel);
  }

  // Eliminar un hotel por ID
  deleteHotel(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
