package helpers;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DataHelper {
    public static String generatePassword(int len) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        int length = 8;
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }

    public static String randomNumeric(int strLen) {
        String numStr = "";
        Random random = new Random();
        int i1 = random.nextInt(9) + 1;
        numStr += i1;

        for (int i = 0; i < strLen - 1; i++) {
            int num = random.nextInt(10);
            numStr += num;
        }
        return numStr;
    }
    public static String generateEmail(int len) {
        return "test_" + randomNumeric(len) + "@gmail.com";
    }
    public  static String generateCountry(){
        List<String> countries = Arrays.asList("isr", "ando", "germ", "ukr");
        int randomIndex = new Random().nextInt(countries.size());
        return countries.get(randomIndex);
    }
}
