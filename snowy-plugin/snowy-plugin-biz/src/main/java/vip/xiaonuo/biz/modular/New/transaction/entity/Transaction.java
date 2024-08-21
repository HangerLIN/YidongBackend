package vip.xiaonuo.biz.modular.New.transaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName Transaction
 */
@TableName(value ="Transaction")
@Data
public class Transaction implements Serializable {
    /**
     * 业务ID
     */
    @TableId(value = "Transaction_id", type = IdType.AUTO)
    private Long transactionId;

    /**
     * 业务名称
     */
    @TableField(value = "Transaction_name")
    private String transactionName;

    /**
     * 业务类型
     */
    @TableField(value = "Transaction_type")
    private Integer transactionType;

    /**
     * 模版数量
     */
    @TableField(value = "Template_num")
    private Integer templateNum;

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
        Transaction other = (Transaction) that;
        return (this.getTransactionId() == null ? other.getTransactionId() == null : this.getTransactionId().equals(other.getTransactionId()))
            && (this.getTransactionName() == null ? other.getTransactionName() == null : this.getTransactionName().equals(other.getTransactionName()))
            && (this.getTransactionType() == null ? other.getTransactionType() == null : this.getTransactionType().equals(other.getTransactionType()))
            && (this.getTemplateNum() == null ? other.getTemplateNum() == null : this.getTemplateNum().equals(other.getTemplateNum()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTransactionId() == null) ? 0 : getTransactionId().hashCode());
        result = prime * result + ((getTransactionName() == null) ? 0 : getTransactionName().hashCode());
        result = prime * result + ((getTransactionType() == null) ? 0 : getTransactionType().hashCode());
        result = prime * result + ((getTemplateNum() == null) ? 0 : getTemplateNum().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", transactionId=").append(transactionId);
        sb.append(", transactionName=").append(transactionName);
        sb.append(", transactionType=").append(transactionType);
        sb.append(", templateNum=").append(templateNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}