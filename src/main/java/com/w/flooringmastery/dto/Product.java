/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.w.flooringmastery.dto;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 *
 * @author William
 */
public class Product {

    private String ProductType;
    private BigDecimal CostPerSquareFoot;
    private BigDecimal LaborCostPerSquareFoot;

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String ProductType) {
        this.ProductType = ProductType;
    }

    public BigDecimal getCostPerSquareFoot() {
        return CostPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal CostPerSquareFoot) {
        this.CostPerSquareFoot = CostPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return LaborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal LaborCostPerSquareFoot) {
        this.LaborCostPerSquareFoot = LaborCostPerSquareFoot;
    }

    public String getFormattedMaterialCost() {//formatted string of material cost
        return NumberFormat.getCurrencyInstance().format(CostPerSquareFoot);
    }
    public String getFormattedLaborCost() {//formatted string of item cost
        return NumberFormat.getCurrencyInstance().format(LaborCostPerSquareFoot);
    }
}
