package com.problem;

import com.problem.dataaccess.Car;
import com.problem.service.ParkingService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class StartUp {

    private static final String CREATE_PARKING_LOT = "create_parking_lot";
    private static final String PARK = "park";
    private static final String LEAVE = "leave";
    private static final String STATUS = "status";
    private static final String REGISTRATION_NOS_BY_COLOR = "registration_numbers_for_cars_with_colour";
    private static final String SLOT_NOS_BY_COLOR = "slot_numbers_for_cars_with_colour";
    private static final String SLOT_NOS_BY_REG_NO = "slot_number_for_registration_number";

    public static void main(String[] args) throws IOException {
        System.out.println("Press 1. for input from inputFile");
        System.out.println("Press 2. for Command Line Interaction");
        Scanner sc = new Scanner(System.in);
        String commands = "";
        while (true) {
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    inputFromFile();
                    break;
                case 2:
                    final ParkingService service = new ParkingService();
                    runCommands(commands, service);
                    break;
                default:
                    System.out.println("Illegal Option");
                    break;
            }
        }
    }

    private static void inputFromFile() {
        //"C:\\amrendra\\projects\\parking_lot\\bin\\parking_lot_file_inputs";
        String inputFile = new File("").getAbsolutePath() + "\\bin\\parking_lot_file_inputs.txt";
        try (Stream<String> commandStream = Files.lines(Paths.get(inputFile))) {
            final ParkingService service = new ParkingService();
            commandStream.forEach(line -> {
                runCommands(line, service);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runCommands(String commandLine, final ParkingService service) {
        String[] commandArray = commandLine.split(" ");
        if (commandArray.length > 0) {
            String command = commandArray[0];
            switch (command) {
                case CREATE_PARKING_LOT:
                    final List<Car> cars = new ArrayList<>(Integer.parseInt(commandArray[1]));
                    service.setCars(cars);
                    service.setTickets(new HashSet<>());
                    service.createParkingLot(Integer.parseInt(commandArray[1]));
                    break;
                case PARK:
                    final Car car = new Car();
                    car.setColor(commandArray[2]);
                    car.setRegistrationNo(commandArray[1]);
                    service.parkNewEntry(car);
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
    }
}
