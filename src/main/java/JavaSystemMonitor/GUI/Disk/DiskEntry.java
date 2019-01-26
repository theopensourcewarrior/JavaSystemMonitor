package JavaSystemMonitor.GUI.Disk;

import java.io.IOException;
import java.nio.file.FileStore;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiskEntry extends javax.swing.JPanel
{

    public DiskEntry(FileStore store)
    {
        initComponents();

        diskName.setText(store.name());

        try
        {
            final long totalSpace = store.getTotalSpace();

            final long unUsedSpace = store.getUnallocatedSpace();

            final long usedSpace = totalSpace - unUsedSpace;

            final double percentUsed = 100.0 * (usedSpace / (double) totalSpace);

            usageMeter.setValue((int) percentUsed);

            usageMeter.setStringPainted(true);

            NumberFormat nf = NumberFormat.getPercentInstance();

            usageMeter.setString(nf.format(percentUsed / 100.0));
        }
        catch (IOException ex)
        {
            Logger.getLogger(DiskEntry.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        diskName = new javax.swing.JLabel();
        usageMeter = new javax.swing.JProgressBar();

        jLabel1.setText("Disk:");

        diskName.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usageMeter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(diskName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(diskName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(usageMeter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel diskName;
    private javax.swing.JProgressBar usageMeter;
    // End of variables declaration//GEN-END:variables
}
