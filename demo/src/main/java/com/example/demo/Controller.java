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
    private static double startValue; // xo

    static double lowY;
    static double highY;
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

        if (exactSolutionField.getText().isEmpty()) {
            exactString = "0";
        } else {
            exactString = exactSolutionField.getText();
        }
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);
        xAxis.setLowerBound(0 - T / N);
        xAxis.setUpperBound(T + T / N);
        xAxis.setTickUnit(T / N);
        yAxis.setTickUnit(T / N);
        lowY = startValue;
        highY = startValue;
    }

    @FXML
    void clear() {
        lineChart.getData().clear();
        lineChart.layout();
    }

    @FXML
    void eilerAndKoshiGraph() {
        save();
        XYChart.Series<Double, Double> koshiSeries = new XYChart.Series<>();
        koshiSeries.setName("Метод Коші-Ейлера");
        double a = 0.0;
        X.clear();
        Y.clear();
        double h = (T - a) / N;
        double x = a + h;
        int i = 0;
        X.add(a);
        Y.add(startValue);
        do {
            koshiSeries.getData().add(new XYChart.Data<>(X.get(i), Y.get(i)));
            double f1 = f(X.get(i), Y.get(i));
            double f2 = f(X.get(i) + h, Y.get(i) + h * f1);
            Y.add(Y.get(i) + h * (f1 + f2) / 2.0);
            comparison(Y.get(i) + h * (f1 + f2) / 2.0);
            X.add(X.get(i) + h);
            x += h;
            i++;
        } while (x <= T);
        koshiSeries.getData().add(new XYChart.Data<>(X.get(X.size() - 1), Y.get(Y.size() - 1)));
        comparison(Y.get(Y.size() - 1));
        lineChart.getData().addAll(koshiSeries);
    }

    @FXML
    void eilerGraph() {
        save();
        XYChart.Series<Double, Double> approxSeries = new XYChart.Series<>();
        approxSeries.setName("Метод Ейлера");
        double a = 0.0;
        X.clear();
        Y.clear();
        double h = (T - a) / N;
        double x = a + h;
        int i = 0;
        X.add(a);
        Y.add(startValue);
        do {
            approxSeries.getData().add(new XYChart.Data<>(X.get(i), Y.get(i)));
            X.add(X.get(i) + h);
            Y.add(Y.get(i) + h * f(X.get(i), Y.get(i)));
            comparison(Y.get(i) + h * f(X.get(i), Y.get(i)));
            x += h;
            i++;
        } while (x <= T);
        approxSeries.getData().add(new XYChart.Data<>(X.get(X.size() - 1), Y.get(Y.size() - 1)));
        comparison(Y.get(Y.size() - 1));
        lineChart.getData().addAll(approxSeries);
    }

    @FXML
    void exactGraph() {
        save();
        X.clear();
        XYChart.Series<Double, Double> exactSeries = new XYChart.Series<>();
        exactSeries.setName("Графік точного розв'язку");
        double a = 0.0;
        double h = (T - a) / N;
        double x = a + h;
        int i = 0;
        X.add(a);
        Y.add(startValue);
        do {
            X.add(X.get(i) + h);
            x += h;
            i++;
        } while (x <= T);

        for (int j = 0; j < X.size(); j++) {
            exactSeries.getData().add(new XYChart.Data<>(X.get(j), exact(X.get(j))));
            comparison(exact(X.get(j)));
        }
        lineChart.getData().addAll(exactSeries);
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

    private void comparison(double x) {
        if (x > highY) {
            highY = x;
        }
        if (x < lowY) {
            lowY = x;
        }
        yAxis.setLowerBound(lowY - startValue / 2);
        yAxis.setUpperBound(highY + startValue / 2);
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
