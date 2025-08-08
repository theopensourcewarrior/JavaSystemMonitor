package JavaSystemMonitor.GUI;

import JavaSystemMonitor.Constants;
import com.sun.management.OperatingSystemMXBean;
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class CPU_Usage extends javax.swing.JPanel
{

    private static final long serialVersionUID = 1L;

    private static final int HISTORY_SECONDS = 60;

    private transient XYChart chart;

    private final Timer timer;

    private final transient List<Double> xData = new ArrayList<>();

    private final transient List<List<Double>> cpuData = new ArrayList<>();

    private int coreCount;

    private long[] prevTotal;

    private long[] prevIdle;

    @SuppressWarnings("this-escape")
    public CPU_Usage()
    {
        initComponents();

        initializeCpuStats();

        addChartToPanel(Constants.DEFAULT_HEIGHT, Constants.DEFAULT_WIDTH);

        timer = new Timer(Constants.UPDATE_RATE_MS, e -> updateChart());
        timer.setInitialDelay(0);
    }

    private void initializeCpuStats()
    {
        final String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("linux"))
        {
            coreCount = Runtime.getRuntime().availableProcessors();
            prevTotal = new long[coreCount];
            prevIdle = new long[coreCount];
            readLinuxCpuStats(prevTotal, prevIdle);
        }
        else
        {
            coreCount = 1;
            prevTotal = new long[1];
            prevIdle = new long[1];
        }

        for (int i = 0; i < coreCount; i++)
        {
            cpuData.add(new ArrayList<>());
        }
    }

    private void addChartToPanel(int height, int width)
    {
        setLayout(new BorderLayout());

        chart = new XYChart(width, height);
        chart.setYAxisTitle("CPU Usage (%)");
        chart.setXAxisTitle("Seconds");

        for (int i = 0; i < coreCount; i++)
        {
            chart.addSeries("Core " + i, new double[]
                    {
                        0
            }, new double[]
                    {
                        0
            }).setMarker(SeriesMarkers.NONE);
        }

        final XChartPanel<XYChart> chartPanel = new XChartPanel<>(chart);
        add(chartPanel, BorderLayout.CENTER);
    }

    private void updateChart()
    {
        final double[] loads = getCpuLoads();

        for (int i = 0; i < loads.length; i++)
        {
            cpuData.get(i).add(loads[i] * 100);
            if (cpuData.get(i).size() > HISTORY_SECONDS)
            {
                cpuData.get(i).remove(0);
            }
        }

        xData.clear();
        for (int i = 0; i < cpuData.get(0).size(); i++)
        {
            xData.add((double) i);
        }

        for (int i = 0; i < coreCount; i++)
        {
            chart.updateXYSeries("Core " + i, xData, cpuData.get(i), null);
        }

        revalidate();
        repaint();
    }

    private double[] getCpuLoads()
    {
        final String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("linux"))
        {
            return getCpuLoadsLinux();
        }

        final OperatingSystemMXBean mbean
                = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        final double load = Math.min(1.0, Math.max(0.0, mbean.getCpuLoad()));
        return new double[]
        {
            load
        };
    }

    private double[] getCpuLoadsLinux()
    {
        final long[] totals = new long[coreCount];
        final long[] idles = new long[coreCount];

        readLinuxCpuStats(totals, idles);

        final double[] loads = new double[coreCount];
        for (int i = 0; i < coreCount; i++)
        {
            final long total = totals[i] - prevTotal[i];
            final long idle = idles[i] - prevIdle[i];
            if (total > 0)
            {
                loads[i] = (double) (total - idle) / total;
            }
            else
            {
                loads[i] = 0;
            }
            prevTotal[i] = totals[i];
            prevIdle[i] = idles[i];
        }
        return loads;
    }

    private void readLinuxCpuStats(long[] totals, long[] idles)
    {
        try (BufferedReader br = new BufferedReader(new FileReader("/proc/stat")))
        {
            String line;
            int index = 0;
            while ((line = br.readLine()) != null && index < totals.length)
            {
                line = line.trim();
                if (!line.startsWith("cpu") || line.startsWith("cpu "))
                {
                    continue;
                }
                final String[] tokens = line.split("\\s+");
                if (tokens.length < 8)
                {
                    continue;
                }
                final long user = parseLong(tokens[1]);
                final long nice = parseLong(tokens[2]);
                final long system = parseLong(tokens[3]);
                final long idle = parseLong(tokens[4]);
                final long iowait = parseLong(tokens[5]);
                final long irq = parseLong(tokens[6]);
                final long softirq = parseLong(tokens[7]);
                final long steal = tokens.length > 8 ? parseLong(tokens[8]) : 0;
                totals[index] = user + nice + system + idle + iowait + irq + softirq + steal;
                idles[index] = idle + iowait;
                index++;
            }
        }
        catch (IOException ignored)
        {
        }
    }

    private long parseLong(String s)
    {
        try
        {
            return Long.parseLong(s);
        }
        catch (NumberFormatException e)
        {
            return 0;
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
    private void initComponents()
    {

        setBorder(javax.swing.BorderFactory.createTitledBorder("CPU Utilization"));

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

