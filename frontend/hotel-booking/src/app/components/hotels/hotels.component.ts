import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // Asegúrate de importar FormsModule
import { HotelService } from '../../services/hotel.service';
import { Hotel } from '../../models/hotel.model';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-hotels',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './hotels.component.html',
  styleUrls: ['./hotels.component.scss'],
})
export class HotelsComponent implements OnInit {
  hotels: Hotel[] = [];
  isLoading: boolean = false;
  searchHotelName: string = ''; // Variable para el nombre del hotel en la búsqueda
  searchCity: string = ''; // Variable para la ciudad en la búsqueda

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

  async searchHotels() {
    this.isLoading = true;
    try {
      this.hotels = await firstValueFrom(
        this.hotelService.searchHotels(this.searchHotelName, this.searchCity)
      );
      this.isLoading = false;
    } catch (error) {
      console.error('Error searching hotels:', error);
      this.isLoading = false;
    }
  }

  async deleteHotel(hotelId: number) {
    try {
      const response = await firstValueFrom(this.hotelService.deleteHotel(hotelId));
      if (response.status === 204 || response.status === 200) {
        this.hotels = this.hotels.filter(hotel => hotel.id !== hotelId);
      } else {
        console.error('No se pudo eliminar el hotel.');
      }
    } catch (error) {
      console.error('Error eliminando el hotel:', error);
    }
  }

  editHotel(hotel: Hotel) {
    console.log('Editar hotel:', hotel);
    // Lógica para editar el hotel
  }
}
