package me.danwi.sqlex.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

public class SqlexBeanDefinitionRegistry implements BeanDefinitionRegistryPostProcessor {

    private ClassPathBeanDefinitionScanner scanner;
    private String basePackages;
    private SpringDaoFactory springDaoFactory;

    public SqlexBeanDefinitionRegistry(String basePackages, SpringDaoFactory springDaoFactory) {
        this.basePackages = basePackages;
        this.springDaoFactory = springDaoFactory;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        this.scanner = new ClassPathSqlexDaoScanner(registry, this.springDaoFactory);
        this.scanner.scan(this.basePackages);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }
}

