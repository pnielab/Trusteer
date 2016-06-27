/**
 * 
 */
package com.trusteer.pniel.service.resolver.impl;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.trusteer.pniel.model.FileHolder;
import com.trusteer.pniel.service.resolver.FileResolver;
import com.trusteer.pniel.utils.EncryptDecriptUtils;

/**
 * @author Pniel Abramovich
 *
 */
@Service
public class FileResolverImpl implements FileResolver {

	private static final Logger logger = LoggerFactory.getLogger(FileResolverImpl.class);

	// holds list of file to be monitored, injected from project.properties file
	@Value("#{${fileResolverImpl.filesToMonitor}}")
	private Map<String, String> filesToMonitor;

	// holds the persisted files that need to be monitored, can be distributed DB,
	// file system,distributed map, distributed cache like Hazelcast and more.
	private Map<String, FileHolder> persistedFiles = new ConcurrentHashMap<String, FileHolder>();

	@Autowired
	private RestTemplate restTemplate;

	private String resolveIp(String host) {
		InetAddress ia;
		try {
			ia = InetAddress.getByName(host);
			return ia.getHostAddress();
		}
		catch (UnknownHostException e) {
			logger.error("error DNS resolving ip address for the given host: {}", host, e);
		}
		return null;
	}

	private String getBody(String address, HttpMethod method) {
		try {
			ResponseEntity<String> result = restTemplate.exchange(address, method, null, String.class);
			return result.getBody();
		}
		catch (Exception e) {
			logger.error("unable to execute: {} request for the given address: {}", method, address, e);
		}
		return null;
	}

	private String calculateHash(String body) {
		return EncryptDecriptUtils.encrypt(body);
	}

	@Override
	public List<FileHolder> resolve() {
		List<FileHolder> files = new LinkedList<FileHolder>();
		for (Entry<String, String> entry : filesToMonitor.entrySet()) {
			try {
				String host;
				URL url = new URL(entry.getKey());
				if (entry.getValue().equals("*")) {
					host = resolveIp(url.getHost());
				}
				else {
					host = entry.getValue();
				}
				String fileAddress = entry.getKey().replace(url.getHost(), host);
				String body = getBody(fileAddress, HttpMethod.GET);
				String hash = calculateHash(body);
				files.add(new FileHolder(entry.getKey(), hash));
			}
			catch (Exception e) {
				logger.error("unable to resolve file: {}", entry.getKey(), e);
			}
		}
		return files;
	}

	@Override
	public List<FileHolder> persist(List<FileHolder> files) {
		List<FileHolder> chagedFiles = new LinkedList<FileHolder>();
		for (FileHolder newFileHolder : files) {
			FileHolder currentFile = persistedFiles.get(newFileHolder.getUrl());
			if (currentFile != null) {
				if (!currentFile.getHash1().equals(newFileHolder.getHash1())) {
					currentFile.setHash1(newFileHolder.getHash1());
					chagedFiles.add(newFileHolder);
				}
			}
			else {// new file
				persistedFiles.put(newFileHolder.getUrl(), newFileHolder);
			}
		}
		return chagedFiles;
	}
}
