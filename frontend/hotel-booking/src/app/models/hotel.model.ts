export interface Hotel {
  id?: number;  
  hotelName: string;
  city: string;
  checkinDate: string;  // Si es tipo `Date`, asegúrate de convertirlo en el formato correcto
  pricePerNight: number;
}
