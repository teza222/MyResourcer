package com.myresourcer.MyResourcer.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "conditions")
public class Condition {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer conditionId;
    @Column(nullable = false)
    private String conditionName;

    public Condition(Integer conditionId, String conditionName) {
        this.conditionId = conditionId;
        this.conditionName = conditionName;
    }

    public Condition() {
    }

    public Integer getConditionId() {
        return conditionId;
    }

    public void setConditionId(Integer conditionId) {
        this.conditionId = conditionId;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

}
