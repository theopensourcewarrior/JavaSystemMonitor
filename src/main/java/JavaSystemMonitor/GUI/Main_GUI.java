package JavaSystemMonitor.GUI;

public class Main_GUI extends javax.swing.JFrame
{

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("this-escape")
    public Main_GUI()
    {
        initComponents();

    }

    @SuppressWarnings(
            {
                "unchecked", "this-escape"
            })
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        javax.swing.JTabbedPane tabs = new javax.swing.JTabbedPane();
        CPU_UsagePanel = new JavaSystemMonitor.GUI.CPU_Usage();
        memoryUsagePanel = new JavaSystemMonitor.GUI.MemoryUsage();
        diskUsagePanel = new JavaSystemMonitor.GUI.Disk.DiskUsage();
        networkUsagePanel = new JavaSystemMonitor.GUI.NetworkUsage();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java System Monitor");

        tabs.addTab("CPU Usage", CPU_UsagePanel);
        tabs.addTab("Memory Usage", memoryUsagePanel);
        tabs.addTab("Disk Usage", diskUsagePanel);
        tabs.addTab("Network Usage", networkUsagePanel);

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
    private JavaSystemMonitor.GUI.Disk.DiskUsage diskUsagePanel;
    private JavaSystemMonitor.GUI.MemoryUsage memoryUsagePanel;
    private JavaSystemMonitor.GUI.NetworkUsage networkUsagePanel;
    // End of variables declaration//GEN-END:variables

}
