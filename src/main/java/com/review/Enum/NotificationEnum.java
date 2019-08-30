package com.review.Enum;

public enum NotificationEnum {

    Reply_Question(1,"回复了问题"),
    Reply_Comment(2,"回复了评论");
    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    NotificationEnum(int status, String name){
        this.name=name;
        this.type =status;
    }
}
