/**
 * 
 */
package com.trusteer.pniel.service.events.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.trusteer.pniel.service.email.EmailService;
import com.trusteer.pniel.service.events.EventManager;

/**
 * @author Pniel Abramovich
 *
 */
@Service
public class EventManagerImpl implements EventManager {

	@Value("${eventManagerImpl.emails}")
	private List<String> emails;

	@Autowired
	private EmailService emailService;

	@PostConstruct
	public void init() {
		if (emails != null) {
			emails = null;
		}
	}

	@Override
	public void publis(List<String> address) {
		// TODO: publish the event to the event us instead of invoking the actual email service.
		// TODO email service should subscribe to topic of files.hash1.changed
		emailService.sendFilesChanged(address, emails);
	}

}
