package com.justedlev.account.component.aspect;

import com.justedlev.account.repository.AccountJournalRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AccountJournalAspect {
    private final AccountJournalRepository accountJournalRepository;

    @Pointcut("execution(* com.justedlev.account.repository.AccountRepository.save*(..))")
    public void savePointcut() {
    }

    @Pointcut("execution(* com.justedlev.account.repository.AccountRepository.saveAll*(..))")
    public void saveAllPointcut() {
    }

    @After("savePointcut()")
    public void savePointcutHandler(JoinPoint joinPoint) {

    }

    @After("saveAllPointcut()")
    public void saveAllPointcutHandler(JoinPoint joinPoint) {

    }
}
