package com.klu.exp3;

import org.hibernate.Session;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class Action {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory sf = cfg.buildSessionFactory();

        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

        // Add a Book to the library
		/*
		 * Book book = new Book(); book.setTitle("Java"); book.setAuthor("kc");
		 * book.setIsbn("123456789"); session.save(book);
		 * 
		 * // Add a DVD to the library DVD dvd = new DVD(); dvd.setTitle("Inception");
		 * dvd.setDirector("Nolan"); dvd.setRegionCode("1"); session.save(dvd);
		 */

        // Add a Car to the fleet
        Car car = new Car();
        car.setModel("Toyota");
        car.setSeats(5);
        car.setFuelType("Hybrid");
        session.save(car);

        // Add a Bike to the fleet
        Bike bike = new Bike();
        bike.setModel("Yamaha R1");
        bike.setType("Sport");
        bike.setEngineCapacity(1000);
        session.save(bike);

        // Add Electronics to the store
        Electronics electronics = new Electronics();
        electronics.setName("Smartphone");
        electronics.setWarranty("1 year");
        electronics.setBrand("Samsung");
        session.save(electronics);

        // Add Clothing to the store
        Clothing clothing = new Clothing();
        clothing.setName("T-Shirt");
        clothing.setSize("L");
        clothing.setMaterial("Cotton");
        session.save(clothing);

        tx.commit();
        session.close();
        sf.close();
    }
}
