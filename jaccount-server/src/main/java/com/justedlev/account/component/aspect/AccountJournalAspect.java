package com.justedlev.account.component.aspect;

import com.justedlev.account.repository.AccountJournalRepository;
import com.justedlev.account.repository.entity.Account;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.annotation.Async;
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

    @Async
    @After("savePointcut()")
    public void savePointcutHandler(JoinPoint joinPoint) {
        AspectUtils.mapToEntity(joinPoint, Account.class)
                .ifPresent(account -> {

                });
    }

    @Async
    @After("saveAllPointcut()")
    public void saveAllPointcutHandler(JoinPoint joinPoint) {

    }
}
