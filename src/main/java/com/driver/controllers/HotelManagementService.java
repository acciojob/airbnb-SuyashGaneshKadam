package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class HotelManagementService {

    HotelManagementRepository repositoryObject = new HotelManagementRepository();
    public String addHotel(Hotel hotel)
    {
        return repositoryObject.addHotel(hotel);
    }
    public void addUser(User user)
    {
        repositoryObject.addUser(user);
    }
    public String getHotelWithMostFacilities()
    {
        HashMap<String, Hotel> hotelDb = repositoryObject.getHotelDb();
        if(hotelDb.size() == 0){ return ""; }
        int maxFacilities = 0;
        for(Hotel hotel : hotelDb.values())
        {
            maxFacilities = Math.max(maxFacilities, hotel.getFacilities().size());
        }
        if(maxFacilities == 0){ return ""; }
        List<String> hotelNames = new ArrayList<>();
        for(Hotel hotel : hotelDb.values())
        {
            if(maxFacilities == hotel.getFacilities().size())
            {
                hotelNames.add(hotel.getHotelName());
            }
        }
        Collections.sort(hotelNames);
        return hotelNames.get(0);
    }
}
