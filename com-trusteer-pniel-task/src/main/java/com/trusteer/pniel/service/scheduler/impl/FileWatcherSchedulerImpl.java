/**
 * 
 */
package com.trusteer.pniel.service.scheduler.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.trusteer.pniel.model.FileHolder;
import com.trusteer.pniel.service.events.EventManager;
import com.trusteer.pniel.service.resolver.FileResolver;
import com.trusteer.pniel.service.scheduler.FileWatcherScheduler;

/**
 * @author Pniel Abramovich
 *
 */
@Service
public class FileWatcherSchedulerImpl implements FileWatcherScheduler {

	private static final Logger logger = LoggerFactory.getLogger(FileWatcherSchedulerImpl.class);
	@Autowired
	private FileResolver FileResolver;

	@Autowired
	private EventManager eventManager;

	// scheduled to run in a given rate provided in project.properties.
	@Scheduled(fixedRateString = "${filewatcher.schedulerimpl.run.fixrate}", initialDelayString = "${filewatcher.schedulerimpl.run.initialdelay}")
	@Override
	public void run() {

		List<FileHolder> resolvedFiles = FileResolver.resolve();
		List<FileHolder> changedFiles = FileResolver.persist(resolvedFiles);
		if (!changedFiles.isEmpty()) {
			eventManager.publish(changedFiles.stream().map(file -> file.getUrl()).collect(Collectors.toList()));
		}
		else {
			logger.error("no files changed");
		}

	}
}
