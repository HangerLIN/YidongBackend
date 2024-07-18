package vip.xiaonuo.biz.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.time.LocalDateTime;

@TableName("project")
public class Project {
    @TableId(value = "Project_id", type = IdType.AUTO)
    private Long projectId;

    @TableField("User_id")
    private Long userId;

    @TableField("Project_name")
    private String projectName;

    @TableField("Project_describe")
    private String projectDescribe;

    @TableField("Project_type")
    private String projectType;

    @TableField("Project_mainclass")
    private String projectMainClass;

    @TableField("Project_subclass")
    private String projectSubClass;

    @TableField("Construct_start")
    private LocalDateTime constructStart;

    @TableField("Construct_end")
    private LocalDateTime constructEnd;

    @TableField("Construct_cycle")
    private int constructCycle;

    @TableField("Evaluate_start")
    private LocalDateTime evaluateStart;

    @TableField("Evaluate_end")
    private LocalDateTime evaluateEnd;

    @TableField("Evaluate_cycle")
    private int evaluateCycle;

    @TableField("Single_ae_cycle")
    private int singleAeCycle;

    @TableField("CreateTime")
    private LocalDateTime createTime;

    @TableField("IsDelete")
    private int isDelete;

    @TableField("Isdraft")
    private int isDraft;

    // Getter 和 Setter 方法
}
