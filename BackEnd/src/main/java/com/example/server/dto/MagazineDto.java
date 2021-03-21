package com.example.server.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.example.server.entity.Contribution;
import com.example.server.entity.Magazine;

import org.springframework.stereotype.Component;

@Component
public class MagazineDto {
    private Long id;

    @Size(min = 5, max = 5)
    private String code;

    private Date submitAt;

    private Date publishedAt;

    private List<Contribution> contribution;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getSubmitAt() {
        return submitAt;
    }

    public void setSubmitAt(Date submitAt) {
        this.submitAt = submitAt;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public List<Contribution> getContribution() {
        return contribution;
    }

    public void setContribution(List<Contribution> contribution) {
        this.contribution = contribution;
    }

    public Magazine getMagazineFromDto(){
        Magazine magazine = new Magazine();
        magazine.setSubmitAt(submitAt);
        magazine.setPublishedAt(publishedAt);
        magazine.setContribution(contribution);
        magazine.setCode(code);
        return magazine;
    }
}
