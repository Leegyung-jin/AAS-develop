package kr.co.hulan.aas.common.utils;

import java.io.FileNotFoundException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class YamlPropertiesAccessor {
	
	private static Logger logger = LoggerFactory.getLogger(YamlPropertiesAccessor.class);
	
	private static YamlPropertiesAccessor instance;
	
	private static final Object syncObj = new Object();
	
	private Properties properties;
	private long lastModifed;
	
	private long refreshTime = 0;
	private long refreshInterval = 1000 * 60;
	
	private YamlPropertiesAccessor() {
	}
	
	private void initialize() {
		try {
			refreshTime = System.currentTimeMillis();

			properties = new Properties();

			String defaultPropName = "application.yaml";
			Resource defaultRes = new ClassPathResource(defaultPropName);
			if( defaultRes.exists() ) {
				YamlPropertiesProcessor bean = new YamlPropertiesProcessor(defaultRes);
				properties.putAll(bean.createProperties());
				logger.info(defaultPropName+" is reloaded");
			}

			String propName = "application-"+System.getProperty("spring.profiles.active")+".yaml";
			Resource res = new ClassPathResource(propName);
			if( !res.exists() ) {
				throw new FileNotFoundException(propName+" is not exists");
			}

			long currentLastModifed = res.getFile().lastModified();
			if( currentLastModifed != lastModifed ) {
				YamlPropertiesProcessor bean = new YamlPropertiesProcessor(res);
				properties.putAll(bean.createProperties());
				logger.info(propName+" is reloaded");
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public String getString(String code, String defaultMessage){
		try{
			return properties.getProperty(code);
		}
		catch(Exception e){
			logger.error("cann't load message ["+code+"], return defaultMessage ["+defaultMessage+"]", e);
		}
		return defaultMessage;
	}	
	
	public boolean needRefresh() {
		return System.currentTimeMillis() - refreshTime > refreshInterval;
	}

	public static YamlPropertiesAccessor getInstance(){
		synchronized (syncObj) {
			if( instance == null ) {
				instance = new YamlPropertiesAccessor();
				instance.initialize();
			}
			else if ( instance.needRefresh() ) {
				instance.initialize();
			}
		}
		return instance;
	}

	public static String getString(String key) {
		return getMessage(key);
	}

	public static String getMessage(String code){
		return getMessage(code, "");
	}
	
	public static String getMessage(String code, String defaultMessage){
		return YamlPropertiesAccessor.getInstance().getString(code, defaultMessage);
	}
}
