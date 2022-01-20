package kr.co.hulan.aas.config.database;

import java.util.Properties;
import javax.sql.DataSource;
import kr.co.hulan.aas.config.security.oauth.CustomAuthenticationKeyGenerator;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Configuration
@MapperScan(basePackages = "kr.co.hulan.aas.mvc.dao.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class PersistenceMyBatisConfiguration {

  private final Logger logger = LoggerFactory.getLogger(PersistenceMyBatisConfiguration.class);

  // private static final String AOP_POINTCUT_EXPRESSION = "execution(* kr.co.hulan.aas.mvc..service..*.*(..))";

  @Bean(name = "mybatisDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.mybatis")
  public DataSource mybatisDataSource() {
    return DataSourceBuilder.create().build();
  }


  @Bean
  public TokenStore tokenStore(@Qualifier("mybatisDataSource") DataSource mybatisDataSource) {
    JdbcTokenStore jdbcTokenStore = new JdbcTokenStore(mybatisDataSource);
    jdbcTokenStore.setAuthenticationKeyGenerator(new CustomAuthenticationKeyGenerator());
    return jdbcTokenStore;
  }

  @Bean
  @Primary
  public JdbcClientDetailsService jdbcClientDetailsService(@Qualifier("mybatisDataSource") DataSource mybatisDataSource) {
    return new JdbcClientDetailsService(mybatisDataSource);
  }

  @Bean(name = "sqlSessionFactory")
  public SqlSessionFactory sqlSessionFactory(@Qualifier("mybatisDataSource") DataSource mybatisDataSource, ApplicationContext applicationContext) {
    try {
      SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
      sqlSessionFactoryBean.setDataSource(mybatisDataSource);
      sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml"));
      sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/**/mapper/**/*.xml"));

      return sqlSessionFactoryBean.getObject();
    } catch (Exception e) {
      logger.error("sqlSessionFactory," + e.getMessage(), e);
    }
    return null;
  }

  @Bean(name = "sqlSessionTemplate", destroyMethod = "clearCache")
  public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
    return new SqlSessionTemplate(sqlSessionFactory);
  }

  @Bean(name = "mybatisTransactionManager")
  public PlatformTransactionManager mybatisTransactionManager(@Qualifier("mybatisDataSource") DataSource mybatisDataSource) {
    return new DataSourceTransactionManager(mybatisDataSource);
  }

  /*
  @Bean
  public TransactionInterceptor txAdvice(@Qualifier("mybatisDataSource") DataSource mybatisDataSource) {
    Properties txAttributes = new Properties();
    txAttributes.setProperty("get*", TransactionUtils.readOnlyTransactionAttribute());
    txAttributes.setProperty("duplicate*", TransactionUtils.readOnlyTransactionAttribute());
    txAttributes.setProperty("login*", TransactionUtils.readOnlyTransactionAttribute());
    txAttributes.setProperty("insert*", TransactionUtils.writeTransactionAttribute());
    txAttributes.setProperty("create*", TransactionUtils.writeTransactionAttribute());
    txAttributes.setProperty("update*", TransactionUtils.writeTransactionAttribute());
    txAttributes.setProperty("delete*", TransactionUtils.writeTransactionAttribute());
    txAttributes.setProperty("new*", TransactionUtils.serializableTransactionAttribute());

    TransactionInterceptor txAdvice = new TransactionInterceptor();
    txAdvice.setTransactionAttributes(txAttributes);
    txAdvice.setTransactionManager(mybatisTransactionManager(mybatisDataSource));
    return txAdvice;
  }

  @Bean
  public Advisor txAdviceAdvisor(@Qualifier("mybatisDataSource") DataSource mybatisDataSource) {
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
    return new DefaultPointcutAdvisor(pointcut, txAdvice(mybatisDataSource));
  }
   */

}
