package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogTestController {
//    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest() {
        String naame = "Spring";
        log.trace("trace log = {}", naame);
        log.debug("debug log = {}", naame);
        log.info("info log = {}", naame);
        log.warn("warn log = {}", naame);
        log.error("error log = {}", naame);

        return "ok";
    }
}
