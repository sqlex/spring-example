package me.danwi.sqlex.spring;

import me.danwi.sqlex.core.annotation.SqlExRepository;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

public class ClassPathSqlexDaoScanner extends ClassPathBeanDefinitionScanner {

    private final Class<? extends SqlexDaoFactoryBean> daoFactoryBeanClass = SqlexDaoFactoryBean.class;

    private SpringDaoFactory springDaoFactory;

    public ClassPathSqlexDaoScanner(BeanDefinitionRegistry registry, SpringDaoFactory springDaoFactory) {
        super(registry, false);
        this.springDaoFactory = springDaoFactory;
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        this.resetFilters(false);
        this.addIncludeFilter(new AnnotationTypeFilter(SqlExRepository.class));

        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);

        processBeanDefinitions(beanDefinitionHolders);

        return beanDefinitionHolders;
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        AbstractBeanDefinition definition;
        BeanDefinitionRegistry registry = getRegistry();

        for (BeanDefinitionHolder holder : beanDefinitions) {
            definition = (AbstractBeanDefinition) holder.getBeanDefinition();
            String beanClassName = definition.getBeanClassName();

            definition.getPropertyValues().add("daoFactory", this.springDaoFactory);
            try {
                // 设置 daoInterface 类型
                definition.getPropertyValues().add("daoInterface", Class.forName(beanClassName));
            } catch (ClassNotFoundException ignore) {
                // ignore
            }

            definition.setBeanClass(this.daoFactoryBeanClass);

            // Attribute for MockitoPostProcessor 兼容 @MockBean
            definition.setAttribute(FactoryBean.OBJECT_TYPE_ATTRIBUTE, beanClassName);

            definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

            definition.setLazyInit(false);

            // 多例处理
            if (!definition.isSingleton()) {
                BeanDefinitionHolder proxyHolder = ScopedProxyUtils.createScopedProxy(holder, registry, true);
                if (registry.containsBeanDefinition(proxyHolder.getBeanName())) {
                    registry.removeBeanDefinition(proxyHolder.getBeanName());
                }
                registry.registerBeanDefinition(proxyHolder.getBeanName(), proxyHolder.getBeanDefinition());
            }

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) {
        if (super.checkCandidate(beanName, beanDefinition)) {
            return true;
        } else {
            return false;
        }
    }
}
