package JavaSystemMonitor.GUI;

import JavaSystemMonitor.Constants;
import com.sun.management.OperatingSystemMXBean;
import java.awt.BorderLayout;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class MemoryUsage extends javax.swing.JPanel
{

    private static final long serialVersionUID = 1L;

    private static final int HISTORY_SECONDS = 60;

    private transient XYChart chart;

    private final Timer timer;

    private final transient List<Double> xData = new ArrayList<>();

    private final transient List<Double> usedData = new ArrayList<>();

    private final transient List<Double> totalData = new ArrayList<>();

    @SuppressWarnings("this-escape")
    public MemoryUsage()
    {
        initComponents();

        addChartToPanel(Constants.DEFAULT_HEIGHT, Constants.DEFAULT_WIDTH);

        timer = new Timer(Constants.UPDATE_RATE_MS, e -> updateChart());
        timer.setInitialDelay(0);
    }

    private void addChartToPanel(int height, int width)
    {
        setLayout(new BorderLayout());

        chart = new XYChart(width, height);
        chart.setYAxisTitle("Memory");
        chart.setXAxisTitle("Seconds");
        chart.getStyler().setLegendVisible(false);
        chart.addSeries("Used", new double[]
                {
                    0
        }, new double[]
                {
                    0
        }).setMarker(SeriesMarkers.NONE);
        chart.addSeries("Total", new double[]
                {
                    0
        }, new double[]
                {
                    0
        }).setMarker(SeriesMarkers.NONE);

        final XChartPanel<XYChart> chartPanel = new XChartPanel<>(chart);
        add(chartPanel, BorderLayout.CENTER);
    }

    private void updateChart()
    {
        final OperatingSystemMXBean mbean
                = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        final long totalMemory = mbean.getTotalMemorySize();
        final long freeMemory = mbean.getFreeMemorySize();
        final long usedMemory = totalMemory - freeMemory;

        usedData.add((double) usedMemory);
        totalData.add((double) totalMemory);
        if (usedData.size() > HISTORY_SECONDS)
        {
            usedData.remove(0);
            totalData.remove(0);
        }

        xData.clear();
        for (int i = 0; i < usedData.size(); i++)
        {
            xData.add((double) i);
        }

        double scale;
        String unit;
        if (totalMemory >= Constants.BYTES_TO_GIGABYTES)
        {
            scale = Constants.BYTES_TO_GIGABYTES;
            unit = "GB";
        }
        else
        {
            scale = Constants.BYTES_TO_MEGABYTES;
            unit = "MB";
        }

        final List<Double> scaledUsed = new ArrayList<>(usedData.size());
        final List<Double> scaledTotal = new ArrayList<>(totalData.size());
        for (int i = 0; i < usedData.size(); i++)
        {
            scaledUsed.add(usedData.get(i) / scale);
            scaledTotal.add(totalData.get(i) / scale);
        }

        chart.setYAxisTitle("Memory (" + unit + ")");
        chart.updateXYSeries("Used", xData, scaledUsed, null);
        chart.updateXYSeries("Total", xData, scaledTotal, null);

        revalidate();
        repaint();
    }

    @Override
    public void addNotify()
    {
        super.addNotify();
        timer.start();
    }

    @Override
    public void removeNotify()
    {
        timer.stop();
        super.removeNotify();
    }

    @SuppressWarnings(
            {
                "unchecked", "this-escape"
            })
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        setBorder(javax.swing.BorderFactory.createTitledBorder("Memory Utilization"));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 390, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 275, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

