package JavaSystemMonitor.GUI;

public class Main_GUI extends javax.swing.JFrame
{

    public Main_GUI()
    {
        initComponents();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JTabbedPane tabs = new javax.swing.JTabbedPane();
        CPU_UsagePanel = new JavaSystemMonitor.GUI.CPU_Usage();
        memoryUsagePanel = new JavaSystemMonitor.GUI.MemoryUsage();
        diskUsage1 = new JavaSystemMonitor.GUI.Disk.DiskUsage();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java System Monitor");

        tabs.addTab("CPU Usage", CPU_UsagePanel);
        tabs.addTab("Memory Usage", memoryUsagePanel);
        tabs.addTab("Disk Usage", diskUsage1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JavaSystemMonitor.GUI.CPU_Usage CPU_UsagePanel;
    private JavaSystemMonitor.GUI.Disk.DiskUsage diskUsage1;
    private JavaSystemMonitor.GUI.MemoryUsage memoryUsagePanel;
    // End of variables declaration//GEN-END:variables

}
