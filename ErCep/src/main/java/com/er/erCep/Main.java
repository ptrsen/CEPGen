package com.er.erCep;

import com.er.cluster.Clustering;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.er.cep.Engine;

import com.er.classificator.Classification;

import java.io.IOException;


/**
 *  Main
 *
 */

public class Main
{
    private static final Logger log = LoggerFactory.getLogger(Main.class);
   // private static String train = "src/main/resources/svm/SYNflood_Normal2.arff" ;
   // private static String test = "src/main/resources/svm/SYNflood_Normal2.arff" ;
    private static String train = "src/main/resources/svm/WISDMA04.arff" ;
    private static String test = "src/main/resources/svm/WISDMA04.arff" ;

    //private static String train2 = "src/main/resources/svm/DataXmeanClustered2.libsvm";


    public static void main ( String[] args ) throws IOException {


        // -------------   Clustering  -------------------------------------------

 ///*

        Clustering model1 = new Clustering(train,test);
        model1.ClModel();

//  */

        // ---------------------------------------------------------------------------


        // -------------   Classification  -------------------------------------------


/*
        Classification model2 = new Classification(train2,train2);
        model2.SVModel();
*/



        // ---------------------------------------------------------------------------




/*
        // -------------   Esper CEP  -----------------------------------------------


        Engine CEPEngine = new Engine("TestEventFileSyn.csv","conf.epl");
        Thread CEPEngineThread = new Thread(CEPEngine);
        CEPEngineThread.start();


        while(true){
            if( !CEPEngineThread.isAlive()  ){
                log.info("end..");
                break;
            }

        }


        // -------------------------------------------------------------------------

*/

    }
}
