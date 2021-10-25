/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.w.flooringmastery.service;

import com.w.flooringmastery.dao.FlooringMasteryDAO;
import com.w.flooringmastery.dao.FlooringMasteryDAOImpl;
import com.w.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.w.flooringmastery.dto.Order;
import com.w.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class FlooringMasteryServiceImplTest {

    FlooringMasteryService instance = new FlooringMasteryServiceImpl();
    String testFileName = "./SampleFileData/Orders/Orders_06022022.txt";
    FlooringMasteryDAO dao = new FlooringMasteryDAOImpl();
    Order orderExample;

    public FlooringMasteryServiceImplTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        dao.LoadOrdersFileNEW("./SampleFileData/Orders/Orders_06012013.txt");

        dao.WriteOrdersFileNEW(testFileName);
        dao.LoadOrdersFileNEW(testFileName);

        instance.getLoadOPTFiles("./SampleFileData/Orders/Orders_06012013.txt", "./SampleFileData/Data/Products.txt", "./SampleFileData/Data/Taxes.txt");

        orderExample = new Order(1, "Ada Lovelace", "CA",
                new BigDecimal("25.00"), "Tile", new BigDecimal("249.00"),
                new BigDecimal("3.50"), new BigDecimal("4.15"),
                new BigDecimal("871.50"), new BigDecimal("1033.35"), new BigDecimal("476.21"), new BigDecimal("2381.06"), LocalDate.parse("2022-02-06"));

    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of ValidateDate method, of class FlooringMasteryServiceImpl.
     */
    @Test
    public void testValidateDate() throws Exception {
        System.out.println("ValidateDate");
        String dateAsString = (LocalDate.now().plusDays(1)).toString();
        LocalDate expResult = LocalDate.now().plusDays(1);
        LocalDate result = instance.ValidateDate(dateAsString);

        assertEquals(expResult, result);
    }

    /**
     * Test of ValidateCustomerName method, of class FlooringMasteryServiceImpl.
     */
    @Test
    public void testValidateCustomerName() throws Exception {
        System.out.println("ValidateCustomerName");
        String expResult = "Acme, Inc.";
        String result = instance.ValidateCustomerName(expResult);
        assertEquals(expResult, result);
    }

    /**
     * Test of ValidateCustomerName method, of class FlooringMasteryServiceImpl.
     */
    @Test
    public void testValidateBadCustomerName() throws Exception {
        System.out.println("ValidateBadCustomerName");
        String expResult = "Acme, Inc.!@#$%^";
        try {
            String result = instance.ValidateCustomerName(expResult);
            fail("if this line runs, this test fails");
        } catch (InvalidOrderException IOE) {
//            assert (true);
        }
    }

    /**
     * Test of EditOrder method, of class FlooringMasteryServiceImpl.
     */
    @Test
    public void testEditOrder() throws Exception {

        System.out.println("EditOrder");
        Order order = orderExample;
        instance.EditOrder(order);
        Order result = dao.getOrder(order.getOrderNumber());
//       order.printvalues();
//       result.printvalues();

        assertTrue(order.equals(result));
    }

    /**
     * Test of RemoveOrder method, of class FlooringMasteryServiceImpl.
     */
    @Test
    public void testRemoveOrder() throws Exception {
        System.out.println("RemoveOrder");
        int orderNumber = 1;

        instance.RemoveOrder(orderNumber);
        Order result;
        try {
            result = instance.getOrder(orderNumber);
        } catch (InvalidOrderException IOE) {
            result = null;
        }

        Order expResult = null;
        assertEquals(expResult, result);
    }

    /**
     * Test of ValidateOrder method, of class FlooringMasteryServiceImpl.
     */
    @Test
    public void testValidateOrderBadName() throws Exception {
        System.out.println("ValidateOrderBadName");
        Order order = orderExample;
        order.setCustomerName("!!!!!!");
        try {
            instance.ValidateOrder(order);
        } catch (InvalidOrderException IOE) {
            assert (true);
        }
    }

    @Test
    public void testValidateOrderBadState() throws Exception {
        System.out.println("ValidateOrderBadState");
        Order order = orderExample;
        order.setState("happy");
        try {
            instance.ValidateOrder(order);
        } catch (InvalidOrderException IOE) {
            assert (true);
        }

    }

    @Test
    public void testValidateOrderBadDate() throws Exception {
        System.out.println("ValidateOrderBadDate");
        Order order = orderExample;
        order.setOrderDate(LocalDate.now());
        try {
            instance.ValidateOrder(order);
        } catch (InvalidOrderException IOE) {
            assert (true);
        }

    }

    /**
     * Test of AddOrder method, of class FlooringMasteryServiceImpl.
     */
    @Test
    public void testAddOrder() throws Exception {
        System.out.println("AddOrder");
        Order order = orderExample;
        Order expResult = orderExample;
        instance.AddOrder(order);
        Order result = instance.getOrder(order.getOrderNumber());
        assertEquals(expResult, result);
    }

}
