package hello.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        // ApplicationContext는 스프링 컨테이너, 인터페이스
        // AnnotationConfigApplicationContext 에노테이션 기반 스프링 컨테이너 구현체(이외에도 XML 기반이 될 수도 있음)
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        // 스프링 컨테이너가 생성
    }
}
