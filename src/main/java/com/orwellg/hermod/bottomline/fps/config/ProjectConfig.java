package com.orwellg.hermod.bottomline.fps.config;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;
import com.orwellg.hermod.bottomline.fps.utils.singletons.SchemeValidatorBean;
import com.orwellg.umbrella.commons.types.fps.PaymentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import com.orwellg.umbrella.commons.utils.enums.fps.FPSDirection;

import java.util.concurrent.CountDownLatch;

import static com.codahale.metrics.MetricRegistry.name;

@Configuration
public class ProjectConfig extends ComponentConfig {

	private static final Logger LOG = LogManager.getLogger(ProjectConfig.class);

	@Autowired
	private AbstractMessageListenerContainer<?,?> kafkaResponseInboundListenerContainer;

	@Autowired
	private AbstractMessageListenerContainer<?,?> kafkaResponseReversalInboundListenerContainer;

	@Autowired
	private AbstractMessageListenerContainer<?,?> kafkaRequestOutboundListenerContainer;

	@Autowired
	private AbstractMessageListenerContainer<?,?> kafkaRequestInMemoryListenerContainer;

	@Autowired
	private AbstractMessageListenerContainer<?,?> kafkaResponseInMemoryListenerContainer;

	@Autowired
	private DefaultMessageListenerContainer jmsSIPListenerContainer;

	@Autowired
	private DefaultMessageListenerContainer jmsASYNCListenerContainer;

	@Autowired
	private DefaultMessageListenerContainer jmsSTANDINListenerContainer;

	@Autowired
	private DefaultMessageListenerContainer jmsPOOListenerContainer;

	@Autowired
	private DefaultMessageListenerContainer jmsUSMListenerContainer;

	@Autowired
	private DefaultMessageListenerContainer jmsSIPOutboundListenerContainer;
	@Autowired
	private DefaultMessageListenerContainer jmsAsynOutboundListenerContainer;

	@Autowired
	private DefaultMessageListenerContainer jmsSIPListenerSite2Container;

	@Autowired
	private DefaultMessageListenerContainer jmsASYNCListenerSite2Container;

	@Autowired
	private DefaultMessageListenerContainer jmsSTANDINListenerSite2Container;

	@Autowired
	private DefaultMessageListenerContainer jmsPOOListenerSite2Container;

	@Autowired
	private DefaultMessageListenerContainer jmsUSMListenerSite2Container;

	@Autowired
	private DefaultMessageListenerContainer jmsSIPOutboundListenerSite2Container;
	@Autowired
	private DefaultMessageListenerContainer jmsAsynOutboundListenerSite2Container;

	@Autowired
	private MetricRegistry metricRegistry;

    @Value("${useSSL}")
    private Boolean useSSL;

    @Value("${trustStore}")
    private String trustStore;

    @Value("${trustStorePassword}")
    private String trustStorePassword;

    @Value("${keyStore}")
    private String keyStore;

    @Value("${keyStorePassword}")
    private String keyStorePassword;

    @Value("${useIBMCipherMappings}")
    private Boolean useIBMCipherMappings;

    private CountDownLatch shutdownLatch = new CountDownLatch(1);

	@EventListener(ApplicationReadyEvent.class)
	public void startListeners() {
	    LOG.info("Connector to Bottomline start. Starting containers....");
        shutdownLatch = new CountDownLatch(1);

        initializeMetrics();

		final JmxReporter reporterJMX = JmxReporter.forRegistry(metricRegistry).build();
		reporterJMX.start();

        LOG.info("Use SSL? {}", useSSL);
        if(useSSL){
            LOG.info("Connecting using SSl config. truststore: {}, keystore: {}", trustStore, keyStore);
            System.setProperty("javax.net.ssl.trustStore", trustStore);
            System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
            System.setProperty("javax.net.ssl.keyStore", keyStore);
            System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
            System.setProperty("com.ibm.mq.cfg.useIBMCipherMappings", useIBMCipherMappings.toString());
        }
		try{
			SchemeValidatorBean.getInstance();
		}catch(Exception e){
			LOG.error("[FPS] Error creating scheme validators to validate payment messages");
		}

        new Thread(() -> {
            LOG.info("[FPS] Starting listeners to datacenter 1");
            startDatacenter1Listeners();
            setAutoStartListenersDatacenter1();
            LOG.info("[FPS] Finishing listeners to datacenter 1");
            shutdownLatch.countDown();
        }).start();

		new Thread(() -> {
            LOG.info("[FPS] Starting listeners to datacenter 2");
            startDatacenter2Listeners();

            setAutoStartListenersDatacenter2();

            LOG.info("[FPS] Finishing listeners to datacenter 2");
            shutdownLatch.countDown();
		}).start();



        try {
            shutdownLatch.await();
        } catch (InterruptedException e) {
            LOG.error("[FPS] Error waiting for connect to one datacenter al least");
        }

        kafkaResponseInboundListenerContainer.start();
		kafkaRequestOutboundListenerContainer.start();
		kafkaResponseReversalInboundListenerContainer.start();
		kafkaRequestInMemoryListenerContainer.start();
		kafkaResponseInMemoryListenerContainer.start();

	    LOG.info("Connector started");
	}

    private void setAutoStartListenersDatacenter2() {
        if(jmsSIPListenerSite2Container.isRunning()){
            jmsSIPListenerSite2Container.setAutoStartup(true);
        }
        if(jmsASYNCListenerSite2Container.isRunning()){
            jmsASYNCListenerSite2Container.setAutoStartup(true);
        }
        if(jmsSTANDINListenerSite2Container.isRunning()){
            jmsSTANDINListenerSite2Container.setAutoStartup(true);
        }
        if(jmsSIPOutboundListenerSite2Container.isRunning()){
            jmsSIPOutboundListenerSite2Container.setAutoStartup(true);
        }
        if(jmsAsynOutboundListenerSite2Container.isRunning()){
            jmsAsynOutboundListenerSite2Container.setAutoStartup(true);
        }
        if(jmsPOOListenerSite2Container.isRunning()){
            jmsPOOListenerSite2Container.setAutoStartup(true);
        }
        if(jmsUSMListenerSite2Container.isRunning()){
            jmsUSMListenerSite2Container.setAutoStartup(true);
        }
    }

    private void setAutoStartListenersDatacenter1() {
        if(jmsSIPListenerContainer.isRunning()){
            jmsSIPListenerContainer.setAutoStartup(true);
        }
        if(jmsASYNCListenerContainer.isRunning()){
            jmsASYNCListenerContainer.setAutoStartup(true);
        }
        if(jmsSTANDINListenerContainer.isRunning()){
            jmsSTANDINListenerContainer.setAutoStartup(true);
        }
        if(jmsSIPOutboundListenerContainer.isRunning()){
            jmsSIPOutboundListenerContainer.setAutoStartup(true);
        }
        if(jmsAsynOutboundListenerContainer.isRunning()){
            jmsAsynOutboundListenerContainer.setAutoStartup(true);
        }
        if(jmsPOOListenerContainer.isRunning()){
            jmsPOOListenerContainer.setAutoStartup(true);
        }
        if(jmsUSMListenerContainer.isRunning()){
            jmsUSMListenerContainer.setAutoStartup(true);
        }
    }

    private void startDatacenter2Listeners() {
        jmsSIPListenerSite2Container.start();
        jmsASYNCListenerSite2Container.start();
        jmsSTANDINListenerSite2Container.start();
        jmsSIPOutboundListenerSite2Container.start();
        jmsAsynOutboundListenerSite2Container.start();
        jmsPOOListenerSite2Container.start();
        jmsUSMListenerSite2Container.start();
    }

    private void startDatacenter1Listeners() {
        jmsSIPListenerContainer.start();
        jmsASYNCListenerContainer.start();
        jmsSTANDINListenerContainer.start();
        jmsSIPOutboundListenerContainer.start();
        jmsAsynOutboundListenerContainer.start();
        jmsPOOListenerContainer.start();
        jmsUSMListenerContainer.start();
    }

    private void initializeMetrics(){

	    LOG.info("Defining default metrics and initialize to 0");

        metricRegistry.counter(name("connector_fps", "inbound", PaymentType.PaymentTypeCode.SOP.name(),  FPSDirection.INPUT.getDirection()));
        metricRegistry.counter(name("connector_fps", "inbound", PaymentType.PaymentTypeCode.CBP.name(), FPSDirection.INPUT.getDirection()));
        metricRegistry.counter(name("connector_fps", "inbound", PaymentType.PaymentTypeCode.FDP.name(), FPSDirection.INPUT.getDirection()));
        metricRegistry.counter(name("connector_fps", "inbound", PaymentType.PaymentTypeCode.SRN.name(), FPSDirection.INPUT.getDirection()));
        metricRegistry.counter(name("connector_fps", "inbound", PaymentType.PaymentTypeCode.RTN.name(), FPSDirection.INPUT.getDirection()));
        metricRegistry.counter(name("connector_fps", "inbound", PaymentType.PaymentTypeCode.SIP.name(), FPSDirection.INPUT.getDirection()));

        metricRegistry.counter(name("connector_fps_reversal", "inbound", PaymentType.PaymentTypeCode.SOP.name(),  FPSDirection.INPUT.getDirection()));
        metricRegistry.counter(name("connector_fps_reversal", "inbound", PaymentType.PaymentTypeCode.CBP.name(), FPSDirection.INPUT.getDirection()));
        metricRegistry.counter(name("connector_fps_reversal", "inbound", PaymentType.PaymentTypeCode.FDP.name(), FPSDirection.INPUT.getDirection()));
        metricRegistry.counter(name("connector_fps_reversal", "inbound", PaymentType.PaymentTypeCode.SRN.name(), FPSDirection.INPUT.getDirection()));
        metricRegistry.counter(name("connector_fps_reversal", "inbound", PaymentType.PaymentTypeCode.RTN.name(), FPSDirection.INPUT.getDirection()));
        metricRegistry.counter(name("connector_fps_reversal", "inbound", PaymentType.PaymentTypeCode.SIP.name(), FPSDirection.INPUT.getDirection()));

        metricRegistry.counter(name("connector_fps", "outbound", PaymentType.PaymentTypeCode.SOP.name(), FPSDirection.OUTPUT.getDirection()));
        metricRegistry.counter(name("connector_fps", "outbound", PaymentType.PaymentTypeCode.FDP.name(), FPSDirection.OUTPUT.getDirection()));
        metricRegistry.counter(name("connector_fps", "outbound", PaymentType.PaymentTypeCode.CBP.name(), FPSDirection.OUTPUT.getDirection()));
        metricRegistry.counter(name("connector_fps", "outbound", PaymentType.PaymentTypeCode.SRN.name(), FPSDirection.OUTPUT.getDirection()));
        metricRegistry.counter(name("connector_fps", "outbound", PaymentType.PaymentTypeCode.RTN.name(), FPSDirection.OUTPUT.getDirection()));
        metricRegistry.counter(name("connector_fps", "outbound", PaymentType.PaymentTypeCode.SIP.name(), FPSDirection.OUTPUT.getDirection()));

        metricRegistry.counter(name("connector_fps", "inbound", "-", FPSDirection.OUTPUT.getDirection(),"TotalRejects"));
        metricRegistry.counter(name("connector_fps", "inbound", "-", FPSDirection.OUTPUT.getDirection(),"TotalAcceptances"));

        metricRegistry.counter(name("connector_fps", "inbound", "-", FPSDirection.OUTPUT.getDirection(),"TotalRejects"));
        metricRegistry.counter(name("connector_fps", "inbound", "-", FPSDirection.OUTPUT.getDirection(),"TotalAcceptances"));

        metricRegistry.counter(name("connector_fps_reversal", "inbound", "-", FPSDirection.OUTPUT.getDirection(),"TotalRejects"));
        metricRegistry.counter(name("connector_fps_reversal", "inbound", "-", FPSDirection.OUTPUT.getDirection(),"TotalAcceptances"));

        metricRegistry.counter(name("connector_fps", "outbound", "-", FPSDirection.INPUT.getDirection(),"TotalRejects"));
        metricRegistry.counter(name("connector_fps", "outbound", "-", FPSDirection.INPUT.getDirection(),"TotalAcceptances"));

        metricRegistry.counter(name("connector_fps", "inbound", "STANDIN", FPSDirection.INPUT.getDirection()));
        metricRegistry.counter(name("connector_fps", "inbound", "POO", FPSDirection.INPUT.getDirection()));

        metricRegistry.counter(name("connector_fps", "inbound", "USM", FPSDirection.INPUT.getDirection()));

    }
}
