package me.danwi.sqlex.spring;

import org.springframework.beans.factory.FactoryBean;

public class SqlexDaoFactoryBean<T> implements FactoryBean<T> {

    private Class<T> daoInterface;
    private SpringDaoFactory daoFactory;

    public SqlexDaoFactoryBean() {
    }

    @Override
    public T getObject() throws Exception {
        return daoFactory.getInstance(this.daoInterface);
    }

    @Override
    public Class<?> getObjectType() {
        return this.daoInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public Class<T> getDaoInterface() {
        return daoInterface;
    }

    public void setDaoInterface(Class<T> daoInterface) {
        this.daoInterface = daoInterface;
    }

    public SpringDaoFactory getDaoFactory() {
        return daoFactory;
    }

    public void setDaoFactory(SpringDaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
