import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // Importa CommonModule
import { HotelService } from '../../services/hotel.service'; // Importa el servicio
import { Hotel } from '../../models/hotel.model'; // Importa el modelo Hotel
import { firstValueFrom } from 'rxjs'; // Utilizamos firstValueFrom para manejar observables

@Component({
  selector: 'app-hotels',
  standalone: true,
  imports: [CommonModule], // Asegúrate de incluir CommonModule aquí
  templateUrl: './hotels.component.html',
  styleUrls: ['./hotels.component.scss'],
})
export class HotelsComponent implements OnInit {
  hotels: Hotel[] = [];
  isLoading: boolean = false;

  constructor(private hotelService: HotelService) {}

  ngOnInit(): void {
    this.fetchHotels();
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
      await firstValueFrom(this.hotelService.deleteHotel(hotelId));
      this.hotels = this.hotels.filter(hotel => hotel.id !== hotelId);
    } catch (error) {
      console.error('Error eliminando el hotel:', error);
    }
  }

  editHotel(hotel: Hotel) {
    console.log('Editar hotel:', hotel);
  }
}
