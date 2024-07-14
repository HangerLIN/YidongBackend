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
package vip.xiaonuo.sys.modular.user.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 用户授权资源树结果
 *
 * @author xuyuxiang
 * @date 2022/7/27 15:09
 **/
@Getter
@Setter
public class SysUserGrantResourceTreeResult {

    /** 模块id */
    @Schema(description = "模块id")
    private String id;

    /** 模块名称*/
    @Schema(description = "模块名称")
    private String title;

    /** 模块图标 */
    @Schema(description = "模块图标")
    private String icon;

    /** 模块下菜单集合*/
    @Schema(description = "模块下菜单集合")
    private List<SysUserGrantResourceMenuResult> menu;

    /**
     * 授权菜单类
     *
     * @author xuyuxiang
     * @date 2022/8/13 16:54
     */
    @Getter
    @Setter
    public static class SysUserGrantResourceMenuResult {

        /** 菜单id */
        @Schema(description = "菜单id")
        private String id;

        /** 父id */
        @Schema(description = "父id")
        private String parentId;

        /** 父名称 */
        @Schema(description = "菜单名称")
        private String parentName;

        /** 标题 */
        @Schema(description = "菜单标题")
        private String title;

        /** 模块 */
        @Schema(description = "菜单模块")
        private String module;

        /** 菜单下按钮集合 */
        @Schema(description = "菜单下按钮集合")
        private List<SysUserGrantResourceButtonResult> button;

        /**
         * 授权按钮类
         *
         * @author xuyuxiang
         * @date 2022/8/13 16:54
         */
        @Getter
        @Setter
        public static class SysUserGrantResourceButtonResult {

            /** 按钮id */
            @Schema(description = "按钮id")
            private String id;

            /** 标题 */
            @Schema(description = "按钮标题")
            private String title;
        }
    }
}
