package zst.cloud.config.bootstrap;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 自定义 Bootstrap 配置
 * @author: Zhoust
 * @date: 2018/12/13 下午8:34
 * @version: V1.0
 */
public class CustomBootstrapConfiguration implements ApplicationContextInitializer {


    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment configurableEnvironment = applicationContext.getEnvironment();
        MutablePropertySources propertySources = configurableEnvironment.getPropertySources();
        propertySources.addFirst(propertySource());
    }

    private PropertySource propertySource() {
        Map<String,Object> map = new HashMap<>(16);
        map.put("kkkk","vvvvv");
        PropertySource result = new MapPropertySource("custom",map);
        return result;
    }

}