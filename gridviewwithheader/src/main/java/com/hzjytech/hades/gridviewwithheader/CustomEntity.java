package com.hzjytech.hades.gridviewwithheader;

/**
 * Created by Hades on 2016/6/21.
 */
public class CustomEntity {

    private boolean isHeader;

    private String content;


    public CustomEntity(String content) {
        this(false,content);
    }

    public CustomEntity(boolean isHeader, String content) {
        this.isHeader = isHeader;
        this.content = content;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
