package vip.xiaonuo.biz.modular.New.subtemplate.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class SubtemplateParam {


    private List<Submoule> submouleList;

    @NoArgsConstructor
    @Data
    public static class Submoule {
        private Integer templateId;
        private Integer subtemplateSerial;
        private String subtemplateName;
        private Integer templateType;
        private List<BasicInformation> basicInformation;
        private String startYearEq;
        private String endyearEq;

        @NoArgsConstructor
        @Data
        public static class BasicInformation {
            private String key;
            private String value;
        }
    }
}
