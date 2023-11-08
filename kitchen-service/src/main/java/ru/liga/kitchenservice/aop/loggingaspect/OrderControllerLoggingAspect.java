package ru.liga.kitchenservice.aop.loggingaspect;

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

    @Pointcut("execution(* ru.liga.kitchenservice.controller.OrderController.acceptOrder(..))")
    public void acceptOrderMethod() {
    }

    @Pointcut("execution(* ru.liga.kitchenservice.controller.OrderController.declineOrder(..))")
    public void declineOrderMethod() {
    }

    @Pointcut("execution(* ru.liga.kitchenservice.controller.OrderController.endOrder(..))")
    public void endOrderMethod() {
    }


    ///Логгирование метода принятия заказа
    @Before("acceptOrderMethod()")
    public void logCallOfAcceptOrderMethod(JoinPoint jp) {
        logger.info("[" + new Date() + "] Метод принятия заказа вызывается. В него передаются данные: " +
                Arrays.toString(jp.getArgs()));
    }

    @AfterReturning(pointcut = "acceptOrderMethod()", returning = "result")
    public void logSuсcessfulEndingOfAcceptOrder(Object result) {
        logger.info("[" + new Date() + "] Метод принятия заказа завершился успешно. Заказ принят в работу. Статус заказа установлен KITCHEN_ACCEPTED. Метод вернул " +
                result.toString());
    }

    @AfterThrowing(pointcut = "acceptOrderMethod()", throwing = "exception")
    public void logExceptionEndingOfAcceptOrder(Throwable exception) {
        logger.info("[" + new Date() + "] Метод принятия заказа завершился неудачно, вызвав ошибку " +
                exception.getClass().getName() + " с сообщением " + exception.getMessage());
    }
    ///Логгирование метода отклонения заказа
    @Before("declineOrderMethod()")
    public void logCallOfDeclineOrderMethod(JoinPoint jp) {
        logger.info("[" + new Date() + "] Метод отклонения заказа вызывается. В него передаются данные: " +
                Arrays.toString(jp.getArgs()));
    }

    @AfterReturning(pointcut = "declineOrderMethod()", returning = "result")
    public void logSuсcessfulEndingOfDeclineOrder(Object result) {
        logger.info("[" + new Date() + "] Метод отклонения заказа завершился успешно. Заказ отклонен рестораном. Статус заказа установлен KITCHEN_DENIED. Метод вернул " +
                result.toString());
    }

    @AfterThrowing(pointcut = "declineOrderMethod()", throwing = "exception")
    public void logExceptionEndingOfDeclineOrder(Throwable exception) {
        logger.info("[" + new Date() + "] Метод отклонения заказа завершился неудачно, вызвав ошибку " +
                exception.getClass().getName() + " с сообщением " + exception.getMessage());
    }
    ///Логгирование метода завершения заказа
    @Before("endOrderMethod()")
    public void logCallOfEndOrderMethod(JoinPoint jp) {
        logger.info("[" + new Date() + "] Метод завершения работы над заказом вызывается. В него передаются данные: " +
                Arrays.toString(jp.getArgs()));
    }

    @AfterReturning(pointcut = "endOrderMethod()", returning = "result")
    public void logSuсcessfulEndingOfEndOrder(Object result) {
        logger.info("[" + new Date() + "] Метод завершения работы над заказом завершился успешно. Заказ приготовлен рестораном. Статус заказа установлен DELIVERY_PENDING. Метод вернул " +
                result.toString());
    }

    @AfterThrowing(pointcut = "endOrderMethod()", throwing = "exception")
    public void logExceptionEndingOfEndOrder(Throwable exception) {
        logger.info("[" + new Date() + "] Метод завершения работы над заказом завершился неудачно, вызвав ошибку " +
                exception.getClass().getName() + " с сообщением " + exception.getMessage());
    }
}

