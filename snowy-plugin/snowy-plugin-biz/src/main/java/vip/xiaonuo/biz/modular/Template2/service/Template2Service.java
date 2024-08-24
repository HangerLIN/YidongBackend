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
package vip.xiaonuo.biz.modular.Template2.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.Template2.entity.Template2;
import vip.xiaonuo.biz.modular.Template2.param.Template2AddParam;
import vip.xiaonuo.biz.modular.Template2.param.Template2EditParam;
import vip.xiaonuo.biz.modular.Template2.param.Template2IdParam;
import vip.xiaonuo.biz.modular.Template2.param.Template2PageParam;

import java.util.List;

/**
 * Template2Service接口
 *
 * @author gaobaoqu
 * @date  2024/08/24 09:07
 **/
public interface Template2Service extends IService<Template2> {

    /**
     * 获取Template2分页
     *
     * @author gaobaoqu
     * @date  2024/08/24 09:07
     */
    Page<Template2> page(Template2PageParam template2PageParam);

    /**
     * 添加Template2
     *
     * @author gaobaoqu
     * @date  2024/08/24 09:07
     */
    void add(Template2AddParam template2AddParam);

    /**
     * 编辑Template2
     *
     * @author gaobaoqu
     * @date  2024/08/24 09:07
     */
    void edit(Template2EditParam template2EditParam);

    /**
     * 删除Template2
     *
     * @author gaobaoqu
     * @date  2024/08/24 09:07
     */
    void delete(List<Template2IdParam> template2IdParamList);

    /**
     * 获取Template2详情
     *
     * @author gaobaoqu
     * @date  2024/08/24 09:07
     */
    Template2 detail(Template2IdParam template2IdParam);

    /**
     * 获取Template2详情
     *
     * @author gaobaoqu
     * @date  2024/08/24 09:07
     **/
    Template2 queryEntity(String id);
}
