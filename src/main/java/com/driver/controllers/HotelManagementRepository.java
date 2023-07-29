package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class HotelManagementRepository {
    HashMap<String, Hotel> hotelDb = new HashMap<>();
    HashMap<Integer, User> userDb = new HashMap<>();
    public String addHotel(Hotel hotel)
    {
        String hotelName = hotel.getHotelName();
        if(hotelDb.containsKey(hotelName))
        {
            return "FAILURE";
        }
        hotelDb.put(hotelName, hotel);
        return "SUCCESS";
    }
    public void addUser(User user)
    {
        userDb.put(user.getaadharCardNo(), user);
    }
    public HashMap<String, Hotel> getHotelDb()
    {
        return hotelDb;
    }
}
