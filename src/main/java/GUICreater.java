import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;

import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;

public class GUICreater {

    public static void createGUI(double[] x, double[] y, double[] coef, int basis) {

        int length = x.length;
        int maxDeltaIndex = 0;
        double delta = 0.0;
        for (int i = 0; i < length; i++) {
            double func = 0.0;

            for (int j = 0; j < basis + 1; j++) {
                func += coef[j] * Math.pow(x[i], j);
            }
            System.out.println(1);
            if (Math.abs(func - y[i]) > delta) {
                delta = Math.abs(func - y[i]);
                maxDeltaIndex = i;
            }
        }
        double[] xc_n = new double[length - 1];
        double[] yc_n = new double[length - 1];
        for (int i = 0; i < length - 1; i++) {
            if (i < maxDeltaIndex) {
                xc_n[i] = x[i];
                yc_n[i] = y[i];
            } else {
                xc_n[i] = x[i + 1];
                yc_n[i] = y[i + 1];
            }
        }

        System.out.println(2);
        double[] coef_n = new FormMain().solve(xc_n, yc_n, basis);

        double[] func_o = new double[length];
        for (int i = 0; i < length; i++) {
            double s = 0.D;
            for (int j = 0; j < basis + 1; j++) {
                s += coef[j] * Math.pow(x[i], j);
            }
            func_o[i] = s;
        }
        double[] func_n = new double[length - 1];
        for (int i = 0; i < length - 1; i++) {
            double s = 0.D;
            for (int j = 0; j < basis + 1; j++) {
                s += coef_n[j] * Math.pow(xc_n[i], j);
            }
            func_n[i] = s;
        }
        System.out.println(3);

        LineChart1 demo = new LineChart1();
        XYDataset dataset = demo.createDataset(x, y, func_o, func_n, xc_n, length);
        demo.drawChart(dataset);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
        System.out.println(4);
    }


    static class LineChart1 extends JFrame {
        public void drawChart(XYDataset dataset) {
            JFreeChart chart = createChart(dataset);
            ChartPanel chartPanel = new ChartPanel(chart);

            chartPanel.setPreferredSize(new Dimension(480, 512));
            setContentPane(chartPanel);
        }
        JFreeChart createChart(XYDataset dataset) {
            JFreeChart chart = ChartFactory.createXYLineChart(
                    "Ordinary Least Square",
                    "X",
                    "Y",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );
            chart.setBackgroundPaint(Color.lightGray);
            XYPlot plot = chart.getXYPlot();
            plot.setBackgroundPaint(new Color(0xD2CCD6));
            plot.setAxisOffset(new RectangleInsets(1.0, 1.0, 1.0, 1.0));
            ValueAxis axis = plot.getDomainAxis();
            axis.setAxisLineVisible(false);

            XYSplineRenderer renderer = new XYSplineRenderer();
            renderer.setPrecision(7);
            renderer.setSeriesLinesVisible(0, false);
            renderer.setSeriesPaint(0, new Color(0x4dfed1));
            renderer.setSeriesPaint(1, new Color(255, 255, 0));
            renderer.setSeriesStroke(1, new BasicStroke(2.5f));
            renderer.setSeriesPaint(2, new Color(255, 150, 190));
            renderer.setSeriesStroke(2, new BasicStroke(2.5f));
            plot.setRenderer(0, renderer);

            plot.setRenderer(1, renderer);
            plot.setRenderer(2, renderer);
            return chart;
        }

        XYDataset createDataset(double[] x, double[] y, double[] func_o, double[] func_n, double[] x_n, int n) {
            XYSeries begin = new XYSeries("User data");
            for (int i = 0; i < n; i++) {
                begin.add(x[i], y[i]);
            }
            XYSeries first = new XYSeries("First approximation");
            for (int i = 0; i < n; i++) {
                first.add(x[i], func_o[i]);
            }
            XYSeries sec = new XYSeries("Second approximation");
            for (int i = 0; i < n - 1; i++) {
                sec.add(x_n[i], func_n[i]);
            }
            XYSeriesCollection dataset = new XYSeriesCollection();
            dataset.addSeries(begin);
            dataset.addSeries(first);
            dataset.addSeries(sec);
            return dataset;
        }

    }
}

