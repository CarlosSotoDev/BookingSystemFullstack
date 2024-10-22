import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlightService } from '../../services/flight.service'; // Importa el servicio de vuelos
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http'; // Importa HttpClient directamente

@Component({
  selector: 'app-flight',
  standalone: true,
  imports: [CommonModule], // Solo necesitas CommonModule aquí, Angular gestiona el resto
  templateUrl: './flights.component.html',
  styleUrls: ['./flights.component.scss'],
})
export class FlightComponent implements OnInit {
  flights$: Observable<any[]> = of([]);  // Inicializamos el observable
  private flightService = inject(FlightService);  // Inyectamos el servicio

  ngOnInit(): void {
    this.getFlights();  // Cargar los vuelos al inicializar el componente
  }

  getFlights() {
    this.flights$ = this.flightService.getFlights();  // Llamamos al servicio para obtener los vuelos
  }

  deleteFlight(id: number) {
    this.flightService.deleteFlight(id).subscribe({
      next: (response) => {
        console.log('Vuelo eliminado con éxito:', response);
        this.getFlights();  // Volvemos a cargar los vuelos
      },
      error: (error) => {
        console.error('Error eliminando el vuelo:', error);
      },
    });
  }

  // Agrega este método para la edición de vuelos
  editFlight(flight: any) {
    // Aquí puedes manejar la lógica de edición, por ejemplo, abrir un modal o redirigir a otra página.
    console.log('Editar vuelo:', flight);
    // Si estás usando un formulario o un modal, puedes inyectar el vuelo a editar y trabajar con los datos.
  }
}
