package lk.ijse.pos.entity;

import java.math.BigDecimal;

public class OrderDetails {
    private String oId;
    private String itemCode;
    private int qty;
    private BigDecimal unitPrice;

    public OrderDetails() {
    }

    public OrderDetails(String oId, String itemCode, int qty, BigDecimal unitPrice) {
        this.oId = oId;
        this.itemCode = itemCode;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
