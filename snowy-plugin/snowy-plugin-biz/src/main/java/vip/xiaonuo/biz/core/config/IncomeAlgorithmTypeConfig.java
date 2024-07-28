package vip.xiaonuo.biz.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "income")
public class IncomeAlgorithmTypeConfig {
    private Map<String,String> types;
}
