package at.htl.kfz.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Car extends PanacheEntity {
    public String model;
    public Brand brand;
    public String color;
    public int hp;
    public double price;

    public Car(String model, Brand brand, String color, int hp, double price) {
        this.model = model;
        this.brand = brand;
        this.color = color;
        this.hp = hp;
        this.price = price;
    }

    public Car() {
    }

    @Override
    public String toString() {
        return String.format("%d: The %s %s %s with %d hp costs %f",
                id,
                color,
                brand,
                model,
                hp,
                price);
    }
}
