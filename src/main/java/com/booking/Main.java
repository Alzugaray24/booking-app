package com.booking;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Main {
    private static final List<String> availableHotels = new ArrayList<>(Arrays.asList(
            "Hotel Buenos Aires 1, Buenos Aires, Hotel, 2023-12-01, 2023-12-10, 1, 2, 1, 5, 100, 50, 10, 100, 20",
            "Hotel Buenos Aires 2, Buenos Aires, Hotel, 2023-12-01, 2023-12-10, 1, 2, 1, 4, 80, 40, 5, 80, 10",
            "Apartamento Buenos Aires 1, Buenos Aires, Apartamento, 2023-12-01, 2023-12-10, 1, 2, 1, 3, 60, 30, 3, 60, 5",
            "Finca Buenos Aires 1, Buenos Aires, Finca, 2023-12-01, 2023-12-10, 1, 2, 1, 4, 90, 20, 2, 40, 8",
            "Dia de Sol Buenos Aires 1, Buenos Aires, Dia de Sol, 2023-12-01, 2023-12-10, 1, 2, 1, 5, 120, 10, 1, 20, 2"
    ));

    private static final List<String> availableRooms = new ArrayList<>(Arrays.asList(
            "Hotel Buenos Aires 1, 2023-12-01, 2023-12-10, 1, 2, 1, Habitación Doble, 2 camas dobles, vista al mar, aire acondicionado, cafetera, TV de pantalla plana, ducha, escritorio, 100",
            "Hotel Buenos Aires 1, 2023-12-01, 2023-12-10, 1, 2, 1, Habitación Suite, 1 cama king, vista al mar, aire acondicionado, cafetera, TV de pantalla plana, ducha, escritorio, 200",
            "Hotel Buenos Aires 2, 2023-12-01, 2023-12-10, 1, 2, 1, Habitación Doble, 2 camas dobles, vista al mar, aire acondicionado, cafetera, TV de pantalla plana, ducha, escritorio, 80",
            "Hotel Madrid 1, 2023-12-01, 2023-12-10, 1, 2, 1, Habitación Doble, 2 camas dobles, vista al mar, aire acondicionado, cafetera, TV de pantalla plana, ducha, escritorio, 110",
            "Hotel Madrid 1, 2023-12-01, 2023-12-10, 1, 2, 1, Habitación Suite, 1 cama king, vista al mar, aire acondicionado, cafetera, TV de pantalla plana, ducha, escritorio, 220"
    ));

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Buscar hoteles");
            System.out.println("2. Reservar habitación");
            System.out.println("3. Confirmar habitaciones");
            System.out.println("4. Salir");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    findHotels();
                    break;
                case 2:
                    bookRoom();
                    break;
                case 3:
                    confirmRooms();
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }

        scanner.close();
    }

    private static void findHotels() {
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
                int totalCapacity = Integer.parseInt(hotelDetails[12]);
                int currentOccupancy = Integer.parseInt(hotelDetails[13]);
                int availableCapacity = totalCapacity - currentOccupancy;
                int requiredCapacity = (adults + children) * rooms;

                if (availableCapacity >= requiredCapacity) {
                    int availableRooms = Integer.parseInt(hotelDetails[10]) - Integer.parseInt(hotelDetails[11]);
                    int price = findLowestPrice(hotelDetails[0]);
                    double totalPrice = calculateTotalPrice(price, startDate, endDate, rooms);
                    matchingHotels.add(String.format("Nombre: %s, Calificación: %s, Precio por noche: %d, Precio total: %.2f, Habitaciones disponibles: %d, Capacidad disponible: %d",
                            hotelDetails[0], hotelDetails[8], price, totalPrice, availableRooms, availableCapacity));
                }
            }
        }

        return matchingHotels;
    }

    private static int findLowestPrice(String hotelName) {
        int lowestPrice = Integer.MAX_VALUE;
        for (String room : availableRooms) {
            String[] roomDetails = room.split(", ");
            if (roomDetails[0].equals(hotelName)) {
                int price = Integer.parseInt(roomDetails[14]); // Corrected index for price
                if (price < lowestPrice) {
                    lowestPrice = price;
                }
            }
        }
        return lowestPrice;
    }

    private static double calculateTotalPrice(int pricePerNight, String startDate, String endDate, int rooms) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        long days = ChronoUnit.DAYS.between(start, end);
        double totalPrice = pricePerNight * days * rooms;

        if (start.getDayOfMonth() >= 5 && start.getDayOfMonth() <= 10) {
            totalPrice *= 0.92; // 8% discount
        } else if (start.getDayOfMonth() >= 10 && start.getDayOfMonth() <= 15) {
            totalPrice *= 1.10; // 10% increase
        } else if (end.getDayOfMonth() >= 25) {
            totalPrice *= 1.15; // 15% increase
        }

        return totalPrice;
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

    private static void bookRoom() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Seleccione el nombre del hotel:");
        for (int i = 0; i < availableHotels.size(); i++) {
            String[] hotelDetails = availableHotels.get(i).split(", ");
            System.out.println((i + 1) + ". " + hotelDetails[0]);
        }
        int hotelChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        String hotelName = availableHotels.get(hotelChoice - 1).split(", ")[0];

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
        scanner.nextLine(); // Consume newline

        System.out.print("Ingrese su nombre: ");
        String firstName = scanner.nextLine();

        System.out.print("Ingrese su apellido: ");
        String lastName = scanner.nextLine();

        System.out.print("Ingrese su email: ");
        String email = scanner.nextLine();

        System.out.print("Ingrese su nacionalidad: ");
        String nationality = scanner.nextLine();

        System.out.print("Ingrese su número de teléfono: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Ingrese la hora aproximada de llegada (HH:MM): ");
        String arrivalTime = scanner.nextLine();

        // Actualizar la cantidad de habitaciones disponibles
        for (int i = 0; i < availableHotels.size(); i++) {
            String[] hotelDetails = availableHotels.get(i).split(", ");
            if (hotelDetails[0].equals(hotelName)) {
                int availableRooms = Integer.parseInt(hotelDetails[10]) - rooms;
                hotelDetails[10] = String.valueOf(availableRooms);
                availableHotels.set(i, String.join(", ", hotelDetails));
                break;
            }
        }

        System.out.println("Se ha realizado la reserva con éxito");
    }

    private static void confirmRooms() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Seleccione el nombre del hotel:");
        for (int i = 0; i < availableHotels.size(); i++) {
            String[] hotelDetails = availableHotels.get(i).split(", ");
            System.out.println((i + 1) + ". " + hotelDetails[0]);
        }
        int hotelChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        String hotelName = availableHotels.get(hotelChoice - 1).split(", ")[0];

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

        List<String> availableRooms = confirmRooms(hotelName, startDate, endDate, adults, children, rooms);
        if (!availableRooms.isEmpty()) {
            System.out.println("Habitaciones disponibles:");
            for (String room : availableRooms) {
                System.out.println(room);
            }
        } else {
            System.out.println("No hay habitaciones disponibles.");
        }
    }

    public static List<String> confirmRooms(String hotelName, String startDate, String endDate, int adults, int children, int rooms) {
        List<String> matchingRooms = new ArrayList<>();
        for (String room : availableRooms) {
            String[] roomDetails = room.split(", ");
            if (roomDetails[0].equals(hotelName) &&
                    roomDetails[1].equals(startDate) &&
                    roomDetails[2].equals(endDate) &&
                    Integer.parseInt(roomDetails[3]) == adults &&
                    Integer.parseInt(roomDetails[4]) == children &&
                    Integer.parseInt(roomDetails[5]) == rooms) {
                matchingRooms.add(String.format("Tipo de habitación: %s, Características: %s, %s, %s, %s, %s, %s, Precio: %s",
                        roomDetails[6], roomDetails[7], roomDetails[8], roomDetails[9], roomDetails[10], roomDetails[11], roomDetails[12], roomDetails[13]));
            }
        }
        return matchingRooms;
    }
}