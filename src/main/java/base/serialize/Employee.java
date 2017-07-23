package base.serialize;

import java.io.Serializable;
import java.util.Deque;
import java.util.List;

/**
 * Created by wenlong on 2017/3/5.
 */
public class Employee implements Serializable {
    //  private static final long serialVersionUID = -6470090944414208496L;

    private String name;
    private int id;
    transient private int salary;

    @Override
    public String toString(){
        return "Employee{name="+name+",id="+id+",salary="+salary+"}";
    }

    //getter and setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

}

