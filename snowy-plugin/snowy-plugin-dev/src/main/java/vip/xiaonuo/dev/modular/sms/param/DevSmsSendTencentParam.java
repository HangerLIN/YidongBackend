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
package vip.xiaonuo.dev.modular.sms.param;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 短信发送——阿里云参数
 *
 * @author xuyuxiang
 * @date 2022/7/31 15:21
 */
@Getter
@Setter
public class DevSmsSendTencentParam {

    /** 手机号 */
    @Schema(description = "手机号，多个逗号拼接", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "phoneNumbers不能为空")
    private String phoneNumbers;

    /** 模板编码 */
    @Schema(description = "短信服务控制台配置且审核通过的模板编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "templateCode不能为空")
    private String templateCode;

    /** 发送参数 */
    @Schema(description = "短信模板变量对应的顺序。支持传入多个参数，逗号拼接")
    private String templateParam;

    /** sdkAppId */
    @Schema(description = "在短信控制台添加应用后生成的实际SdkAppId")
    private String sdkAppId;

    /** 短信签名 */
    @Schema(description = "短信服务控制台配置且审核通过的短信签名")
    private String signName;
}
