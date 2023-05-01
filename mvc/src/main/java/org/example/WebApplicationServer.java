package org.example;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class WebApplicationServer {
    private  static  final Logger log = LoggerFactory.getLogger(WebApplicationServer.class);
    public static void main(String[] args) throws Exception {
        String webappDirLocation = "webapps/"; // 루트 디렉토리
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080); // 포트 설정

        // 실행된 빌드 파일을 webapps/classes 경로를 하위로 지정(Project Structure - output 설정함)
        // tomcat 규약
        tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        log.info("configuring app with basedir: {}", new File("./"+webappDirLocation).getAbsolutePath());

        tomcat.start();
        tomcat.getServer().await();
    }
}