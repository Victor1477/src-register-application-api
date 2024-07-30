package com.register.application.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(public * com.register.application.core..*(..))")
    public void forAllApplicationPackages() {
    }

    @Before("forAllApplicationPackages()")
    public void log(JoinPoint joinPoint) throws Exception {
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        try {
            String args = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(joinPoint.getArgs());
            if (!args.isEmpty()) {
                System.out.printf("%s - [%s] Calling %s with params: %s%n", dateTime, className, methodName, args);
            } else {
                System.out.printf("%s - [%s] Calling %s", dateTime, className, methodName);
            }
        } catch (Exception e) {
            System.out.printf("%s - [%s] Calling %s Exception: %s", dateTime, className, methodName, e.getLocalizedMessage());
        }
    }
}
