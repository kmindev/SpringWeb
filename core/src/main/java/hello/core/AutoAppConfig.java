package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration

@ComponentScan(
        basePackages = "hello.core.member", // 해당 패키지를 포함한 하위패키지까지 @Component 만 찾아서 등록, default: 해당 클래스의 패키지에서 탐색(hello.core)
        basePackageClasses = AutoAppConfig.class, // 해당 클래스를 탐색 위치로
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class) // Configuration 애노테이션이 붙은 클래스는 제외
)
public class AutoAppConfig {

}
