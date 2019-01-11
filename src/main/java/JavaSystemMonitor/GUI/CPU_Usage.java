package JavaSystemMonitor.GUI;

import JavaSystemMonitor.Constants;
import com.sun.management.OperatingSystemMXBean;
import java.awt.BorderLayout;
import java.lang.management.ManagementFactory;
import java.util.Timer;
import java.util.TimerTask;
import org.knowm.xchart.DialChart;
import org.knowm.xchart.XChartPanel;

public class CPU_Usage extends javax.swing.JPanel
{

    private static final String CPU_USAGE_TITLE = "CPU Usage";

    private DialChart dial;

    public CPU_Usage()
    {
        initComponents();

        addChartToPanel(Constants.DEFAULT_HEIGHT, Constants.DEFAULT_WIDTH);

        final Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                updateChart();
            }
        }, 0, Constants.UPDATE_RATE_MS);
    }

    private void addChartToPanel(int height, int width)
    {
        dial = new DialChart(width, height);

        dial.addSeries(CPU_USAGE_TITLE, 0);

        setLayout(new BorderLayout());

        final XChartPanel chartPanel = new XChartPanel(dial);

        add(chartPanel, BorderLayout.CENTER);
    }

    private void updateChart()
    {
        final OperatingSystemMXBean mbean
                = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        final double CPU_Load = mbean.getSystemCpuLoad();

        dial.removeSeries(CPU_USAGE_TITLE);
        dial.addSeries(CPU_USAGE_TITLE, CPU_Load);

        repaint();
    }

    @SuppressWarnings("unchecked")
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
