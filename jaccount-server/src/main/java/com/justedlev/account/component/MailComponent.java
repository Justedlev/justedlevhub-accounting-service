package com.justedlev.account.component;

import com.justedlev.account.component.command.SendEmailCommand;

public interface MailComponent {
    void sendConfirmActivationMail(SendEmailCommand command);
}
