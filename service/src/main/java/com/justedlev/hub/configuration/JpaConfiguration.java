package com.justedlev.hub.configuration;

import com.justedlev.hub.AccountServiceApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Primary
@Configuration
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "requestContextAuditorAware")
@EnableJpaRepositories(
        repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class,
        basePackageClasses = AccountServiceApplication.class
)
public class JpaConfiguration {}
