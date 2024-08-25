package vip.xiaonuo.biz.modular.New.subtemplate.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
public class SubmoduleFragementParam {

    private Long projectId;
    private Long templateId;
    private Long subtemplateId;
    private List<SubtemplateInputParam.SubmouleDV.Data> data;

}
