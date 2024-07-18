package com.example.demo_spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

// Spring에 등록해야 되므로, Component 어노테이션 필요. -> SpringConfig에 빈으로 등록해서 쓰는 것이 더 선호.
// AOP 사용하기 위한 @Aspect 적용.
@Aspect
public class TimeTraceAop {

    // SpringConfig에 빈으로 등록한다면, 순환 참조가 되므로 SpringConfig 클래스는 AOP 항목에서 제거.
    @Around("execution(* com.example.demo_spring..*(..)) && !target(com.example.demo_spring.SpringConfig)")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try {
            return joinPoint.proceed();
        }  finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
