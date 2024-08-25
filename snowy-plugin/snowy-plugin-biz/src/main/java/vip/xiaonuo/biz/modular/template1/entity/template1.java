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
package vip.xiaonuo.biz.modular.template1.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * template1实体
 *
 * @author gaobaoqu
 * @date  2024/08/24 08:36
 **/
@Getter
@Setter
@TableName("Transaction")
public class template1 {

    /** 业务ID */
    @TableId
    @Schema(description = "业务ID")
    private Long transactionId;

    /** 业务名称 */
    @Schema(description = "业务名称")
    private String transactionName;

    /** 业务类型 */
    @Schema(description = "业务类型")
    private Integer transactionType;

    /** 模版数量 */
    @Schema(description = "模版数量")
    private Integer templateNum;
}