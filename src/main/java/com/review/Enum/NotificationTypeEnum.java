package com.review.Enum;

public enum NotificationTypeEnum {
    Unread(0),
    Read(1)
    ;

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    NotificationTypeEnum(int status) {
        this.status = status;
    }
}
