package com.er.metrics;

public class ClassifMetrics {

    private double truePositive;
    private double trueNegative;
    private double falsePositive;
    private double falseNegative;

    private double precision;
    private double recall;
    private double specificity;

    private double accuracy;
    private double fscore;

    public ClassifMetrics(double truePositive, double trueNegative,double falsePositive, double falseNegative) {
        this.truePositive = truePositive;
        this.trueNegative = trueNegative;
        this.falsePositive = falsePositive;
        this.falseNegative = falseNegative;

        this.precision = truePositive/(truePositive+falsePositive);
        this.recall = truePositive/(truePositive+falseNegative);
        this.specificity = truePositive/(trueNegative+falsePositive);

        this.accuracy = (truePositive+trueNegative)/(truePositive+trueNegative+falsePositive+falseNegative);
        this.fscore = (2*precision * recall)/(precision + recall);

    }

    public double getTruePositive() {
        return truePositive;
    }

    public double getTrueNegative() {
        return trueNegative;
    }

    public double getFalsePositive() {
        return falsePositive;
    }

    public double getFalseNegative() {
        return falseNegative;
    }



    public double getPrecision() {
        return precision;
    }

    public double getRecall() {
        return recall;
    }

    public double getSpecificity() {
        return specificity;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public double getFscore() {
        return fscore;
    }

    @Override
    public String toString() {
        return "\n \n True Positive:" + truePositive +
                "\n False Positive:" + falsePositive +"\n"+
                "\n True Negative:" + trueNegative +
                "\n False Negative:" + falseNegative +"\n"+
                "\n Recall:" + recall +
                "\n Specificity:" + specificity +
                "\n Precision:" + precision + "\n"+
                "\n Accuracy:" + accuracy +
                "\n F-score:" + fscore + "\n" ;
    }
}
