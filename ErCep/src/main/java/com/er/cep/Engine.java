package com.er.cep;


import com.er.erCep.Main;
import com.er.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esperio.csv.AdapterInputSource;
import com.espertech.esperio.csv.CSVInputAdapter;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.StatementAwareUpdateListener;


import com.espertech.esper.client.deploy.*;

import java.lang.annotation.Annotation;


/*
    Esper Framework

 */

public class Engine implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Main.class);      // Logger
    private String CsvEventsFile;                                               // Path to csv file with feed events
    private String ConfFile;                                                    // Path to config epl file

    public Engine (String CsvEventsFile,String ConfFile){
        this.CsvEventsFile = CsvEventsFile;
        this.ConfFile = ConfFile;
    }


    private void deploy() throws Exception {

        // Setting up Esper
        log.info("Setting up default CEP Engine ...");

        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
        EPDeploymentAdmin deployAdmin = epService.getEPAdministrator().getDeploymentAdmin();
        Module queries = deployAdmin.read(ConfFile);

        String moduleDeploymentId = deployAdmin.add(queries);
        deployAdmin.deploy(moduleDeploymentId, null);

        // Configuring
        log.info("Registering and adding local Subcriptor ...");

        String [] StatementsN = epService.getEPAdministrator().getStatementNames();


        for (String Names : StatementsN){
            log.info(Names);
        }


        processAnnotations(epService.getEPAdministrator().getStatement("FilterRuleClass1")); // Statename called FilterRule
        processAnnotations(epService.getEPAdministrator().getStatement("FilterRuleClass2")); // Statename called FilterRule


        // Publish events from csv file
        log.info("Publish events from Csv filename ...");
        AdapterInputSource source = new AdapterInputSource(CsvEventsFile);
        (new CSVInputAdapter(epService,source,"Event")).start();

    }


    private static void processAnnotations(EPStatement statement) throws Exception {

        Annotation[] annotations = statement.getAnnotations();
        for (Annotation annotation : annotations) {

            // For Esper Subscriber
            if (annotation instanceof Subscriber){
                Subscriber subscriber  = (Subscriber) annotation;
                Class<?> cl = Class.forName(subscriber.value());
                Object obj = cl.newInstance();
                statement.setSubscriber(obj);
            }

            // For Esper Listeners StatementAwareUpdateListener or UpdateListener
            if (annotation instanceof Listeners) {
                Listeners listeners = (Listeners) annotation;
                for (String values : listeners.value()) {
                    Class<?> cl = Class.forName(values);
                    Object obj = cl.newInstance();
                    if (obj instanceof StatementAwareUpdateListener) {
                        statement.addListener((StatementAwareUpdateListener) obj);

                    } else {
                        statement.addListener((UpdateListener) obj);
                    }
                }
            }


        }
    }



    @Override
    public void run() {
        try {
            deploy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
