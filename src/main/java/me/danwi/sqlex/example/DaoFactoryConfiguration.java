package me.danwi.sqlex.example;

import me.danwi.sqlex.example.dao.Repository;
import me.danwi.sqlex.spring.SpringDaoFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DaoFactoryConfiguration {
    private final DataSource dataSource;

    public DaoFactoryConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public SpringDaoFactory factory() {
        //使用spring配置好的数据源和对应的SqlEx Repository来创建DAO工厂
        SpringDaoFactory factory = new SpringDaoFactory(dataSource, Repository.class);
        //执行迁移任务,并做数据库结构比对
        factory.migrate();
        factory.check();
        return factory;
    }
}
