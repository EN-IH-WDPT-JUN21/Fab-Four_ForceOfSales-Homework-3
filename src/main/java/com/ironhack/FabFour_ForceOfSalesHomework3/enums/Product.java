package com.ironhack.FabFour_ForceOfSalesHomework3.enums;

public enum Product {
    HYBRID("HYBRID"),
    FLATBED("FLATBED"),
    BOX("BOX");

    public final String productType;

    Product(String productType) {
        this.productType = productType;
    }

    public static Product getProduct(String productType) {
        for (Product product : values()) {
            if (product.productType.equals(productType.toUpperCase()))
                return product;
        }
        return null;
    }
}
