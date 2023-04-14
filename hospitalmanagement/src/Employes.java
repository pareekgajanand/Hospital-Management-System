import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Employes {
    String ename,dept,city,gen;
    int age;
    int userInput=0;
    public void show() throws IOException, CsvException {
        Patients p = new Patients();
        boolean y = true;
        while(y){
            System.out.println("Enter The Task You Want to Perform :-");
            System.out.println("\u001B[36m" + "          1.New Employee Entry\n" +
                    "          2.All Employee Details\n" +
                    "          3.Search Employee\n" +
                    "          4.Delete Record\n" +
                    "          5.Exit" + "\u001B[0m");
            Scanner Sin = new Scanner(System.in);
            boolean x = true;
            while (x) {
                System.out.print("Enter Your Choice Here : ");
                if (Sin.hasNextInt()) {

                    userInput = Sin.nextInt();
                    System.out.println("You entered: " + userInput);
                    if (userInput < 1 || userInput > 5) {
                        System.out.println("Please Select Valid Option");
                    } else {
                        x = false;
                    }
                } else {
                    System.out.println("Error: input must be an integer.");
                    Sin.nextLine();
                }
            }

            switch (userInput) {
                case 1:
                    System.out.println("Enter Employee Details Below:-");
                    System.out.print("Enter Employee Name :");

                    ename = p.nameset();
                    System.out.print("Enter Employee Age Here : ");

                    age = p.intset();
                    System.out.print("Enter Employee Gender Here : ");

                    gen = p.nameset();
                    System.out.print("Enter Employee City Here : ");

                    city = p.nameset();
                    System.out.print("Enter Employee Department Here : ");
                    dept = p.nameset();


                    LocalDate today = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String dateString = today.format(formatter);
                    System.out.println("You entered Patient Data: " + ename + " " + age + " " + dept + " " + gen + " " + city);
                    String[] ddata = {ename, String.valueOf(age), city, gen, dept, dateString};
                    writeCsvFile(ddata, "employee.csv");
                    break;
                case 2:
                    System.out.println("\u001B[32m" + "Here IS Your All Employee In The Data :-" + "\u001B[0m");
                    System.out.println("+------------+------------+------------+------------+------------+------------+");
                    System.out.printf("\u001B[35m"+"| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |%n", "Name", "Age","City","Gender", "Dept","JoinDate");
                    System.out.println("\u001B[0m"+"+------------+------------+------------+------------+------------+------------+");
                    p.datareader("employee.csv");

                    break;
                case 3:
                    System.out.print("Enter The Field Value Of You Want To search Data :- ");

                    String s1 = p.nameset();
                    System.out.printf("\u001B[35m"+"| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |%n", "Name", "Age","City","Gender" ,"Dept","JoinDate");
                    p.csvsearcher(s1, "employee.csv");
                    break;
                case 4:
                    System.out.print("\u001B[34m" + "Enter The Field Value You Want To Delete Record:- " + "\u001B[0m");
                    String valued = p.nameset();
                    p.datadelete(valued, "employee.csv");
                    break;
                case 5:
                    y = false;
                    break;
            }
        }
        //end of the show function
    }
    String[] FILE_HEADER = {"Name", "Age","City","Gender","Dept","JoinDate"};


    public void writeCsvFile(String[] data,String FILE_NAME) {
        // Check if file exists
        boolean fileExists = new File(FILE_NAME).exists();

        try (CSVWriter writer = new CSVWriter(new FileWriter(FILE_NAME, true))) {
            // Write headers if file is created newly
            if (!fileExists) {
                writer.writeNext(FILE_HEADER);
            }

            // Write data
            writer.writeNext(data);
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e);
        }
    }
}
