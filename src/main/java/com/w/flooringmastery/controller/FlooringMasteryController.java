/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.w.flooringmastery.controller;

import com.w.flooringmastery.UI.FlooringMasteryView;
import com.w.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.w.flooringmastery.dto.Order;
import com.w.flooringmastery.service.FlooringMasteryServiceImpl;
import com.w.flooringmastery.service.InvalidOrderException;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author William
 */
@Component
public class FlooringMasteryController {

    @Autowired
    private FlooringMasteryView view;
    @Autowired
    private FlooringMasteryServiceImpl service;
    private String orderFile = "./SampleFileData/Orders/Orders_26102021.txt";
    private String productsFile = "./SampleFileData/Data/Products.txt";
    private String taxesFile = "./SampleFileData/Data/Taxes.txt";

    public FlooringMasteryController() {
    }// constructor

    public FlooringMasteryController(FlooringMasteryView view, FlooringMasteryServiceImpl service) {
        this.view = view;
        this.service = service;
    }// constructor

    public FlooringMasteryController(FlooringMasteryView view, FlooringMasteryServiceImpl service, String orderfile, String productsfile, String taxesfile) {
        this.view = view;
        this.service = service;
        this.orderFile = orderfile;
        this.productsFile = productsfile;
        this.taxesFile = taxesfile;
    }// constructor

    public void run() {
        try {
            service.getLoadOPTFiles(orderFile, productsFile, taxesFile);
        } catch (FlooringMasteryPersistenceException FMPE) {
            view.DisplayErrorMessage(FMPE.getMessage());
        }

        boolean keepGoing = true;
        int menuSelection;

//  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
//   * <<Flooring Program>>
//   * 1. Display Orders
//   * 2. Add an Order
//   * 3. Edit an Order
//   * 4. Remove an Order
//   * 5. Export All Data
//   * 6. Quit
//   *
//   * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
        while (keepGoing) {
            menuSelection = view.PrintMenuAndGetSelection();
            switch (menuSelection) {
                case 1 -> //Display Orders
                    DisplayOrders();
                case 2 -> //Add an Order
                    AddOrder();
                case 3 ->
                    EditOrder();
                case 4 ->
                    RemoveOrder();
                case 5 ->
                    ExportAllData();
                case 6 ->
                    keepGoing = false;
                default ->
                    UnknownCommand();
            }
        }

        ExitMessage();

    }

    private void DisplayOrders() {
        String dateAsString = view.AskForDate();
        try {
            service.ValidateDate(dateAsString);
            try {
                view.PrintOrders(service.getAllOrdersDate(dateAsString));
            } catch (InvalidOrderException IOE) {
                view.DisplayErrorMessage(IOE.getMessage());
            } catch (FlooringMasteryPersistenceException e) {
                view.DisplayErrorMessage(e.getMessage());
            }
        } catch (InvalidOrderException IOE) {
            view.DisplayErrorMessage(IOE.getMessage());
        }

//        view.PrintOrders(service.getAllOrdersDate());
    }

    private void AddOrder() {
//        To add an order will query the user for each piece of order data necessary:
//
//    Order Date – Must be in the future
//    Customer Name – May not be blank, allowed to contain [a-z][0-9] as well as periods and comma characters. "Acme, Inc." is a valid name.
//    State – Entered states must be checked against the tax file. If the state does not exist in the tax file we cannot sell there. If the tax file is modified to include the state, it should be allowed without changing the application code.
//    Product Type – Show a list of available products and pricing information to choose from. Again, if a product is added to the file it should show up in the application without a code change.
//    Area – The area must be a positive decimal. Minimum order size is 100 sq ft.
        Order order = new Order();
        boolean isValid = true;
        do {
            try {
                order.setOrderDate(service.ValidateDate(view.AskForDate()));
                service.IsFutureDate(order.getOrderDate());
                order.setCustomerName(service.ValidateCustomerName(view.GetCustomerName()));
                order.setState(view.GetState());
                order.setProductType(view.GetProductType());
                order.setArea(new BigDecimal(view.GetArea()));
                service.ValidateOrder(order);
                service.AddOrder(order);
                isValid = true;

            } catch (InvalidOrderException IOE) {
                view.DisplayErrorMessage(IOE.getMessage());
                isValid = false;
            } catch (FlooringMasteryPersistenceException e) {

                view.DisplayErrorMessage(e.getMessage());
            }
        } while (!isValid);
    }

    private void EditOrder() {
        Order order;
        try {
            order = service.getOrder(view.AskForOrderNumber(), service.ValidateDate(view.AskForDate()));
            try {
                order.setCustomerName(view.GetCustomerName());
                order.setState(view.GetState());
                order.setProductType(view.GetProductType());
                order.setArea(new BigDecimal(view.GetArea()));
                service.EditOrder(order);
            } catch (InvalidOrderException | FlooringMasteryPersistenceException IOE) {
                view.DisplayErrorMessage(IOE.getMessage());
            }
        } catch (InvalidOrderException IOE) {
            view.DisplayErrorMessage(IOE.getMessage());
        }

    }

    private void RemoveOrder() {
        Order order;
        try {
            order = service.getOrder(view.AskForOrderNumber(), service.ValidateDate(view.AskForDate()));

            service.RemoveOrder(order.getOrderNumber());

        } catch (InvalidOrderException IOE) {
            view.DisplayErrorMessage(IOE.getMessage());
        }
    }

    private void ExportAllData() {
        try {
            service.WriteOrdersToFile(view.AskForFileDate());
        } catch (FlooringMasteryPersistenceException | InvalidOrderException e) {
            view.DisplayErrorMessage(e.getMessage());
        }
        view.ExportDataSuccessBanner();
    }

    private void ExportAllDataDefault() {
        try {
            service.WriteOrdersToFile();
        } catch (FlooringMasteryPersistenceException FMPE) {
            view.DisplayErrorMessage(FMPE.getMessage());
        }
        view.ExportDataSuccessBanner();
    }

    private void UnknownCommand() {
        view.DisplayUnknownCommandBanner();
    }

    private void ExitMessage() {
        view.DisplayExitBanner();
    }

}
