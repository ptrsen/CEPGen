package com.er.classificator;

import net.jkernelmachines.classifier.multiclass.OneAgainstAll;
import net.jkernelmachines.evaluation.MulticlassAccuracyEvaluator;
import net.jkernelmachines.evaluation.NFoldCrossValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.er.erCep.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.jkernelmachines.classifier.LaSVM;
import net.jkernelmachines.classifier.*;

import net.jkernelmachines.evaluation.ApEvaluator;
import net.jkernelmachines.io.LibSVMImporter;
import net.jkernelmachines.io.ArffImporter;
import net.jkernelmachines.kernel.GaussianKernel;
import net.jkernelmachines.kernel.typed.DoubleGaussL2;
import net.jkernelmachines.kernel.typed.DoubleLinear;
import net.jkernelmachines.kernel.typed.DoublePolynomial;
import net.jkernelmachines.type.TrainingSample;

import net.jkernelmachines.type.ListSampleStream;

import java.io.*;

public class Classification {

    private String path_train,path_test;
    private static final Logger log = LoggerFactory.getLogger(Main.class);


    public Classification(String path_train,String path_test){
        this.path_train = path_train;
        this.path_test = path_test;
    }


    public void SVModel() throws IOException {

        log.info("Init");


        // convert Arff to vw
        Runtime rt = Runtime.getRuntime();
        // change cluster results to vw format , just choose cluster 3 y 4
        String cmd1 = "python /home/erickpetersen/Documents/IdeaProjects/ErCep/src/main/resources/tools/arffToVw.py /home/erickpetersen/Documents/IdeaProjects/ErCep/src/main/resources/svm/Data_cluster34.arff /home/erickpetersen/Documents/IdeaProjects/ErCep/src/main/resources/svm/DataXmeanClustered.vw";
        // change test data to vw format
        String cmd2 = "python /home/erickpetersen/Documents/IdeaProjects/ErCep/src/main/resources/tools/arffToVw.py /home/erickpetersen/Documents/IdeaProjects/ErCep/src/main/resources/svm/SYNflood_Normal2.arff /home/erickpetersen/Documents/IdeaProjects/ErCep/src/main/resources/svm/SYNflood_Normal2.vw";


        // others model
        // String cmd2 = "/home/erickpetersen/Downloads/vowpal_wabbit/vowpalwabbit/./vw --oaa 4 /home/erickpetersen/Documents/IdeaProjects/ErCep/src/main/resources/svm/DataXmeanClustered.vw -f /home/erickpetersen/Documents/IdeaProjects/ErCep/src/main/resources/svm/DataXmeanClustered.model";
       // String cmd3 = "/home/erickpetersen/Downloads/vowpal_wabbit/vowpalwabbit/./vw -t -i /home/erickpetersen/Documents/IdeaProjects/ErCep/src/main/resources/svm/DataXmeanClustered.model /home/erickpetersen/Documents/IdeaProjects/ErCep/src/main/resources/svm/DataXmeanClustered.vw -p /home/erickpetersen/Documents/IdeaProjects/ErCep/src/main/resources/svm/DataXmeanClustered.predict";



        // train online SVM
       // String cmd3 = "/home/erickpetersen/Downloads/vowpal_wabbit/vowpalwabbit/./vw --ksvm --l2 1.0 -d /home/erickpetersen/Documents/IdeaProjects/ErCep/src/main/resources/svm/DataXmeanClustered.vw -f /home/erickpetersen/Documents/IdeaProjects/ErCep/src/main/resources/svm/DataXmeanClustered.model";

        // test data
        //String cmd4 = "/home/erickpetersen/Downloads/vowpal_wabbit/vowpalwabbit/./vw -i /home/erickpetersen/Documents/IdeaProjects/ErCep/src/main/resources/svm/DataXmeanClustered.model -t /home/erickpetersen/Documents/IdeaProjects/ErCep/src/main/resources/svm/SYNflood_Normal2.vw -p /home/erickpetersen/Documents/IdeaProjects/ErCep/src/main/resources/svm/DataXmeanClustered.predict";

        // change result to measure things with perf
        //String cmd5 = "python /home/erickpetersen/Documents/IdeaProjects/ErCep/src/main/resources/tools/VwToPerf.py /home/erickpetersen/Documents/IdeaProjects/ErCep/src/main/resources/svm/DataXmeanClustered.predict /home/erickpetersen/Documents/IdeaProjects/ErCep/src/main/resources/svm/Perf.predict";

        // measure things with perf
        // String cmd6 "/home/erickpetersen/Downloads/perf.linux/./perf -acc -pre -rec -file /home/erickpetersen/Documents/IdeaProjects/ErCep/src/main/resources/svm/Perf.predict";



        Process pr = rt.exec(cmd1);

        BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line=null;
        while((line=input.readLine()) != null) {
            log.info(line);
        }



        /*
            Runtime rt = Runtime.getRuntime();
            //Process pr = rt.exec("cmd /c dir");
            Process pr = rt.exec("/home/erickpetersen/Downloads/vowpal_wabbit/vowpalwabbit/./vw --help ");

            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

            String line=null;

            while((line=input.readLine()) != null) {
                log.info("line");
            }

        */

/*
        log.info("Reading Train and Test File for SVM ...");
        List<TrainingSample<double[]>> train= LibSVMImporter.importFromFile(path_train);
        List<TrainingSample<double[]>> test= LibSVMImporter.importFromFile(path_train);

        //  List<TrainingSample<double[]>> train = ArffImporter.importFromFile(path_train);
      //  List<TrainingSample<double[]>> test = ArffImporter.importFromFile(path_test);

        log.info("Converting to stream");
        ListSampleStream str = new ListSampleStream(train);

        log.info("Number data: "+Integer.toString( train.size() ));
        log.info("Stream epochs: "+Integer.toString(str.getE()) );


        log.info("Setting SVM ...");


        // Setting Linear kernel  for SVM
//        DoubleLinear kernel = new DoubleLinear();

        //DoublePolynomial kernel = new DoublePolynomial();


        //   Gauss kernel
        DoubleGaussL2 kernel = new DoubleGaussL2(2.0);

        //setting SVM parameters
        LaSVM<double[]> svm = new LaSVM<double[]>(kernel);
        svm.setC(10); //C hyperparameter


        log.info("Preparing to train SVM ...");
        OneAgainstAll<double[]> mcsvm = new OneAgainstAll<double[]>(svm);

        mcsvm.train(train);

        log.info("10 fold CrossValidation ...");
        //doing crossvalidation with multiclass accuracy
      //  MulticlassAccuracyEvaluator<double[]> eval = new MulticlassAccuracyEvaluator<double[]>();

       // NFoldCrossValidation<double[]> cv = new NFoldCrossValidation<double[]>(10, mcsvm, train, eval);

        //launch cv
      //  cv.run();

        //print results
      //  System.out.println("Multiclass accuracy: "+cv.getAverageScore()+" +/- "+cv.getStdDevScore());


/*
        DoublePegasosSVM svm = new DoublePegasosSVM();
       // LaSVMI<double[]> svm = new LaSVMI<>(kernel);
        svm.setC(10);
*/

//        /*
        /*
        log.info("Start train");
        int a =0;
        TrainingSample<double[]> t;
        while( (t = str.nextSample()) != null) {
            log.info( Integer.toString(str.nextSample().label));
            a+=1;
        }
        log.info("Stream: " + Integer.toString(a));
        log.info("Train: "+Integer.toString(train.size()));
*/
  //      svm.train(train);
    //    svm.onlineTrain(str);
  //      */
   //     log.info("finish train");


     //  double[] a = svm.getAlphas(); // getting Alphas
      //  log.info("Alphas :" + Arrays.toString(a));
       // log.info("Number of Alphas :" + a.length);
       // log.info("Train Vectors:" +train.size());


        /*
        //evaluation on testing set using evaluator
        // just to check its done correctly with the same data
        ApEvaluator<double[]> ape = new ApEvaluator<double[]>();
        ape.setClassifier(svm);
        ape.setTrainingSet(train);
        ape.setTestingSet(test);
        ape.evaluate(); // training and evaluating at the same time

        // printing average precision, bias, alphas  obtained
        log.info("Average Precision: "+ape.getScore());
        */

    }





}
