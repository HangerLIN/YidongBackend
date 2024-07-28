package vip.xiaonuo.biz.modular.spend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 
 * @TableName benchmark
 */
@TableName(value ="benchmark")
@Data
public class Benchmark implements Serializable {
    /**
     * 主键，自增
     */
    @TableId(value = "Benchmark_id", type = IdType.AUTO)
    private Long benchmarkId;

    /**
     * 产品代码
     */
    @TableField(value = "Product_code")
    private String productCode;

    /**
     * 产品名称
     */
    @TableField(value = "Product_name")
    private String productName;

    /**
     * 单位
     */
    @TableField(value = "Unit")
    private String unit;

    /**
     * 折旧率1
     */
    @TableField(value = "Depreciation_rate1")
    private BigDecimal depreciationRate1;

    /**
     * 折旧率2
     */
    @TableField(value = "Depreciation_rate2")
    private BigDecimal depreciationRate2;

    /**
     * 折旧率3
     */
    @TableField(value = "Depreciation_rate3")
    private BigDecimal depreciationRate3;

    /**
     * 折旧率4
     */
    @TableField(value = "Depreciation_rate4")
    private BigDecimal depreciationRate4;

    /**
     * 其他折旧类成本
     */
    @TableField(value = "Depreciation_other")
    private BigDecimal depreciationOther;

    /**
     * 建设人工成本
     */
    @TableField(value = "Construction_labor")
    private BigDecimal constructionLabor;

    /**
     * 建材费用
     */
    @TableField(value = "Construction_material")
    private BigDecimal constructionMaterial;

    /**
     * 其他建设成本
     */
    @TableField(value = "Construction_other")
    private BigDecimal constructionOther;

    /**
     * 人工维护费用
     */
    @TableField(value = "Maintenance_labor")
    private BigDecimal maintenanceLabor;

    /**
     * 其他维护费用
     */
    @TableField(value = "Maintenance_other")
    private BigDecimal maintenanceOther;

    /**
     * 图纸建设成本
     */
    @TableField(value = "Construction_drawing")
    private BigDecimal constructionDrawing;

    /**
     * 库存物资类成本
     */
    @TableField(value = "Inventory_cost")
    private BigDecimal inventoryCost;

    /**
     * 单位总成本
     */
    @TableField(value = "Unit_total_cost")
    private BigDecimal unitTotalCost;

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
        Benchmark other = (Benchmark) that;
        return (this.getBenchmarkId() == null ? other.getBenchmarkId() == null : this.getBenchmarkId().equals(other.getBenchmarkId()))
            && (this.getProductCode() == null ? other.getProductCode() == null : this.getProductCode().equals(other.getProductCode()))
            && (this.getProductName() == null ? other.getProductName() == null : this.getProductName().equals(other.getProductName()))
            && (this.getUnit() == null ? other.getUnit() == null : this.getUnit().equals(other.getUnit()))
            && (this.getDepreciationRate1() == null ? other.getDepreciationRate1() == null : this.getDepreciationRate1().equals(other.getDepreciationRate1()))
            && (this.getDepreciationRate2() == null ? other.getDepreciationRate2() == null : this.getDepreciationRate2().equals(other.getDepreciationRate2()))
            && (this.getDepreciationRate3() == null ? other.getDepreciationRate3() == null : this.getDepreciationRate3().equals(other.getDepreciationRate3()))
            && (this.getDepreciationRate4() == null ? other.getDepreciationRate4() == null : this.getDepreciationRate4().equals(other.getDepreciationRate4()))
            && (this.getDepreciationOther() == null ? other.getDepreciationOther() == null : this.getDepreciationOther().equals(other.getDepreciationOther()))
            && (this.getConstructionLabor() == null ? other.getConstructionLabor() == null : this.getConstructionLabor().equals(other.getConstructionLabor()))
            && (this.getConstructionMaterial() == null ? other.getConstructionMaterial() == null : this.getConstructionMaterial().equals(other.getConstructionMaterial()))
            && (this.getConstructionOther() == null ? other.getConstructionOther() == null : this.getConstructionOther().equals(other.getConstructionOther()))
            && (this.getMaintenanceLabor() == null ? other.getMaintenanceLabor() == null : this.getMaintenanceLabor().equals(other.getMaintenanceLabor()))
            && (this.getMaintenanceOther() == null ? other.getMaintenanceOther() == null : this.getMaintenanceOther().equals(other.getMaintenanceOther()))
            && (this.getConstructionDrawing() == null ? other.getConstructionDrawing() == null : this.getConstructionDrawing().equals(other.getConstructionDrawing()))
            && (this.getInventoryCost() == null ? other.getInventoryCost() == null : this.getInventoryCost().equals(other.getInventoryCost()))
            && (this.getUnitTotalCost() == null ? other.getUnitTotalCost() == null : this.getUnitTotalCost().equals(other.getUnitTotalCost()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBenchmarkId() == null) ? 0 : getBenchmarkId().hashCode());
        result = prime * result + ((getProductCode() == null) ? 0 : getProductCode().hashCode());
        result = prime * result + ((getProductName() == null) ? 0 : getProductName().hashCode());
        result = prime * result + ((getUnit() == null) ? 0 : getUnit().hashCode());
        result = prime * result + ((getDepreciationRate1() == null) ? 0 : getDepreciationRate1().hashCode());
        result = prime * result + ((getDepreciationRate2() == null) ? 0 : getDepreciationRate2().hashCode());
        result = prime * result + ((getDepreciationRate3() == null) ? 0 : getDepreciationRate3().hashCode());
        result = prime * result + ((getDepreciationRate4() == null) ? 0 : getDepreciationRate4().hashCode());
        result = prime * result + ((getDepreciationOther() == null) ? 0 : getDepreciationOther().hashCode());
        result = prime * result + ((getConstructionLabor() == null) ? 0 : getConstructionLabor().hashCode());
        result = prime * result + ((getConstructionMaterial() == null) ? 0 : getConstructionMaterial().hashCode());
        result = prime * result + ((getConstructionOther() == null) ? 0 : getConstructionOther().hashCode());
        result = prime * result + ((getMaintenanceLabor() == null) ? 0 : getMaintenanceLabor().hashCode());
        result = prime * result + ((getMaintenanceOther() == null) ? 0 : getMaintenanceOther().hashCode());
        result = prime * result + ((getConstructionDrawing() == null) ? 0 : getConstructionDrawing().hashCode());
        result = prime * result + ((getInventoryCost() == null) ? 0 : getInventoryCost().hashCode());
        result = prime * result + ((getUnitTotalCost() == null) ? 0 : getUnitTotalCost().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", benchmarkId=").append(benchmarkId);
        sb.append(", productCode=").append(productCode);
        sb.append(", productName=").append(productName);
        sb.append(", unit=").append(unit);
        sb.append(", depreciationRate1=").append(depreciationRate1);
        sb.append(", depreciationRate2=").append(depreciationRate2);
        sb.append(", depreciationRate3=").append(depreciationRate3);
        sb.append(", depreciationRate4=").append(depreciationRate4);
        sb.append(", depreciationOther=").append(depreciationOther);
        sb.append(", constructionLabor=").append(constructionLabor);
        sb.append(", constructionMaterial=").append(constructionMaterial);
        sb.append(", constructionOther=").append(constructionOther);
        sb.append(", maintenanceLabor=").append(maintenanceLabor);
        sb.append(", maintenanceOther=").append(maintenanceOther);
        sb.append(", constructionDrawing=").append(constructionDrawing);
        sb.append(", inventoryCost=").append(inventoryCost);
        sb.append(", unitTotalCost=").append(unitTotalCost);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}