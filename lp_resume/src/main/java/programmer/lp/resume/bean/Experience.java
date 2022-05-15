package programmer.lp.resume.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import programmer.lp.resume.base.BaseBean;

import java.util.Date;

public class Experience extends BaseBean {

    private String job;
    private String intro;
    private Date beginDay;
    private Date endDay;
    private Company company;

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
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

    @JsonIgnore
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Integer getCompanyId() {
        return company.getId();
    }

}
