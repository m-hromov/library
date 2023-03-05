package com.hromov.library.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@Aspect
public class AspectLoggingAdvice {
    @Pointcut("execution(public * com.hromov.library.service.*.*(..))")
    private void allInServiceImpl() {
    }

    @Pointcut("execution(public * com.hromov.library.service.impl.UserServiceImpl.login(..))")
    private void login() {
    }

    @Before("allInServiceImpl() && !login()")
    public void logRequests(JoinPoint joinPoint) {
        log.info(String.format("Request to %s with parameters: %s",
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs())));
    }

    @Before("login()")
    public void logRequestsToLogin(JoinPoint joinPoint) {
        log.info(String.format("Request to %s.",
                joinPoint.getSignature().getName()));
    }

    @AfterThrowing(pointcut = "allInServiceImpl()", throwing = "e")
    public void logErrors(JoinPoint joinPoint, Exception e) {
        log.error(String.format("Error for request to %s with cause: %s",
                joinPoint.getSignature().getName(),
                e.toString()));
    }
}
