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
package vip.xiaonuo.biz.modular.SubTemplate001.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * SubTemplate001编辑参数
 *
 * @author gaobaoqu
 * @date  2024/08/24 10:27
 **/
@Getter
@Setter
public class Subtemplate001EditParam {

    /** 子模版ID */
    @Schema(description = "子模版ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "subtemplateId不能为空")
    private Long subtemplateId;

    /** 模版ID */
    @Schema(description = "模版ID")
    private Long templateId;

    /** 子模版序号 */
    @Schema(description = "子模版序号")
    private Integer subtemplateSerial;

    /** 子模版名称 */
    @Schema(description = "子模版名称")
    private String subtemplateName;

    /** 子模版类型（0:涉及计算，1：不涉及计算，2:可涉及计算也可不涉及） */
    @Schema(description = "子模版类型（0:涉及计算，1：不涉及计算，2:可涉及计算也可不涉及）")
    private Integer templateType;

    /** 基础信息 */
    @Schema(description = "基础信息")
    private String basicInformation;

    /** 开始年份公式 */
    @Schema(description = "开始年份公式")
    private String startyearEq;

    /** 其他年份公式 */
    @Schema(description = "其他年份公式")
    private String endyearEq;

}
