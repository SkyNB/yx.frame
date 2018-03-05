package com.lnet.frame.core;

import java.io.Serializable;

/**
 * 选择项数据序列化对象,减少没必要的额外传输
 * 暂时直接先换成下面这个对象,有更好的方式再改.
 */
/*@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor*/
public class SelectObject implements Serializable {
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String value;
    private String text;

    public SelectObject(String id, String value, String text) {
        this.id = id;
        this.value = value;
        this.text = text;
    }

    public SelectObject(String value, String text) {
        this.value = value;
        this.text = text;
    }

}
