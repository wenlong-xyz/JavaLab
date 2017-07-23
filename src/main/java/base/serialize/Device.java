package base.serialize;

/**
 * Created by wenlong on 2017/3/5.
 */
public class Device {
    private int id;
    private String desc;

    public Device(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
