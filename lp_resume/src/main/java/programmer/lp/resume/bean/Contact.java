package programmer.lp.resume.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import programmer.lp.resume.base.BaseBean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Contact extends BaseBean {

    private String name;
    private String email;
    private String comment;
    private String subject;
    private Boolean alreadyRead = false;
    /*
        Java：Boolean  自动  数据库：INT
              false                 0
              true                  1
    */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Boolean getAlreadyRead() {
        return alreadyRead;
    }

    public void setAlreadyRead(Boolean alreadyRead) {
        this.alreadyRead = alreadyRead;
    }

    @Override
    @JsonIgnore
    public String getJson() {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            return objectMapper.writeValueAsString(this).replace("\"", "'"); // HTML中的onclick使用双引号
        } catch (Exception e) {
            return null;
        }
    }

    public static class Search {

        public Date beginDay; // 数据库SQL可以直接使用字符串查找日期
        public Date endDay; // 表单无论是什么类型，都可以直接被解析为字符串类型（当然包括日期类型）
        public String keyWord;
        public Integer pageSize = 10; // 一页有多少条
        public Integer pageIndex = 1; // 第几页
        /*
            0 未读
            1 已读
            2 全部
        */
        public Integer alreadyRead = 2; // 查询全部

        public Date getBeginDay() {
            return beginDay;
        }

        public void setBeginDay(Date beginDay) {
            this.beginDay = beginDay;
        }

        public Date getEndDay() {
            return endDay;
        }

        public void setEndDay(Date endDay) {
            this.endDay = endDay;
        }

        public String getKeyWord() {
            return keyWord;
        }

        public void setKeyWord(String keyWord) {
            this.keyWord = keyWord;
        }

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        public Integer getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(Integer pageIndex) {
            this.pageIndex = pageIndex;
        }

        public Integer getAlreadyRead() {
            return alreadyRead;
        }

        public void setAlreadyRead(Integer alreadyRead) {
            this.alreadyRead = alreadyRead;
        }
    }

}
