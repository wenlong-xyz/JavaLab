package base.exception;

/**
 * Created by wenlong on 2017/3/5.
 */
public class FinallyTest {
    public String finallyTest() {
        String a = "";
        try {
            a = "try block";
            throw new Exception("bye");
        } catch (Exception e) {
            return a=function();
        } finally {
            System.out.println("finally");
            a = "finally block";
        }
    }
    private String function() {
        System.out.println("function");
        return "catch function";
    }
    public static void main(String[] args) {
        FinallyTest test = new FinallyTest();
        System.out.println("a = " + test.finallyTest());
    }
}
