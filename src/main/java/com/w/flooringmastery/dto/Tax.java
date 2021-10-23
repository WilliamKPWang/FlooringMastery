/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.w.flooringmastery.dto;

import java.math.BigDecimal;

/**
 *
 * @author William
 */
public class Tax {

    private String StateAbbreviation;
    private String StateName;
    private BigDecimal TaxRate;

    public String getStateAbbreviation() {
        return StateAbbreviation;
    }

    public void setStateAbbreviation(String StateAbbreviation) {
        this.StateAbbreviation = StateAbbreviation;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String StateName) {
        this.StateName = StateName;
    }

    public BigDecimal getTaxRate() {
        return TaxRate;
    }

    public void setTaxRate(BigDecimal TaxRate) {
        this.TaxRate = TaxRate;
    }

}
