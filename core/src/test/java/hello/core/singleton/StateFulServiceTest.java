package hello.core.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;


class StateFulServiceTest {

    @Test
    void statefulServiceSingleton() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StateFulService stateFulService1 = ac.getBean(StateFulService.class);
        StateFulService stateFulService2 = ac.getBean(StateFulService.class);
        
        // ThreadA: "A 사용자 10000원 주문"
        stateFulService1.order("userA", 10000);
        // ThreadB: "B 사용자 20000원 주문"
        stateFulService2.order("userB", 20000);
        
        // ThreadA: 사용자 A 주문 금액 조회
        int price = stateFulService1.getPrice();
        System.out.println("price = " + price);

        assertThat(stateFulService1.getPrice()).isEqualTo(20000);
        
    }

    static class TestConfig {
        @Bean
        public StateFulService stateFulService() {
            return new StateFulService();
        }
    }
}