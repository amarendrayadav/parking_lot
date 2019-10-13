package com.problem.service;

import com.problem.dataaccess.Car;
import com.problem.dataaccess.EmptyObject;
import com.problem.dataaccess.Ticket;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ParkingService {

    private List<Car> cars;
    private Set<Ticket> tickets;

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    final EmptyObject emptyObject = new EmptyObject();

    private static void accept(Ticket ticket) {
        System.out.println(ticket.getSlotNo());
    }

    public void createParkingLot(int parkingSlots) {
        // initialized each slot with empty object
        for (int i = 0; i < parkingSlots; i++) {
            this.cars.add(emptyObject);
        }
        System.out.println("Created a parking lot with " + parkingSlots + " slots");
    }

    public void parkNewEntry(Car car) {
        int parkingSlot = 0;
        for (Car c : cars) {
            if (c instanceof EmptyObject) {
                break;
            }
            ++parkingSlot;
        }
        if (parkingSlot < cars.size()) {
            this.cars.set(parkingSlot, car);
            // generate ticket
            Ticket ticket = new Ticket();
            ticket.setCar(car);
            ticket.setSlotNo(parkingSlot);
            tickets.add(ticket);
            System.out.println("Allocated slot number: " + parkingSlot);
        } else {
            System.out.println("Sorry, parking lot is full");
        }
    }

    //removeCarFromParking
    public void removeCarFromParking(int slotNo) {
        Optional<Ticket> ticketOptional = tickets.stream().filter(ticket -> ticket.getSlotNo().equals(slotNo)).findFirst();
        if (ticketOptional.isPresent()) {
            final Ticket ticket = ticketOptional.get();
            final Car car = ticket.getCar();
            int freeSlot = cars.indexOf(car);
            cars.set(freeSlot, emptyObject);
            tickets.remove(ticket);
            System.out.println("Slot no " + freeSlot + " is free");
        }
    }

    //status
    public void getStatus() {
        System.out.println("Slot No.\tRegistration No\tColour");
        tickets.forEach(ticket -> {
            System.out.println(ticket.getSlotNo() + "\t" + ticket.getCar().getRegistrationNo() + "\t" + ticket.getCar().getColor());
        });
    }

    //getCarsByColor
    public List<String> getCarsRegByColor(String color) {
        List<Car> carList = cars.stream().
                filter(c -> Objects.nonNull(c.getColor())).
                filter(c -> c.getColor().equals(color)).
                collect(Collectors.toList());
        return carList.stream().map(Car::getRegistrationNo).collect(Collectors.toList());
    }

    //getSlotByRegistration
    public void getSlotByRegistration(String regNo) {
        Optional<Car> carOptional = cars.stream().filter(c -> c.getRegistrationNo().equals(regNo)).findFirst();
        if (carOptional.isPresent()) {
            Optional<Ticket> ticket = tickets.stream().
                    filter(t -> t.getCar().getRegistrationNo().equals(carOptional.get().getRegistrationNo())).
                    findFirst();
            ticket.ifPresent(ParkingService::accept);
        } else {
            System.out.println("Not found");
        }
    }

    //allSlotsByColor
    public List<Integer> allSlotsByColor(String color) {
        final List<Ticket> ticketByColor = tickets.
                stream().
                filter(ticket -> ticket.getCar().getColor().equals(color)).
                collect(Collectors.toList());
        return ticketByColor.stream().map(Ticket::getSlotNo).collect(Collectors.toList());
    }
}
