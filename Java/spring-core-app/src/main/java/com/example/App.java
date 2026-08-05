package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        // HelloService obj = context.getBean("helloService", HelloService.class);

        // obj.sayHello();

        // ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // HelloService obj = context.getBean("helloService", HelloService.class);

        // obj.sayHello();
        
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Greeter greeter = context.getBean(Greeter.class);

        greeter.greet();

    }
}
