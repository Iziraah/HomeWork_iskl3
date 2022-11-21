import java.io.*;
import java.util.*;

public class task1 {
    public static void main(String[] args) {
        HashMap<String, String> data = getUserData();
        writeFile(data);
    }

public static HashMap<String, String> getUserData() {
    try (Scanner scanner = new Scanner(System.in)) {
            StringBuilder sb = new StringBuilder();
            HashMap<String, String> data = new HashMap<>();
            System.out.println("Enter Name, LastName, MiddleName, " +
                    "BirthDate (dd.mm.yyyy), " +
                    "PhoneNum (7XXXXXXXXXX), " +
                    "Sex (f/m)");
            sb.append(scanner.nextLine());
            String[] str = sb.toString().split(" ");
            if (str.length != 6){
            throw new ElementQuantityException("should be 6 elements" + str.length);}
            System.out.println(Arrays.toString(str));
            for (String elem : str) {
                if (Objects.equals(elem.toLowerCase(), "f") || Objects.equals(elem.toLowerCase(), "m"))
                    data.put("Sex", elem.toLowerCase());
                else if (elem.contains(".")) {
                    String[] date = elem.split("\\.");
                    if (date[0].length() != 2 || date[1].length() != 2 || date[2].length() != 4) {
                        throw new DateFormatException("wrong date format");
                    }
                    try {
                        if (Integer.parseInt(date[0]) > 31 || Integer.parseInt(date[0]) <= 0) {
                            throw new DateFormatException("not so many days in a month" + date[0]);
                        } else if (Integer.parseInt(date[1]) > 12 || Integer.parseInt(date[1]) <= 0) {
                            throw new DateFormatException("the number of months in a year cannot be as you wrote" + date[1]);
                        } else if (Integer.parseInt(date[2]) > 2022) {
                            throw new DateFormatException("you can't be that young" + date[2]);
                        } else data.put("BirthDate", elem);
                    } catch (NumberFormatException e) {
                        throw new DateFormatException("Date format must be numerical");
                    }
                } else if (elem.matches("[0-9]+")) {
                    if (elem.length() == 11 && elem.startsWith("7")) data.put("PhoneNum", elem);
                        else throw new TelNumberFormatException("TelNumber format must be 7хххххххххх");
                } else if (elem.toLowerCase().endsWith("vich") || elem.toLowerCase().endsWith("vna"))
                        data.put("MiddleName", elem);
                else if (elem.toLowerCase().endsWith("in") || elem.toLowerCase().endsWith("ina") ||
                        elem.toLowerCase().endsWith("ov") || elem.toLowerCase().endsWith("ova") || 
                        elem.toLowerCase().endsWith("ovich") || elem.toLowerCase().endsWith("ij") ||
                         elem.toLowerCase().endsWith("aja")) {
                        data.put("LastName", elem);
                } else {
                    if (data.containsKey("Name"))
                        throw new NotIdentifyElementException("Can't identify element " + elem);
                    else data.put("Name", elem);
                }
            }
            System.out.println(data);
            return data;
        }
    }

    static void writeFile(HashMap<String, String> hm) {
        try (FileWriter fw = new FileWriter("file.txt", true)) {
            if (!hm.isEmpty()) fw.append("\n");
            for (Map.Entry<String, String> el : hm.entrySet()) {
                String key = el.getKey();
                String value = el.getValue();
                fw.append(String.format("%s: %s\n", key, value));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
