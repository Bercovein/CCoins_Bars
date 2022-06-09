package com.ccoins.Bars;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BarsApplication.class);
    }
}

// crear Servlet Initializer class en java/com/ccoins/Bars
// en pom.xml <packaging>war</packaging>
// (opcional) remover tomcat embebido dependency agregando el scope provided
// (opcional) remover devtools dependency
// mvn clean package
// ejecutar tomcat en /bin  ./startup.bat or ./startup.sh
// copy .war file in /webapps
// si no arranca automaticamente ejecutar en /bin ./catalina.bat run or ./catalina.sh run
