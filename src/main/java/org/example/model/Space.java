package org.example.model;

import java.math.BigDecimal;

public class Space {
    private String type;
    private BigDecimal price;

    public Space(String type, BigDecimal price) {
        this.type = type;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Space{" +
                "type='" + type + '\'' +
                ", price=" + price +
                '}';
    }
}
