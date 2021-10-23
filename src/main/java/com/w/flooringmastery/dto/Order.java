/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.w.flooringmastery.dto;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author William
 */
public class Order {

//    OrderNumber – Integer
//    CustomerName – String
//    State – String
//    TaxRate – BigDecimal
//    ProductType – String
//    Area – BigDecimal
//    CostPerSquareFoot – BigDecimal
//    LaborCostPerSquareFoot – BigDecimal
//    MaterialCost – BigDecimal
//    LaborCost – BigDecimal
//    Tax – BigDecimal
//    Total – BigDecimal
    private int OrderNumber;
    private String CustomerName;
    private String State;
    private BigDecimal TaxRate;
    private String ProductType;
    private BigDecimal Area;
    private BigDecimal CostPerSquareFoot;
    private BigDecimal LaborCostPerSquareFoot;
    private BigDecimal MaterialCost;
    private BigDecimal LaborCost;
    private BigDecimal Tax;
    private BigDecimal Total;
    private LocalDate OrderDate;

    public Order() {
    }

    public Order(int ordernumber, String customername, String state,
            BigDecimal taxrate, String producttype, BigDecimal area,
            BigDecimal costpersquarefoot, BigDecimal laborcostpersquarefoot,
            BigDecimal materialcost, BigDecimal laborcost, BigDecimal tax, BigDecimal total, LocalDate orderdate) {
        this.OrderNumber = ordernumber;
        this.CustomerName = customername;
        this.State = state;
        this.TaxRate = taxrate;
        this.ProductType = producttype;
        this.Area = area;
        this.CostPerSquareFoot = costpersquarefoot;
        this.LaborCostPerSquareFoot = laborcostpersquarefoot;
        this.MaterialCost = materialcost;
        this.LaborCost = laborcost;
        this.Tax = tax;
        this.Total = total;
        this.OrderDate = orderdate;

    }

    public LocalDate getOrderDate() {
        return OrderDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.OrderNumber;
        hash = 29 * hash + Objects.hashCode(this.CustomerName);
        hash = 29 * hash + Objects.hashCode(this.State);
        hash = 29 * hash + Objects.hashCode(this.TaxRate);
        hash = 29 * hash + Objects.hashCode(this.ProductType);
        hash = 29 * hash + Objects.hashCode(this.Area);
        hash = 29 * hash + Objects.hashCode(this.CostPerSquareFoot);
        hash = 29 * hash + Objects.hashCode(this.LaborCostPerSquareFoot);
        hash = 29 * hash + Objects.hashCode(this.MaterialCost);
        hash = 29 * hash + Objects.hashCode(this.LaborCost);
        hash = 29 * hash + Objects.hashCode(this.Tax);
        hash = 29 * hash + Objects.hashCode(this.Total);
        hash = 29 * hash + Objects.hashCode(this.OrderDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.OrderNumber != other.OrderNumber) {
            return false;
        }
        if (!Objects.equals(this.CustomerName, other.CustomerName)) {
            return false;
        }
        if (!Objects.equals(this.State, other.State)) {
            return false;
        }
        if (!Objects.equals(this.ProductType, other.ProductType)) {
            return false;
        }
        if (!Objects.equals(this.TaxRate, other.TaxRate)) {
            return false;
        }
        if (!Objects.equals(this.Area, other.Area)) {
            return false;
        }
        if (!Objects.equals(this.CostPerSquareFoot, other.CostPerSquareFoot)) {
            return false;
        }
        if (!Objects.equals(this.LaborCostPerSquareFoot, other.LaborCostPerSquareFoot)) {
            return false;
        }
        if (!Objects.equals(this.MaterialCost, other.MaterialCost)) {
            return false;
        }
        if (!Objects.equals(this.LaborCost, other.LaborCost)) {
            return false;
        }
        if (!Objects.equals(this.Tax, other.Tax)) {
            return false;
        }
        if (!Objects.equals(this.Total, other.Total)) {
            return false;
        }
        if (!Objects.equals(this.OrderDate, other.OrderDate)) {
            return false;
        }
        return true;
    }

    public void setOrderDate(LocalDate OrderDate) {
        this.OrderDate = OrderDate;
    }

    public int getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(int OrderNumber) {
        this.OrderNumber = OrderNumber;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getFormattedTaxRate() {
        return NumberFormat.getCurrencyInstance().format(TaxRate);
    }

    public void setTaxRate(BigDecimal TaxRate) {
        this.TaxRate = TaxRate;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String ProductType) {
        this.ProductType = ProductType;
    }

    public String getFormattedArea() {
        return NumberFormat.getCurrencyInstance().format(Area);
    }

    public void setArea(BigDecimal Area) {
        this.Area = Area;
    }

    public String getFormattedCostPerSquareFoot() {
        return NumberFormat.getCurrencyInstance().format(CostPerSquareFoot);
    }

    public void setCostPerSquareFoot(BigDecimal CostPerSquareFoot) {
        this.CostPerSquareFoot = CostPerSquareFoot;
    }

    public String getFormattedLaborCostPerSquareFoot() {
        return NumberFormat.getCurrencyInstance().format(LaborCostPerSquareFoot);
    }

    public void setLaborCostPerSquareFoot(BigDecimal LaborCostPerSquareFoot) {
        this.LaborCostPerSquareFoot = LaborCostPerSquareFoot;
    }

    public String getFormattedMaterialCost() {
        return NumberFormat.getCurrencyInstance().format(MaterialCost);
    }

    public void setMaterialCost(BigDecimal MaterialCost) {
        this.MaterialCost = MaterialCost;
    }

    public String getFormattedLaborCost() {
        return NumberFormat.getCurrencyInstance().format(LaborCost);
    }

    public BigDecimal getTaxRate() {
        return TaxRate;
    }

    public BigDecimal getArea() {
        return Area;
    }

    public BigDecimal getCostPerSquareFoot() {
        return CostPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return LaborCostPerSquareFoot;
    }

    public BigDecimal getMaterialCost() {
        return MaterialCost;
    }

    public BigDecimal getLaborCost() {
        return LaborCost;
    }

    public BigDecimal getTax() {
        return Tax;
    }

    public BigDecimal getTotal() {
        return Total;
    }

    public void setLaborCost(BigDecimal LaborCost) {
        this.LaborCost = LaborCost;
    }

    public String getFormattedTax() {
        return NumberFormat.getCurrencyInstance().format(Tax);
    }

    public void setTax(BigDecimal Tax) {
        this.Tax = Tax;
    }

    public String getFormattedTotal() {
        return NumberFormat.getCurrencyInstance().format(Total);
    }

    public void setTotal(BigDecimal Total) {
        this.Total = Total;
    }

    public void printvalues() {
        System.out.println(OrderNumber + "   " + CustomerName + "   " + State + "   " + TaxRate + "   " + ProductType + "   " + Area + "   " + CostPerSquareFoot + "   " + LaborCostPerSquareFoot + "   " + MaterialCost + "   " + LaborCost + "   " + Tax + "   " + Total + "   " + OrderDate + "   ");
    }
}
