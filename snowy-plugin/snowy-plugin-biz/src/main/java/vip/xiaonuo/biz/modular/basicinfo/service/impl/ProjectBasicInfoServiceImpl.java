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
package vip.xiaonuo.biz.modular.basicinfo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.basicinfo.entity.ProjectBasicInfo;
import vip.xiaonuo.biz.modular.basicinfo.mapper.ProjectBasicInfoMapper;
import vip.xiaonuo.biz.modular.basicinfo.param.ProjectBasicInfoAddParam;
import vip.xiaonuo.biz.modular.basicinfo.param.ProjectBasicInfoEditParam;
import vip.xiaonuo.biz.modular.basicinfo.param.ProjectBasicInfoIdParam;
import vip.xiaonuo.biz.modular.basicinfo.param.ProjectBasicInfoPageParam;
import vip.xiaonuo.biz.modular.basicinfo.service.ProjectBasicInfoService;

import java.util.List;

/**
 * 项目基础信息Service接口实现类
 *
 * @author lth
 * @date  2024/07/19 21:16
 **/
@Service
public class ProjectBasicInfoServiceImpl extends ServiceImpl<ProjectBasicInfoMapper, ProjectBasicInfo> implements ProjectBasicInfoService {

    @Override
    public Page<ProjectBasicInfo> page(ProjectBasicInfoPageParam projectBasicInfoPageParam) {
        QueryWrapper<ProjectBasicInfo> queryWrapper = new QueryWrapper<ProjectBasicInfo>().checkSqlInjection();
        if(ObjectUtil.isNotEmpty(projectBasicInfoPageParam.getName())) {
            queryWrapper.lambda().eq(ProjectBasicInfo::getName, projectBasicInfoPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(projectBasicInfoPageParam.getProjectManager())) {
            queryWrapper.lambda().eq(ProjectBasicInfo::getProjectManager, projectBasicInfoPageParam.getProjectManager());
        }
        if(ObjectUtil.isNotEmpty(projectBasicInfoPageParam.getProjectCode())) {
            queryWrapper.lambda().eq(ProjectBasicInfo::getProjectCode, projectBasicInfoPageParam.getProjectCode());
        }
        if(ObjectUtil.isAllNotEmpty(projectBasicInfoPageParam.getSortField(), projectBasicInfoPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(projectBasicInfoPageParam.getSortOrder());
            queryWrapper.orderBy(true, projectBasicInfoPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(projectBasicInfoPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ProjectBasicInfo::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ProjectBasicInfoAddParam projectBasicInfoAddParam) {
        ProjectBasicInfo projectBasicInfo = BeanUtil.toBean(projectBasicInfoAddParam, ProjectBasicInfo.class);
        this.save(projectBasicInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ProjectBasicInfoEditParam projectBasicInfoEditParam) {
        ProjectBasicInfo projectBasicInfo = this.queryEntity(projectBasicInfoEditParam.getId());
        BeanUtil.copyProperties(projectBasicInfoEditParam, projectBasicInfo);
        this.updateById(projectBasicInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ProjectBasicInfoIdParam> projectBasicInfoIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(projectBasicInfoIdParamList, ProjectBasicInfoIdParam::getId));
    }

    @Override
    public ProjectBasicInfo detail(ProjectBasicInfoIdParam projectBasicInfoIdParam) {
        return this.queryEntity(projectBasicInfoIdParam.getId());
    }

    @Override
    public ProjectBasicInfo queryEntity(Long id) {
        ProjectBasicInfo projectBasicInfo = this.getById(id);
        if(ObjectUtil.isEmpty(projectBasicInfo)) {
            throw new CommonException("项目基础信息不存在，id值为：{}", id);
        }
        return projectBasicInfo;
    }
}
