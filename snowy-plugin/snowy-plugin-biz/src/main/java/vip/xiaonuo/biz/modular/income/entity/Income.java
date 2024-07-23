package vip.xiaonuo.biz.modular.income.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName income
 */
@TableName(value ="income")
@Data
public class Income implements Serializable {
    /**
     * 主键，自增
     */
    @TableId(value = "Income_id", type = IdType.AUTO)
    private Long incomeId;

    /**
     * 子项目ID
     */
    @TableField(value = "SubProject_id")
    private Long subprojectId;

    /**
     * 每年新增投入道路条数
     */
    @TableField(value = "Annual_add")
    private String annualAdd;

    /**
     * 每年废弃道路条数
     */
    @TableField(value = "Annual_discard")
    private String annualDiscard;

    /**
     * 年底投入使用的道路条数
     */
    @TableField(value = "yearEnd_use")
    private String yearendUse;

    /**
     * 收入（不含税）
     */
    @TableField(value = "Uninclude_total")
    private String unincludeTotal;

    /**
     * 收入（含税）
     */
    @TableField(value = "Include_taxRate6")
    private String includeTaxrate6;

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
        Income other = (Income) that;
        return (this.getIncomeId() == null ? other.getIncomeId() == null : this.getIncomeId().equals(other.getIncomeId()))
            && (this.getSubprojectId() == null ? other.getSubprojectId() == null : this.getSubprojectId().equals(other.getSubprojectId()))
            && (this.getAnnualAdd() == null ? other.getAnnualAdd() == null : this.getAnnualAdd().equals(other.getAnnualAdd()))
            && (this.getAnnualDiscard() == null ? other.getAnnualDiscard() == null : this.getAnnualDiscard().equals(other.getAnnualDiscard()))
            && (this.getYearendUse() == null ? other.getYearendUse() == null : this.getYearendUse().equals(other.getYearendUse()))
            && (this.getUnincludeTotal() == null ? other.getUnincludeTotal() == null : this.getUnincludeTotal().equals(other.getUnincludeTotal()))
            && (this.getIncludeTaxrate6() == null ? other.getIncludeTaxrate6() == null : this.getIncludeTaxrate6().equals(other.getIncludeTaxrate6()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getIncomeId() == null) ? 0 : getIncomeId().hashCode());
        result = prime * result + ((getSubprojectId() == null) ? 0 : getSubprojectId().hashCode());
        result = prime * result + ((getAnnualAdd() == null) ? 0 : getAnnualAdd().hashCode());
        result = prime * result + ((getAnnualDiscard() == null) ? 0 : getAnnualDiscard().hashCode());
        result = prime * result + ((getYearendUse() == null) ? 0 : getYearendUse().hashCode());
        result = prime * result + ((getUnincludeTotal() == null) ? 0 : getUnincludeTotal().hashCode());
        result = prime * result + ((getIncludeTaxrate6() == null) ? 0 : getIncludeTaxrate6().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", incomeId=").append(incomeId);
        sb.append(", subprojectId=").append(subprojectId);
        sb.append(", annualAdd=").append(annualAdd);
        sb.append(", annualDiscard=").append(annualDiscard);
        sb.append(", yearendUse=").append(yearendUse);
        sb.append(", unincludeTotal=").append(unincludeTotal);
        sb.append(", IncludeTaxrate6=").append(includeTaxrate6);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}