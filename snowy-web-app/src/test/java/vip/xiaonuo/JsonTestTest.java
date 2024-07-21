package vip.xiaonuo;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import vip.xiaonuo.biz.modular.subjectinfo.dto.SubProjcetParam;
import vip.xiaonuo.biz.modular.subjectinfo.service.SubprojectService;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
class JsonTestTest {
    @Resource
    private SubprojectService subprojectService;

    @Test
    void TestAddSubProject() {

        String JsonMessage = ResourceUtil.readUtf8Str("template2.json");
        SubProjcetParam Meta = JSONUtil.toBean(JsonMessage, SubProjcetParam.class);

        // 字段校验

        // 获取子项目列表
        List<SubProjcetParam.SubprojectInfo> subproject = Meta.getSubproject();

        //遍历所有子项目
        for (SubProjcetParam.SubprojectInfo sp : subproject) {
            //添加数据库
            subprojectService.addSubProject(sp);
        }

        System.out.println(Meta);


    }
}