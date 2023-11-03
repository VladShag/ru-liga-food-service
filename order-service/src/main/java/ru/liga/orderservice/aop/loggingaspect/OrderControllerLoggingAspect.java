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
    public void setOrderStatusMethod(){
    }
    @Before("setOrderStatusMethod()")
    public void logCallOfMethod(JoinPoint jp) {
        logger.info("[" + new Date() + "] Метод установки статуса заказа вызывается. В него передаются данные: " +
                Arrays.toString(jp.getArgs()));
    }
    @AfterReturning(pointcut = "setOrderStatusMethod()", returning = "result")
    public void logSuсcessfulEnding(Object result) {
        logger.info("[" + new Date() + "] Метод Метод установки статуса заказа завершился успешно и " +
                "вернул " + result.toString());
    }
    @AfterThrowing(pointcut = "setOrderStatusMethod()", throwing = "exception")
    public void logExceptionEnding(JoinPoint jp, Throwable exception) {
        logger.info("[" + new Date() + "] Метод установки статуса заказа завершился неудачно, вызвав ошибку " +
                 exception.getClass().getName() + " с сообщением " + exception.getMessage() + " и StackTrace " + Arrays.toString(exception.getStackTrace()));
           }
}
