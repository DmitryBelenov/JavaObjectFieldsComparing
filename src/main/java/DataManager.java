package main.java;

import java.io.InvalidClassException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Менеджер обработки данных
 *
 * @author Dmitry Belenov
 *
 * */


public abstract class DataManager {

    /**
     * Сравнение полей двух экземпляров идентичных
     * объектов
     *
     * @param o1 - объект 1
     * @param o2 - объект 2
     * @param checkFields - поля для сравнения
     *
     * @return - признак равенства полей (true / false)
     * */
    public static boolean compareFieldsOfSameObjects (Object o1, Object o2, List<String> checkFields)
            throws IllegalAccessException, InvalidClassException {
        List<Boolean> res = new ArrayList<>();
        Class cl1 = o1.getClass();
        Class cl2 = o2.getClass();
        if (cl1.equals(cl2)) {
            Field[] fields = o1.getClass().getDeclaredFields();
            for (Field f : fields) {
                String paramName = f.getName();
                if (checkFields.contains(paramName)) {
                    f.setAccessible(true);
                    Object val1 = f.get(o1);
                    Object val2 = f.get(o2);
                    if (val1 == null && val2 == null) {
                        res.add(true);
                    } else {
                        if (val1 != null && val2 != null) {
                            if (val1.equals(val2)) {
                                res.add(true);
                            } else {
                                res.add(false);
                            }
                        } else {
                            res.add(false);
                        }
                    }
                }
            }
        } else {
            throw new InvalidClassException("\nТип объекта "+cl1+" не соответствует "+cl2);
        }
        return res.stream().allMatch(b->b.equals(true));
    }
}

