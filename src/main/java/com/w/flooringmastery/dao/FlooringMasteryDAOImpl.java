/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.w.flooringmastery.dao;

import com.w.flooringmastery.dto.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 *
 * @author William
 */
@Component
public class FlooringMasteryDAOImpl implements FlooringMasteryDAO {

    public static final String DELIMITER = ",";
//    private String OrderFileName = "Orders_" + String.format("%02d%02d%d", LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
    private final Map<Integer, Order> orders = new HashMap<>(); //Order Number, Order
    private String orderFileHeader = "";
    private final Map<String, Tax> taxes = new HashMap<>();//State abrv, Tax
    private String productFileHeader = "";
    private final Map<String, Product> products = new HashMap<>();
    private String taxFileHeader = "";

    @Override
    public Map<Integer, Order> GetAllOrdersFromDate(String dateAsString) throws FlooringMasteryPersistenceException {
        this.LoadOrdersFileNEW("./SampleFileData/Orders/Orders_" + dateAsString.substring(0, 2) + dateAsString.substring(3, 5) + dateAsString.substring(6) + ".txt");
        LocalDate date = LocalDate.parse(dateAsString, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        Map<Integer, Order> map = new HashMap<>();// = orders;
//        List<Integer> keys = new ArrayList<>();
        orders.entrySet().stream().filter(entry -> (entry.getValue().getOrderDate().isEqual(date))).forEachOrdered(entry -> {
            map.put(entry.getKey(), entry.getValue());
        });//            System.out.println("Initial Mappings are: " + entry.getValue().getOrderDate());
//            System.out.println("Initial Mappings are: " + date);
//        Map<Integer, Order> map = orders;
//        orders.entrySet().stream()
//                .filter(entry -> entry.getValue().getOrderDate() == date)
//                .map(entry -> entry.getKey()) //.map(entry -> entry.getValue())
//                .collect(Collectors.toList());

        return map;
//        Map<Integer, LocalDate> map = new HashMap<>();
//        return orders.entrySet().stream()
//                .filter(o -> o.getValue().getOrderDate() == date).collect(Collectors.toSet());

    }

    @Override
    public void AddOrder(Order order) throws FlooringMasteryPersistenceException {
        orders.put(order.getOrderNumber(), order);
        this.WriteOrdersFileNEW("./SampleFileData/Orders/Orders_" + order.getOrderDate().toString().substring(8) + order.getOrderDate().toString().substring(5, 7) + order.getOrderDate().toString().substring(0, 4) + ".txt");
    }

    @Override
    public void RemoveOrder(int orderNumber) {
        orders.remove(orderNumber);
    }

    @Override
    public Order getOrder(int orderNumber) {
        return orders.get(orderNumber);
    }

    @Override
    public int GetNewOrderNumber() {
        // Find the entry with highest key

        // To store the result
        Map.Entry<Integer, Order> entryWithMaxKey = null;

        // Iterate in the map to find the required entry
        for (Map.Entry<Integer, Order> currentEntry : orders.entrySet()) {

            if ( // If this is the first entry,
                    // set the result as this
                    entryWithMaxKey == null
                    // If this entry's key is more than the max key
                    // Set this entry as the max
                    || currentEntry.getKey()
                            .compareTo(entryWithMaxKey.getKey())
                    > 0) {

                entryWithMaxKey = currentEntry;
            }
        }

        // Return the entry with highest key +1
        return entryWithMaxKey.getKey() + 1;
    }

    @Override
    public Product GetProduct(String productName) {
        return products.get(productName);
    }

    @Override
    public Tax GetTax(String state) {
        return taxes.get(state);
    }

    @Override
    public void LoadProductsAndTaxes(String productsFileName, String taxesFileName) throws FlooringMasteryPersistenceException {
        LoadProductsFile(productsFileName);
        LoadTaxesFile(taxesFileName);
    }

    @Override
    public void LoadTaxesFile(String fileName) throws FlooringMasteryPersistenceException {
        try (Scanner scanner = LoadFile(fileName)) {
            String currentLine;
            String[] taxTokens;
            Tax currentTax;
            taxFileHeader = scanner.nextLine();
            while (scanner.hasNextLine()) {
                // get the next line in the file
                currentTax = new Tax();
                currentLine = scanner.nextLine();

                taxTokens = currentLine.split(DELIMITER);

                currentTax.setStateAbbreviation(taxTokens[0]);
                currentTax.setStateName(taxTokens[1]);
                currentTax.setTaxRate(new BigDecimal(taxTokens[2]));

                taxes.put(currentTax.getStateAbbreviation(), currentTax);
            }
        }
    }

    @Override
    public void WriteTaxesFile(String fileName) throws FlooringMasteryPersistenceException {
        // Write out the Tax objects to the vm file.
        // NOTE TO THE APPRENTICES: We could just grab the tax map,
        // get the Collection of Taxes and iterate over them but we've
        // already created a method that gets a List of Taxes so
        // we'll reuse it.
        try (PrintWriter out = WriteFile(fileName)) {
            // Write out the Tax objects to the vm file.
            // NOTE TO THE APPRENTICES: We could just grab the tax map,
            // get the Collection of Taxes and iterate over them but we've
            // already created a method that gets a List of Taxes so
            // we'll reuse it.
            String taxAsText;
            // writing the header first
            out.println(taxFileHeader);
            // force PrintWriter to write line to the file
            out.flush();
            List<Tax> taxList = this.GetAllTaxes();
            for (Tax currentTax : taxList) {
                // turn a Tax into a String
                taxAsText = currentTax.getStateAbbreviation() + DELIMITER;

                // add the rest of the properties in the correct tax:
                taxAsText += currentTax.getStateName() + DELIMITER;
                taxAsText += currentTax.getTaxRate() + DELIMITER;

                // write the Tax object to the file
                out.println(taxAsText);
                // force PrintWriter to write line to the file
                out.flush();
                taxAsText = "";
            }
            // Clean up
        }
    }

    @Override
    public List<Tax> GetAllTaxes() {
        return new ArrayList(taxes.values());
    }

    @Override
    public void LoadProductsFile(String fileName) throws FlooringMasteryPersistenceException {
        try (Scanner scanner = LoadFile(fileName)) {
            String currentLine;
            String[] productTokens;
            Product currentProduct;
            productFileHeader = scanner.nextLine();
            while (scanner.hasNextLine()) {
                // get the next line in the file
                currentProduct = new Product();
                currentLine = scanner.nextLine();

                productTokens = currentLine.split(DELIMITER);

                currentProduct.setProductType(productTokens[0]);
                currentProduct.setCostPerSquareFoot(new BigDecimal(productTokens[1]));
                currentProduct.setLaborCostPerSquareFoot(new BigDecimal(productTokens[2]));

                products.put(currentProduct.getProductType(), currentProduct);
            }
        }
    }

    @Override
    public void WriteProductsFile(String fileName) throws FlooringMasteryPersistenceException {
        // Write out the Product objects to the vm file.
        // NOTE TO THE APPRENTICES: We could just grab the product map,
        // get the Collection of Products and iterate over them but we've
        // already created a method that gets a List of Products so
        // we'll reuse it.
        try (PrintWriter out = WriteFile(fileName)) {
            // Write out the Product objects to the vm file.
            // NOTE TO THE APPRENTICES: We could just grab the product map,
            // get the Collection of Products and iterate over them but we've
            // already created a method that gets a List of Products so
            // we'll reuse it.
            String productAsText;
            // writing the header first
            out.println(productFileHeader);
            // force PrintWriter to write line to the file
            out.flush();
            List<Product> productList = this.GetAllProducts();
            for (Product currentProduct : productList) {
                // turn a Product into a String
                productAsText = currentProduct.getProductType() + DELIMITER;

                // add the rest of the properties in the correct product:
                productAsText += currentProduct.getCostPerSquareFoot() + DELIMITER;
                productAsText += currentProduct.getLaborCostPerSquareFoot() + DELIMITER;

                // write the Product object to the file
                out.println(productAsText);
                // force PrintWriter to write line to the file
                out.flush();
                productAsText = "";
            }
            // Clean up
        }
    }

    @Override
    public List<Product> GetAllProducts() {

        //System.out.println("Initial Mappings are: " + orders);
        return new ArrayList(products.values());
    }

    @Override
    public void LoadOrdersFileNEW(String fileName) throws FlooringMasteryPersistenceException {
        try ( // orderAsText is expecting a line read in from our file.
                Scanner scanner = LoadFile(fileName)) {
            String currentLine;
            String[] orderTokens;
            Order currentOrder;
            orderFileHeader = scanner.nextLine();
            while (scanner.hasNextLine()) {
                // get the next line in the file
                currentOrder = new Order();
                currentLine = scanner.nextLine();

                orderTokens = currentLine.split(DELIMITER);

                currentOrder.setOrderNumber(parseInt(orderTokens[0]));
                currentOrder.setCustomerName(orderTokens[1]);
                currentOrder.setState(orderTokens[2]);
                currentOrder.setTaxRate(new BigDecimal(orderTokens[3]));
                currentOrder.setProductType(orderTokens[4]);
                currentOrder.setArea(new BigDecimal(orderTokens[5]));
                currentOrder.setCostPerSquareFoot(new BigDecimal(orderTokens[6]));
                currentOrder.setLaborCostPerSquareFoot(new BigDecimal(orderTokens[7]));
                currentOrder.setMaterialCost(new BigDecimal(orderTokens[8]));
                currentOrder.setLaborCost(new BigDecimal(orderTokens[9]));
                currentOrder.setTax(new BigDecimal(orderTokens[10]));
                currentOrder.setTotal(new BigDecimal(orderTokens[11]));

//                currentOrder.setOrderDate(LocalDate.parse(fileName, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                int stringIndex = 31;
                currentOrder.setOrderDate(LocalDate.parse(fileName.substring(stringIndex, stringIndex + 2) + "-"
                        + fileName.substring(stringIndex + 2, stringIndex + 4) + "-"
                        + fileName.substring(stringIndex + 4, stringIndex + 8), DateTimeFormatter.ofPattern("dd-MM-yyyy")));

                orders.put(currentOrder.getOrderNumber(), currentOrder);
            }
        }
    }

    @Override
    public void WriteOrdersFileNEW(String fileName) throws FlooringMasteryPersistenceException {
        // NOTE FOR APPRENTICES: We are not handling the IOException - but
        // we are translating it to an application specific exception and 
        // then simple throwing it (i.e. 'reporting' it) to the code that
        // called us.  It is the responsibility of the calling code to 
        // handle any errors that occur.
        try {
            File file = new File(fileName);
            file.createNewFile();

        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("error creating file, IOException");
        }
        // Write out the Order objects to the vm file.
        // NOTE TO THE APPRENTICES: We could just grab the order map,
        // get the Collection of Orders and iterate over them but we've
        // already created a method that gets a List of Orders so
        // we'll reuse it.
        try (PrintWriter out = WriteFile(fileName)) {
            // Write out the Order objects to the vm file.
            // NOTE TO THE APPRENTICES: We could just grab the order map,
            // get the Collection of Orders and iterate over them but we've
            // already created a method that gets a List of Orders so
            // we'll reuse it.
            String orderAsText;
            // writing the header first
            out.println(orderFileHeader);
            // force PrintWriter to write line to the file
            out.flush();
            List<Order> orderList = this.GetAllOrders();
            for (Order currentOrder : orderList) {
                // turn a Order into a String
                orderAsText = currentOrder.getOrderNumber() + DELIMITER;

                // add the rest of the properties in the correct order:
                orderAsText += currentOrder.getCustomerName() + DELIMITER;
                orderAsText += currentOrder.getState() + DELIMITER;
                orderAsText += currentOrder.getTaxRate().toString() + DELIMITER;
                orderAsText += currentOrder.getProductType() + DELIMITER;
                orderAsText += currentOrder.getArea().toString() + DELIMITER;
                orderAsText += currentOrder.getCostPerSquareFoot().toString() + DELIMITER;
                orderAsText += currentOrder.getLaborCostPerSquareFoot().toString() + DELIMITER;
                orderAsText += currentOrder.getMaterialCost().toString() + DELIMITER;
                orderAsText += currentOrder.getLaborCost().toString() + DELIMITER;
                orderAsText += currentOrder.getTax().toString() + DELIMITER;
                orderAsText += currentOrder.getTotal().toString();

                // write the Order object to the file
                out.println(orderAsText);
                // force PrintWriter to write line to the file
                out.flush();
                orderAsText = "";
            }
            // Clean up
        }
    }

    @Override
    public List<Order> GetAllOrders() {

        //System.out.println("Initial Mappings are: " + orders);
        return new ArrayList(orders.values());
    }

    private Scanner LoadFile(String fileName) throws FlooringMasteryPersistenceException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(new BufferedReader(new FileReader(fileName)));
        } catch (FileNotFoundException e) {
//            System.out.println(e.getMessage());
            throw new FlooringMasteryPersistenceException( ////change this if you want to create a new file instead
                    "-_- Could not load data into memory, from: " + fileName, e);
        }
        // currentLine holds the most recent line read from the file
        return scanner;
    }

    private PrintWriter WriteFile(String fileName) throws FlooringMasteryPersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(fileName));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("Could not save data to: " + fileName, e);
        }
        return out;
    }

    {
//    private Order UnmarshallOrder(String orderAsText) throws FlooringMasteryPersistenceException {
//        // orderAsText is expecting a line read in from our file.
//        String[] orderTokens = orderAsText.split(DELIMITER);
//
//        Order orderFromFile = new Order();
//
//        orderFromFile.setOrderNumber(parseInt(orderTokens[0]));
//        orderFromFile.setCustomerName(orderTokens[1]);
//        orderFromFile.setState(orderTokens[2]);
//        orderFromFile.setTaxRate(new BigDecimal(orderTokens[3]));
//        orderFromFile.setProductType(orderTokens[4]);
//        orderFromFile.setArea(new BigDecimal(orderTokens[5]));
//        orderFromFile.setCostPerSquareFoot(new BigDecimal(orderTokens[6]));
//        orderFromFile.setLaborCostPerSquareFoot(new BigDecimal(orderTokens[7]));
//        orderFromFile.setMaterialCost(new BigDecimal(orderTokens[8]));
//        orderFromFile.setLaborCost(new BigDecimal(orderTokens[9]));
//        orderFromFile.setTax(new BigDecimal(orderTokens[10]));
//        orderFromFile.setTotal(new BigDecimal(orderTokens[11]));
//
//        // We have now created a order! Return it!
//        return orderFromFile;
//    }
//
//    private String MarshallOrder(Order aOrder) {
//        String orderAsText = aOrder.getOrderNumber() + DELIMITER;
//
//        // add the rest of the properties in the correct order:
//        orderAsText += aOrder.getCustomerName() + DELIMITER;
//        orderAsText += aOrder.getState() + DELIMITER;
//        orderAsText += aOrder.getTaxRate().toString() + DELIMITER;
//        orderAsText += aOrder.getProductType() + DELIMITER;
//        orderAsText += aOrder.getArea().toString() + DELIMITER;
//        orderAsText += aOrder.getCostPerSquareFoot().toString() + DELIMITER;
//        orderAsText += aOrder.getLaborCostPerSquareFoot().toString() + DELIMITER;
//        orderAsText += aOrder.getMaterialCost().toString() + DELIMITER;
//        orderAsText += aOrder.getLaborCost().toString() + DELIMITER;
//        orderAsText += aOrder.getTax().toString() + DELIMITER;
//        orderAsText += aOrder.getTotal().toString() + DELIMITER;
//        // We have now turned a order to text! Return it!
//        return orderAsText;
//    }
//
//    @Override
//    public void LoadOrderFile(String fileName) throws FlooringMasteryPersistenceException {
//        Scanner scanner;
//
//        try {
//            // Create Scanner for reading the file
//            scanner = new Scanner(
//                    new BufferedReader(
//                            new FileReader(fileName)));
//        } catch (FileNotFoundException e) {
//            throw new FlooringMasteryPersistenceException( ////change this if you want to create a new file instead
//                    "-_- Could not load order data into memory.", e);
//        }
//        // currentLine holds the most recent line read from the file
//        String currentLine;
//        // currentOrder holds the most recent order unmarshalled
//        Order currentOrder;
//
//        orderFileHeader = currentLine = scanner.nextLine();
//        // Go through LIBRARY_FILE line by line, decoding each line into a 
//        // Order object by calling the UnmarshallOrder method.
//        // Process while we have more lines in the file
//        while (scanner.hasNextLine()) {
//            // get the next line in the file
//            currentLine = scanner.nextLine();
//            // unmarshall the line into a Order
//            currentOrder = UnmarshallOrder(currentLine);
//
//            // We are going to use the order id as the map key for our order object.
//            // Put currentOrder into the map using order id as the key
//            orders.put(currentOrder.getOrderNumber(), currentOrder);
//        }
//        // close scanner
//        scanner.close();
//    }
//
//    /**
//     * Writes all Orders in the vending machine out to a
//     * VENDING_MACHINE_FILE.See loadFlooringMastery for file format.
//     *
//     * @param fileName name of the file
//     * @throws FlooringMasteryPersistenceException if an error occurs writing to
//     * the file
//     */
//    @Override
//    public void WriteOrderFile(String fileName) throws FlooringMasteryPersistenceException {
//        // NOTE FOR APPRENTICES: We are not handling the IOException - but
//        // we are translating it to an application specific exception and 
//        // then simple throwing it (i.e. 'reporting' it) to the code that
//        // called us.  It is the responsibility of the calling code to 
//        // handle any errors that occur.
//        PrintWriter out;
//
//        try {
//            out = new PrintWriter(new FileWriter(fileName));
//        } catch (IOException e) {
//            throw new FlooringMasteryPersistenceException(
//                    "Could not save order data.", e);
//        }
//
//        // Write out the Order objects to the vm file.
//        // NOTE TO THE APPRENTICES: We could just grab the order map,
//        // get the Collection of Orders and iterate over them but we've
//        // already created a method that gets a List of Orders so
//        // we'll reuse it.
//        String orderAsText;
//        List<Order> orderList = this.GetAllOrders();
//        for (Order currentOrder : orderList) {
//            // turn a Order into a String
//            orderAsText = MarshallOrder(currentOrder);
//            // write the Order object to the file
//            out.println(orderAsText);
//            // force PrintWriter to write line to the file
//            out.flush();
//        }
//        // Clean up
//        out.close();
//    }
    }//OLD CODE, still usable as backup
}
