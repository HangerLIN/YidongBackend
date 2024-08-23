package vip.xiaonuo.biz.modular.New.subtemplate.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
public class SubtemplateOutput {

    private List<SubmouleOutputDV> submouleDVList;

    @NoArgsConstructor
    @Data
    public static class SubmouleOutputDV {
        private Long subtemplateId;
        private Long projectId;
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
