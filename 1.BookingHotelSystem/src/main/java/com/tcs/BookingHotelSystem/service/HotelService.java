package com.tcs.BookingHotelSystem.service;

import com.tcs.BookingHotelSystem.model.Hotel;
import com.tcs.BookingHotelSystem.repository.HotelRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HotelService {

    @Autowired
    HotelRepository hotelRepository;

    //Allows inyect an EntityManager that allow control the life cycle of a transaction like a search
    @PersistenceContext
    private EntityManager entityManager;

    //Methods
    //Get all Hotel
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    //CreateHotel
    public Hotel registerHotel(String hotelName, String city, LocalDate checkinDate, BigDecimal pricePerNight) {
        // Check for null or empty values in the fields
        if (hotelName == null || hotelName.trim().isEmpty()) {
            throw new IllegalArgumentException("Hotel name cannot be empty");
        }
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City field cannot be empty");
        }
        if (checkinDate == null) {
            throw new IllegalArgumentException("Check-in date field cannot be empty");
        }
        if (pricePerNight == null || pricePerNight.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price per night cannot be negative");
        }
        Hotel hotel = new Hotel();
        hotel.setHotelName(hotelName);
        hotel.setCity(city);
        hotel.setCheckinDate(checkinDate);
        hotel.setPricePerNight(pricePerNight);
        return hotelRepository.save(hotel);
    }

        //DeleteHotelById
    public String deleteHotelById(int id) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if (hotel.isPresent()) {
            hotelRepository.deleteById(id);
            return "Hotel with id" + id + " was deleted";
        } else {
            return "Hotel with id" + id + " not found";
        }
    }

    //Update hotel by Id
    public String updateHotel(int id, String hotelName, String city, LocalDate checkinDate, BigDecimal pricePerNight) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if (hotel.isPresent()) {
            //Check for null or empty values in the fields
            if (hotelName == null || hotelName == "" || hotelName.trim().isEmpty()) {
                throw new IllegalArgumentException("Hotel name cannot be empty");
            }
            if (city == null || city == "" || city.trim().isEmpty()) {
                throw new IllegalArgumentException("City field cannot be empty");
            }
            if (checkinDate == null) {
                throw new IllegalArgumentException("checkinDate field cannot be empty");
            }
            if (pricePerNight == null || pricePerNight.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("PricePerNight field cannot be negative");
            }
            Hotel existingHotel = hotel.get();
            existingHotel.setHotelName(hotelName);
            existingHotel.setCity(city);
            existingHotel.setCheckinDate(checkinDate);
            existingHotel.setPricePerNight(pricePerNight);
            hotelRepository.save(existingHotel);
            return "Hotel with id" + id + " was updated";
        } else {
            return "Hotel with id" + id + " not found";
        }
    }

    //Dinamic Filters
    public List<Hotel> searchHotel(String hotelName, String city, BigDecimal minPrice, BigDecimal maxPrice, LocalDate startDate, LocalDate endDate) {
        // Inicializar CriteriaBuilder
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Hotel> query = cb.createQuery(Hotel.class);
        Root<Hotel> hotel = query.from(Hotel.class);

        // Lista de predicados para los filtros
        List<Predicate> predicates = new ArrayList<>();

        // Filtros opcionales
        if (hotelName != null && !hotelName.trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(hotel.get("hotelName")), "%" + hotelName.toLowerCase() + "%"));
        }
        if (city != null && !city.trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(hotel.get("city")), "%" + city.toLowerCase() + "%"));
        }

        // Filtros de fechas
        if (startDate != null && endDate != null) {
            predicates.add(cb.between(hotel.get("checkinDate"), startDate, endDate));
        } else if (startDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(hotel.get("checkinDate"), startDate));
        } else if (endDate != null) {
            predicates.add(cb.lessThanOrEqualTo(hotel.get("checkinDate"), endDate));
        }

        // Filtros de precio
        if (minPrice != null && maxPrice != null) {
            predicates.add(cb.between(hotel.get("pricePerNight"), minPrice, maxPrice));
        } else if (minPrice != null) {
            predicates.add(cb.greaterThanOrEqualTo(hotel.get("pricePerNight"), minPrice));
        } else if (maxPrice != null) {
            predicates.add(cb.lessThanOrEqualTo(hotel.get("pricePerNight"), maxPrice));
        }

        // Construcción de la consulta
        query.select(hotel).where(cb.and(predicates.toArray(new Predicate[0])));

        // Ejecución de la consulta
        TypedQuery<Hotel> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }


    //Finding Hotel By Id
    public Hotel getHotelById(int id) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if (hotel.isPresent()) {
            return hotel.get();
        } else {
            throw new RuntimeException("Hotel with id" + id + " not found");
        }
    }

    //Finding Hotel By HotelName
    public Hotel getHotelByHotelName(String hotelName) {
        Optional<Hotel> hotel = hotelRepository.findByHotelName(hotelName);
        if (hotel.isPresent()) {
            return hotel.get();
        } else {
            throw new RuntimeException("Hotel with id " + hotelName + " not found");
        }
    }

    //Finding Hotel By City
    public Hotel getHotelByCity(String city) {
        Optional<Hotel> hotel = hotelRepository.findByCity(city);
        if (hotel.isPresent()) {
            return hotel.get();
        } else {
            throw new RuntimeException("Hotel with id " + city + " not found");
        }
    }

    //Finding Hotel By Price
    public Hotel getHotelByPricePerNight(BigDecimal pricePerNight) {
        Optional<Hotel> hotel = hotelRepository.findByPricePerNight(pricePerNight);
        if (hotel.isPresent()) {
            return hotel.get();
        } else {
            throw new RuntimeException("Hotel with id " + pricePerNight + " not found");
        }
    }

}

