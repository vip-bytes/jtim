package cn.bytes.jtim.connector.configuration;

import cn.bytes.jtim.connector.websocket.WebsocketServerInitialize;
import cn.bytes.jtim.core.channel.module.initialize.InitializeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * @version 1.0
 * @date 2020/3/17 10:01
 */
@Configuration
public class ConnectorConfiguration implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(false).maxAge(3600);
    }

    @Bean
    public InitializeModule websocketServiceInitialize() {
        cn.bytes.jtim.core.channel.config.Configuration configuration = new cn.bytes.jtim.core.channel.config.Configuration();
        InitializeModule initializeModule = new WebsocketServerInitialize(configuration);

        // TODO: 2020/3/17 编码处理

//        initializeModule.then();

//        initializeModule.open();

        return initializeModule;
    }


//    @Bean
//    CorsWebFilter corsFilter() {
//        CorsConfiguration config = new CorsConfiguration();
//        // Possibly...
//        // config.applyPermitDefaultValues()
//
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("https://domain1.com");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//        return new CorsWebFilter(source);
//    }
}
