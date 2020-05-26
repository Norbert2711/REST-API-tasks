package com.crud.tasks.service;


import com.crud.tasks.domain.Mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

@Service
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailCreatorService mailCreatorService;

    @Autowired
    private MailCreatorMailOnceADayService mailCreatorMailOnceADayService;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public void send(final Mail mail) {

        LOGGER.info("Starting email preparation...");

        try {
            javaMailSender.send(createMimeMessage(mail));
            LOGGER.info("Email has been sent.");

        } catch (MailException e) {
            LOGGER.error("Failed to process email sending: " + e.getMessage(), e);

        }
    }

    private MimeMessagePreparator createMimeMessage(final Mail mail) {

        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()), true);
            messageHelper.setText(mailCreatorMailOnceADayService.buildTrelloEmailOnceADay(mail.getMessage()),true);
        };

    }

    private String choseMailContents(String message, MailCreatorService mailCreatorService,
                                     MailCreatorMailOnceADayService mailCreatorMailOnceADayService) {
//HERE
        if (templateEngine == MailCreatorService.class) {
            return mailCreatorService.buildTrelloCardEmail(message);
        } else if (MailCreatorMailOnceADayService == templateEngine) {
            return mailCreatorMailOnceADayService.buildTrelloEmailOnceADay(message);
        }
        return "";
    }

    private SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());


        if (mail.getToCc() != null) {
            mailMessage.setCc(mail.getToCc());
        }
        return mailMessage;
    }
}

