package programmer.lp.resume.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

public class Jsons {

    // TODO 写入博客 fasterxml jackson
    /* 配置JSON解析Date格式 */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    static {
        OBJECTMAPPER.setDateFormat(DATE_FORMAT);
    }

    public static ObjectMapper objectMapper() {
        return OBJECTMAPPER;
    }

}
