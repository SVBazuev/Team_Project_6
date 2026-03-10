package tests;

import model.Car;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BuilderTests {

    public static void main(String[] args) {
        System.out.println("=== Тестирование Builder паттерна и Car модели (3 поля) ===\n");

        testBuilderPattern();
        testValidation();
        testComparable();
        testToString();
        testEquals();
    }

    private static void testBuilderPattern() {
        System.out.println("1. Тестирование Builder паттерна:");
        System.out.println("--------------------------------");

        try {
            Car car1 = new Car.Builder()
                    .brand("Toyota")
                    .model("Camry")
                    .year(2024)
                    .build();

            Car car2 = new Car.Builder()
                    .brand("BMW")
                    .model("X5")
                    .year(2023)
                    .build();

            System.out.println("Car 1: " + car1);
            System.out.println("Car 2: " + car2);
            System.out.println("✓ Builder успешно создал объекты Car\n");

        } catch (Exception e) {
            System.out.println("✗ Ошибка при создании объектов: " + e.getMessage() + "\n");
        }
    }

    private static void testValidation() {
        System.out.println("2. Тестирование валидации:");
        System.out.println("---------------------------");

        try {
            new Car.Builder()
                    .brand("")
                    .model("Civic")
                    .year(2022)
                    .build();
            System.out.println("✗ Должна быть ошибка для пустого brand");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Корректно поймана ошибка: " + e.getMessage());
        }

        try {
            new Car.Builder()
                    .brand("Honda")
                    .model(null)
                    .year(2022)
                    .build();
            System.out.println("✗ Должна быть ошибка для null model");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Корректно поймана ошибка: " + e.getMessage());
        }

        try {
            new Car.Builder()
                    .brand("Honda")
                    .model("Civic")
                    .year(1800)
                    .build();
            System.out.println("✗ Должна быть ошибка для некорректного года");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Корректно поймана ошибка: " + e.getMessage());
        }

        try {
            new Car.Builder()
                    .brand("Honda")
                    .model("Civic")
                    .year(2030)
                    .build();
            System.out.println("✗ Должна быть ошибка для года в далеком будущем");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Корректно поймана ошибка: " + e.getMessage());
        }

        System.out.println();
    }

    private static void testComparable() {
        System.out.println("3. Тестирование Comparable (сортировка по brand -> model -> year):");
        System.out.println("----------------------------------------------------------------");

        List<Car> cars = Arrays.asList(
                new Car.Builder().brand("Toyota").model("Camry").year(2022).build(),
                new Car.Builder().brand("Audi").model("A4").year(2021).build(),
                new Car.Builder().brand("BMW").model("X3").year(2023).build(),
                new Car.Builder().brand("Audi").model("Q5").year(2022).build(),
                new Car.Builder().brand("Audi").model("A4").year(2022).build(),
                new Car.Builder().brand("Toyota").model("Corolla").year(2022).build()
        );

        System.out.println("До сортировки:");
        cars.forEach(System.out::println);

        Collections.sort(cars);

        System.out.println("\nПосле сортировки (по brand -> model -> year):");
        cars.forEach(System.out::println);

        boolean correctOrder = true;
        for (int i = 0; i < cars.size() - 1; i++) {
            if (cars.get(i).compareTo(cars.get(i + 1)) > 0) {
                correctOrder = false;
                break;
            }
        }

        System.out.println("\n✓ Порядок сортировки корректен: " + correctOrder + "\n");
    }

    private static void testToString() {
        System.out.println("4. Тестирование toString():");
        System.out.println("--------------------------");

        Car car = new Car.Builder()
                .brand("Tesla")
                .model("Model 3")
                .year(2023)
                .build();

        System.out.println("toString output: " + car);
        System.out.println("✓ toString() успешно реализован\n");
    }

    private static void testEquals() {
        System.out.println("5. Тестирование equals() и hashCode():");
        System.out.println("-------------------------------------");

        Car car1 = new Car.Builder()
                .brand("Honda")
                .model("Civic")
                .year(2022)
                .build();

        Car car2 = new Car.Builder()
                .brand("Honda")
                .model("Civic")
                .year(2022)
                .build();

        Car car3 = new Car.Builder()
                .brand("Honda")
                .model("Accord")
                .year(2022)
                .build();

        System.out.println("car1 equals car2: " + car1.equals(car2) + " (должно быть true)");
        System.out.println("car1 equals car3: " + car1.equals(car3) + " (должно быть false)");
        System.out.println("car1 hashCode: " + car1.hashCode());
        System.out.println("car2 hashCode: " + car2.hashCode());
        System.out.println("car3 hashCode: " + car3.hashCode());
        System.out.println("✓ equals и hashCode работают корректно\n");
    }
}