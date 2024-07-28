/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.biz.modular.basicinfo.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目基础信息实体
 *
 * @author lth
 * @date  2024/07/18 19:32
 **/
@Getter
@Setter
@TableName("project_basic_info")
public class ProjectBasicInfo {

    /** 主键，自增 */
    @TableId
    @Schema(description = "主键，自增")
    private Long id;

    /** 名称 */
    @Schema(description = "名称")
    private String name;

    /** 公司名称 */
    @Schema(description = "公司名称")
    private String companyName;

    /** 下属 */
    @Schema(description = "下属")
    private String subordinate;

    /** 电话 */
    @Schema(description = "电话")
    private String phone;

    /** 项目经理 */
    @Schema(description = "项目经理")
    private String projectManager;

    /** 项目经理电话 */
    @Schema(description = "项目经理电话")
    private String projectManagerPhone;

    /** 项目经理单位 */
    @Schema(description = "项目经理单位")
    private String projectManagerUnit;

    /** 项目类型1 */
    @Schema(description = "项目类型1")
    private String projectType1;

    /** 项目类型2 */
    @Schema(description = "项目类型2")
    private String projectType2;

    /** 项目名称 */
    @Schema(description = "项目名称")
    private String projectName;

    /** 项目编码 */
    @Schema(description = "项目编码")
    private String projectCode;

    /** 评估周期 */
    @Schema(description = "评估周期")
    private Integer evaluationPeriod;

    /** 资源周期 */
    @Schema(description = "资源周期")
    private Integer resourcePeriod;

    /** 建设周期 */
    @Schema(description = "建设周期")
    private String buildPeriod;

    /** 评估时间 */
    @Schema(description = "评估时间")
    private String evaluationTime;

    /** 评估时间 */
    @Schema(description = "评估开始日期")
    private String Evaluate_start;

    /** 评估时间 */
    @Schema(description = "评估结束日期")
    private String Evaluate_end;
}
