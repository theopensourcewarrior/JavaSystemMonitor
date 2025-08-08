package JavaSystemMonitor.GUI;

import JavaSystemMonitor.GUI.Disk.DiskUsage;
import java.awt.GridLayout;

public class Main_GUI extends javax.swing.JFrame
{

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("this-escape")
    public Main_GUI()
    {
        initComponents();

        setLayout(new GridLayout(2, 2));
        add(new CPU_Usage());
        add(new MemoryUsage());
        add(new NetworkUsage());
        add(new DiskUsage());

    }

    @SuppressWarnings(
            {
                "unchecked", "this-escape"
            })
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java System Monitor");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 780, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 504, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
