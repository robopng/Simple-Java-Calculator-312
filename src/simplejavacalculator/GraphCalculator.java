/**
 * @name Simple Java Calculator
 * @package ph.calculator
 * @file GraphCalculator.java
 * @author Liam Wilson
 * @author Celeste Payton
 */

package simplejavacalculator;

import org.knowm.xchart.*;

import javax.swing.*;
import java.util.ArrayList;

/**
 * A graphing calculator with functionality for mapping user inputted coordinates
 * as one of several different forms of graph.
 * ///////////////////////////////////////////////////////
 * This is an entirely new class, no part of which existed before project adoption.
 * This class heavily leverages Xchart, an open source wrapper for swing.
 * The project can be found here: https://github.com/knowm/XChart
 */
public class GraphCalculator {
    private final ArrayList<Double> xData;
    private final ArrayList<Double> yData;
    private final XYChart chart;
    private String seriesName;
    private int numSeries;

    public GraphCalculator() {
        xData = new ArrayList<>(10);
        yData = new ArrayList<>(10);
        chart = new XYChartBuilder()
                .width(600)
                .height(400)
                .xAxisTitle("X")
                .yAxisTitle("Y")
                .build();
        numSeries = 0;
        seriesName = String.format("Data %d", numSeries);
    }

    /**
     * Load a set of coordinates into the GraphCalculator instance's current series.
     * @param coords the coordinate pair to be added, represented as X,Y
     */
    public void load(String coords) {
        String[] coordVals = coords.split(",");
        xData.add(Double.valueOf(coordVals[0]));
        yData.add(Double.valueOf(coordVals[1]));
        System.out.printf("Added %s,%s\n", coordVals[0], coordVals[1]);
    }

    /**
     * Finalize the construction of the GraphCalculator instance's current series, and
     * reset all series-specific variables for construction of another.
     */
    public void commitSeries() {
        seriesName = String.format("Data %d", ++numSeries);
        chart.addSeries(seriesName, xData, yData);

        xData.clear();
        yData.clear();
    }

    /**
     * Display all the GraphCalculator instance's currently held series.
     */
    public void go() {
        this.commitSeries();

        JFrame graph = new SwingWrapper<>(chart).displayChart();
        graph.setAlwaysOnTop(true);
        graph.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Remove all the GraphCalculator instance's currently held series.
     */
    public void reset() {
        for (int i = 0; i <= numSeries; i++)
            chart.removeSeries(String.format("Data %d", i));
        numSeries = 0;
    }

    /**
     * Set the GraphCalculator instance's preferred graph type to a scatter plot.
     */
    public void setScatter() {
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
    }

    /**
     * Set the GraphCalculator instance's preferred graph type to a line graph.
     */
    public void setLine() {
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
    }
}
