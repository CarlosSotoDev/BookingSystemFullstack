package com.tcs.graphqlgateway.resolver.query;

import com.tcs.graphqlgateway.client.HotelClient;
import com.tcs.graphqlgateway.dto.Hotel;
import com.tcs.graphqlgateway.dto.HotelDTO;
import org.springframework.beans.factory.aot.BeanRegistrationExcludeFilter;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class HotelQueryResolver {

    private final HotelClient hotelClient;

    public HotelQueryResolver(HotelClient hotelClient) {
        this.hotelClient = hotelClient;
    }

    @QueryMapping
    public List<Hotel> hotels(@Argument String city, @Argument BigDecimal maxPrice) {
        List<HotelDTO> base = (city == null || city.isBlank())
                ? hotelClient.getAll()
                : hotelClient.byCity(city);

        return base.stream()
                .filter(h -> maxPrice == null || h.pricePerNight().compareTo(maxPrice) <= 0)
                .map(Hotel::from)
                .toList();
    }
}