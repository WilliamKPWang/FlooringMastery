/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.w.flooringmastery.service;

import com.w.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.w.flooringmastery.dto.Order;
import com.w.flooringmastery.dto.Product;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author William
 */
public interface FlooringMasteryService {

    void getLoadOPTFiles(String orderFile, String productsFile, String taxesFile) throws FlooringMasteryPersistenceException;

    void WriteOrdersToFile() throws FlooringMasteryPersistenceException;

    void WriteOrdersToFile(String fileName) throws FlooringMasteryPersistenceException, InvalidOrderException;

    LocalDate ValidateDate(String dateAsString) throws InvalidOrderException;

    boolean IsFutureDate(LocalDate date) throws InvalidOrderException;

    String ValidateCustomerName(String name) throws InvalidOrderException;

    Order getOrder(int orderNumber, LocalDate date) throws InvalidOrderException;

    Order getOrder(int orderNumber) throws InvalidOrderException;

    Map<Integer, Order> getAllOrdersDate(String dateAsString) throws InvalidOrderException;

    List<Order> getAllOrders();

    List<Product> getAllProducts();

    void AddOrder(Order order) throws InvalidOrderException;

    void EditOrder(Order order) throws InvalidOrderException;

    void RemoveOrder(int orderNumber) throws InvalidOrderException;

    void ValidateOrder(Order order) throws InvalidOrderException;
}
