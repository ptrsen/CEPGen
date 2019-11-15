package com.csp.cep.publisher;

import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import com.espertech.esper.common.client.EventBean;


public class Listener implements UpdateListener {
  //  private int a = 0;
 // private static final Logger log = LogManager.getLogger(Listener.class);


    public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {

        // Here are Biz codes
     //   EventBean event = newEvents[0];

       // log.debug(event.get("Type"));
        //Object itemName = ((Map<?,?>) event.getUnderlying()).get("itemName");
        //Object average = event.get("avg(price)");

/*
        // just for display
        int NoA = 43;                     // Total number of event attributes

        String display = "Event: ";
        a=a+1;
        for(int i=0; i<NoA ; i++){
            display += event.get("A"+ Integer.toString(i))+",";
        }
        display += event.get("Type");
        log.info(display);

  */
       // a=a+1;
       // log.info(Integer.toString(a));

        // log.info("Event: {}, {}, {}",A0,A1,A2);

        //  log.info("Alert=" + itemName+" "+price);
        //log.info("atribute 0 {}",event.get("a0"));

    }


}
