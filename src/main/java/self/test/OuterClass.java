package self.test;

/**
 * Created by wenlong on 2016/10/2.
 */
public class OuterClass {
    private int a;
    class InnerClass{
        private int a;
        public void fun(){
            OuterClass.this.a = 1;
            this.a = 2;
        }
    }

    public static void main(String[] args) {
        OuterClass aa = new OuterClass();
        OuterClass.InnerClass in = aa.new InnerClass();
    }

}
