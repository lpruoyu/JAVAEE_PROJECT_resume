package programmer.lp.resume.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import programmer.lp.resume.base.BaseBean;

import java.util.Date;

public class Education extends BaseBean {

    private String name;
    private String intro;
    private Date beginDay;
    private Date endDay;

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @JsonIgnore
    public String getTypeString() {
        String typeString = "其他";
        switch (type) {
            case 1:
                typeString = "小学";
                break;
            case 2:
                typeString = "初中";
                break;
            case 3:
                typeString = "高中";
                break;
            case 4:
                typeString = "中专";
                break;
            case 5:
                typeString = "大专";
                break;
            case 6:
                typeString = "本科";
                break;
            case 7:
                typeString = "硕士";
                break;
            case 8:
                typeString = "博士";
                break;
        }
        return typeString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

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

}
