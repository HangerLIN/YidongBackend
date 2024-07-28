package vip.xiaonuo.biz.core.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
public class GlobalVoMap<T> {
    private Map<String, T> voForCompreAss = new HashMap<>();

    public void put(String key, T value) {
        voForCompreAss.put(key, value);
    }

    public T get(String key) {
        return voForCompreAss.get(key);
    }

    public Map<String, T> getMap() {
        return voForCompreAss;
    }
}
