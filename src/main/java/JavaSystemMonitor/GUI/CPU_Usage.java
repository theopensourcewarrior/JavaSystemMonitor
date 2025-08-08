package JavaSystemMonitor.GUI;

import JavaSystemMonitor.Constants;
import com.sun.management.OperatingSystemMXBean;
import java.awt.BorderLayout;
import java.lang.management.ManagementFactory;
import javax.swing.Timer;
import org.knowm.xchart.DialChart;
import org.knowm.xchart.XChartPanel;

public class CPU_Usage extends javax.swing.JPanel
{

    private static final String CPU_USAGE_TITLE = "CPU Usage";

    private DialChart dial;
    private final Timer timer;

    public CPU_Usage()
    {
        initComponents();

        addChartToPanel(Constants.DEFAULT_HEIGHT, Constants.DEFAULT_WIDTH);

        timer = new Timer(Constants.UPDATE_RATE_MS, e -> updateChart());
        timer.setInitialDelay(0);
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

        final double CPU_Load = Math.min(1.0, Math.max(0.0, mbean.getSystemCpuLoad()));

        dial.removeSeries(CPU_USAGE_TITLE);
        dial.addSeries(CPU_USAGE_TITLE, CPU_Load);

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
