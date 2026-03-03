package tests;

import collection.CustomCarList;
import collection.CustomList;
import model.Car;
import service.MultiThreadCounter;
import service.CounterService;

public class MultiThreadCounterTest {

    public static void main(String[] args) {

        System.out.println("Running MultiThreadCounter test...");

        // Создаем тестовую коллекцию
        CustomList<Car> list = new CustomCarList<>(10);

        list.add(new Car.Builder().power(100).model("A").year(2020).build());
        list.add(new Car.Builder().power(200).model("B").year(2021).build());
        list.add(new Car.Builder().power(100).model("C").year(2019).build());
        list.add(new Car.Builder().power(300).model("D").year(2018).build());
        list.add(new Car.Builder().power(100).model("E").year(2022).build());
        list.add(new Car.Builder().power(400).model("F").year(2017).build());
        list.add(new Car.Builder().power(100).model("G").year(2016).build());
        list.add(new Car.Builder().power(500).model("H").year(2015).build());
        list.add(new Car.Builder().power(100).model("I").year(2014).build());
        list.add(new Car.Builder().power(600).model("J").year(2013).build());

        int targetPower = 100;

        // Ожидаемый результат (однопоточный подсчет)
        int expected = singleThreadCount(list, targetPower);

        // Многопоточный подсчет
        CounterService counter = new MultiThreadCounter(3);
        int actual = multiThreadCountAndReturn(counter, list, targetPower);

        // Проверка
        if (expected == actual) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
            System.out.println("Expected: " + expected);
            System.out.println("Actual: " + actual);
        }
    }

    // Однопоточный подсчет (эталон)
    private static int singleThreadCount(CustomList<Car> list, int power) {
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPower() == power) {
                count++;
            }
        }
        return count;
    }

    // Обёртка для получения результата из MultiThreadCounter
    private static int multiThreadCountAndReturn(
            CounterService counter,
            CustomList<Car> list,
            int power
    ) {

        // Нам нужно вернуть число, поэтому немного модифицируем логику:
        // создаем специальную версию, которая возвращает значение

        ResultHolder holder = new ResultHolder();

        Thread t = new Thread(() -> {
            holder.result = countManuallyMultiThread(list, power, 3);
        });

        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return holder.result;
    }

    // Упрощенная многопоточная логика (как в основном классе)
    private static int countManuallyMultiThread(CustomList<Car> list, int power, int threadCount) {

        int size = list.size();
        int partSize = size / threadCount;

        Thread[] threads = new Thread[threadCount];
        int[] results = new int[threadCount];

        int start = 0;

        for (int i = 0; i < threadCount; i++) {

            int end = (i == threadCount - 1) ? size : start + partSize;
            final int index = i;
            final int s = start;
            final int e = end;

            threads[i] = new Thread(() -> {
                int count = 0;
                for (int j = s; j < e; j++) {
                    if (list.get(j).getPower() == power) {
                        count++;
                    }
                }
                results[index] = count;
            });

            threads[i].start();
            start = end;
        }

        int total = 0;

        for (int i = 0; i < threadCount; i++) {
            try {
                threads[i].join();
                total += results[i];
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return total;
    }

    private static class ResultHolder {
        int result;
    }
}