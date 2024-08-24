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
package vip.xiaonuo.biz.modular.SubTemplate001.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.SubTemplate001.entity.Subtemplate001;
import vip.xiaonuo.biz.modular.SubTemplate001.param.Subtemplate001AddParam;
import vip.xiaonuo.biz.modular.SubTemplate001.param.Subtemplate001EditParam;
import vip.xiaonuo.biz.modular.SubTemplate001.param.Subtemplate001IdParam;
import vip.xiaonuo.biz.modular.SubTemplate001.param.Subtemplate001PageParam;

import java.util.List;

/**
 * SubTemplate001Service接口
 *
 * @author gaobaoqu
 * @date  2024/08/24 10:27
 **/
public interface Subtemplate001Service extends IService<Subtemplate001> {

    /**
     * 获取SubTemplate001分页
     *
     * @author gaobaoqu
     * @date  2024/08/24 10:27
     */
    Page<Subtemplate001> page(Subtemplate001PageParam subtemplate001PageParam);

    /**
     * 添加SubTemplate001
     *
     * @author gaobaoqu
     * @date  2024/08/24 10:27
     */
    void add(Subtemplate001AddParam subtemplate001AddParam);

    /**
     * 编辑SubTemplate001
     *
     * @author gaobaoqu
     * @date  2024/08/24 10:27
     */
    void edit(Subtemplate001EditParam subtemplate001EditParam);

    /**
     * 删除SubTemplate001
     *
     * @author gaobaoqu
     * @date  2024/08/24 10:27
     */
    void delete(List<Subtemplate001IdParam> subtemplate001IdParamList);

    /**
     * 获取SubTemplate001详情
     *
     * @author gaobaoqu
     * @date  2024/08/24 10:27
     */
    Subtemplate001 detail(Subtemplate001IdParam subtemplate001IdParam);

    /**
     * 获取SubTemplate001详情
     *
     * @author gaobaoqu
     * @date  2024/08/24 10:27
     **/
    Subtemplate001 queryEntity(String id);
}
