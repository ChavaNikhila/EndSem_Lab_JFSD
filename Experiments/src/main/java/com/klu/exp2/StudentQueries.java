package com.klu.exp2;

import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import com.klu.exp1.Student;

public class StudentQueries {

    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();

        Scanner scanner = new Scanner(System.in);
        String choice;

        do {
            System.out.println("Choose an operation:");
            System.out.println("1. Display all student records");
            System.out.println("2. Display specific columns of student records");
            System.out.println("3. Display names of students with CGPA > 7");
            System.out.println("4. Delete a student by ID");
            System.out.println("5. Update details of a student by ID");
            System.out.println("6. Perform aggregate functions on CGPA");
            System.out.println("7. Display specific columns using HCQL");
            System.out.println("8. Display 5th to 10th records using HCQL");
            System.out.println("9. Apply conditions on CGPA using HCQL");
            System.out.println("10. Get records in ascending/descending order by name");
            System.out.println("11. Exit");

            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayAllStudents(session);
                    break;
                case "2":
                    displaySpecificColumns(session);
                    break;
                case "3":
                    displayNamesWithHighCGPA(session);
                    break;
                case "4":
                    deleteStudentById(session, scanner);
                    break;
                case "5":
                    updateStudentById(session, scanner);
                    break;
                case "6":
                    performAggregateFunctions(session);
                    break;
                case "7":
                    displaySpecificColumnsHCQL(session);
                    break;
                case "8":
                    display5thTo10thRecordsHCQL(session);
                    break;
                case "9":
                    applyConditionsOnCGPAHCQL(session);
                    break;
                case "10":
                    getRecordsInOrderHCQL(session);
                    break;
                case "11":
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (!choice.equals("11"));

        session.close();
        sessionFactory.close();
        scanner.close();
    }

    private static void displayAllStudents(Session session) {
        String hql = "FROM Student";
        Query<Student> query = session.createQuery(hql, Student.class);
        List<Student> students = query.list();
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private static void displaySpecificColumns(Session session) {
        String hql = "SELECT id, name, cgpa FROM Student";
        Query<Object[]> query = session.createQuery(hql, Object[].class);
        List<Object[]> results = query.list();
        for (Object[] row : results) {
            System.out.println("ID: " + row[0] + ", Name: " + row[1] + ", CGPA: " + row[2]);
        }
    }

    private static void displayNamesWithHighCGPA(Session session) {
        String hql = "SELECT name FROM Student WHERE cgpa > :cgpaValue";
        Query<String> query = session.createQuery(hql, String.class);
        query.setParameter("cgpaValue", 7.0);
        List<String> names = query.list();
        for (String name : names) {
            System.out.println("Name: " + name);
        }
    }

    private static void deleteStudentById(Session session, Scanner scanner) {
        System.out.print("Enter Student ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        Transaction tx = session.beginTransaction();
        String hql = "DELETE FROM Student WHERE id = :studentId";
        Query<?> query = session.createQuery(hql);
        query.setParameter("studentId", id);
        int result = query.executeUpdate();
        tx.commit();

        if (result > 0) {
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Student ID not found.");
        }
    }

    private static void updateStudentById(Session session, Scanner scanner) {
        System.out.print("Enter Student ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter new name: ");
        String name = scanner.nextLine();

        Transaction tx = session.beginTransaction();
        String hql = "UPDATE Student SET name = :name WHERE id = :studentId";
        Query<?> query = session.createQuery(hql);
        query.setParameter("name", name);
        query.setParameter("studentId", id);
        int result = query.executeUpdate();
        tx.commit();

        if (result > 0) {
            System.out.println("Student updated successfully.");
        } else {
            System.out.println("Student ID not found.");
        }
    }

    private static void performAggregateFunctions(Session session) {
        String hql = "SELECT count(*), sum(cgpa), avg(cgpa), min(cgpa), max(cgpa) FROM Student";
        Query<Object[]> query = session.createQuery(hql, Object[].class);
        Object[] results = query.uniqueResult();
        System.out.println("Count: " + results[0] + ", Sum: " + results[1] + ", Avg: " + results[2] + 
                           ", Min: " + results[3] + ", Max: " + results[4]);
    }

    private static void displaySpecificColumnsHCQL(Session session) {
        Criteria criteria = session.createCriteria(Student.class)
                                   .setProjection(Projections.projectionList()
                                       .add(Projections.property("id"))
                                       .add(Projections.property("name"))
                                       .add(Projections.property("cgpa")));
        List<Object[]> results = criteria.list();
        for (Object[] row : results) {
            System.out.println("ID: " + row[0] + ", Name: " + row[1] + ", CGPA: " + row[2]);
        }
    }

    private static void display5thTo10thRecordsHCQL(Session session) {
        Criteria criteria = session.createCriteria(Student.class)
                                   .setFirstResult(4)  // 5th record (index 4)
                                   .setMaxResults(6);  // Next 6 records (5th to 10th)
        List<Student> students = criteria.list();
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private static void applyConditionsOnCGPAHCQL(Session session) {
        Criteria criteria = session.createCriteria(Student.class)
                                   .add(Restrictions.gt("cgpa", 7.0))    // greater than 7.0
                                   .add(Restrictions.lt("cgpa", 9.0));   // less than 9.0
        List<Student> students = criteria.list();
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private static void getRecordsInOrderHCQL(Session session) {
        Criteria criteria = session.createCriteria(Student.class)
                                   .addOrder(Order.asc("name"));  // ascending order by name
        List<Student> students = criteria.list();
        for (Student student : students) {
            System.out.println(student);
        }

        System.out.println("In descending order:");
        criteria = session.createCriteria(Student.class)
                          .addOrder(Order.desc("name"));  // descending order by name
        students = criteria.list();
        for (Student student : students) {
            System.out.println(student);
        }
    }
}
