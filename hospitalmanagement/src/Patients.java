import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import com.opencsv.CSVWriter;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.CSVParser;
import java.util.Arrays;
import java.util.List;
import java.time.format.DateTimeFormatter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

public class Patients {
    String pname,dname,city,gen;
    int age;
//    void Patients(){
//        show();
//    }
    void show() throws IOException, CsvException {
        boolean y = true;
        while(y) {
            System.out.println("Enter The Task You Want to Perform :-");
            System.out.println("\u001B[36m" + "          1.New Patient Entry\n" +
                    "          2.All Patient Details\n" +
                    "          3.Search Patient\n" +
                    "          4.Delete Record\n" +
                    "          5.Exit" + "\u001B[0m");
            Scanner Sin = new Scanner(System.in);
            boolean x = true;
            int userInput = 0;
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
                    System.out.println("Enter Patient Details Below:-");
                    System.out.print("Enter Patient Name :");

                    pname = nameset();
                    System.out.print("Enter Patient Age Here : ");

                    age = intset();
                    System.out.print("Enter Patient Gender Here : ");

                    gen = nameset();
                    System.out.print("Enter Patient City Here : ");

                    city = nameset();
                    boolean d =true;
                    while(d) {
                        System.out.println("Available Doctors:-: ");
                        System.out.println("+------------+------------+------------+------------+------------+------------+");
                        System.out.printf("\u001B[35m"+"| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |%n", "Name", "Age","City","Gender", "Dept","JoinDate");
                        System.out.println("\u001B[0m"+"+------------+------------+------------+------------+------------+------------+");
                        datareader("doctors.csv");
                        dname = nameset();

                        boolean v =csvsearcher(dname, "doctors.csv");
                        if(v){
                            d = false;
                        }
                        else{
                            System.out.println("No Matching  Doctor Found In Database..... \nPlease Enter Available Doctor...");
                        }
                    }




                    LocalDate today = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String dateString = today.format(formatter);
                    System.out.println("You entered Patient Data: " + pname+" "+dname+" "+age+" "+gen+" "+city);
                    String [] pdata = {pname,String.valueOf(age),city,gen,dname,dateString};
                    writeCsvFile(pdata,"patients.csv");
                    break;
                case 2:
                    System.out.println("\u001B[32m"+"Here IS Your All Patient In The Data :-"+ "\u001B[0m");
                    System.out.println("+------------+------------+------------+------------+------------+------------+");
                    System.out.printf("\u001B[35m"+"| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |%n", "Name", "Age", "Gender", "City", "Doctor","Date");
                    System.out.println("\u001B[0m"+"+------------+------------+------------+------------+------------+------------+");
                    datareader("patients.csv");

                    break;
                case 3:
                    System.out.print("Enter The Field Value Of You Want To search Data :- ");

                    String s1 = nameset();
                    System.out.printf("\u001B[35m"+"| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |%n", "Name", "Age", "Gender", "City", "Doctor","Date");
                    csvsearcher(s1,"patients.csv");

                    break;
                case 4:
                    System.out.print("\u001B[34m"+"Enter The Field Value You Want To Delete Record:- "+ "\u001B[0m");
                    String valued = nameset();
                    datadelete(valued,"patients.csv");
                    break;
                case 5:
                    y = false;
                    break;
            }
        }



    }
   String nameset(){
        String n1;
       Scanner Sin1 = new Scanner(System.in);
       while (Sin1.hasNextInt()) {
           System.out.println("Invalid input! Please enter a string:");
           Sin1.next();
       }
       n1 = Sin1.next();
       return n1;
   }
   int intset(){
        int n;
       Scanner Sin2 = new Scanner(System.in);
       while (!Sin2.hasNextInt()) {
           System.out.println("Invalid input! Please enter a Integer:");
           Sin2.next();
       }
       n = Sin2.nextInt();
       return n;
   }



            String[] FILE_HEADER = {"Name", "Age", "Gender","City","Doctor","Date"};
            String FILE_NAME = "patients.csv";

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

public void datareader(String fname) throws IOException, CsvException {
    String csvFile = fname;
    String line = "";
    String cvsSplitBy = ",";
    char delimiter = ',';
    boolean firstRow = true;
    try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            if (firstRow) {
                firstRow = false;
                continue;
            }
            System.out.printf("\u001B[35m"+"| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |%n", nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4],nextLine[5]);
        }
        System.out.println("\u001B[0m"+"+------------+------------+------------+------------+------------+------------+");
    } catch (IOException e) {
        e.printStackTrace();
    }


    }
    public boolean csvsearcher(String arr,String fname){
        String csvFilePath = fname;

        boolean x =true;
        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                for (String value : nextLine) {
                    if (value.equalsIgnoreCase(arr)) {
//                        System.out.println("Found value " + arr + " in CSV file.");

                        System.out.printf("\u001B[35m"+"| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |%n", nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4],nextLine[5]);
                        x = false;
                    }
                }
            }
            if(x) {
                System.out.println("\u001B[31m" + "Value " + arr + " not found in CSV file."+"\u001B[0m");
                return false;
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return true;
    }
    public void datadelete(String arr1,String file_name) throws IOException, CsvValidationException {
        List<String[]> records = new ArrayList<>();
        CSVReader reader = new CSVReader(new FileReader(file_name));
        String[] record;
        while ((record = reader.readNext()) != null) {
            records.add(record);
        }
//        System.out.println(records);
        reader.close();
        // Remove the identified record from the list
        int initialSize = records.size();
        records.removeIf(row -> {
            for (String fields : row) {
//                System.out.println(fields+" "+arr1);
                if (fields.equalsIgnoreCase(arr1)) {
                    System.out.println(arr1+" Match found: " + fields);
                    return true;
                }
            }
            return false;
        });
        int finalSize = records.size();
        if (finalSize < initialSize) {
            System.out.println("\u001B[31m"+"Records were removed."+"\u001B[0m");
        } else {
            System.out.println("\u001B[31m"+"No records were removed."+"\u001B[0m");
        }
        // Write the updated list of records back to the CSV file
        CSVWriter writer = new CSVWriter(new FileWriter(file_name));
        writer.writeAll(records);
        writer.close();
    }
//System.out.printf("| %-10s | %-10s | %-10s | %-10s | %-10s |%n", values[0], values[1], values[2],values[3],values[4]);
//end of the class
}

