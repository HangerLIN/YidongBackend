package vip.xiaonuo.biz.modular.New.transaction.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class transactionParam {

    private String transactionName;
    private Integer transactionType;
    private Integer templateNum;
}
