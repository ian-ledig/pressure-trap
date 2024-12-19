package com.pressuretrap.trap;

public enum PressureTrapState {
    NONE("Empty"),
    FIRE("Fire spray"),
    POISON("Poison stinger"),
    WITHER("Whiter contamination");

    public final String label;

    private PressureTrapState(String label){
        this.label = label;
    }
}