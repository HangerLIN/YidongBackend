package vip.xiaonuo.biz.modular.income.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class IncomeSaveParam {

    private List<String> incomeIdList;
}
