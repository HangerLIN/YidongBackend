package vip.xiaonuo.biz.modular.New.subtemplate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName SubTemplate
 */
@TableName(value ="SubTemplate")
@Data
public class Subtemplate implements Serializable {
    /**
     * 子模版ID
     */
    @TableId(value = "Subtemplate_id", type = IdType.AUTO)
    private Long subtemplateId;

    /**
     * 模版ID
     */
    @TableField(value = "Template_id")
    private Long templateId;

    /**
     * 子模版序号
     */
    @TableField(value = "Subtemplate_Serial")
    private Integer subtemplateSerial;

    /**
     * 子模版名称
     */
    @TableField(value = "Subtemplate_name")
    private String subtemplateName;

    /**
     * 子模版类型（0:涉及计算，1：不涉及计算）
     */
    @TableField(value = "Template_type")
    private Integer templateType;

    /**
     * 基础信息
     */
    @TableField(value = "Basic_information")
    private String basicInformation;

    /**
     * 开始年份公式
     */
    @TableField(value = "StartYear_eq")
    private String startyearEq;

    /**
     * 其他年份公式
     */
    @TableField(value = "EndYear_eq")
    private String endyearEq;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

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
        Subtemplate other = (Subtemplate) that;
        return (this.getSubtemplateId() == null ? other.getSubtemplateId() == null : this.getSubtemplateId().equals(other.getSubtemplateId()))
            && (this.getTemplateId() == null ? other.getTemplateId() == null : this.getTemplateId().equals(other.getTemplateId()))
            && (this.getSubtemplateSerial() == null ? other.getSubtemplateSerial() == null : this.getSubtemplateSerial().equals(other.getSubtemplateSerial()))
            && (this.getSubtemplateName() == null ? other.getSubtemplateName() == null : this.getSubtemplateName().equals(other.getSubtemplateName()))
            && (this.getTemplateType() == null ? other.getTemplateType() == null : this.getTemplateType().equals(other.getTemplateType()))
            && (this.getBasicInformation() == null ? other.getBasicInformation() == null : this.getBasicInformation().equals(other.getBasicInformation()))
            && (this.getStartyearEq() == null ? other.getStartyearEq() == null : this.getStartyearEq().equals(other.getStartyearEq()))
            && (this.getEndyearEq() == null ? other.getEndyearEq() == null : this.getEndyearEq().equals(other.getEndyearEq()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSubtemplateId() == null) ? 0 : getSubtemplateId().hashCode());
        result = prime * result + ((getTemplateId() == null) ? 0 : getTemplateId().hashCode());
        result = prime * result + ((getSubtemplateSerial() == null) ? 0 : getSubtemplateSerial().hashCode());
        result = prime * result + ((getSubtemplateName() == null) ? 0 : getSubtemplateName().hashCode());
        result = prime * result + ((getTemplateType() == null) ? 0 : getTemplateType().hashCode());
        result = prime * result + ((getBasicInformation() == null) ? 0 : getBasicInformation().hashCode());
        result = prime * result + ((getStartyearEq() == null) ? 0 : getStartyearEq().hashCode());
        result = prime * result + ((getEndyearEq() == null) ? 0 : getEndyearEq().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", subtemplateId=").append(subtemplateId);
        sb.append(", templateId=").append(templateId);
        sb.append(", subtemplateSerial=").append(subtemplateSerial);
        sb.append(", subtemplateName=").append(subtemplateName);
        sb.append(", templateType=").append(templateType);
        sb.append(", basicInformation=").append(basicInformation);
        sb.append(", startyearEq=").append(startyearEq);
        sb.append(", endyearEq=").append(endyearEq);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}