package JavaSystemMonitor.GUI;

import JavaSystemMonitor.Constants;
import com.sun.management.OperatingSystemMXBean;
import java.awt.BorderLayout;
import java.awt.Color;
import java.lang.management.ManagementFactory;
import javax.swing.Timer;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.PieStyler;

public class MemoryUsage extends javax.swing.JPanel
{

    private static final long serialVersionUID = 1L;

    private static final String FREE_MEMORY = "Free Memory";

    private static final String USED_MEMORY = "Used Memory";

    private transient PieChart memoryPieChart;

    private final Timer timer;

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

        memoryPieChart = new PieChart(width, height);

        memoryPieChart.getStyler().setLabelType(PieStyler.LabelType.Name);

        final Color[] colors =
        {
            Color.green, Color.red
        };

        memoryPieChart.getStyler().setSeriesColors(colors);

        memoryPieChart.addSeries(FREE_MEMORY, 0);
        memoryPieChart.addSeries(USED_MEMORY, 0);

        final XChartPanel<PieChart> chartPanel = new XChartPanel<>(memoryPieChart);

        add(chartPanel, BorderLayout.CENTER);

        updateChart();

    }

    private void updateChart()
    {
        final OperatingSystemMXBean mbean
                = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        final long totalMemory = mbean.getTotalMemorySize();

        final long freeMemory = mbean.getFreeMemorySize();

        final long usedMemory = totalMemory - freeMemory;

        memoryPieChart.updatePieSeries(USED_MEMORY, usedMemory);
        memoryPieChart.updatePieSeries(FREE_MEMORY, freeMemory);

        memoryPieChart.getSeriesMap().get(USED_MEMORY).setLabel(
                String.format("%s: %s", USED_MEMORY, formatMemory(usedMemory)));
        memoryPieChart.getSeriesMap().get(FREE_MEMORY).setLabel(
                String.format("%s: %s", FREE_MEMORY, formatMemory(freeMemory)));

        revalidate();
        repaint();
    }

    private String formatMemory(long bytes)
    {
        if (bytes >= Constants.BYTES_TO_GIGABYTES)
        {
            return String.format("%.2f GB", bytes / (double) Constants.BYTES_TO_GIGABYTES);
        }
        else
        {
            return String.format("%.2f MB", bytes / (double) Constants.BYTES_TO_MEGABYTES);
        }
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
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
