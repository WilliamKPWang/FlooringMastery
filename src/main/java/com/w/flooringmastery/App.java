/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.w.flooringmastery;

import com.w.flooringmastery.controller.FlooringMasteryController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author William
 */
public class App {

    public static void main(String[] args) {
//                UserIO myIo = new UserIOImpl();
//        FlooringMasteryView view = new FlooringMasteryView(myIo);
//        FlooringMasteryDAO dao = new FlooringMasteryDAOImpl();
//        FlooringMasteryServiceImpl service = new FlooringMasteryServiceImpl(dao);
//        FlooringMasteryController controller = new FlooringMasteryController(view, service, "SampleFileData/Orders/Orders_06022013.txt","SampleFileData/Data/Products.txt","SampleFileData/Data/Taxes.txt");
//
//       
//            controller.run();
//        

//        ApplicationContext appContext
//                = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        FlooringMasteryController controller = appContext.getBean("controller", FlooringMasteryController.class);
//        controller.run();

        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.w.flooringmastery");
        appContext.refresh();

        FlooringMasteryController controller = appContext.getBean("flooringMasteryController", FlooringMasteryController.class);
        controller.run();
    }
}
