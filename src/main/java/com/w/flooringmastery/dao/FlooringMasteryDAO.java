/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.w.flooringmastery.dao;

import com.w.flooringmastery.dto.*;
import java.util.List;
import java.util.Map;

/**
 *
 * @author William
 */
public interface FlooringMasteryDAO {

    Map<Integer, Order> GetAllOrdersFromDate(String dateAsString) throws FlooringMasteryPersistenceException;

    void AddOrder(Order order) throws FlooringMasteryPersistenceException;

    void RemoveOrder(int orderNumber);

    Order getOrder(int orderNumber);

    int GetNewOrderNumber();

    Product GetProduct(String productName);

    Tax GetTax(String state);

    List<Order> GetAllOrders();

    List<Product> GetAllProducts();

    List<Tax> GetAllTaxes();

    //void CreateFile(String fileName) throws FlooringMasteryPersistenceException;
//    void LoadOrderFile(String fileName) throws FlooringMasteryPersistenceException;
//
//    void WriteOrderFile(String fileName) throws FlooringMasteryPersistenceException; OLD CODE
    void LoadOrdersFileNEW(String fileName) throws FlooringMasteryPersistenceException;

    void WriteOrdersFileNEW(String fileName) throws FlooringMasteryPersistenceException;

    void LoadProductsAndTaxes(String productsFileName, String taxesFileName) throws FlooringMasteryPersistenceException;

    void LoadProductsFile(String fileName) throws FlooringMasteryPersistenceException;

    void WriteProductsFile(String fileName) throws FlooringMasteryPersistenceException;

    void LoadTaxesFile(String fileName) throws FlooringMasteryPersistenceException;

    void WriteTaxesFile(String fileName) throws FlooringMasteryPersistenceException;

}
