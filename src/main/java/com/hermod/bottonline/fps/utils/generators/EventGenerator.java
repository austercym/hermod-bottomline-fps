package com.hermod.bottonline.fps.utils.generators;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;

import com.orwellg.umbrella.avro.types.event.EntityIdentifierType;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.avro.types.event.EventType;
import com.orwellg.umbrella.avro.types.event.ProcessIdentifierType;
import com.orwellg.umbrella.commons.utils.constants.Constants;

public class EventGenerator {

	private final static Logger LOG = LogManager.getLogger(EventGenerator.class);

	public static Event generateEvent(String source, String eventName, String id, String eventData) {
		return generateEvent(source, eventName, id, eventData, Constants.IPAGOO_ENTITY, Constants.IPAGOO_BRAND);
	}
	
	public static Event generateEvent(String source, String eventName, String id, String eventData, String componentEntity, String componentBrand) {
		
		LOG.debug("Generating event with received datas.");
		
		String entity = Constants.IPAGOO_ENTITY;
		if (!StringUtils.isEmpty(componentEntity)) { entity = componentEntity; }
		String brand = Constants.IPAGOO_BRAND;
		if (!StringUtils.isEmpty(componentBrand)) { entity = componentBrand; }
		
		// Create the event type
		EventType eventType = new EventType();
		eventType.setName(eventName);
		eventType.setVersion(Constants.getDefaultEventVersion());
		eventType.setParentKey(Constants.EMPTY);
		eventType.setKey(id);
		eventType.setSource(source);
		SimpleDateFormat format = new SimpleDateFormat(Constants.getDefaultEventTimestampFormat());
		eventType.setTimestamp(format.format(new Date()));
		eventType.setData(eventData);
		
		ProcessIdentifierType processIdentifier = new ProcessIdentifierType();
		processIdentifier.setUuid("PROCESS-" + id);
		
		EntityIdentifierType entityIdentifier = new EntityIdentifierType();
		entityIdentifier.setEntity(entity);
		entityIdentifier.setBrand(brand);
		
		// Create the correspondent event
		Event event = new Event();
		event.setEvent(eventType);
		event.setProcessIdentifier(processIdentifier);
		event.setEntityIdentifier(entityIdentifier);

		LOG.debug("Event results generated correctly. Parameters: {}", eventData);
		
		return event;
	}
}
