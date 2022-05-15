package programmer.lp.resume.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import programmer.lp.resume.base.BaseBean;

public class Skill extends BaseBean {

    private String name;
    private Integer level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @JsonIgnore
    public String getLevelString() {
        String levelString = "了解";
        switch (level) {
            case 1:
                levelString = "熟悉";
                break;
            case 2:
                levelString = "掌握";
                break;
            case 3:
                levelString = "精通";
                break;
        }
        return levelString;
    }

}
