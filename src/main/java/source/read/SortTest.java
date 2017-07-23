package source.read;

import org.junit.Test;

/**
 * Created by wenlong on 2017/1/6.
 */
public class SortTest {
    @Test
    public void insertTest(){
        int[] a = {1, 2, 3, 4, 5, 9, 7 , 8 , 6 };
        int left = 6;
        int right = 8;
        for (int k = left; ++left <= right; k = ++left) {
            int a1 = a[k], a2 = a[left];

            if (a1 < a2) {
                a2 = a1; a1 = a[left];
            }
            while (a1 < a[--k]) {
                a[k + 2] = a[k];
            }
            a[++k + 1] = a1;

            while (a2 < a[--k]) {
                a[k + 1] = a[k];
            }
            a[k + 1] = a2;
        }
    }

    @Test
    public void bitTest(){
        bitCountTest(2);
        bitCountTest(3);
        bitCountTest(4);
        bitCountTest(5);
        bitCountTest(5);
    }

    public void bitCountTest(int count){
        byte odd = 0;
        for (int n = 1; (n <<= 1) < count; odd ^= 1);
        System.out.println(odd);
    }

}
