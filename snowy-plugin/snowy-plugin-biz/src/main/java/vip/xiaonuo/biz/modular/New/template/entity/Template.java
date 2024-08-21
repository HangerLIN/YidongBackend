package vip.xiaonuo.biz.modular.New.template.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @TableName Template
 */
@TableName(value ="Template")
@Data
public class Template implements Serializable {
    /**
     * 模版ID
     */
    @TableId(value = "Template_id", type = IdType.AUTO)
    private Long templateId;

    /**
     * 业务ID
     */
    @TableField(value = "Transaction_id")
    private Long transactionId;

    /**
     * 模版名称
     */
    @TableField(value = "Template_name")
    private String templateName;

    /**
     * 模版序号
     */
    @TableField(value = "Template_Serial")
    private Integer templateSerial;

    /**
     * 子模版数量
     */
    @TableField(value = "Subtemplate_num")
    private Integer subtemplateNum;

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
        Template other = (Template) that;
        return (this.getTemplateId() == null ? other.getTemplateId() == null : this.getTemplateId().equals(other.getTemplateId()))
                && (this.getTransactionId() == null ? other.getTransactionId() == null : this.getTransactionId().equals(other.getTransactionId()))
                && (this.getTemplateName() == null ? other.getTemplateName() == null : this.getTemplateName().equals(other.getTemplateName()))
                && (this.getTemplateSerial() == null ? other.getTemplateSerial() == null : this.getTemplateSerial().equals(other.getTemplateSerial()))
                && (this.getSubtemplateNum() == null ? other.getSubtemplateNum() == null : this.getSubtemplateNum().equals(other.getSubtemplateNum()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTemplateId() == null) ? 0 : getTemplateId().hashCode());
        result = prime * result + ((getTransactionId() == null) ? 0 : getTransactionId().hashCode());
        result = prime * result + ((getTemplateName() == null) ? 0 : getTemplateName().hashCode());
        result = prime * result + ((getTemplateSerial() == null) ? 0 : getTemplateSerial().hashCode());
        result = prime * result + ((getSubtemplateNum() == null) ? 0 : getSubtemplateNum().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", templateId=").append(templateId);
        sb.append(", transactionId=").append(transactionId);
        sb.append(", templateName=").append(templateName);
        sb.append(", templateSerial=").append(templateSerial);
        sb.append(", subtemplateNum=").append(subtemplateNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}