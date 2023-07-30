package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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
    public int bookARoom(Booking booking)
    {
        String bookingId = UUID.randomUUID().toString();
        booking.setBookingId(bookingId);
        HashMap<String, Hotel> hotelDb = repositoryObject.getHotelDb();
        Hotel hotel = hotelDb.get(booking.getHotelName());
        if(booking.getNoOfRooms() > hotel.getAvailableRooms())
        {
            return -1;
        }
        else
        {
            hotel.setAvailableRooms(hotel.getAvailableRooms() - booking.getNoOfRooms());
            int totalAmountPaid = booking.getNoOfRooms() * hotel.getPricePerNight();
            booking.setAmountToBePaid(totalAmountPaid);
            repositoryObject.updateHotel(hotel);
            repositoryObject.addHotelAndBooking(booking);
            repositoryObject.addUserBooking(booking);
            return totalAmountPaid;
        }
    }
    public int getBookings(Integer aadharCard)
    {
        HashMap<Integer, List<Booking>> userBookingDb = repositoryObject.getUserBookingDb(aadharCard);
        if(userBookingDb.size() == 0) { return 0; }
        return userBookingDb.get(aadharCard).size();
    }
    public Hotel updateFacilities(List<Facility> newFacilities,String hotelName)
    {
        HashMap<String, Hotel> hotelDb = repositoryObject.getHotelDb();
        Hotel hotel = hotelDb.get(hotelName);
        List<Facility> oldFacilities = hotel.getFacilities();
        for(Facility facility : newFacilities)
        {
            if(!oldFacilities.contains(facility))
            {
                oldFacilities.add(facility);
            }
        }
        hotel.setFacilities(oldFacilities);
        repositoryObject.updateHotel(hotel);
        return hotel;
    }
}
