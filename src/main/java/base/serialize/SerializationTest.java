package base.serialize;

import java.io.IOException;

/**
 * Created by wenlong on 2017/3/5.
 */
public class SerializationTest {
    public static void main(String[] args) {
        String fileName="employee.ser";
        Employee emp = new Employee();
        emp.setId(100);
        emp.setName("Pankaj");
        emp.setSalary(5000);
        Device device = new Device(1,"computer");
        //serialize to file
        try {
            SerializationUtil.serialize(emp, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Employee empNew = null;
        try {
            empNew = (Employee) SerializationUtil.deserialize(fileName);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        System.out.println("emp Object::"+emp);
        System.out.println("empNew Object::"+empNew);
    }
}
