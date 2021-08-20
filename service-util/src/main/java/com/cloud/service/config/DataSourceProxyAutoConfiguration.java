//package com.cloud.service.config;
//
//import com.zaxxer.hikari.HikariDataSource;
//import io.seata.rm.datasource.DataSourceProxy;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//
//
///**
// * @author xjh
// * @version 1.0
// * @ClassName: DataSourceProxyAutoConfiguration
// * @description: 代理数据源
// * @date 2021/8/19 11:10
// */
//@Configuration
//public class DataSourceProxyAutoConfiguration {
//    /**
//     * 数据源属性配置
//     * {@link DataSourceProperties}
//     */
//    private DataSourceProperties dataSourceProperties;
//
//    public DataSourceProxyAutoConfiguration(DataSourceProperties dataSourceProperties) {
//        this.dataSourceProperties = dataSourceProperties;
//    }
//
//    /**
//     * 配置数据源代理，用于事务回滚
//     *
//     * @return The default datasource
//     * @see DataSourceProxy
//     */
//    @Primary
//    @Bean("dataSource")
//    public DataSource dataSource() {
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setJdbcUrl(dataSourceProperties.getUrl());
//        dataSource.setUsername(dataSourceProperties.getUsername());
//        dataSource.setPassword(dataSourceProperties.getPassword());
//        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
//        return new DataSourceProxy(dataSource);
//    }
//}
