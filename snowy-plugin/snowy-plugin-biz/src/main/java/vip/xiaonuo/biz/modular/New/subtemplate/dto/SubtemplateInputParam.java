package vip.xiaonuo.biz.modular.New.subtemplate.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
public class SubtemplateInputParam {

    private List<SubmouleDVList> submouleDVList;

    @NoArgsConstructor
    @Data
    public static class SubmouleDVList {
        private Integer subtemplateId;
        private Integer projectId;
        private List<Data> data;

        @NoArgsConstructor
        @lombok.Data
        public static class Data {
            private List<String> basicInformation;
            private BigDecimal startYearData;
            private List<BigDecimal> endYearData;
        }
    }
}
