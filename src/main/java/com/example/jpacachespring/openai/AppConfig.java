package com.example.jpacachespring.openai;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
/*
    @Bean
    public MyRepository myRepository() {
        // Создаем реальный объект, реализующий интерфейс
        MyRepository realObject = (MyRepository) Proxy.newProxyInstance(
                MyRepository.class.getClassLoader(),
                new Class<?>[] { MyRepository.class },
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("Real object method called: " + method.getName());
                        // Логика обработки
                        return null; // Можно вернуть значение по умолчанию
                    }
                }
        );

        // Создаем прокси-объект с использованием рефлексии
        return (MyRepository) Proxy.newProxyInstance(
                MyRepository.class.getClassLoader(),
                new Class<?>[] { MyRepository.class },
                new MyInvocationHandler(realObject)
        );
    }*/
}
