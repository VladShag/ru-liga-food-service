package ru.liga.deliveryservice.aop.loggingaspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Date;

@Configuration
@Aspect
public class DeliveryRestControllerLoggingAspect {
    private final Logger logger = LogManager.getLogger(DeliveryRestControllerLoggingAspect.class);

    @Pointcut("execution(* ru.liga.deliveryservice.controller.DeliveryRestController.getDeliveriesByStatus(..))")
    public void getDeliveriesByStatusMethod() {
    }

    @Pointcut("execution(* ru.liga.deliveryservice.controller.DeliveryRestController.acceptDelivery(..))")
    public void acceptDeliveryMethod() {
    }

    @Pointcut("execution(* ru.liga.deliveryservice.controller.DeliveryRestController.completeDelivery(..))")
    public void completeDeliveryMethod() {
    }


    ///Логгирование метода получения списка всех заказов по статусу
    @Before("getDeliveriesByStatusMethod()")
    public void logCallOfGetDeliveriesByStatusMethod(JoinPoint jp) {
        logger.info("[" + new Date() + "] Метод получения списка всех заказов по статусу вызывается. В него передаются данные: " +
                Arrays.toString(jp.getArgs()));
    }

    @AfterReturning(pointcut = "getDeliveriesByStatusMethod()", returning = "result")
    public void logSuсcessfulEndingOfGetDeliveriesByStatus(Object result) {
        logger.info("[" + new Date() + "] Метод получения списка всех заказов по статусу завершился успешно. Метод вернул " +
                result.toString());
    }

    @AfterThrowing(pointcut = "getDeliveriesByStatusMethod()", throwing = "exception")
    public void logExceptionEndingOfGetDeliveriesByStatus(Throwable exception) {
        logger.info("[" + new Date() + "] Метод получения списка всех заказов по статусу завершился неудачно, вызвав ошибку " +
                exception.getClass().getName() + " с сообщением " + exception.getMessage());
    }
    ///Логгирование метода принятия доставки
    @Before("acceptDeliveryMethod()")
    public void logCallOfAcceptDeliveryMethod(JoinPoint jp) {
        logger.info("[" + new Date() + "] Метод принятия доставки вызывается. В него передаются данные: " +
                Arrays.toString(jp.getArgs()));
    }

    @AfterReturning(pointcut = "acceptDeliveryMethod()", returning = "result")
    public void logSuсcessfulEndingOfAcceptDelivery(Object result) {
        logger.info("[" + new Date() + "] Метод принятия доставки завершился успешно. Заказ принят курьером. Статус заказа установлен DELIVERY_PICKING. Метод вернул " +
                result.toString());
    }

    @AfterThrowing(pointcut = "acceptDeliveryMethod()", throwing = "exception")
    public void logExceptionEndingOfAcceptDelivery(Throwable exception) {
        logger.info("[" + new Date() + "] Метод принятия доставки завершился неудачно, вызвав ошибку " +
                exception.getClass().getName() + " с сообщением " + exception.getMessage());
    }
    ///Логгирование метода завершения доставки
    @Before("completeDeliveryMethod()")
    public void logCallOfCompleteDeliveryMethod(JoinPoint jp) {
        logger.info("[" + new Date() + "] Метод завершения доставки вызывается. В него передаются данные: " +
                Arrays.toString(jp.getArgs()));
    }

    @AfterReturning(pointcut = "completeDeliveryMethod()", returning = "result")
    public void logSuсcessfulEndingOfCompleteDelivery(Object result) {
        logger.info("[" + new Date() + "] Метод завершения доставки завершился успешно. Заказ доставлен клиенту. Статус заказа установлен DELIVERY_COMPLETE. Метод вернул " +
                result.toString());
    }

    @AfterThrowing(pointcut = "completeDeliveryMethod()", throwing = "exception")
    public void logExceptionEndingOfCompleteDelivery(Throwable exception) {
        logger.info("[" + new Date() + "] Метод завершения доставки завершился неудачно, вызвав ошибку " +
                exception.getClass().getName() + " с сообщением " + exception.getMessage());
    }
}
