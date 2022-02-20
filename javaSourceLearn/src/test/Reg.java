package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reg {

    public static void main(String[] args) {
        // 邮政编码
        String postal_code = "[1-9]\\d{5}";
        // 区号-座机号码
        String landline = "\\d{3}-\\d{8}|\\d{4}-\\d{7}";
        // 手机号码
        String phone = "1[345678]\\d{9}";

        // 测试字符串
        String text = "513215 13411156663 010-88888888";

        Pattern r = Pattern.compile(postal_code);
        Matcher m = r.matcher(text);
        System.out.println("邮政编码：");
        if (m.find()) {
            System.out.println(m.group());
        }

        r = Pattern.compile(landline);
        m = r.matcher(text);
        System.out.println("区号-座机号码：");
        if (m.find()) {
            System.out.println(m.group());
        }

        r = Pattern.compile(phone);
        m = r.matcher(text);
        System.out.println("手机号码：");
        if (m.find()) {
            System.out.println(m.group());
        }
    }
}
