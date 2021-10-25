/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.w.flooringmastery.UI;

import com.w.flooringmastery.dto.*;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author William
 */
@Component
public class FlooringMasteryView {

    private UserIO io;// = new UserIOImpl();

    @Autowired
    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    public int PrintMenuAndGetSelection() {

        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("* <<Flooring Program>>                                               ");
        io.print("* 1. Display Orders                                                  ");
        io.print("* 2. Add an Order                                                    ");
        io.print("* 3. Edit an Order                                                   ");
        io.print("* 4. Remove an Order                                                 ");
        io.print("* 5. Export All Data                                                 ");
        io.print("* 6. Quit                                                            ");
        io.print("*                                                                    ");
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        return io.readInt("");
    }

    public void PrintOrders(Map<Integer, Order> orders) {
//    Product Type – Show a list of available products and pricing information to choose from. Again, if a product is added to the file it should show up in the application without a code change.

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
        io.print("Orders: ");
        for (Order CO : orders.values()) {
            io.print("Order Number: " + CO.getOrderNumber());
            io.print("Name: " + CO.getCustomerName());
            io.print("Product Type: " + CO.getProductType());
            io.print("Area: " + CO.getArea());
            io.print("Total: " + CO.getTotal());
            io.print("================================================");
//            CO.printvalues();
        }

    }

    public void PrintOrder(Order order) {
//    Product Type – Show a list of available products and pricing information to choose from. Again, if a product is added to the file it should show up in the application without a code change.
        io.print("Order: ");
        io.print("Order Number: " + order.getOrderNumber());
        io.print("Name: " + order.getCustomerName());
        io.print("Product Type: " + order.getProductType());
        io.print("Area: " + order.getArea());
        io.print("Total: " + order.getTotal());
    }

    public String GetCustomerName() {
        return io.readString("What is the customer name?");
    }

    public String GetState() {
        return io.readString("What is the state? (Abreviation)");
    }

    public String GetProductType() {
        return io.readString("What is the product type?");
    }

    public String GetArea() {
        return io.readString("What is the area?");
    }

    public String AskForDate() {
        return io.readString("What is the date of the order? (DD-MM-YYYY)");
    }

    public String AskForFileDate() {
        return io.readString("What is the date of the file to save as (what's today)? (DD-MM-YYYY)");
    }

    public int AskForOrderNumber() {
        return io.readInt("What is the order number?");
    }

    public void PrintProducts(Map<String, Product> products) {
//    Product Type – Show a list of available products and pricing information to choose from. Again, if a product is added to the file it should show up in the application without a code change.
        io.print("Products: ");
        products.values().forEach(currentProduct -> {
            io.print(currentProduct.getProductType() + "   Material Cost: " + currentProduct.getFormattedMaterialCost() + "   Labor Cost: " + currentProduct.getFormattedLaborCost());
        });

    }

    public void ExportDataSuccessBanner() {
        io.print("Order Data Exported Successfully.");
    }

    public void DisplayExitBanner() {
        io.print("----Goodbye!----");
    }

    public void DisplayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }

    public void DisplayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
}
