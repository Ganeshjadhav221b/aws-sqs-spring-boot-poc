package com.ns.awssqspoc.aspects;


import com.ns.awssqspoc.annotations.CustomAnnotation;
import com.ns.awssqspoc.retryhandler.SqsMessageProducerService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class HandlerAspect {
//
//  @Around("execution(* event..*(..)) ")
//  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
//    final String methodName = joinPoint.getTarget().getClass().getSimpleName().concat(" ")
//        .concat(joinPoint.getSignature().getName());
//    log.info("request : {} : {}", methodName, joinPoint.getArgs());
//    return joinPoint.proceed();
//  }

  @Autowired
  SqsMessageProducerService sqsMessageProducerService;

  //  @AfterThrowing(value="execution(* com.payufin.gringotts.controllers..*(..))", throwing = "ex")
  @AfterThrowing(value = "@annotation(customAnnotation)", throwing = "ex")
  public Object handle(JoinPoint joinPoint, Exception ex, CustomAnnotation customAnnotation)
      throws Throwable {
    Object[] args = joinPoint.getArgs();
    final String methodName = joinPoint.getTarget().getClass().getSimpleName().concat(" ")
        .concat(joinPoint.getSignature().getName());
    log.info("handle exception : {} : {}", methodName, args);
    sqsMessageProducerService.sendMessage((String) args[0]);
//    if(Collections.singleton("list-of-exceptions").contains(ex.getClass().getSimpleName()))){
//      processAgain();
//    }
    return joinPoint;
  }
}
