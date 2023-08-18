package org.example;

import org.example.entities.User;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Scanner;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        SessionFactory sf = HibernateUtil.getSessionFactory();
        try(Session context = sf.openSession()) {
            // start transaction
            Transaction tx = context.beginTransaction();

            System.out.println("\t\t\t\t ***** USERS CRUD *****");
            while(true) {
                try {
                    System.out.println("\n\n1. Create user");
                    System.out.println("2. Update user");
                    System.out.println("3. Delete user");
                    System.out.println("4. All users");
                    System.out.println("5. Exit");
                    System.out.print(">> ");
                    int choice = Integer.parseUnsignedInt(in.nextLine());
                    if (choice == 5)
                        break;

                    switch(choice) {
                        case 1: { // Create
                            while(true) {
                                try {
                                    CreateUser(context);
                                    break;
                                } catch(Exception e) {
                                    System.out.println(e.getMessage());
                                    System.in.read();
                                }
                            }
                            break;
                        }
                        case 2: { // Update
                            while (true) {
                                try {
                                    System.out.print("Input user's ID: ");
                                    int id = Integer.parseUnsignedInt(in.nextLine());
                                    UpdateUser(id, context);
                                    break;
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                    System.in.read();
                                }
                            }
                            break;
                        }
                        case 3: { // Delete
                            while (true) {
                                try {
                                    System.out.print("Input user's ID: ");
                                    int id = Integer.parseUnsignedInt(in.nextLine());
                                    RemoveUser(id, context);
                                    break;
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                    System.in.read();
                                }
                            }
                            break;
                        }
                        case 4: { // List
                            List<User> users = loadAllData(User.class, context);
                            for(User item: users) {
                                System.out.println(item);
                            }
                            break;
                        }
                        default: {
                            System.out.println("Unknown command. Try one of the recommended.");
                            System.in.read(); // wait to press the ENTER
                        }
                    }

                }
                catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            // Create User 'C'
            /*
            User user = new User();
            user.setFirstName("Назар");
            user.setLastName("Федун");
            user.setEmail("fedunnazar@gmail.com");
            user.setPassword("123456");
            user.setPhone("+38096 812 93 63");
            context.save(user); // добавляєм юзера в БД
             */

            //Read Users 'R'
            /*List<User> users = loadAllData(User.class, context);
            for (User item : users) {
                System.out.println(item);
            }
             */

            // Update User 'U'
            /*UpdateUser(1, context);*/

            // Remove User 'D'
            /*RemoveUser(2, context);*/



            // Get User by Id
            /*
            User user = GetUserById(2, context);
            if (user != null) {
                System.out.println("Found user: " + user);
            }
            else {
                System.out.println("User not found.");
            }
            */

            // close transaction
            tx.commit();
            //System.out.println("Add new User id = " + user.getId());
        }
    }

    private static User GetUserById (int id, Session context) {
        return context.get(User.class, id);
    }

    private static void CreateUser(Session context) {
        Scanner in = new Scanner(System.in);
        User user = new User();

        System.out.print("Email: ");
        user.setEmail(in.nextLine());
        System.out.print("First name: ");
        user.setFirstName(in.nextLine());
        System.out.print("Last name: ");
        user.setLastName(in.nextLine());
        System.out.print("Password: ");
        user.setPassword(in.nextLine());
        System.out.print("Phone: ");
        user.setPhone(in.nextLine());

        context.save(user);
    }

    private static void RemoveUser(int id, Session context) {
        User user = GetUserById(id, context);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        context.remove(user);
        System.out.println("User #" + id + " has been deleted successfully");
    }

    private static void UpdateUser(int id, Session context) {
        User user = GetUserById(id, context);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        System.out.println("User's data: " + user);

        Scanner in = new Scanner(System.in);
        System.out.print("Email: ");
        user.setEmail(in.nextLine());
        System.out.print("First name: ");
        user.setFirstName(in.nextLine());
        System.out.print("Last name: ");
        user.setLastName(in.nextLine());
        System.out.print("Phone: ");
        user.setPhone(in.nextLine());

        context.saveOrUpdate(user);
        System.out.println("User has been updated successfully");
    }

    private static <T> List<T> loadAllData(Class<T> type, Session context) {
        CriteriaBuilder builder = context.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        //List<T> data = context.createQuery(criteria).getResultList();
        return context.createQuery(criteria).getResultList();
    }
}