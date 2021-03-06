package com.planet.dashboard.config;

import com.planet.dashboard.email.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing
public class WebConfig implements WebMvcConfigurer {

    private final EmailSender mailSender;

    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:messages_error","classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl config = new JavaMailSenderImpl();
        config.setHost(mailSender.getEmailPlatform().getHost());
        config.setPort(mailSender.getEmailPlatform().getPort());
        config.setPassword(mailSender.getPassword());
        config.setUsername(mailSender.getId());
        Properties props = config.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.trust", mailSender.getEmailPlatform().getHost());
        props.put("mail.debug", "true");
        return config;
    }


}
