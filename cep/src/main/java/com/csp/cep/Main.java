package com.csp.cep;

import com.csp.cep.collector.Generator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.csp.cep.processor.Engine;

/**
 *    some tests
 *
 */

/*
        LOG.debug("This Will Be Printed On Debug");
        LOG.info("This Will Be Printed On Info");
        LOG.warn("This Will Be Printed On Warn");
        LOG.error("This Will Be Printed On Error");
        LOG.fatal("This Will Be Printed On Fatal");
        LOG.info("Appending string: {}.", "Hello, World");
*/

public class Main {

    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main( String[] args ) throws Exception {


        log.debug("Esper Test ......  !");
        Engine ep = new Engine();
        Thread Engine_thread = new Thread(ep);
        Engine_thread.start();

        ep.updateRule("./src/main/resources/conf.epl");





/*
        Generator ge = new Generator(ep.getEPRuntime(),ep.getEventTypeName(),"./src/main/resources/TestEventFile.csv");
        Thread generator_thread = new Thread(ge);
        generator_thread.start();
*/


/*
        try {
            Thread.sleep(1L * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        ep.updateRule("conf.epl");

        // Elapsed time 10 seg to see whats happen
        try {
            Thread.sleep(10L * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/

        ep.doStop();
   //     ge.doStop();


      //  p.updateRule();
       // p.publishCsv("TestEventFile.csv");
       // p.destroy();

    }
}
