package com.csp.cep.publisher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;


public class Subscriptor {
     private static final Logger log = LogManager.getLogger(Subscriptor.class);


    /*
        public void update(Object[] row) {
            a=a+1;
            log.info( Integer.toString(  row.length)+" , "+Integer.toString(a)   );
            log.info(row[0].toString());
        }
    */

    public void update(Map row) {

        log.info(  row.toString()  );
     // log.info( row.get("Type").toString()  );

    }

    /*
    public void updateEnd() {
    //    log.info("end");
    }
    */

}