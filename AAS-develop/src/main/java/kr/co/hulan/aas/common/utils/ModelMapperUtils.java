package kr.co.hulan.aas.common.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class ModelMapperUtils<K, T> {

    public T convertToModel(K source , Class<T> target){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(source, target);
    }

    public T convertToModel(K source , Class<T> target, PropertyMap<Class<K>, Class<T>> propertyMap){
        ModelMapper modelMapper = new ModelMapper();
        if( propertyMap != null ){
            modelMapper.addMappings(propertyMap);
        }
        return modelMapper.map(source, target);
    }
}
