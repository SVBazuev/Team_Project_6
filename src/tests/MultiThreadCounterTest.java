package tests;

import collection.CustomCarList;
import model.Car;
import service.MultiThreadCounter;

public class MultiThreadCounterTest {

    public static void main(String[] args) {

        CustomCarList list = new CustomCarList();

        list.add(new Car.Builder().brand("Toyota").model("A").year(2020).build());
        list.add(new Car.Builder().brand("BMW").model("B").year(2020).build());
        list.add(new Car.Builder().brand("Audi").model("C").year(2021).build());
        list.add(new Car.Builder().brand("Honda").model("D").year(2020).build());

        MultiThreadCounter counter = new MultiThreadCounter(3);

        int result = counter.countCarsByYear(list, 2020);

        System.out.println("Expected: 3");
        System.out.println("Actual: " + result);
    }
}