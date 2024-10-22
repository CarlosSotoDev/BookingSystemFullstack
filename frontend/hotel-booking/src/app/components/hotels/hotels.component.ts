import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // Importa CommonModule para directivas como *ngIf y *ngFor
import { HotelService } from '../../services/hotel.service'; // Servicio para manejar los hoteles
import { Hotel } from '../../models/hotel.model';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-hotels',
  standalone: true,
  imports: [CommonModule], // Importa CommonModule para usar *ngIf y *ngFor
  templateUrl: './hotels.component.html',
  styleUrls: ['./hotels.component.scss'],
})
export class HotelsComponent implements OnInit {
  hotels: Hotel[] = [];
  isLoading: boolean = false;

  constructor(private hotelService: HotelService) {}

  ngOnInit(): void {
    this.fetchHotels(); // Cargar los hoteles al iniciar
  }

  async fetchHotels() {
    this.isLoading = true;
    try {
      this.hotels = await firstValueFrom(this.hotelService.getAllHotels());
      this.isLoading = false;
    } catch (error) {
      console.error('Error fetching hotels:', error);
      this.isLoading = false;
    }
  }

  async deleteHotel(hotelId: number) {
    try {
      const response = await firstValueFrom(this.hotelService.deleteHotel(hotelId));
      if (response.status === 204 || response.status === 200) {
        this.hotels = this.hotels.filter(hotel => hotel.id !== hotelId); // Filtra los hoteles eliminados
      } else {
        console.error('No se pudo eliminar el hotel.');
      }
    } catch (error) {
      console.error('Error eliminando el hotel:', error);
    }
  }

  editHotel(hotel: Hotel) {
    console.log('Editar hotel:', hotel);
    // Aquí puedes abrir un modal o redirigir a una página de edición
  }
}
