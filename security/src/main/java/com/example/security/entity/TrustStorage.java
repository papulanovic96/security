package com.example.security.entity;

import java.util.List;


public class TrustStorage {

	private String target;

    private List<String> serialNumbers;

    public TrustStorage() {
    }

    public TrustStorage(String target, List<String> serialNumbers) {
        this.target = target;
        this.serialNumbers = serialNumbers;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<String> getSerialNumbers() {
        return serialNumbers;
    }

    public void setSerialNumbers(List<String> serialNumbers) {
        this.serialNumbers = serialNumbers;
    }
}
