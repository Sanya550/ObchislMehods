package com.example.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.mariuszgromada.math.mxparser.*;
import org.mariuszgromada.math.mxparser.parsertokens.*;

import java.io.IOException;

//public class Application extends javafx.application.Application {
//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("methods.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        launch();
//    }
//}
//package com.example.obchisllab1;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Application1 extends Application {
//    private static final double T = 1; // права межа інтервалу
//    private static final int N = 7; // кількість точок розбиття
////
//    // права частина диференціального рівняння
//    private static double f(double t, double y) {
//        return Math.pow(t, 2) - 2 * y;
//    }
//
//    // точний розв'язок
//    private static double exact(double t) {
//        return  0.5 * Math.pow(t, 2) - 0.5 * t + 0.25;
//    }
//
//    // реалізація методу Ейлера
//    private static double[] eulerMethod(double t0, double y0, double h) {
//        double[] y = new double[N + 1];
//        double t = t0;
//        y[0] = y0;
//
//        for (int i = 1; i <= N; i++) {
//            y[i] = y[i - 1] + h * f(t, y[i - 1]);
//            t += h;
//        }
//
//        return y;
//    }
//
//    // метод Коші-Ейлера
//    private static double[] modifiedEulerMethod(double t0, double y0, double h) {
//        double[] y = new double[N];
//        double[] z = new double[N];
//        double t = t0;
//        y[0] = y0;
//        z[0] = f(t, y0);
//
//        for (int i = 1; i < N; i++) {
//            double zp = z[i - 1] + h * f(t, y[i - 1]);
//            double yp = y[i - 1] + h * z[i - 1];
//            y[i] = y[i - 1] + h * (z[i - 1] + zp) / 2;
//            z[i] = z[i - 1] + h * (f(t, y[i - 1]) + f(t + h, yp)) / 2;
//            t += h;
//        }
//
//        return y;
//    }
//
//
//    @Override
//    public void start(Stage stage) {
//        // вісь X
//        NumberAxis xAxis = new NumberAxis(0, T, T / N);
//        xAxis.setLabel("t");
//
//        // вісь Y
//        NumberAxis yAxis = new NumberAxis();
//        yAxis.setLabel("y");
//
//        // графік наближеного розв'язку
//        XYChart.Series<Double, Double> approxSeries = new XYChart.Series<>();
//        approxSeries.setName("Approximate Solution");
//
//        double h = T / N;
//        double[] approx = eulerMethod(0, 1, h);
//        for (int i = 0; i <= N; i++) {
//            approxSeries.getData().add(new XYChart.Data<>(i * h, approx[i]));
//        }
//
//        // графік точного розв'язку
//        XYChart.Series<Double, Double> exactSeries = new XYChart.Series<>();
//        exactSeries.setName("Exact Solution");
//
//        for (int i = 0; i <= N; i++) {
//            exactSeries.getData().add(new XYChart.Data<>(i * h, exact(i * h)));
//        }
//
//
////        XYChart.Series<Double, Double> koshiSeries = new XYChart.Series<>();
////        koshiSeries.setName("koshi Solution");
////        double[] modified = modifiedEulerMethod(0, 1, h);
//
//        for (int i = 0; i < N; i++) {
////            koshiSeries.getData().add(new XYChart.Data<>(i * h, modified[i]));
//        }
//
//        // графічний інтерфейс
//        LineChart<Double, Double> lineChart = new LineChart(xAxis, yAxis);
//        lineChart.getData().add(approxSeries);
//        lineChart.getData().add(exactSeries);
////        lineChart.getData().add(koshiSeries);
//        // кореневий вузол
//        StackPane root = new StackPane();
//        root.getChildren().add(lineChart);
//
//        // сцена
//        Scene scene = new Scene(root, 800, 600);
//
//        // відображення
//        stage.setScene(scene);
//        stage.setTitle("Euler Method");
//        stage.show();
//    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application1.class.getResource("methods.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("App");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }

}