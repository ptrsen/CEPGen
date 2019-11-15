package com.er.subscriptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;


//import java.util.Map;


public class Listener implements UpdateListener {
    private int a = 0;
    private static final Logger log = LoggerFactory.getLogger(UpdateListener.class);

    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        // Here are Biz codes
        EventBean event = newEvents[0];

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
        a=a+1;
        log.info(Integer.toString(a));

        // log.info("Event: {}, {}, {}",A0,A1,A2);

        //  log.info("Alert=" + itemName+" "+price);
        //log.info("atribute 0 {}",event.get("a0"));

    }


}