package com.klu.exp1;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class StudentWork {

    public static void main(String[] args) {
        StudentWork operations = new StudentWork();
        Scanner scanner = new Scanner(System.in);

        String choice;
        do {
            System.out.println("Choose an operation: add, fetch, update, delete, exit");
            choice = scanner.nextLine();
            switch (choice) {
                case "add":
                    operations.addStudent();
                    break;
                case "fetch":
                    operations.fetchStudent();
                    break;
                case "update":
                    operations.updateStudent();
                    break;
                case "delete":
                    operations.deleteStudent();
                    break;
                case "exit":
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (!choice.equals("exit"));

        scanner.close();
    }

    public void addStudent() {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        SessionFactory sf = cfg.buildSessionFactory();
        Session session = sf.openSession();

        Scanner sc = new Scanner(System.in);
        Transaction tx = session.beginTransaction();

        String choice;
        do {
            System.out.println("Enter Student details:");

            Student student = new Student();
            System.out.print("Name: ");
            student.setName(sc.nextLine());
            System.out.print("Gender: ");
            student.setGender(sc.nextLine());
            System.out.print("Department: ");
            student.setDepartment(sc.nextLine());
            System.out.print("Program: ");
            student.setProgram(sc.nextLine());
            System.out.print("Date of Birth (yyyy-MM-dd): ");
            student.setDob(sc.nextLine());
            System.out.print("Contact Number: ");
            student.setContact(sc.nextLine());
            System.out.print("Graduation Status: ");
            student.setGraduationStatus(sc.nextLine());
            System.out.print("CGPA: ");
            student.setCgpa(Double.parseDouble(sc.nextLine()));
            System.out.print("Number of Backlogs: ");
            student.setBacklogs(Integer.parseInt(sc.nextLine()));

            session.persist(student);

            System.out.print("Add another student? (yes/no): ");
            choice = sc.nextLine();
        } while (choice.equalsIgnoreCase("yes"));

        tx.commit();
        System.out.println("Student(s) added successfully");

        session.close();
        sf.close();
    }

    public void fetchStudent() {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        SessionFactory sf = cfg.buildSessionFactory();
        Session session = sf.openSession();

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Student ID to fetch: ");
        int id = sc.nextInt();

        String hql = "from Student where id = :studentId";
        Query<Student> qry = session.createQuery(hql, Student.class);
        qry.setParameter("studentId", id);
        Student student = qry.uniqueResult();

        if (student != null) {
            System.out.println("Student Details:");
            System.out.println("ID: " + student.getId());
            System.out.println("Name: " + student.getName());
            System.out.println("Gender: " + student.getGender());
            System.out.println("Department: " + student.getDepartment());
            System.out.println("Program: " + student.getProgram());
            System.out.println("Date of Birth: " + student.getDob());
            System.out.println("Contact Number: " + student.getContact());
            System.out.println("Graduation Status: " + student.getGraduationStatus());
            System.out.println("CGPA: " + student.getCgpa());
            System.out.println("Backlogs: " + student.getBacklogs());
        } else {
            System.out.println("Student not found with ID: " + id);
        }

        session.close();
        sf.close();
    }

    public void updateStudent() {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        SessionFactory sf = cfg.buildSessionFactory();
        Session session = sf.openSession();

        Transaction tx = session.beginTransaction();
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Student ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();  // Consume newline

        System.out.print("Enter new Name: ");
        String name = sc.nextLine();
        System.out.print("Enter new Gender: ");
        String gender = sc.nextLine();
        System.out.print("Enter new Department: ");
        String department = sc.nextLine();
        System.out.print("Enter new Program: ");
        String program = sc.nextLine();
        System.out.print("Enter new Date of Birth (yyyy-MM-dd): ");
        String dob = sc.nextLine();
        System.out.print("Enter new Contact Number: ");
        String contact = sc.nextLine();
        System.out.print("Enter new Graduation Status: ");
        String graduationStatus = sc.nextLine();
        System.out.print("Enter new CGPA: ");
        double cgpa = sc.nextDouble();
        System.out.print("Enter new Number of Backlogs: ");
        int backlogs = sc.nextInt();

        String hql = "update Student set name = :name, gender = :gender, department = :department, "
                + "program = :program, dob = :dob, contact = :contact, "
                + "graduationStatus = :graduationStatus, cgpa = :cgpa, backlogs = :backlogs "
                + "where id = :studentId";
        Query qry = session.createQuery(hql);
        qry.setParameter("name", name);
        qry.setParameter("gender", gender);
        qry.setParameter("department", department);
        qry.setParameter("program", program);
        qry.setParameter("dob", dob);
        qry.setParameter("contact", contact);
        qry.setParameter("graduationStatus", graduationStatus);
        qry.setParameter("cgpa", cgpa);
        qry.setParameter("backlogs", backlogs);
        qry.setParameter("studentId", id);

        int n = qry.executeUpdate();
        tx.commit();

        if (n > 0) {
            System.out.println("Student updated successfully");
        } else {
            System.out.println("Student ID not found");
        }

        session.close();
        sf.close();
    }

    public void deleteStudent() {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        SessionFactory sf = cfg.buildSessionFactory();
        Session session = sf.openSession();

        Transaction tx = session.beginTransaction();
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Student ID to delete: ");
        int id = sc.nextInt();

        String hql = "delete from Student where id = :studentId";
        Query qry = session.createQuery(hql);
        qry.setParameter("studentId", id);

        int n = qry.executeUpdate();
        tx.commit();

        if (n > 0) {
            System.out.println("Student deleted successfully");
        } else {
            System.out.println("Student ID not found");
        }

        session.close();
        sf.close();
    }
}
