package programmer.lp.resume.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import programmer.lp.resume.util.Jsons;

import java.util.Date;

public abstract class BaseBean {

    // TODO 写入博客 commons-beanutils
    static {
        // 配置BeanUtils：String -> Date
        // 前端表单 -> 格式化字符串 -> Date
        DateConverter dateConverter = new DateConverter(null);
        dateConverter.setPatterns(new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"});
        ConvertUtils.register(dateConverter, Date.class);
    }

    private Integer id;
    private Date createdTime;
    private Date lastUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonIgnore
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    //    @JsonIgnore
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @JsonIgnore
    public String getJson() {
        try {
//            return objectMapper.writeValueAsString(this);  // HTML中的onclick使用单引号
            return Jsons.objectMapper().writeValueAsString(this).replace("\"", "'"); // HTML中的onclick使用双引号
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
