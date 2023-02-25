package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private static double T; // права межа інтервалу
    private static int N; // кількість точок розбиття
    private static String exactString; // Точний розв'язок
    private static String rightPartStr; // Права частина
    private static double h; // Крок
    private static double startValue; // xo

    static List<Double> X = new ArrayList<>();
    static List<Double> Y = new ArrayList<>();
    @FXML
    private TextField rightPartField;

    @FXML
    private TextField finalDotField;

    @FXML
    private TextField valueOfStartField;

    @FXML
    private TextField exactSolutionField;

    @FXML
    private TextField quantityOfDots;

    @FXML
    private LineChart<Double, Double> lineChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;


    void save() {
        T = Integer.parseInt(finalDotField.getText());
        N = Integer.parseInt(quantityOfDots.getText());
        rightPartStr = rightPartField.getText();
        startValue = Double.parseDouble(valueOfStartField.getText());
        h = T / N;

        //???????:
        if (exactSolutionField.getText().isEmpty()) {
            exactString = "0";
        } else {
            exactString = exactSolutionField.getText();
        }
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(T);
        xAxis.setTickUnit(T / N);
        yAxis.setTickUnit(T / N);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(4);
    }

    @FXML
    void clear() {
        lineChart.getData().clear();
        lineChart.layout();
    }

    @FXML
    void eilerAndKoshiGraph() {

    }

    @FXML
    void eilerGraph() {
        save();
        XYChart.Series<Double, Double> approxSeries = new XYChart.Series<>();
        approxSeries.setName("Ейлер");
        double[] approx = eulerMethod(0, startValue, h);
        for (int i = 0; i <= N; i++) {
            approxSeries.getData().add(new XYChart.Data<>(i * h, approx[i]));
        }
        lineChart.getData().add(approxSeries);
    }

    @FXML
    void exactGraph() {
        btnEiler_Click();
//        save();
//        // графік точного розв'язку
//        XYChart.Series<Double, Double> exactSeries = new XYChart.Series<>();
//        exactSeries.setName("Графік точного розв'язку");
//        for (int i = 0; i <= N; i++) {
//            exactSeries.getData().add(new XYChart.Data<>(i * h, exact(i * h)));
//        }
//        lineChart.getData().add(exactSeries);
    }

    // права частина диференціального рівняння
    private static double f(double x, double y) {
        Argument xArg = new Argument("x");
        Argument yArg = new Argument("y");
        Expression e = new Expression(rightPartStr, xArg, yArg);
        xArg.setArgumentValue(x);
        yArg.setArgumentValue(y);
        double result = e.calculate();  // evaluate the expression
        return result;
    }

    // точний розв'язок
    private static double exact(double x) {
        DoubleEvaluator evalRA = new DoubleEvaluator();
        StaticVariableSet<Double> variablesRA = new StaticVariableSet<Double>();
        variablesRA.set("x", x);
        double resultRA = evalRA.evaluate(exactString, variablesRA);
        return resultRA;
    }

    // реалізація методу Ейлера
    private static double[] eulerMethod(double t0, double y0, double h) {
        double[] y = new double[N + 1];
        double t = t0;
        y[0] = y0;

        for (int i = 1; i <= N; i++) {
            y[i] = y[i - 1] + h * f(t, y[i - 1]);
            t += h;
        }

        return y;
    }

    private void btnEiler_Click() {
        save();
        XYChart.Series<Double, Double> exactSeries = new XYChart.Series<>();
        exactSeries.setName("Графік точного розв'язку");
        XYChart.Series<Double, Double> approxSeries = new XYChart.Series<>();
        approxSeries.setName("Ейлер");
        double a = 0.0;
        double b = T;
        int n = N;
        double x0 = startValue;
        List<Double> X = new ArrayList<>();
        List<Double> Y = new ArrayList<>();
        double h = (b - a) / n;
        double x = a + h;
        int i = 0;
        X.add(a);
        Y.add(x0);
        do {
            approxSeries.getData().add(new XYChart.Data<>(X.get(i), Y.get(i)));
            X.add(X.get(i) + h);
            Y.add(Y.get(i) + h * f(X.get(i), Y.get(i)));
            x += h;
            i++;
        } while (x <= b);
        approxSeries.getData().add(new XYChart.Data<>(X.get(X.size() - 1), Y.get(Y.size() - 1)));


        for (int j = 0; j < X.size(); j++) {
            exactSeries.getData().add(new XYChart.Data<>(X.get(j), exact(X.get(j))));
        }
        lineChart.getData().addAll(exactSeries, approxSeries);
    }

    // метод Коші-Ейлера
    private static double[] modifiedEulerMethod(double t0, double y0, double h) {
        double[] y = new double[N + 1];
        double[] z = new double[N + 1];
        double t = t0;
        y[0] = y0;
        z[0] = f(t, y0);

        for (int i = 1; i <= N; i++) {
            double zp = z[i - 1] + h * f(t, y[i - 1]);
            double yp = y[i - 1] + h * z[i - 1];
            y[i] = y[i - 1] + h * (z[i - 1] + zp) / 2;
            z[i] = z[i - 1] + h * (f(t, y[i - 1]) + f(t + h, yp)) / 2;
            t += h;
        }

        return y;
    }

}
