package me.zhouzhuo810.stringkiller.bean;

/**
 * 字符串实体类
 * Created by zz on 2017/9/20.
 */
public class StringEntity {
    private String id;
    private String value;

    public StringEntity() {
    }

    public StringEntity(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
