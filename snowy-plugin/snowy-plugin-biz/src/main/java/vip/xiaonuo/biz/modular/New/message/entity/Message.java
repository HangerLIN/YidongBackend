package vip.xiaonuo.biz.modular.New.message.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName Message
 */
@TableName(value ="Message")
@Data
public class Message implements Serializable {
    /**
     * 信息ID
     */
    @TableId(value = "Inform_id", type = IdType.AUTO)
    private Long informId;

    /**
     * 子模版ID
     */
    @TableField(value = "Subtemplate_id")
    private Long subtemplateId;

    /**
     * Message
     */
    @TableField(value = "specific information")
    private String specificInformation;

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
        Message other = (Message) that;
        return (this.getInformId() == null ? other.getInformId() == null : this.getInformId().equals(other.getInformId()))
            && (this.getSubtemplateId() == null ? other.getSubtemplateId() == null : this.getSubtemplateId().equals(other.getSubtemplateId()))
            && (this.getSpecificInformation() == null ? other.getSpecificInformation() == null : this.getSpecificInformation().equals(other.getSpecificInformation()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getInformId() == null) ? 0 : getInformId().hashCode());
        result = prime * result + ((getSubtemplateId() == null) ? 0 : getSubtemplateId().hashCode());
        result = prime * result + ((getSpecificInformation() == null) ? 0 : getSpecificInformation().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", informId=").append(informId);
        sb.append(", subtemplateId=").append(subtemplateId);
        sb.append(", specificInformation=").append(specificInformation);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}