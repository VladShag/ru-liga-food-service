package ru.liga.orderservice.aop.loggingaspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;


import java.util.Arrays;
import java.util.Date;


@Configuration
@Aspect
public class OrderControllerLoggingAspect {
    private final Logger logger = LogManager.getLogger(OrderControllerLoggingAspect.class);

    @Pointcut("execution(* ru.liga.orderservice.controller.OrderRestController.setOrderStatus(..))")
    public void setOrderStatusMethod() {
    }

    @Pointcut("execution(* ru.liga.orderservice.controller.OrderRestController.addNewOrder(..))")
    public void addNewOrderMethod() {
    }

    @Pointcut("execution(* ru.liga.orderservice.controller.OrderRestController.getAllOrders(..))")
    public void getAllOrdersMethod() {
    }
    @Pointcut("execution(* ru.liga.orderservice.controller.OrderRestController.getOrderById(..))")
    public void getOrderByIdMethod() {}



    ///Логгирование метода утановки статуса
    @Before("setOrderStatusMethod()")
    public void logCallOfSetOrderStatusMethod(JoinPoint jp) {
        logger.info("[" + new Date() + "] Метод установки статуса заказа вызывается. В него передаются данные: " +
                Arrays.toString(jp.getArgs()));
    }

    @AfterReturning(pointcut = "setOrderStatusMethod()", returning = "result")
    public void logSuсcessfulEndingOfSetOrderStatus(Object result) {
        logger.info("[" + new Date() + "] Метод Метод установки статуса заказа завершился успешно и " +
                "вернул " + result.toString());
    }

    @AfterThrowing(pointcut = "setOrderStatusMethod()", throwing = "exception")
    public void logExceptionEndingOfSetOrderStatus(Throwable exception) {
        logger.info("[" + new Date() + "] Метод установки статуса заказа завершился неудачно, вызвав ошибку " +
                exception.getClass().getName() + " с сообщением " + exception.getMessage());
    }

    ///Логгирование метода создания нового заказа
    @Before("addNewOrderMethod()")
    public void logCallAddNewOrderMethod(JoinPoint jp) {
        logger.info("[" + new Date() + "] Метод создания нового заказа вызывается. В него передаются данные: " +
                Arrays.toString(jp.getArgs()));
    }

    @AfterReturning(pointcut = "addNewOrderMethod()", returning = "result")
    public void logSuсcessfulEndingOfAddNewOrder(Object result) {
        logger.info("[" + new Date() + "] Метод создания нового заказа завершился успешно и " +
                "вернул " + result.toString());
    }

    @AfterThrowing(pointcut = "addNewOrderMethod()", throwing = "exception")
    public void logExceptionEndingAddNewOrder(Throwable exception) {
        logger.info("[" + new Date() + "] Метод создания нового заказа завершился неудачно, вызвав ошибку " +
                exception.getClass().getName() + " с сообщением " + exception.getMessage());
    }

    ///Логгирование метода получения всех заказов
    @Before("getAllOrdersMethod()")
    public void logCallGetAllOrdersMethod() {
        logger.info("[" + new Date() + "] Метод получения списка всех заказов вызывается.");
    }

    @AfterReturning(pointcut = "getAllOrdersMethod()", returning = "result")
    public void logSuсcessfulEndingOfGetAllOrders(Object result) {
        logger.info("[" + new Date() + "] Метод получения списка всех заказов завершился успешно и " +
                "вернул " + result.toString());
    }

    @AfterThrowing(pointcut = "getAllOrdersMethod()", throwing = "exception")
    public void logExceptionEndingGetAllOrders(Throwable exception) {
        logger.info("[" + new Date() + "] Метод получения списка всех заказов завершился неудачно, вызвав ошибку " +
                exception.getClass().getName() + " с сообщением " + exception.getMessage());
    }

    ///Логгирование метода получения заказа по ID
    @Before("getOrderByIdMethod()")
    public void logCallGetOrdersByIdMethod(JoinPoint jp) {
        logger.info("[" + new Date() + "] Метод получения заказа по ID вызывается. Происходит поиск по id: " + Arrays.toString(jp.getArgs()));
    }

    @AfterReturning(pointcut = "getOrderByIdMethod()", returning = "result")
    public void logSuсcessfulEndingOfGetOrdersById(Object result) {
        logger.info("[" + new Date() + "] Метод получения заказа по ID завершился успешно и " +
                "вернул " + result.toString());
    }

    @AfterThrowing(pointcut = "getOrderByIdMethod()", throwing = "exception")
    public void logExceptionEndingGetOrdersById(Throwable exception) {
        logger.info("[" + new Date() + "] Метод получения заказа по ID завершился неудачно, вызвав ошибку " +
                exception.getClass().getName() + " с сообщением " + exception.getMessage());
    }
}
