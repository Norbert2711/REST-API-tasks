package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorMailOnceADayService {

    @Autowired
    @Qualifier("templateEngine")
    private ITemplateEngine templateEngine;

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private CompanyConfig companyConfig;

    public String buildTrelloEmailOnceADay(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can check the number of tasks each day.");
        functionality.add("Tasks are created on Trello.");
        functionality.add("Have a nice day.");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("task_url", "https://norbert2711.github.io/");
        context.setVariable("button", "Visit website");
        context.setVariable("show_button", true);
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        context.setVariable("company_name",
                companyConfig.getCompanyName() + "." + "\n" +
                        companyConfig.getCompanyGoal());
        context.setVariable("company_details", "Email: " + companyConfig.getCompanyEmail() + "\n" +
                "Phone: " + companyConfig.getCompanyPhone());
        return templateEngine.process("mail/email-scheduler-once-a-day", context);

    }

}
