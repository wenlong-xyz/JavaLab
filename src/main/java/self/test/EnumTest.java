package self.test;

import java.util.Arrays;

/**
 * Created by wenlong on 2017/2/25.
 */
public enum EnumTest {
    INSTANCE;
    private final String[] favoriteSongs =
            { "Hound Dog", "Heartbreak Hotel" };
    public void printFavorites() {
        System.out.println(Arrays.toString(favoriteSongs));
    }
}
