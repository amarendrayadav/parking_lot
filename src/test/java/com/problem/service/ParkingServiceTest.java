package com.problem.service;

import com.problem.dataaccess.Car;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ParkingServiceTest {

    private ParkingService serviceTester;

    @Before
    public void init() {
        int slots = 6;
        serviceTester = new ParkingService();
        serviceTester.setCars(new ArrayList<>(slots));
        serviceTester.setTickets(new HashSet<>());
        serviceTester.createParkingLot(slots);
    }

    @Test
    public void createParkingLotTest() {
        int slots = 6;
        ParkingService serviceTester = new ParkingService();
        serviceTester.setCars(new ArrayList<>(slots));
        serviceTester.createParkingLot(slots);
        assertEquals(slots, serviceTester.getCars().size());
    }

    @Test
    public void parkNewEntryTest() {
        Car car = new Car();
        car.setRegistrationNo("KA 01 AB 1234");
        car.setColor("Black");
        serviceTester.parkNewEntry(car);
        assertEquals(1, serviceTester.getTickets().size());
        serviceTester.removeCarFromParking(0);
    }

    @Test
    public void removeCarFromParkingTest() {
        createDummyEntry();
        int slotToBeEmptied = 0;
        serviceTester.removeCarFromParking(slotToBeEmptied);
        assertEquals(0, serviceTester.getTickets().size());
    }

    @Test
    public void getCarsRegByColorTest() {
        String color = "Black";
        createDummyEntry();
        List<String> cars = serviceTester.getCarsRegByColor(color);
        assertEquals(1, cars.size());
    }

    private void createDummyEntry() {
        Car car = new Car();
        car.setRegistrationNo("KA 01 AB 1234");
        car.setColor("Black");
        serviceTester.parkNewEntry(car);
    }


}