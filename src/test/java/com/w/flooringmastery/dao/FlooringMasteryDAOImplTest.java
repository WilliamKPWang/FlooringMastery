/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.w.flooringmastery.dao;

import com.w.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author William
 */
public class FlooringMasteryDAOImplTest {

    FlooringMasteryDAOImpl instance = new FlooringMasteryDAOImpl();
    String testFileName = "./SampleFileData/Orders/Orders_06022022.txt";

    public FlooringMasteryDAOImplTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws Exception {

        FlooringMasteryDAO dao = new FlooringMasteryDAOImpl();
        Map<Integer, Order> orders = new HashMap<>();

//        Order order = new Order(1, "Ada Lovelace", "CA",
//                new BigDecimal("25.00"), "Tile", new BigDecimal("249.00"),
//                new BigDecimal("3.50"), new BigDecimal("4.15"),
//                new BigDecimal("871.50"), new BigDecimal("1033.35"), new BigDecimal("476.21"), new BigDecimal("2381.06"), LocalDate.parse("2022-01-08"));
////        orders.put(order.getOrderNumber(), order);
//        dao.AddOrder(order);
//        order = new Order(2, "Doctor Who", "WA",
//                new BigDecimal("9.25"), "Wood", new BigDecimal("243.00"),
//                new BigDecimal("5.15"), new BigDecimal("4.75"),
//                new BigDecimal("1251.45"), new BigDecimal("1154.25"), new BigDecimal("216.51"), new BigDecimal("2622.21"), LocalDate.parse("2022-06-17"));
////        orders.put(order.getOrderNumber(), order);
//        dao.AddOrder(order);
//        order = new Order(3, "Doctor Who", "WA",
//                new BigDecimal("6.00"), "Carpet", new BigDecimal("217.00"),
//                new BigDecimal("2.25"), new BigDecimal("2.10"),
//                new BigDecimal("488.25"), new BigDecimal("455.70"), new BigDecimal("56.64"), new BigDecimal("1000.59"), LocalDate.parse("2022-07-18"));
////        orders.put(order.getOrderNumber(), order);
        dao.LoadOrdersFileNEW("./SampleFileData/Orders/Orders_06012013.txt");

        dao.WriteOrdersFileNEW(testFileName);
        instance.LoadOrdersFileNEW(testFileName);
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of GetAllOrdersFromDate method, of class FlooringMasteryDAOImpl.
     */
    @Test
    public void testGetAllOrdersFromDate() throws Exception {
        System.out.println("GetAllOrdersFromDate");
        String dateAsString = "06-02-2022";

        instance.LoadOrdersFileNEW("./SampleFileData/Orders/Orders_06022022.txt");
        Map<Integer, Order> expResult = new HashMap<>();
        expResult.put(1, instance.getOrder(1));

        Map<Integer, Order> result = instance.GetAllOrdersFromDate(dateAsString);
        assertEquals(expResult, result);
    }

    /**
     * Test of AddOrder method, of class FlooringMasteryDAOImpl.
     */
    @Test
    public void testAddOrder() throws Exception {
        System.out.println("AddOrder");
        Order order = new Order(2, "Doctor Who", "WA",
                new BigDecimal("9.25"), "Wood", new BigDecimal("243.00"),
                new BigDecimal("5.15"), new BigDecimal("4.75"),
                new BigDecimal("1251.45"), new BigDecimal("1154.25"), new BigDecimal("216.51"), new BigDecimal("2622.21"), LocalDate.parse("2022-06-17"));
        instance.AddOrder(order);
        assertEquals(order, instance.getOrder(order.getOrderNumber()));
    }

    /**
     * Test of RemoveOrder method, of class FlooringMasteryDAOImpl.
     */
    @Test
    public void testRemoveOrder() {
        System.out.println("RemoveOrder");
        int orderNumber = 1;
        instance.RemoveOrder(orderNumber);
        assertEquals(null, instance.getOrder(orderNumber));
    }

    /**
     * Test of getOrder method, of class FlooringMasteryDAOImpl.
     */
    @Test
    public void testGetOrder() throws Exception {
        System.out.println("getOrder");

//        Order expResult = null;
        Order expResult = new Order(1, "Ada Lovelace", "CA",
                new BigDecimal("25.00"), "Tile", new BigDecimal("249.00"),
                new BigDecimal("3.50"), new BigDecimal("4.15"),
                new BigDecimal("871.50"), new BigDecimal("1033.35"), new BigDecimal("476.21"), new BigDecimal("2381.06"), LocalDate.parse("2022-01-08"));
        instance.AddOrder(expResult);

        Order result = instance.getOrder(expResult.getOrderNumber());
        assertEquals(expResult, result);
    }

    /**
     * Test of GetNewOrderNumber method, of class FlooringMasteryDAOImpl.
     */
    @Test
    public void testGetNewOrderNumber() {
        System.out.println("GetNewOrderNumber");
        int expResult = instance.GetAllOrders().size() + 1;
        int result = instance.GetNewOrderNumber();
        assertEquals(expResult, result);
    }

//    /**
//     * Test of GetAllOrders method, of class FlooringMasteryDAOImpl.
//     */
//    @Test
//    public void testGetAllOrders() {
//        System.out.println("GetAllOrders");
//        FlooringMasteryDAOImpl instance = new FlooringMasteryDAOImpl();
//        List<Order> expResult = new ArrayList<>();
//        expResult.add(new Order(1, "Ada Lovelace", "CA",
//                new BigDecimal("25.00"), "Tile", new BigDecimal("249.00"),
//                new BigDecimal("3.50"), new BigDecimal("4.15"),
//                new BigDecimal("871.50"), new BigDecimal("1033.35"), new BigDecimal("476.21"), new BigDecimal("2381.06"), LocalDate.parse("2022-01-08")));
//
//        List<Order> result = instance.GetAllOrders();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
