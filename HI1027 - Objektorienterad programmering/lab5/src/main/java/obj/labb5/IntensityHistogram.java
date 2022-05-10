/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj.labb5;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 *
 * @author Pierre Le Fevre (pierrelf@kth.se) and Emil Karlsson (emilk2@kth.se)
 */
public class IntensityHistogram
{

    /**
     * Creates a LineChart object with relevant histogram data
     *
     * @param pixels pixels to be processed to histogram
     * @return LineChart object
     */
    static public LineChart generate(int[] pixels)
    {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        XYChart.Series rSeries = new XYChart.Series();
        XYChart.Series gSeries = new XYChart.Series();
        XYChart.Series bSeries = new XYChart.Series();

        rSeries.setName("Red");
        gSeries.setName("Green");
        bSeries.setName("Blue");

        final int intensityArraySize = 256;
        int[] rIntensity = new int[intensityArraySize];
        int[] gIntensity = new int[intensityArraySize];
        int[] bIntensity = new int[intensityArraySize];

        for (var pixel : pixels)
        {
            int r = ((pixel >> 16) & 0xff);
            int g = ((pixel >> 8) & 0xff);
            int b = ((pixel >> 0) & 0xff);

            rIntensity[r]++;
            gIntensity[g]++;
            bIntensity[b]++;
        }

        for (int i = 0; i < intensityArraySize; i++)
        {
            rSeries.getData().add(new XYChart.Data(i, rIntensity[i]));
            gSeries.getData().add(new XYChart.Data(i, gIntensity[i]));
            bSeries.getData().add(new XYChart.Data(i, bIntensity[i]));
        }

        LineChart lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.getData().add(rSeries);
        lineChart.getData().add(gSeries);
        lineChart.getData().add(bSeries);

        return lineChart;
    }

}
