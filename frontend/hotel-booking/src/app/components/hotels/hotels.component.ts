import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // Importa FormsModule para usar [(ngModel)]
import { HotelService } from '../../services/hotel.service';
import { Hotel } from '../../models/hotel.model';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-hotels',
  standalone: true,
  imports: [CommonModule, FormsModule], // Asegúrate de que FormsModule esté importado
  templateUrl: './hotels.component.html',
  styleUrls: ['./hotels.component.scss'],
})
export class HotelsComponent implements OnInit {
  hotels: Hotel[] = [];
  isLoading: boolean = false;
  
  // Inicializa las variables para la búsqueda
  searchHotelName: string = '';
  searchCity: string = '';
  
  // Variables para el modal de creación
  showCreateModal: boolean = false;
  newHotel: Hotel = {
    hotelName: '',
    city: '',
    checkinDate: '',
    pricePerNight: 0
  }; // Asegúrate de inicializar todas las propiedades necesarias

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

  // Abrir el modal
  openCreateModal() {
    this.showCreateModal = true;
  }

  // Cerrar el modal
  closeCreateModal() {
    this.showCreateModal = false;
  }

  // Crear hotel
  async createHotel() {
    try {
      const createdHotel = await firstValueFrom(this.hotelService.createHotel(this.newHotel));
      this.hotels.push(createdHotel); // Agregar el nuevo hotel a la lista
      this.closeCreateModal(); // Cerrar el modal después de crear
    } catch (error) {
      console.error('Error creating hotel:', error);
    }
  }

  async deleteHotel(hotelId: number) {
    try {
      const response = await firstValueFrom(this.hotelService.deleteHotel(hotelId));
      if (response.status === 204 || response.status === 200) {
        this.hotels = this.hotels.filter((hotel) => hotel.id !== hotelId);
      } else {
        console.error('No se pudo eliminar el hotel.');
      }
    } catch (error) {
      console.error('Error eliminando el hotel:', error);
    }
  }
}
