package services;

import models.Inscription;


public interface MailSender {

	String send(Inscription inscription, String badge);

}
