package JavaSystemMonitor.GUI.Disk;

import JavaSystemMonitor.Constants;
import java.awt.BorderLayout;
import java.io.IOException;
import java.nio.file.FileStore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.PieStyler;

public class DiskEntry extends JPanel {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("this-escape")
    public DiskEntry(FileStore store) {
        initComponents();
        addChartToPanel(Constants.DEFAULT_HEIGHT / 2, Constants.DEFAULT_WIDTH / 2, store);
    }

    private void addChartToPanel(int height, int width, FileStore store) {
        setLayout(new BorderLayout());

        PieChart chart = new PieChart(width, height);
        chart.setTitle(store.name());
        chart.getStyler().setLegendVisible(true);
        chart.getStyler().setLabelType(PieStyler.LabelType.NameAndValue);
        chart.getStyler().setDecimalPattern("#0.0 GB");

        try {
            long totalSpace = store.getTotalSpace();
            long freeSpace = store.getUsableSpace();
            long usedSpace = totalSpace - freeSpace;

            double usedSpaceGb = (double) usedSpace / Constants.BYTES_TO_GIGABYTES;
            double freeSpaceGb = (double) freeSpace / Constants.BYTES_TO_GIGABYTES;

            chart.addSeries("Used", usedSpaceGb);
            chart.addSeries("Free", freeSpaceGb);
        } catch (IOException ex) {
            Logger.getLogger(DiskEntry.class.getName()).log(Level.SEVERE, null, ex);
        }

        XChartPanel<PieChart> chartPanel = new XChartPanel<>(chart);
        add(chartPanel, BorderLayout.CENTER);
    }

    @SuppressWarnings({"unchecked", "this-escape"})
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
    }
}
