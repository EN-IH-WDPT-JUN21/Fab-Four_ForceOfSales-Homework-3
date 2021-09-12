package com.ironhack.FabFour_ForceOfSalesHomework3.enums;

public enum TextColor {

    RED("\u001B[31m"),
    GREEN("\u001B[32m");

    public final String color;

    TextColor(String s) {
        this.color=s;
    }
}
