package com.myproject.blog.Until.Constants;

public enum ReturnStatus {
    SUCCESS(new ReturnObject("SUCCESS","Operation completed successfully")),
    ERROR(new ReturnObject("ERROR", "An error occurred")),
    SENT(new ReturnObject("SENT","Message has been sent")),
    SAVED(new ReturnObject("SAVED", "Data has been saved successfully"));

    private ReturnObject object;

    private ReturnStatus(ReturnObject object) {
        this.object = object;
    }
    public ReturnObject getObject() {
       return object;
    }

    public void chargeMessage(String message) {
        object.setMessage(message);
    }
}
