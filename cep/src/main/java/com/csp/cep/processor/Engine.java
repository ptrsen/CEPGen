package com.csp.cep.processor;
import com.csp.cep.annotation.*;


import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.common.client.module.Module;
import com.espertech.esper.compiler.client.CompilerArguments;
import com.espertech.esper.compiler.client.EPCompilerProvider;
import com.espertech.esper.runtime.client.*;


import com.espertech.esperio.csv.AdapterInputSource;
import com.espertech.esperio.csv.CSVInputAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.File;
import java.lang.annotation.Annotation;



public class Engine implements Runnable {

    private static final Logger log = LogManager.getLogger(Engine.class);

      private boolean doStop = false;             //  Stops thread

  //  private com.espertech.esper.runtime.client.EPRuntime epService;
   // private  EPServiceProvider epService;
  //  private  EPDeploymentAdmin deployAdmin;


    private EPRuntime runtime;
    private Configuration conf;
    private CompilerArguments CompArgs;


    private String  eplFile;         //  Path Configuration EPL file  (contains Schema and Rules)
    private  String eventTypeName;







    /**
     * Setup Esper Engine with default values
     **/

    public Engine(){

        log.debug("CEP Engine Setup ...");

        this.conf= new Configuration();
        conf.getCompiler().getByteCode().setAllowSubscriber(true);
        

        this.CompArgs = new CompilerArguments(conf);



    }


    /**
     *     Starts Esper Engine
     **/

    @Override
    public void run() {

        this.runtime = EPRuntimeProvider.getDefaultRuntime(conf);
        log.debug("Successfully started Esper Runtime ...");

        while(keepRunning()) {
/*
            System.out.println("Running thread");


           //  elapsed time
            try {
                Thread.sleep(1L * 500L); // 0.5 seg
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            */
        }


   }


    /**
     * Register new Event Schema and Statements (with attached Subscriptor or Listener) in EPL file
     **/

    public void updateRule(String pathEplFile) throws Exception {
       this.eplFile = pathEplFile;




       log.debug("Deleting previous registered Event Schema and Statements  ...");



     //  CompArgs = new CompilerArguments(runtime.getConfigurationDeepCopy());
       //CompArgs.getConfiguration().getCompiler().getByteCode().setAllowSubscriber(true);


       log.debug("Reading EPL file ...");
       Module eplModule = EPCompilerProvider.getCompiler().readModule(new File(eplFile));




       log.debug("Registering new Event Schema and Statements  ...");
       EPCompiled compiled = EPCompilerProvider.getCompiler().compile(eplModule,CompArgs);

       EPDeployment deployment = runtime.getDeploymentService().deploy(compiled);


       EPStatement [] Statements = deployment.getStatements();

       int i = 0;
       for (EPStatement rule: Statements){
           if (rule.getName()=="CreateEventSchema"){


               eventTypeName = rule.getEventType().getName();
              log.debug("jppp:"+  rule.getDeploymentId().toString()    );
           }

           if (rule.getName().contains("Rule_")){
               processAnnotations(rule); // process statements and attach a subscriptor or listener
           }

        i++;
       }



        CSVInputAdapter input = new CSVInputAdapter(runtime, new AdapterInputSource(new File("./src/main/resources/TestEventFile.csv")), "event");
        input.start();


   }



    /**
     * Prcess Annotations in EPL file , Attach Subscriptor or Listener to Rules
     **/

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
                  //  if (obj instanceof StatementAwareUpdateListener) {
                  //      statement.addListener((StatementAwareUpdateListener) obj);
                  //  } else {
                        statement.addListener((UpdateListener) obj);
                  //  }
                }
            }

        }
    }



    /**
     * Return EPserviceProvider
     **/

   public EPRuntime getEPRuntime (){
        return runtime;
   }

    /**
     * Return EventTypeName from Schema
     **/

    public String getEventTypeName (){
        return eventTypeName;
    }


    /**
     *   Destroy Engine
     **/

    public void destroyEPRuntime(){
        runtime.destroy();
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
