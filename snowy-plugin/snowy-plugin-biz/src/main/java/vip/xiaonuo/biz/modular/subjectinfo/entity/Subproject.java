package vip.xiaonuo.biz.modular.subjectinfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @TableName subproject
 */
@TableName(value ="subproject")
public class Subproject implements Serializable {
    /**
     * 主键，自增
     */
    @TableId(type = IdType.AUTO)
    private Long subprojectId;

    /**
     * 项目ID
     */
    private Long projectId;

    /**
     * 基础信息
     */
    private String basicInformation;

    /**
     * 建设进度
     */
    private String subprojectSchedule;

    /**
     * 单条造价
     */
    private String subprojectSinglecost;

    /**
     * 单条售价
     */
    private String subprojectSingleprice;

    /**
     * 0:未删除 1:删除
     */
    private Integer isdelete;

    /**
     * 收益税率
     */
    @TableField("TaxRate")
    private BigDecimal taxRate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键，自增
     */
    public Long getSubprojectId() {
        return subprojectId;
    }

    /**
     * 主键，自增
     */
    public void setSubprojectId(Long subprojectId) {
        this.subprojectId = subprojectId;
    }

    /**
     * 项目ID
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * 项目ID
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * 基础信息
     */
    public String getBasicInformation() {
        return basicInformation;
    }

    /**
     * 基础信息
     */
    public void setBasicInformation(String basicInformation) {
        this.basicInformation = basicInformation;
    }

    /**
     * 建设进度
     */
    public String getSubprojectSchedule() {
        return subprojectSchedule;
    }

    /**
     * 建设进度
     */
    public void setSubprojectSchedule(String subprojectSchedule) {
        this.subprojectSchedule = subprojectSchedule;
    }

    /**
     * 单条造价
     */
    public String getSubprojectSinglecost() {
        return subprojectSinglecost;
    }

    /**
     * 单条造价
     */
    public void setSubprojectSinglecost(String subprojectSinglecost) {
        this.subprojectSinglecost = subprojectSinglecost;
    }

    /**
     * 单条售价
     */
    public String getSubprojectSingleprice() {
        return subprojectSingleprice;
    }

    /**
     * 单条售价
     */
    public void setSubprojectSingleprice(String subprojectSingleprice) {
        this.subprojectSingleprice = subprojectSingleprice;
    }

    /**
     * 0:未删除 1:删除
     */
    public Integer getIsdelete() {
        return isdelete;
    }

    /**
     * 0:未删除 1:删除
     */
    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    /**
     * 收益税率
     */
    public void setTaxRate(BigDecimal taxRate){
        this.taxRate = taxRate;
    }

    /**
     * 收益税率
     */
    public BigDecimal getTaxRate(){
        return taxRate;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Subproject other = (Subproject) that;
        return (this.getSubprojectId() == null ? other.getSubprojectId() == null : this.getSubprojectId().equals(other.getSubprojectId()))
            && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
            && (this.getBasicInformation() == null ? other.getBasicInformation() == null : this.getBasicInformation().equals(other.getBasicInformation()))
            && (this.getSubprojectSchedule() == null ? other.getSubprojectSchedule() == null : this.getSubprojectSchedule().equals(other.getSubprojectSchedule()))
            && (this.getSubprojectSinglecost() == null ? other.getSubprojectSinglecost() == null : this.getSubprojectSinglecost().equals(other.getSubprojectSinglecost()))
            && (this.getSubprojectSingleprice() == null ? other.getSubprojectSingleprice() == null : this.getSubprojectSingleprice().equals(other.getSubprojectSingleprice()))
            && (this.getIsdelete() == null ? other.getIsdelete() == null : this.getIsdelete().equals(other.getIsdelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSubprojectId() == null) ? 0 : getSubprojectId().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getBasicInformation() == null) ? 0 : getBasicInformation().hashCode());
        result = prime * result + ((getSubprojectSchedule() == null) ? 0 : getSubprojectSchedule().hashCode());
        result = prime * result + ((getSubprojectSinglecost() == null) ? 0 : getSubprojectSinglecost().hashCode());
        result = prime * result + ((getSubprojectSingleprice() == null) ? 0 : getSubprojectSingleprice().hashCode());
        result = prime * result + ((getIsdelete() == null) ? 0 : getIsdelete().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", subprojectId=").append(subprojectId);
        sb.append(", projectId=").append(projectId);
        sb.append(", basicInformation=").append(basicInformation);
        sb.append(", subprojectSchedule=").append(subprojectSchedule);
        sb.append(", subprojectSinglecost=").append(subprojectSinglecost);
        sb.append(", subprojectSingleprice=").append(subprojectSingleprice);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}