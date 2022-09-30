package io.cloudladder.cloudladderserver.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "package")
@Configuration
@Getter
@Setter
public class PackageConfig {
    private String dir;
}
