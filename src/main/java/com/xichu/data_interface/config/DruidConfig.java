package com.xichu.data_interface.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidConfig {
//    @Bean
//    public ServletRegistrationBean<StatViewServlet> druidServlet() {// 主要实现web监控的配置处理
//        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>(
//                new StatViewServlet(), "/druid/*");//表示进行druid监控的配置处理操作
////        servletRegistrationBean.addInitParameter("allow", "127.0.0.1,129.168.1.11");//白名单
////        servletRegistrationBean.addInitParameter("deny", "129.168.1.12");//黑名单
//        servletRegistrationBean.addInitParameter("loginUsername", "xichu");//用户名
//        servletRegistrationBean.addInitParameter("loginPassword", "xichu123");//密码
//        servletRegistrationBean.addInitParameter("resetEnable", "false");//是否可以重置数据源
//        return servletRegistrationBean;
//
//    }
//    @Bean    //监控
//    public FilterRegistrationBean<WebStatFilter> filterRegistrationBean(){
//        FilterRegistrationBean<WebStatFilter> filterRegistrationBean=new FilterRegistrationBean<>();
//        filterRegistrationBean.setFilter(new WebStatFilter());
//        filterRegistrationBean.addUrlPatterns("/*");//所有请求进行监控处理
//        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.css,/druid/*");//排除
//        return filterRegistrationBean;
//    }
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource druidDataSource() {
//        return new DruidDataSource();
//    }
}