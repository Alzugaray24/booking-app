package com.booking;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Main {
    private static final List<String> availableHotels = new ArrayList<>(Arrays.asList(
            "Hotel Buenos Aires 1, Buenos Aires, Hotel, 2023-12-01, 2023-12-10, 1, 2, 1, 5, 100",
            "Hotel Buenos Aires 2, Buenos Aires, Hotel, 2023-12-01, 2023-12-10, 1, 2, 1, 4, 80",
            "Apartamento Buenos Aires 1, Buenos Aires, Apartamento, 2023-12-01, 2023-12-10, 1, 2, 1, 3, 60",
            "Finca Buenos Aires 1, Buenos Aires, Finca, 2023-12-01, 2023-12-10, 1, 2, 1, 4, 90",
            "Dia de Sol Buenos Aires 1, Buenos Aires, Dia de Sol, 2023-12-01, 2023-12-10, 1, 2, 1, 5, 120",
            "Hotel Madrid 1, Madrid, Hotel, 2023-12-01, 2023-12-10, 1, 2, 1, 5, 110",
            "Hotel Madrid 2, Madrid, Hotel, 2023-12-01, 2023-12-10, 1, 2, 1, 4, 90",
            "Apartamento Madrid 1, Madrid, Apartamento, 2023-12-01, 2023-12-10, 1, 2, 1, 3, 70",
            "Finca Madrid 1, Madrid, Finca, 2023-12-01, 2023-12-10, 1, 2, 1, 4, 100",
            "Dia de Sol Madrid 1, Madrid, Dia de Sol, 2023-12-01, 2023-12-10, 1, 2, 1, 5, 130",
            "Hotel Paris 1, Paris, Hotel, 2023-12-01, 2023-12-10, 1, 2, 1, 5, 120",
            "Hotel Paris 2, Paris, Hotel, 2023-12-01, 2023-12-10, 1, 2, 1, 4, 100",
            "Apartamento Paris 1, Paris, Apartamento, 2023-12-01, 2023-12-10, 1, 2, 1, 3, 80",
            "Finca Paris 1, Paris, Finca, 2023-12-01, 2023-12-10, 1, 2, 1, 4, 110",
            "Dia de Sol Paris 1, Paris, Dia de Sol, 2023-12-01, 2023-12-10, 1, 2, 1, 5, 140",
            "Hotel New York 1, New York, Hotel, 2023-12-01, 2023-12-10, 1, 2, 1, 5, 150",
            "Hotel New York 2, New York, Hotel, 2023-12-01, 2023-12-10, 1, 2, 1, 4, 130",
            "Apartamento New York 1, New York, Apartamento, 2023-12-01, 2023-12-10, 1, 2, 1, 3, 100",
            "Finca New York 1, New York, Finca, 2023-12-01, 2023-12-10, 1, 2, 1, 4, 140",
            "Dia de Sol New York 1, New York, Dia de Sol, 2023-12-01, 2023-12-10, 1, 2, 1, 5, 160",
            "Hotel Tokyo 1, Tokyo, Hotel, 2023-12-01, 2023-12-10, 1, 2, 1, 5, 170",
            "Hotel Tokyo 2, Tokyo, Hotel, 2023-12-01, 2023-12-10, 1, 2, 1, 4, 150",
            "Apartamento Tokyo 1, Tokyo, Apartamento, 2023-12-01, 2023-12-10, 1, 2, 1, 3, 120",
            "Finca Tokyo 1, Tokyo, Finca, 2023-12-01, 2023-12-10, 1, 2, 1, 4, 160",
            "Dia de Sol Tokyo 1, Tokyo, Dia de Sol, 2023-12-01, 2023-12-10, 1, 2, 1, 5, 180"
    ));

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String city = selectCity(scanner);
        String accommodationType = selectAccommodationType(scanner);

        System.out.print("Ingrese la fecha de inicio (YYYY-MM-DD): ");
        String startDate = scanner.nextLine();

        System.out.print("Ingrese la fecha de fin (YYYY-MM-DD): ");
        String endDate = scanner.nextLine();

        System.out.print("Ingrese el número de adultos: ");
        int adults = scanner.nextInt();

        System.out.print("Ingrese el número de niños: ");
        int children = scanner.nextInt();

        System.out.print("Ingrese el número de habitaciones: ");
        int rooms = scanner.nextInt();

        try {
            List<String> hotels = findHotels(city, accommodationType, startDate, endDate, adults, children, rooms);
            if (!hotels.isEmpty()) {
                System.out.println("Hoteles disponibles:");
                for (String hotel : hotels) {
                    System.out.println(hotel);
                }
            } else {
                System.out.println("No hay hoteles disponibles.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        scanner.close();
    }

    private static String selectCity(Scanner scanner) {
        System.out.println("Seleccione la ciudad:");
        System.out.println("1. Buenos Aires");
        System.out.println("2. Madrid");
        System.out.println("3. Paris");
        System.out.println("4. New York");
        System.out.println("5. Tokyo");
        int cityChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (cityChoice) {
            case 1: return "Buenos Aires";
            case 2: return "Madrid";
            case 3: return "Paris";
            case 4: return "New York";
            case 5: return "Tokyo";
            default:
                System.out.println("Elección inválida. Predeterminado a Buenos Aires.");
                return "Buenos Aires";
        }
    }

    private static String selectAccommodationType(Scanner scanner) {
        System.out.println("Seleccione el tipo de alojamiento:");
        System.out.println("1. Hotel");
        System.out.println("2. Apartamento");
        System.out.println("3. Finca");
        System.out.println("4. Dia de Sol");
        int accommodationChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (accommodationChoice) {
            case 1: return "Hotel";
            case 2: return "Apartamento";
            case 3: return "Finca";
            case 4: return "Dia de Sol";
            default:
                System.out.println("Elección inválida. Predeterminado a Hotel.");
                return "Hotel";
        }
    }

    public static List<String> findHotels(String city, String accommodationType, String startDate, String endDate, int adults, int children, int rooms) {
        validateInputs(city, accommodationType, startDate, endDate, adults, children, rooms);

        List<String> matchingHotels = new ArrayList<>();
        for (String hotel : availableHotels) {
            String[] hotelDetails = hotel.split(", ");
            if (isMatchingHotel(hotelDetails, city, accommodationType, startDate, endDate, adults, children, rooms)) {
                int days = calculateDays(startDate, endDate);
                int pricePerNight = Integer.parseInt(hotelDetails[9]);
                int totalPrice = calculateTotalPrice(days, pricePerNight, rooms);
                double discountOrIncrease = calculateDiscountOrIncrease(startDate, endDate, totalPrice);

                if (accommodationType.equals("Dia de Sol")) {
                    String activities = "Actividades: Natación, Tomar el sol, Voleibol de playa";
                    String meals = "Incluye: Almuerzo y refrigerios";
                    matchingHotels.add(String.format("Nombre: %s, Calificación: %s, Precio por día: %d, Precio total: %.2f, Descuento/Aumento: %.2f, %s, %s",
                            hotelDetails[0], hotelDetails[8], pricePerNight, (double) totalPrice, discountOrIncrease, activities, meals));
                } else {
                    matchingHotels.add(String.format("Nombre: %s, Calificación: %s, Precio por noche: %d, Precio total: %.2f, Descuento/Aumento: %.2f",
                            hotelDetails[0], hotelDetails[8], pricePerNight, (double) totalPrice, discountOrIncrease));
                }
            }
        }

        return matchingHotels;
    }

    private static void validateInputs(String city, String accommodationType, String startDate, String endDate, int adults, int children, int rooms) {
        List<String> validAccommodationTypes = Arrays.asList("Hotel", "Apartamento", "Finca", "Dia de Sol");

        String[] params = {city, accommodationType, startDate, endDate};
        String[] paramNames = {"Ciudad", "Tipo de alojamiento", "Fecha de inicio", "Fecha de fin"};

        for (int i = 0; i < params.length; i++) {
            if (params[i] == null || params[i].isEmpty()) {
                throw new IllegalArgumentException(paramNames[i] + " es requerido");
            }
        }

        if (!validAccommodationTypes.contains(accommodationType)) {
            throw new IllegalArgumentException("Tipo de alojamiento inválido");
        }

        if (adults < 1) {
            throw new IllegalArgumentException("Se requiere al menos un adulto");
        }
        if (rooms < 1) {
            throw new IllegalArgumentException("Se requiere al menos una habitación");
        }
        if (children < 0) {
            throw new IllegalArgumentException("Los niños no pueden ser negativos");
        }
        if (rooms < adults) {
            throw new IllegalArgumentException("Debe haber al menos un adulto por habitación");
        }
    }

    private static boolean isMatchingHotel(String[] hotelDetails, String city, String accommodationType, String startDate, String endDate, int adults, int children, int rooms) {
        return hotelDetails[1].equals(city) &&
                (accommodationType.equals("Dia de Sol") || hotelDetails[2].equals(accommodationType)) &&
                hotelDetails[3].equals(startDate) &&
                hotelDetails[4].equals(endDate) &&
                Integer.parseInt(hotelDetails[5]) == adults &&
                Integer.parseInt(hotelDetails[6]) == children &&
                Integer.parseInt(hotelDetails[7]) == rooms;
    }

    private static int calculateDays(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        int days = (int) ChronoUnit.DAYS.between(start, end);
        return days < 1 ? 1 : days;
    }

    private static int calculateTotalPrice(int days, int pricePerNight, int rooms) {
        return days * pricePerNight * rooms;
    }

    private static double calculateDiscountOrIncrease(String startDate, String endDate, int totalPrice) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        double discountOrIncrease = 0;

        if (end.getDayOfMonth() > 25) {
            discountOrIncrease = totalPrice * 0.15;
            totalPrice *= 1.15;
        } else if (start.getDayOfMonth() <= 15 && end.getDayOfMonth() >= 10) {
            discountOrIncrease = totalPrice * 0.10;
            totalPrice *= 1.10;
        } else if (start.getDayOfMonth() <= 10 && end.getDayOfMonth() >= 5) {
            discountOrIncrease = totalPrice * 0.08;
            totalPrice *= 0.92;
        }

        return discountOrIncrease;
    }
}