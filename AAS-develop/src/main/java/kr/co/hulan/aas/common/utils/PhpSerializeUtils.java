package kr.co.hulan.aas.common.utils;

import com.github.ooxi.phparser.SerializedPhpParser;
import com.github.ooxi.phparser.SerializedPhpParserException;
import com.marcospassos.phpserializer.Serializer;
import com.marcospassos.phpserializer.SerializerBuilder;

import java.nio.charset.Charset;
import java.util.*;

public class PhpSerializeUtils {

    private static Serializer phpSerializer = new SerializerBuilder()
            .registerBuiltinAdapters()
            .setCharset(Charset.forName("UTF-8"))
            .build();

    public static String serialize(Object obj){
        return phpSerializer.serialize(obj);
    }

    public static Map<Object, Object> deserialize(String serializedString ) throws SerializedPhpParserException {
        SerializedPhpParser serializedPhpParser = new SerializedPhpParser(serializedString);
        return (Map<Object, Object>) serializedPhpParser.parse();
    }


    public static void main(String args[]) throws Exception{

        Serializer phpSerializer = new SerializerBuilder()
                .registerBuiltinAdapters()
                .setCharset(Charset.forName("UTF-8"))
                .build();

        String phpSerializedString = "a:7:{s:5:\"wp_Id\";s:32:\"67d4b93c5853528558a24403ed17bfee\";s:8:\"wwp_work\";a:1:{i:0;s:16:\"건축일 수행\";}s:7:\"wwp_job\";a:1:{i:0;s:7:\"직종1\";}s:8:\"wwp_date\";s:10:\"2019-08-16\";s:8:\"wwp_memo\";a:1:{i:0;s:31:\"16일자 출력일보입니다.\";}s:14:\"worker_mb_name\";a:1:{i:0;s:10:\"근로자1\";}s:10:\"coop_mb_id\";s:10:\"1234567893\";}";
        Map<Object,Object> map = deserialize(phpSerializedString);
        System.out.println(map);

        List<String> wwpJob = Arrays.asList(((LinkedHashMap<Object, Object>) map.get("wwp_job")).values().toArray(new String[0]));
        List<String> workerMbName = Arrays.asList(((LinkedHashMap<Object, Object>) map.get("worker_mb_name")).values().toArray(new String[0]));
        List<String> wwpWork = Arrays.asList(((LinkedHashMap<Object, Object>) map.get("wwp_work")).values().toArray(new String[0]));
        List<String> wwpMemo = Arrays.asList(((LinkedHashMap<Object, Object>) map.get("wwp_memo")).values().toArray(new String[0]));
        HashMap<String,Object> serilaizedMap = new HashMap<String,Object>();
        serilaizedMap.put("wwp_job" , wwpJob);
        serilaizedMap.put("worker_mb_name" , workerMbName);
        serilaizedMap.put("wwp_work" , wwpWork);
        serilaizedMap.put("wwp_memo" , wwpMemo);

        System.out.println(serilaizedMap);

        String wwpData = serialize(serilaizedMap);
        System.out.println(phpSerializedString);
        System.out.println(wwpData);


        Map<Object,Object> map2 = deserialize(wwpData);
        System.out.println(map2);


    }
}
