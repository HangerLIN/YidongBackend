package vip.xiaonuo.biz.modular.spend.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vip.xiaonuo.biz.modular.spend.dto.SpendParam;
import vip.xiaonuo.biz.modular.spend.vo.SpendVO.SubprojectSpendVO;


@Mapper
public interface SpendConvert {
    SpendConvert INSTANCE = Mappers.getMapper(SpendConvert.class);

    @Mapping(target = "spendSafeguard.annual", source = "spendSafeguard")
    @Mapping(target = "spendUpkeep.annual", source = "spendUpkeep")
    @Mapping(target = "spendArtifical.annual", source = "spendArtifical")
    @Mapping(target = "spendOther.annual", source = "spendOther")
    @Mapping(target = "spendNoise.annual", source = "spendNoise")
    @Mapping(target = "spendPublicize.annual", source = "spendPublish")
    SubprojectSpendVO toSubprojectSpendVO(SpendParam.SubprojectSpendInfo subprojectSpendInfo);
}
