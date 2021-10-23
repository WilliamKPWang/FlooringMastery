/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.w.flooringmastery.service;

import com.w.flooringmastery.dao.FlooringMasteryDAO;
import com.w.flooringmastery.dao.FlooringMasteryDAOImpl;
import com.w.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.w.flooringmastery.dto.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author William
 */
@Component
public class FlooringMasteryServiceImpl implements FlooringMasteryService {

    @Autowired
    private FlooringMasteryDAO dao;

    public FlooringMasteryServiceImpl(FlooringMasteryDAO dao) {
        this.dao = dao;
    }

    public FlooringMasteryServiceImpl() {
        this.dao = new FlooringMasteryDAOImpl();
    }

    @Override
    public void getLoadOPTFiles(String orderFile, String productsFile, String taxesFile) throws FlooringMasteryPersistenceException {
        dao.LoadOrdersFileNEW(orderFile);
        dao.LoadProductsAndTaxes(productsFile, taxesFile);
    }

    @Override
    public void WriteOrdersToFile() throws FlooringMasteryPersistenceException {
        dao.WriteOrdersFileNEW("./SampleFileData/Orders/Orders_" + String.format("%02d%02d%d", LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear()) + ".txt");
    }

    @Override
    public void WriteOrdersToFile(String fileName) throws FlooringMasteryPersistenceException, InvalidOrderException {

        dao.WriteOrdersFileNEW("./SampleFileData/Orders/Orders_" + DateToFileDate(fileName) + ".txt");
    }

    private String DateToFileDate(String dateAsText) throws InvalidOrderException {
        this.ValidateDate(dateAsText);
        return dateAsText.substring(0, 2) + dateAsText.substring(3, 5) + dateAsText.substring(6);
    }

    @Override
    public LocalDate ValidateDate(String dateAsString) throws InvalidOrderException {
        LocalDate date;
        try {
            date = LocalDate.parse(dateAsString, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } catch (DateTimeParseException e) {

            try {
                date = LocalDate.parse(dateAsString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException DTPE) {
                throw new InvalidOrderException("Invalid date");
            }
        }

        return date;
    }

    @Override
    public boolean IsFutureDate(LocalDate date) throws InvalidOrderException {
        if (!date.isAfter(LocalDate.now())) {
            throw new InvalidOrderException("Invalid Date, must be after today");
        }
        return true;
    }

    @Override
    public String ValidateCustomerName(String name) throws InvalidOrderException {
        String regex = "[#$%^&*()_+{}\\[\\]:;<>/?-]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(name);
        if (m.find()) {
//        if (!name.matches(regex)) {
            throw new InvalidOrderException("Invalid Name");
        }
        return name;
    }

    @Override
    public Map<Integer, Order> getAllOrdersDate(String dateAsString) throws InvalidOrderException {
        Map<Integer, Order> map = dao.GetAllOrdersFromDate(dateAsString);
        if (map.isEmpty()) {
            throw new InvalidOrderException("no orders with that date");
        }
        return map;
    }

    @Override
    public List<Order> getAllOrders() {
        return dao.GetAllOrders();
    }

    @Override
    public List<Product> getAllProducts() {
        return dao.GetAllProducts();
    }

    @Override
    public void AddOrder(Order order) throws InvalidOrderException {

//
//    Order Date – Must be in the future
//    Customer Name – May not be blank, allowed to contain [a-z][0-9] as well as periods and comma characters. "Acme, Inc." is a valid name.
//    State – Entered states must be checked against the tax file. If the state does not exist in the tax file we cannot sell there. If the tax file is modified to include the state, it should be allowed without changing the application code.
//    Product Type – Show a list of available products and pricing information to choose from. Again, if a product is added to the file it should show up in the application without a code change.
//    Area – The area must be a positive decimal. Minimum order size is 100 sq ft.
//
//    MaterialCost = (Area * CostPerSquareFoot)
//    LaborCost = (Area * LaborCostPerSquareFoot)
//    Tax = (MaterialCost + LaborCost) * (TaxRate/100)
//        Tax rates are stored as whole numbers
//    Total = (MaterialCost + LaborCost + Tax)
//
//
//    CustomerName – String //
//    State – String //
//    ProductType – String //
//    Area – BigDecimal //
//    OrderNumber – Integer //
        order.setOrderNumber(dao.GetNewOrderNumber());
//    TaxRate – BigDecimal //
        order.setTaxRate(dao.GetTax(order.getState()).getTaxRate());
//    CostPerSquareFoot – BigDecimal
        order.setCostPerSquareFoot(dao.GetProduct(order.getProductType()).getCostPerSquareFoot());
//    LaborCostPerSquareFoot – BigDecimal
        order.setLaborCostPerSquareFoot(dao.GetProduct(order.getProductType()).getLaborCostPerSquareFoot());

//    MaterialCost – BigDecimal
        order.setMaterialCost(order.getArea().multiply(order.getCostPerSquareFoot()).setScale(2, RoundingMode.HALF_EVEN));
//    LaborCost – BigDecimal
        order.setLaborCost(order.getArea().multiply(order.getLaborCostPerSquareFoot()).setScale(2, RoundingMode.HALF_EVEN));

        BigDecimal subtotal = order.getMaterialCost().add(order.getLaborCost());
//    Tax – BigDecimal
        BigDecimal taxRate = dao.GetTax(order.getState()).getTaxRate().divide(new BigDecimal("100").setScale(2, RoundingMode.HALF_EVEN));
        order.setTax(subtotal.multiply(taxRate));

//    Total – BigDecimal
        order.setTotal(subtotal.add(order.getTax()).setScale(2, RoundingMode.HALF_EVEN));

        dao.AddOrder(order);
    }

    @Override
    public Order getOrder(int orderNumber, LocalDate date) throws InvalidOrderException {
        Order order = dao.getOrder(orderNumber);
        if (order == null || order.getOrderDate() != date) {
            throw new InvalidOrderException("no orders with that date and order number");
        }
        return order;
    }

    @Override
    public Order getOrder(int orderNumber) throws InvalidOrderException {
        Order order = dao.getOrder(orderNumber);
        if (order == null) {
            throw new InvalidOrderException("no orders with that order number");
        }
        return order;
    }

    @Override
    public void EditOrder(Order order) throws InvalidOrderException {
        Order orderWithMods = dao.getOrder(order.getOrderNumber());
//            order.setCustomerName(view.GetCustomerName());
        if (order.getCustomerName() != "" && order.getCustomerName() != "\n" && order.getCustomerName() != null) {
            orderWithMods.setCustomerName(order.getCustomerName());
        }
//            order.setState(view.GetState());
        if (order.getState() != "" && order.getState() != "\n" && order.getState() != null) {
            orderWithMods.setState(order.getState());
        }
//            order.setProductType(view.GetProductType());
        if (order.getProductType() != "" && order.getProductType() != "\n" && order.getProductType() != null) {
            orderWithMods.setProductType(order.getProductType());
        }
//            order.setArea(new BigDecimal(view.GetArea()));
        if (order.getArea() != null && order.getArea().signum() != 0) {
            orderWithMods.setArea(order.getArea());
        }
        this.AddOrder(orderWithMods);
    }

    @Override
    public void RemoveOrder(int orderNumber) throws InvalidOrderException {
        dao.RemoveOrder(orderNumber);
    }

    @Override
    public void ValidateOrder(Order order) throws InvalidOrderException {

        //    Order Date – Must be in the future
        if (!order.getOrderDate().isAfter(LocalDate.now())) {
            throw new InvalidOrderException("Invalid Date, must be after today");
        }
        //    Customer Name – May not be blank, allowed to contain [a-z][0-9] as well as periods and comma characters. "Acme, Inc." is a valid name.
        ValidateCustomerName(order.getCustomerName());
        //    State – Entered states must be checked against the tax file. If the state does not exist in the tax file we cannot sell there. If the tax file is modified to include the state, it should be allowed without changing the application code.
        if (dao.GetTax(order.getState()) == null) {
            throw new InvalidOrderException("Invalid State");
        }

        //    Product Type – Show a list of available products and pricing information to choose from. Again, if a product is added to the file it should show up in the application without a code change.
        //    Area – The area must be a positive decimal. Minimum order size is 100 sq ft.
        if (order.getArea().compareTo(new BigDecimal("100")) == -1) {
            throw new InvalidOrderException("Invalid Area, must be greater than 100");
        }
//        return true;
    }
}
