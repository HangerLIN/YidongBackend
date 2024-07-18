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
package vip.xiaonuo.biz.modular.basicinfo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.basicinfo.entity.ProjectBasicInfo;
import vip.xiaonuo.biz.modular.basicinfo.param.ProjectBasicInfoAddParam;
import vip.xiaonuo.biz.modular.basicinfo.param.ProjectBasicInfoEditParam;
import vip.xiaonuo.biz.modular.basicinfo.param.ProjectBasicInfoIdParam;
import vip.xiaonuo.biz.modular.basicinfo.param.ProjectBasicInfoPageParam;

import java.util.List;

/**
 * 项目基础信息Service接口
 *
 * @author lth
 * @date  2024/07/18 19:32
 **/
public interface ProjectBasicInfoService extends IService<ProjectBasicInfo> {

    /**
     * 获取项目基础信息分页
     *
     * @author lth
     * @date  2024/07/18 19:32
     */
    Page<ProjectBasicInfo> page(ProjectBasicInfoPageParam projectBasicInfoPageParam);

    /**
     * 添加项目基础信息
     *
     * @author lth
     * @date  2024/07/18 19:32
     */
    void add(ProjectBasicInfoAddParam projectBasicInfoAddParam);

    /**
     * 编辑项目基础信息
     *
     * @author lth
     * @date  2024/07/18 19:32
     */
    void edit(ProjectBasicInfoEditParam projectBasicInfoEditParam);

    /**
     * 删除项目基础信息
     *
     * @author lth
     * @date  2024/07/18 19:32
     */
    void delete(List<ProjectBasicInfoIdParam> projectBasicInfoIdParamList);

    /**
     * 获取项目基础信息详情
     *
     * @author lth
     * @date  2024/07/18 19:32
     */
    ProjectBasicInfo detail(ProjectBasicInfoIdParam projectBasicInfoIdParam);

    /**
     * 获取项目基础信息详情
     *
     * @author lth
     * @date  2024/07/18 19:32
     **/
    ProjectBasicInfo queryEntity(String id);

    /**
     * 跳转进入下一步
     * @param currentStep
     * @return
     */
    String nextStep(String currentStep);
}
