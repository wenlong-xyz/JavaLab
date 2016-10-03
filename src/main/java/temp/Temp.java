package temp;

/**
 * Created by wenlong on 2016/10/1.
 */

public class Temp {
    public static void main(String[] args) {

    }
}
interface I1{
    void f();
}
interface I2{
    void f(int i);
}
interface I3{
    int f();
}
interface I4{
    void f();
}
class C{
    public int f(){
        System.out.println("c.f");
        return 1;
    }
}

class C2 implements I1,I4{

    @Override
    public void f() {

    }


}
class C3 extends C {

    @Override
    public int f() {
        return 2;
    }
}

class C4 extends C implements I3  {

}

