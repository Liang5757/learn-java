package cn.youyitech.anyview.system.utils;
import java.lang.reflect.Field;
import java.text.Collator;
import java.text.NumberFormat;
import java.util.*;

public class SortUtil {
    public static <E> void sort(List<E> list, final boolean isAsc, final String... sortnameArr) {
        Collections.sort(list, new Comparator<E>() {

            public int compare(E a, E b) {
                int ret = 0;
                try {
                    for (int i = 0; i < sortnameArr.length; i++) {
                        ret = SortUtil.compareObject(sortnameArr[i], isAsc, a, b);
                        if (0 != ret) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ret;
            }
        });
    }
    private static <E> int compareObject(final String sortname, final boolean isAsc, E a, E b) throws Exception {
        int ret;
        final Collator collator=Collator.getInstance(Locale.CHINA);
        Object value1 = SortUtil.forceGetFieldValue(a, sortname);
        Object value2 = SortUtil.forceGetFieldValue(b, sortname);
        String str1 = value1.toString();
        String str2 = value2.toString();
        if (value1 instanceof Number && value2 instanceof Number) {
            int maxlen = Math.max(str1.length(), str2.length());
            str1 = SortUtil.addZero2Str((Number) value1, maxlen);
            str2 = SortUtil.addZero2Str((Number) value2, maxlen);
        } else if (value1 instanceof Date && value2 instanceof Date) {
            long time1 = ((Date) value1).getTime();
            long time2 = ((Date) value2).getTime();
            int maxlen = Long.toString(Math.max(time1, time2)).length();
            str1 = SortUtil.addZero2Str(time1, maxlen);
            str2 = SortUtil.addZero2Str(time2, maxlen);
        }
        if (isAsc) {
            ret = collator.compare(str1,str2);
        } else {
            ret =  collator.compare(str2,str1);
        }
        return ret;
    }
    public static Object forceGetFieldValue(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        Object object = null;
        boolean accessible = field.isAccessible();
        if (!accessible) {
            // 如果是private,protected修饰的属性，需要修改为可以访问的
            field.setAccessible(true);
            object = field.get(obj);
            // 还原private,protected属性的访问性质
            field.setAccessible(accessible);
            return object;
        }
        object = field.get(obj);
        return object;
    }
    public static String addZero2Str(Number numObj, int length) {
        NumberFormat nf = NumberFormat.getInstance();
        // 设置是否使用分组
        nf.setGroupingUsed(false);
        // 设置最大整数位数
        nf.setMaximumIntegerDigits(length);
        // 设置最小整数位数
        nf.setMinimumIntegerDigits(length);
        return nf.format(numObj);
    }


}