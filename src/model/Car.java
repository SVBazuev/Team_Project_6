package model;

import java.util.Objects;

public class Car implements Comparable<Car> {

    private final String brand;
    private final String model;
    private final int year;

    private Car(Builder builder) {
        this.brand = builder.brand;
        this.model = builder.model;
        this.year = builder.year;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    @Override
    public int compareTo(Car other) {

        int brandCompare = this.brand.compareTo(other.brand);
        if (brandCompare != 0) {
            return brandCompare;
        }

        int modelCompare = this.model.compareTo(other.model);
        if (modelCompare != 0) {
            return modelCompare;
        }

        return Integer.compare(this.year, other.year);
    }

    @Override
    public String toString() {
        return String.format("Car{brand='%s', model='%s', year=%d}",
                brand, model, year);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return year == car.year &&
                Objects.equals(brand, car.brand) &&
                Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, model, year);
    }

    public static class Builder {
        private String brand;
        private String model;
        private int year;

        public Builder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Car build() {
            validate();
            return new Car(this);
        }

        private void validate() {
            if (brand == null || brand.trim().isEmpty()) {
                throw new IllegalArgumentException("Brand cannot be null or empty");
            }
            if (brand.length() > 50) {
                throw new IllegalArgumentException("Brand name is too long (max 50 characters)");
            }

            if (model == null || model.trim().isEmpty()) {
                throw new IllegalArgumentException("Model cannot be null or empty");
            }
            if (model.length() > 50) {
                throw new IllegalArgumentException("Model name is too long (max 50 characters)");
            }

            int currentYear = java.time.Year.now().getValue();
            if (year < 1886 || year > currentYear + 1) {
                throw new IllegalArgumentException("Year must be between 1886 and " + (currentYear + 1));
            }
        }
    }
}