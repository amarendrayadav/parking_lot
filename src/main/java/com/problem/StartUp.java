package com.problem;

import com.problem.dataaccess.Car;
import com.problem.service.ParkingService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class StartUp {

    private static final String CREATE_PARKING_LOT = "create_parking_lot";
    private static final String PARK = "park";
    private static final String LEAVE = "leave";
    private static final String STATUS = "status";
    private static final String REGISTRATION_NOS_BY_COLOR = "registration_numbers_for_cars_with_colour";
    private static final String SLOT_NOS_BY_COLOR = "slot_numbers_for_cars_with_colour";
    private static final String SLOT_NOS_BY_REG_NO = "slot_number_for_registration_number";

    public static void main(String[] args) {
        ParkingService service = new ParkingService();
        String inputFile = "";
        try (Stream<String> commandStream = Files.lines(Paths.get(inputFile))) {
            commandStream.forEach(line -> {
                System.out.println("Command " + line);
                String[] commandArray = line.split(" ");
                if (commandArray.length > 0) {
                    String command = commandArray[0];
                    switch (command) {
                        case CREATE_PARKING_LOT:
                            service.createParkingLot(Integer.parseInt(commandArray[1]));
                            break;
                        case PARK:
                            final Car car = new Car();
                            car.setColor(commandArray[2]);
                            car.setRegistrationNo(commandArray[1]);
                            service.parkNewEntry(new Car());
                            break;
                        case LEAVE:
                            service.removeCarFromParking(Integer.parseInt(commandArray[1]));
                            break;
                        case STATUS:
                            service.getStatus();
                            break;
                        case REGISTRATION_NOS_BY_COLOR:
                            List<String> regNos = service.getCarsRegByColor(commandArray[1]);
                            System.out.println(regNos);
                            break;
                        case SLOT_NOS_BY_COLOR:
                            List<Integer> slots = service.allSlotsByColor(commandArray[1]);
                            System.out.println(slots);
                            break;
                        case SLOT_NOS_BY_REG_NO:
                            service.getSlotByRegistration(commandArray[1]);
                            break;
                        default:
                            throw new IllegalStateException("Not Supported");
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // create_parking_lot 6
    // park KA-01-HH-1234 White
    // park KA-01-HH-9999 White
    // park KA-01-BB-0001 Black
    // park KA-01-HH-7777 Red
    // park KA-01-HH-2701 Blue
    // park KA-01-HH-3141 Black
    // leave 4
    // status
    // park KA-01-P-333 White
    // park DL-12-AA-9999 White
    // registration_numbers_for_cars_with_colour White
    // slot_numbers_for_cars_with_colour White
    // slot_number_for_registration_number KA-01-HH-3141
    // slot_number_for_registration_number MH-04-AY-1111
}
