package JavaSystemMonitor.GUI;

import JavaSystemMonitor.Constants;
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class NetworkUsage extends javax.swing.JPanel
{

    private transient XYChart chart;

    private final Timer timer;

    private final transient List<Double> xData = new ArrayList<>();
    private final transient List<Double> downloadData = new ArrayList<>();
    private final transient List<Double> uploadData = new ArrayList<>();

    private long previousBytesRecv;
    private long previousBytesSent;
    private int time = 0;

    public NetworkUsage()
    {
        initComponents();

        addChartToPanel(Constants.DEFAULT_HEIGHT, Constants.DEFAULT_WIDTH);

        final long[] totals = readNetworkTotals();
        previousBytesRecv = totals[0];
        previousBytesSent = totals[1];

        timer = new Timer(Constants.UPDATE_RATE_MS, e -> updateChart());
        timer.setInitialDelay(0);
    }

    private void addChartToPanel(int height, int width)
    {
        setLayout(new BorderLayout());

        chart = new XYChart(width, height);
        chart.addSeries("Download KB/s", new double[]
                {
                    0
        }, new double[]
                {
                    0
        }).setMarker(SeriesMarkers.NONE);
        chart.addSeries("Upload KB/s", new double[]
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
        final long[] totals = readNetworkTotals();
        final long recv = totals[0];
        final long sent = totals[1];

        final long deltaRecv = recv - previousBytesRecv;
        final long deltaSent = sent - previousBytesSent;

        previousBytesRecv = recv;
        previousBytesSent = sent;

        time++;
        xData.add((double) time);
        downloadData.add(deltaRecv / 1024.0); // KB/s
        uploadData.add(deltaSent / 1024.0);   // KB/s

        final int maxPoints = 60;
        if (xData.size() > maxPoints)
        {
            xData.remove(0);
            downloadData.remove(0);
            uploadData.remove(0);
        }

        chart.updateXYSeries("Download KB/s", xData, downloadData, null);
        chart.updateXYSeries("Upload KB/s", xData, uploadData, null);

        revalidate();
        repaint();
    }

    private long[] readNetworkTotals()
    {
        final String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("linux"))
        {
            return readLinuxTotals();
        }
        else if (os.contains("mac") || os.contains("darwin"))
        {
            return readMacTotals();
        }
        else if (os.contains("win"))
        {
            return readWindowsTotals();
        }
        return new long[]
        {
            0, 0
        };
    }

    private long[] readLinuxTotals()
    {
        long rx = 0;
        long tx = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("/proc/net/dev")))
        {
            br.readLine();
            br.readLine();
            String line;
            while ((line = br.readLine()) != null)
            {
                line = line.trim();
                if (line.isEmpty())
                {
                    continue;
                }
                final String[] parts = line.split(":");
                if (parts.length < 2)
                {
                    continue;
                }
                final String[] tokens = parts[1].trim().split("\\s+");
                if (tokens.length >= 9)
                {
                    rx += parseLong(tokens[0]);
                    tx += parseLong(tokens[8]);
                }
            }
        }
        catch (IOException ignored)
        {
        }
        return new long[]
        {
            rx, tx
        };
    }

    private long[] readMacTotals()
    {
        long rx = 0;
        long tx = 0;
        final ProcessBuilder pb = new ProcessBuilder("netstat", "-ibn");
        try
        {
            final Process p = pb.start();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream())))
            {
                br.readLine(); // header
                String line;
                while ((line = br.readLine()) != null)
                {
                    line = line.trim();
                    if (line.isEmpty())
                    {
                        continue;
                    }
                    final String[] tokens = line.split("\\s+");
                    if (tokens.length >= 11)
                    {
                        rx += parseLong(tokens[9]);
                        tx += parseLong(tokens[10]);
                    }
                }
            }
        }
        catch (IOException ignored)
        {
        }
        return new long[]
        {
            rx, tx
        };
    }

    private long[] readWindowsTotals()
    {
        long rx = 0;
        long tx = 0;
        final ProcessBuilder pb = new ProcessBuilder("netstat", "-e");
        pb.redirectErrorStream(true);
        try
        {
            final Process p = pb.start();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream())))
            {
                String line;
                while ((line = br.readLine()) != null)
                {
                    line = line.trim();
                    if (line.startsWith("Bytes"))
                    {
                        final String[] tokens = line.split("\\s+");
                        if (tokens.length >= 3)
                        {
                            rx = parseLong(tokens[1]);
                            tx = parseLong(tokens[2]);
                            break;
                        }
                    }
                }
            }
        }
        catch (IOException ignored)
        {
        }
        return new long[]
        {
            rx, tx
        };
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

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

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
