package com.booking;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static final List<String> availableHotels = new ArrayList<>(Arrays.asList(
            "Hotel Buenos Aires 1, Buenos Aires, Hotel, 2023-12-01, 2023-12-10, 1, 2, 1, 5, 100",
            "Hotel Buenos Aires 2, Buenos Aires, Hotel, 2023-12-01, 2023-12-10, 1, 2, 1, 4, 80",
            "Apartamento Buenos Aires 1, Buenos Aires, Apartamento, 2023-12-01, 2023-12-10, 1, 2, 1, 3, 60",
            "Finca Buenos Aires 1, Buenos Aires, Finca, 2023-12-01, 2023-12-10, 1, 2, 1, 4, 90",
            "Dia de Sol Buenos Aires 1, Buenos Aires, Dia de Sol, 2023-12-01, 2023-12-10, 1, 2, 1, 5, 120"
    ));

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter city: ");
        String city = scanner.nextLine();

        System.out.print("Enter accommodation type: ");
        String accommodationType = scanner.nextLine();

        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDate = scanner.nextLine();

        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endDate = scanner.nextLine();

        System.out.print("Enter number of adults: ");
        int adults = scanner.nextInt();

        System.out.print("Enter number of children: ");
        int children = scanner.nextInt();

        System.out.print("Enter number of rooms: ");
        int rooms = scanner.nextInt();

        try {
            List<String> hotels = findHotels(city, accommodationType, startDate, endDate, adults, children, rooms);
            if (!hotels.isEmpty()) {
                System.out.println("Available hotels:");
                for (String hotel : hotels) {
                    System.out.println(hotel);
                }
            } else {
                System.out.println("No hotels available.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        scanner.close();
    }

    public static List<String> findHotels(String city, String accommodationType, String startDate, String endDate, int adults, int children, int rooms) {
        List<String> validAccommodationTypes = Arrays.asList("Hotel", "Apartamento", "Finca", "Dia de Sol");

        String[] params = {city, accommodationType, startDate, endDate};
        String[] paramNames = {"City", "Accommodation type", "Start date", "End date"};

        for (int i = 0; i < params.length; i++) {
            if (params[i] == null || params[i].isEmpty()) {
                throw new IllegalArgumentException(paramNames[i] + " is required");
            }
        }

        if (!validAccommodationTypes.contains(accommodationType)) {
            throw new IllegalArgumentException("Invalid accommodation type");
        }

        if (adults < 1) {
            throw new IllegalArgumentException("At least one adult is required");
        }
        if (rooms < 1) {
            throw new IllegalArgumentException("At least one room is required");
        }
        if (children < 0) {
            throw new IllegalArgumentException("Children cannot be negative");
        }
        if (rooms < adults) {
            throw new IllegalArgumentException("There must be at least one adult per room");
        }

        List<String> matchingHotels = new ArrayList<>();
        for (String hotel : availableHotels) {
            String[] hotelDetails = hotel.split(", ");
            if (hotelDetails[1].equals(city) &&
                    hotelDetails[2].equals(accommodationType) &&
                    hotelDetails[3].equals(startDate) &&
                    hotelDetails[4].equals(endDate) &&
                    Integer.parseInt(hotelDetails[5]) == adults &&
                    Integer.parseInt(hotelDetails[6]) == children &&
                    Integer.parseInt(hotelDetails[7]) == rooms) {
                int days = (int) ((java.sql.Date.valueOf(endDate).getTime() - java.sql.Date.valueOf(startDate).getTime()) / (1000 * 60 * 60 * 24));
                if (days < 1) {
                    days = 1; // Ensure at least one day
                }
                int pricePerNight = Integer.parseInt(hotelDetails[9]);
                int totalPrice = days * pricePerNight;
                matchingHotels.add(String.format("Name: %s, Rating: %s, Price per night: %d, Total price: %d", hotelDetails[0], hotelDetails[8], pricePerNight, totalPrice));
            }
        }

        return matchingHotels;
    }
}