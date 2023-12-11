package l1.tuan.anh.validate;

import l1.tuan.anh.entity.Certificate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
//    private static final String PHONE_NUMBER_REGEX = "^\\0(?:[0-9] ?){6,11}[0-9]$";
    private static final String PHONE_NUMBER_REGEX = "^0\\d{10}$";
    /*kiểm tra xem chuỗi có bắt đầu bằng "0" và tiếp theo là một chuỗi các chữ số có độ dài là 10.*/

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidCode(String code) {
        if (code.contains(" ") || code.length() < 6 || code.length() > 10) {
            return false;
        }
        return true;
    }

    public static boolean isValidName(String name) {
        if (name.trim().length() <= 0) {
            return false;
        }
        return true;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches(PHONE_NUMBER_REGEX);
    }

    public static boolean isValidAge(int age) {
        if (age <= 0) {
            return false;
        }
        return true;
    }

    public static boolean isValidCertificate(List<Certificate> certificates) {
        // Check sum of certificates
        int count = 0;
        int size = certificates.size();
        if (size <= 1) {
            return true;
        }

        LocalDate localDate = LocalDate.now();
        Date now = Date.valueOf(localDate);
        System.out.println(now);
        for (int i = 0; i < size; i++) {
            if (certificates.get(i).getExpired().after(now)) {
                count++;
            }
            if (count > 3) {
                return false;
            }
        }

        // check Province
        List<Integer> provinces = new ArrayList<>();
        provinces.add(certificates.get(0).getProvince().getId());

        for (int i = 1; i < size; i++ ) {
            int sizeofprovinces = provinces.size();
            for (int j = 0; j < sizeofprovinces; j++) {
                if (certificates.get(i).getProvince().getId() == provinces.get(j)) {
                    return false;
                } else {
                    provinces.add(certificates.get(i).getProvince().getId());
                    j = sizeofprovinces;
                }
            }
        }
        provinces = null;
        return true;
    }
}
