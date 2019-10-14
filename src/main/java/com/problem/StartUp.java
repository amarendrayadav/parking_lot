package com.problem;

import com.problem.dataaccess.Car;
import com.problem.service.ParkingService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class StartUp {

    private static final String CREATE_PARKING_LOT = "create_parking_lot";
    private static final String PARK = "park";
    private static final String LEAVE = "leave";
    private static final String STATUS = "status";
    private static final String REGISTRATION_NOS_BY_COLOR = "registration_numbers_for_cars_with_colour";
    private static final String SLOT_NOS_BY_COLOR = "slot_numbers_for_cars_with_colour";
    private static final String SLOT_NOS_BY_REG_NO = "slot_number_for_registration_number";
    private static final String EXIT = "exit";

    public static void main(String[] args) {
        StartUp startUp = new StartUp();
        final ParkingService service = new ParkingService();
        String inputFile = "parking_lot_file_inputs";
        if (args.length > 0) {
            inputFile = args[0];
            startUp.inputFromFile(service, inputFile);
        } else {
            System.out.println("Press 0. to stop app");
            System.out.println("Press 1. for input from inputFile");
            System.out.println("Press 2. for Command Line Interaction");
            final Scanner sc = new Scanner(System.in);
            boolean stopApp = false;
            while (!stopApp) {
                int choice = sc.nextInt();
                switch (choice) {
                    case 0:
                        stopApp = true;
                        System.out.println("Exiting App...");
                        break;
                    case 1:
                        startUp.inputFromFile(service, inputFile);
                        break;
                    case 2:
                        interactiveCommands(service);
                        break;
                    default:
                        System.out.println("Illegal Option");
                        break;
                }
            }
        }
    }

    private static void interactiveCommands(ParkingService service) {
        System.out.println("Running CLI!");
        Scanner sc = new Scanner(System.in);
        while (true) {
            String command = sc.nextLine();
            if (Objects.equals(command, "Exit")) {
                System.out.println("Exiting CLI...");
                break;
            }
            runCommands(command, service);
        }
        sc.close();
    }

    private void inputFromFile(final ParkingService service, final String inputFile) {
        try {
            final BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().
                    getClassLoader().
                    getResourceAsStream(inputFile))));
            String line;
            while (true) {
                if ((line = br.readLine()) == null) break;
                runCommands(line, service);
            }
            br.close();
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
                    final List<String> regNos = service.getCarsRegByColor(commandArray[1]);
                    System.out.println(regNos);
                    break;
                case SLOT_NOS_BY_COLOR:
                    final List<Integer> slots = service.allSlotsByColor(commandArray[1]);
                    System.out.println(slots);
                    break;
                case SLOT_NOS_BY_REG_NO:
                    service.getSlotByRegistration(commandArray[1]);
                    break;
                case EXIT:
                    System.out.println("Exiting...");
                    break;
                default:
                    throw new IllegalStateException("Command Not Supported");
            }
        }
    }
}
