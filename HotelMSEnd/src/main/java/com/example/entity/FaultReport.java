package com.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.swing.text.StyledEditorKit;
import java.util.Objects;

public class FaultReport {

    private Integer id;
    private String deviceName;
    private String description;
    private Integer logisticsId;
    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm:ss")
    private LocalDateTime reportTime; // 存储报告时间
    private String image; // 存储图片的路径


    // 报备记录ID
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 设备名称
     */
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * 描述
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * 后勤ID
     */
    public Integer getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(Integer logisticsId) {
        this.logisticsId = logisticsId;
    }

    /**
     * 时间
     */
    public LocalDateTime getReportTime() {
        return reportTime;
    }

    public void setReportTime(LocalDateTime reportTime) {
        this.reportTime = reportTime;
    }

    /**
     * 图片路径
     */
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FaultReport faultReport = (FaultReport) o;
        return Objects.equals(id, faultReport.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

