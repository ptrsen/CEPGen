package com.csp.cep.collector;

import com.espertech.esper.common.internal.epl.index.advanced.index.service.EventAdvancedIndexProvisionRuntime;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esperio.csv.AdapterInputSource;
import com.espertech.esperio.csv.CSVInputAdapter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class Generator implements Runnable {

    private static final Logger log = LogManager.getLogger(Generator.class);

    private String  CsvEvent_File;                        //  CSV Events file to publish
    private boolean doStop = false;

    private EPRuntime runtime;
    private String eventTypeName;

    public Generator(EPRuntime runtime, String eventTypeName, String pathCsvEventsFile){
        this.runtime = runtime;
        this.eventTypeName = eventTypeName;
        this.CsvEvent_File = pathCsvEventsFile;
    }

    /**
     *  Publish events from csv file
     **/

    private void publishCsv() {
        log.debug("Publish events from Csv filename ...");

        CSVInputAdapter input = new CSVInputAdapter(runtime, new AdapterInputSource(new File(CsvEvent_File)), "Event");
        input.start();
    }




    /**
     * Run Thread
     **/

    @Override
    public void run() {

        publishCsv();

        while(keepRunning()) {

        }

    }


    /**
     * Stops Thread
     **/

    public synchronized void doStop() {
        this.doStop = true;
    }


    /**
     * Checks thread is still running
     **/

    private synchronized boolean keepRunning() {
        return this.doStop == false;
    }


}

