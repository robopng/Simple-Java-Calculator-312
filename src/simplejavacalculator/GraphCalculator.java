package simplejavacalculator;

import org.knowm.xchart.*;

import javax.swing.*;
import java.util.ArrayList;

public class GraphCalculator {
    JFrame graph;
    ArrayList<Double> xData;
    ArrayList<Double> yData;
    XYSeries.XYSeriesRenderStyle style;
    XYChart chart;
    String seriesName;
    int numSeries;

    public GraphCalculator() {
        xData = new ArrayList<>(10);
        yData = new ArrayList<>(10);
        style = XYSeries.XYSeriesRenderStyle.Line;
        chart = new XYChartBuilder()
                .width(600)
                .height(400)
                .xAxisTitle("X")
                .yAxisTitle("Y")
                .build();
        numSeries = 0;
        seriesName = String.format("Data %d", numSeries);
    }

    public void load(String coords) {
        String[] coordVals = coords.split(",");
        xData.add(Double.valueOf(coordVals[0]));
        yData.add(Double.valueOf(coordVals[1]));
        System.out.printf("Added %s,%s\n", coordVals[0], coordVals[1]);
    }

    public void commitSeries() {
        seriesName = String.format("Data %d", ++numSeries);
        chart.addSeries(seriesName, xData, yData);

        xData.clear();
        yData.clear();
    }

    public void go() {
        this.commitSeries();

        graph = new SwingWrapper<>(chart).displayChart();
        graph.setAlwaysOnTop(true);
        graph.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    public void reset() {
        for (int i = 0; i <= numSeries; i++)
            chart.removeSeries(String.format("Data %d", i));
        numSeries = 0;
    }

    public void setScatter() {
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
    }

    public void setLine() {
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
    }
}
