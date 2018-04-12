//package com.achain.conf;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.transaction.annotation.TransactionManagementConfigurer;
//
///**
// * @author yaoxiaohui
// */
//
//@Configuration
//@EnableTransactionManagement
//public class MyBatisConfig implements TransactionManagementConfigurer {
//
//    @Override
//    public PlatformTransactionManager annotationDrivenTransactionManager() {
//        return null;
//    }
//
////    @Autowired
////    private DataSource dataSource;
////
////    @Override
////    public PlatformTransactionManager annotationDrivenTransactionManager() {
////        return new DataSourceTransactionManager(dataSource);
////    }
////
////    @Bean(name = "sqlSessionFactory")
////    public SqlSessionFactory sqlSessionFactoryBean() {
////        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
////        bean.setDataSource(dataSource);
////        bean.setMapperLocations();
////
////        try {
////            return bean.getObject();
////        } catch (Exception e) {
////            e.printStackTrace();
////            throw new RuntimeException(e);
////        }
////    }
////
////    @Bean
////    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
////        return new SqlSessionTemplate(sqlSessionFactory);
////    }
//
//}
