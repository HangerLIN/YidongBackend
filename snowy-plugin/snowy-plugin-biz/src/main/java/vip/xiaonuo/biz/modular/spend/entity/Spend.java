package vip.xiaonuo.biz.modular.spend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName spend
 */
@TableName(value ="spend")
@Data
public class Spend implements Serializable {
    /**
     * 主键，自增
     */
    @TableId(value = "Spend_id")
    private Long spendId;

    /**
     * 子项目ID
     */
    @TableField(value = "SubProject_id")
    private Long subprojectId;

    /**
     * 路面后期维护费用
     */
    @TableField(value = "Spend_safeguard")
    private String spendSafeguard;

    /**
     * 路面保养费用
     */
    @TableField(value = "Spend_upkeep")
    private String spendUpkeep;

    /**
     * 人工服务费用
     */
    @TableField(value = "Spend_artificial")
    private String spendArtificial;

    /**
     * 其他费用
     */
    @TableField(value = "Spend_other")
    private String spendOther;

    /**
     * 噪音污染补偿
     */
    @TableField(value = "Spend_noise")
    private String spendNoise;

    /**
     * 宣传推广费用
     */
    @TableField(value = "Spend_publicize")
    private String spendPublicize;

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
        Spend other = (Spend) that;
        return (this.getSpendId() == null ? other.getSpendId() == null : this.getSpendId().equals(other.getSpendId()))
            && (this.getSubprojectId() == null ? other.getSubprojectId() == null : this.getSubprojectId().equals(other.getSubprojectId()))
            && (this.getSpendSafeguard() == null ? other.getSpendSafeguard() == null : this.getSpendSafeguard().equals(other.getSpendSafeguard()))
            && (this.getSpendUpkeep() == null ? other.getSpendUpkeep() == null : this.getSpendUpkeep().equals(other.getSpendUpkeep()))
            && (this.getSpendArtificial() == null ? other.getSpendArtificial() == null : this.getSpendArtificial().equals(other.getSpendArtificial()))
            && (this.getSpendOther() == null ? other.getSpendOther() == null : this.getSpendOther().equals(other.getSpendOther()))
            && (this.getSpendNoise() == null ? other.getSpendNoise() == null : this.getSpendNoise().equals(other.getSpendNoise()))
            && (this.getSpendPublicize() == null ? other.getSpendPublicize() == null : this.getSpendPublicize().equals(other.getSpendPublicize()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSpendId() == null) ? 0 : getSpendId().hashCode());
        result = prime * result + ((getSubprojectId() == null) ? 0 : getSubprojectId().hashCode());
        result = prime * result + ((getSpendSafeguard() == null) ? 0 : getSpendSafeguard().hashCode());
        result = prime * result + ((getSpendUpkeep() == null) ? 0 : getSpendUpkeep().hashCode());
        result = prime * result + ((getSpendArtificial() == null) ? 0 : getSpendArtificial().hashCode());
        result = prime * result + ((getSpendOther() == null) ? 0 : getSpendOther().hashCode());
        result = prime * result + ((getSpendNoise() == null) ? 0 : getSpendNoise().hashCode());
        result = prime * result + ((getSpendPublicize() == null) ? 0 : getSpendPublicize().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", spendId=").append(spendId);
        sb.append(", subprojectId=").append(subprojectId);
        sb.append(", spendSafeguard=").append(spendSafeguard);
        sb.append(", spendUpkeep=").append(spendUpkeep);
        sb.append(", spendArtificial=").append(spendArtificial);
        sb.append(", spendOther=").append(spendOther);
        sb.append(", spendNoise=").append(spendNoise);
        sb.append(", spendPublicize=").append(spendPublicize);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}