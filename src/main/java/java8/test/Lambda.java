package java8.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by wenlong on 2016/9/19.
 */
public class Lambda {
    public static void main(String[] args) {
        List<String> name = Arrays.asList("peter","anna");
        Collections.sort(name,(a,b) -> b.compareTo(a));
    }
}
