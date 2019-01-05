package com.crsm.maker.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * creat by Ccr on 2018/11/2
 **/
@Slf4j
@Configuration
@PropertySource("classpath:druid.properties")
public class DrudiConfig {


        @Bean(destroyMethod = "close",initMethod = "init")
        @ConfigurationProperties(prefix = "spring.datasource",ignoreInvalidFields = true)
        public DataSource druidDataSource(){
            log.info("使用Druid连接池");
            DruidDataSource dataSource=new DruidDataSource();
            return dataSource;
        }

        /**
         * 注册 statViewServlet
         **/
         @Bean
        public ServletRegistrationBean druidStatViewServlet(){
            ServletRegistrationBean servletRegistrationBean=new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
            //初始化参数initParams
            //白名单
            servletRegistrationBean.addInitParameter("allow","127.0.0.1");
            //开放所有
            servletRegistrationBean.addInitParameter("allow","*");
            // deny IP黑名单 当 allow同时存在时 deny大于allow 满足 deny 提示 Sorry you are not permitted to view page
            servletRegistrationBean.addInitParameter("deny","120.79.160.104");

            servletRegistrationBean.addInitParameter("loginUsername","admin");
            servletRegistrationBean.addInitParameter("loginPassword","admin");
            return servletRegistrationBean;
        }

        /**
         * 过滤拦截规则
         * @return
         */
        @Bean
        public FilterRegistrationBean filterRegistrationBean(){
            FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean(new WebStatFilter());
            filterRegistrationBean.addUrlPatterns("/*");
            filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
            return filterRegistrationBean;
        }
}
