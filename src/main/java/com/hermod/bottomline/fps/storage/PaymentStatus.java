package com.hermod.bottomline.fps.storage;

public enum PaymentStatus {
    PENDING("pending"),
    PROCESSED("processed");

    private final String name;

    public String getName() {
        return name;
    }

    PaymentStatus(String name) {
        this.name = name;
    }
}
