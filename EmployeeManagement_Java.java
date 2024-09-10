import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("serial")
class Employee implements Serializable {

    private int id;
    private String name;
    private float salary;
    private long contactNo;
    private String emailId;

    public Employee(int id, String name, float salary, long contactNo, String emailId) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.contactNo = contactNo;
        this.emailId = emailId;
    }

    @Override
    public String toString() {
        return String.format("\nEmployee Details:\nID: %d\nName: %s\nSalary: %.2f\nContact No: %d\nEmail ID: %s",
                id, name, salary, contactNo, emailId);
    }

    // Getters and setters for modern practices
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public float getSalary() { return salary; }
    public void setSalary(float salary) { this.salary = salary; }
    public long getContactNo() { return contactNo; }
    public void setContactNo(long contactNo) { this.contactNo = contactNo; }
    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }
}

public class EmployeeManagement {

    private static final String FILE_PATH = "EmployeeDataList.ser";

    private static void display(ArrayList<Employee> employees) {
        System.out.println("\n-------------- Employee List ---------------\n");
        System.out.printf("%-10s %-20s %-10s %-15s %-25s%n", "ID", "Name", "Salary", "Contact No", "Email ID");
        for (Employee employee : employees) {
            System.out.printf("%-10d %-20s %-10.2f %-15d %-25s%n",
                    employee.getId(), employee.getName(), employee.getSalary(),
                    employee.getContactNo(), employee.getEmailId());
        }
    }

    private static ArrayList<Employee> loadEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                employees = (ArrayList<Employee>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading employee data: " + e.getMessage());
            }
        }
        return employees;
    }

    private static void saveEmployees(ArrayList<Employee> employees) {
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(employees);
        } catch (IOException e) {
            System.err.println("Error saving employee data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Employee> employees = loadEmployees();

        while (true) {
            System.out.println("\n********* Welcome to the Employee Management System *********\n");
            System.out.println("1. Add Employee\n" +
                               "2. Search Employee\n" +
                               "3. Edit Employee\n" +
                               "4. Delete Employee\n" +
                               "5. Display All Employees\n" +
                               "6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\nEnter Employee Details:");
                    System.out.print("ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Salary: ");
                    float salary = scanner.nextFloat();
                    System.out.print("Contact No: ");
                    long contactNo = scanner.nextLong();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Email ID: ");
                    String emailId = scanner.nextLine();
                    employees.add(new Employee(id, name, salary, contactNo, emailId));
                    display(employees);
                    break;

                case 2:
                    System.out.print("Enter Employee ID to search: ");
                    id = scanner.nextInt();
                    Employee foundEmployee = employees.stream()
                            .filter(e -> e.getId() == id)
                            .findFirst()
                            .orElse(null);
                    if (foundEmployee != null) {
                        System.out.println(foundEmployee);
                    } else {
                        System.out.println("Employee not found!");
                    }
                    break;

                case 3:
                    System.out.print("Enter Employee ID to edit: ");
                    id = scanner.nextInt();
                    Employee employeeToEdit = employees.stream()
                            .filter(e -> e.getId() == id)
                            .findFirst()
                            .orElse(null);
                    if (employeeToEdit != null) {
                        while (true) {
                            System.out.println("\nEdit Employee Details:\n" +
                                               "1. ID\n" +
                                               "2. Name\n" +
                                               "3. Salary\n" +
                                               "4. Contact No\n" +
                                               "5. Email ID\n" +
                                               "6. Go Back");
                            System.out.print("Enter your choice: ");
                            int editChoice = scanner.nextInt();
                            scanner.nextLine();  // Consume newline
                            switch (editChoice) {
                                case 1:
                                    System.out.print("Enter new ID: ");
                                    employeeToEdit.setId(scanner.nextInt());
                                    break;
                                case 2:
                                    System.out.print("Enter new Name: ");
                                    employeeToEdit.setName(scanner.nextLine());
                                    break;
                                case 3:
                                    System.out.print("Enter new Salary: ");
                                    employeeToEdit.setSalary(scanner.nextFloat());
                                    break;
                                case 4:
                                    System.out.print("Enter new Contact No: ");
                                    employeeToEdit.setContactNo(scanner.nextLong());
                                    break;
                                case 5:
                                    System.out.print("Enter new Email ID: ");
                                    employeeToEdit.setEmailId(scanner.nextLine());
                                    break;
                                case 6:
                                    return;  // Go back to main menu
                                default:
                                    System.out.println("Invalid choice, please try again.");
                                    break;
                            }
                            System.out.println(employeeToEdit);
                        }
                    } else {
                        System.out.println("Employee not found!");
                    }
                    break;

                case 4:
                    System.out.print("Enter Employee ID to delete: ");
                    id = scanner.nextInt();
                    employees.removeIf(e -> e.getId() == id);
                    display(employees);
                    break;

                case 5:
                    display(employees);
                    break;

                case 6:
                    saveEmployees(employees);
                    System.out.println("Exiting... Data saved.");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
    }
}
