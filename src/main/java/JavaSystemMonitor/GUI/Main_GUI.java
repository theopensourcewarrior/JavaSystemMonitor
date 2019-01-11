package JavaSystemMonitor.GUI;

public class Main_GUI extends javax.swing.JFrame
{

    public Main_GUI()
    {
        initComponents();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        javax.swing.JTabbedPane tabs = new javax.swing.JTabbedPane();
        memoryUsagePanel = new JavaSystemMonitor.GUI.MemoryUsage();
        CPU_UsagePanel = new JavaSystemMonitor.GUI.CPU_Usage();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java System Monitor");

        tabs.addTab("Memory Usage", memoryUsagePanel);
        tabs.addTab("CPU Usage", CPU_UsagePanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JavaSystemMonitor.GUI.CPU_Usage CPU_UsagePanel;
    private JavaSystemMonitor.GUI.MemoryUsage memoryUsagePanel;
    // End of variables declaration//GEN-END:variables

}
