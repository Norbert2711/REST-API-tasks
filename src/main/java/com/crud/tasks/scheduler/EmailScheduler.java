//package com.crud.tasks.scheduler;
//
//import com.crud.tasks.config.AdminConfig;
//import com.crud.tasks.domain.Mail;
//import com.crud.tasks.repository.TaskRepository;
//import com.crud.tasks.service.MailCreatorMailOnceADayService;
//import com.crud.tasks.service.SimpleEmailService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.MailException;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.mail.javamail.MimeMessagePreparator;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//public class EmailScheduler {
//
//    @Autowired
//    private SimpleEmailService simpleEmailService;
//
//    @Autowired
//    private TaskRepository taskRepository;
//
//    @Autowired
//    private AdminConfig adminConfig;
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    @Autowired
//    private MailCreatorMailOnceADayService mailCreatorMailOnceADayService;
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEmailService.class);
//
//    @Scheduled(fixedDelay = 10000)
//    //@Scheduled(cron = "0 0 10 * * *")
//
//    public void send(final Mail mail) {
//
//        LOGGER.info("Starting email preparation...");
//        try {
//
//            javaMailSender.send(createMimeMessageOnceADay(mail));
//            LOGGER.info("Email has been sent.");
//
//
//        } catch (MailException e) {
//            LOGGER.error("Failed to process email sending: " + e.getMessage(), e);
//
//        }
//    }
//
//    private MimeMessagePreparator createMimeMessageOnceADay(final Mail mail) {
//
//        long size = taskRepository.count();
//
//        String taskQuantity;
//        if (size == 1) {
//            taskQuantity = "task";
//        } else {
//            taskQuantity = "tasks";
//        }
//
//        return messageOnceADay -> {
//            MimeMessageHelper messageHelper = new MimeMessageHelper(messageOnceADay);
//            messageHelper.setTo(mail.getMailTo());
//            messageHelper.setSubject(mail.getSubject());
//            messageHelper.setText(mail.getMessage() + size + taskQuantity);
//            messageHelper.setText(mailCreatorMailOnceADayService.buildTrelloEmailOnceADay(mail.getMessage()), true);
//        };
//    }
//}