package kr.co.hulan.aas.common.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.core.CollectionFactory;
import org.springframework.core.io.Resource;


/**
*
* yaml 형식의 설정 properties 값을 읽어오기 위한 util 클래스  
* <p>
*
* <pre>
* 개정이력(Modification Information)·
* 수정일   수정자    수정내용
* ------------------------------------
* 2018.02.01 jhhur     최초작성
* </pre>
*
* @author tisquare jhhur (jhhur@tisquare.com)
* @since 2018.02.01
* @version 1.0.0
* @see
*
*/
public class YamlPropertiesProcessor extends YamlProcessor{
	public YamlPropertiesProcessor(Resource resource) throws IOException { 
		if(!resource.exists()){ 
			throw new FileNotFoundException(); 
		} 
		this.setResources(resource); 
		
	} 
	
	public Properties createProperties() throws IOException { 
		final Properties result = CollectionFactory.createStringAdaptingProperties(); 
		process((properties, map) -> result.putAll(properties)); return result; 
	}

	
}