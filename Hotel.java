package com.example.btloop;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private List<Room>  rooms;
    public Hotel(){
        rooms= new ArrayList<Room>();
    }


    public boolean addRoom(Room room) {
        //Check if room already exists
        for (Room r : rooms) {
            if (r.getNumber() == room.getNumber()) {
                return false;
            }
        }
        rooms.add(room);
        return true;
    }
    public String removeRoom(int roomNumber){
        for(int i=0;i<rooms.size();i++){
            if(rooms.get(i).getNumber()==roomNumber){
                rooms.remove(i);
                return "Room " + roomNumber + " removed";
            }
        }
        return "Room " + roomNumber + " not found";
    }

    public String updateRoom(int roomNumber, Double area, Integer floor, String status) {
        for (Room room : rooms) {
            if (room.getNumber() == roomNumber) {
                if (area != null) {
                    room.setArea(area);
                }
                if (floor != null) {
                    room.setFloor(floor);
                }
                if (status != null) {
                    room.setStatus(status);
                }
                return "Room " + roomNumber + " updated";
            }
        }
        return "Room " + roomNumber + " not found";
    }

    public List<Room> showAllRooms(){
        return rooms;
    }

    public List<Room> searchRoom(Double area, Integer floor, String roomType, String status) {
        List<Room> result = new ArrayList<Room>();
        for (Room room : rooms) {

            //check roomtype
            String currRoomType;
            if (room instanceof StandardRoom){
                currRoomType = "standard";
            } else {
                currRoomType = "premium";
            }

            if (area != null && room.getArea() != area) {
                continue;
            }
            if (floor != null && room.getFloor() != floor) {
                continue;
            }
            if (roomType != null && !currRoomType.equals(roomType)) {
                continue;
            }

            if (status != null && !room.getStatus().equals(status)) {
                continue;
            }
            result.add(room);
        }
        return result;
    }
    public List<Room> searchRoomByPrice(double maxPrice) {
        List<Room> result = new ArrayList<Room>();
        for (Room room : rooms) {
            if (room.rentalPrice() <= maxPrice) {
                result.add(room);
            }
        }
        return result;
    }
    public double currentRevenue() {
        double revenue = 0;
        for (Room room : rooms) {
            if (room.getStatus().equals("rented")) {
                revenue += room.rentalPrice();
            }
        }
        return revenue;
    }

    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        Room room1 = new StandardRoom(1, 20, 2,  "available");
        Room room2 = new PremiumRoom(2, 25, 3,  "rented");
        Room room3 = new StandardRoom(3, 30, 4,  "rented");
        Room room4 = new PremiumRoom(4, 30, 4,  "rented");

        // Adding rooms
        System.out.println(hotel.addRoom(room1)); // prints "true"
        System.out.println(hotel.addRoom(room2)); // prints "true"
        System.out.println(hotel.addRoom(room3)); // prints "true"
        System.out.println(hotel.addRoom(room4)); // prints "true"

        // Attempting to add a duplicate room
        Room duplicateRoom = new StandardRoom(1, 20, 2,  "available");
        System.out.println(hotel.addRoom(duplicateRoom)); // prints "false"

        // Removing a room
        System.out.println(hotel.removeRoom(2)); // prints "Room 2 removed"
        // Attempting to remove a non-existent room
        System.out.println(hotel.removeRoom(5)); // prints "Room 4 not found"

        // Updating a room
        System.out.println(hotel.updateRoom(1, 22.0, 2, "rented")); // prints "Room 1 updated"
        // Attempting to update a non-existent room
        System.out.println(hotel.updateRoom(5, 22.0, 2,  "rented")); // prints "Room 5 not found"

        // Showing all rooms
        for (Room room : hotel.showAllRooms()) {
            System.out.println(room);
        }

        // Searching for rooms by area, floor, room type, and status
        List<Room> searchResults1 = hotel.searchRoom(22.0, 2, "standard", "rented");
        if (searchResults1.size() != 0){
            for (Room room : searchResults1) {
                System.out.println(room); // search succeed
            }
        } else {
            System.out.println("No room found");
        }

        List<Room> searchResults2 = hotel.searchRoom(22.0, 2, "premium", "rented");
        if (searchResults2.size() != 0){
            for (Room room : searchResults2) {
                System.out.println(room);
            }
        } else {
            System.out.println("No room found"); // search fail
        }

        //Searching for rooms by rental price
        List<Room> searchResultsByPrice = hotel.searchRoomByPrice(30);
        for (Room room : searchResultsByPrice) {
            System.out.println(room);
        }
        //Calculating current revenue
        System.out.println("Current Revenue: " + hotel.currentRevenue());
    }
}