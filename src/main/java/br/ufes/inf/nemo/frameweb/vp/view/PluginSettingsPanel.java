package br.ufes.inf.nemo.frameweb.vp.view;

import java.util.logging.Level;
import com.vp.plugin.view.IDialog;
import br.ufes.inf.nemo.frameweb.vp.FrameWebPlugin;
import br.ufes.inf.nemo.vpzy.logging.Logger;
import br.ufes.inf.nemo.vpzy.managers.ConfigurationManager;

public class PluginSettingsPanel extends javax.swing.JPanel {
  /** The dialog to which this panel serves as contents. */
  private IDialog containerDialog;

  /** Instance of the FrameWeb plug-in running. */
  private FrameWebPlugin plugin = FrameWebPlugin.instance();

  /** Provides the configuration values and lets us change them. */
  private ConfigurationManager configManager = plugin.getConfigManager();

  public PluginSettingsPanel() {
    initComponents();
    readConfig();
  }

  public void setContainerDialog(IDialog containerDialog) {
    this.containerDialog = containerDialog;
  }

  /**
   * This method is called from within the constructor to initialize the form. The code was
   * generated by NetBeans.
   */
  private void initComponents() {
    tabbedPane = new javax.swing.JTabbedPane();
    loggingPanel = new javax.swing.JPanel();
    logginLevelLabel = new javax.swing.JLabel();
    loggingLevelComboBox = new javax.swing.JComboBox<>();
    buttonsPanel = new javax.swing.JPanel();
    cancelButton = new javax.swing.JButton();
    okButton = new javax.swing.JButton();

    setLayout(new java.awt.BorderLayout());

    logginLevelLabel.setText("Logging level:");

    loggingLevelComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"ALL",
        "FINEST", "FINER", "FINE", "CONFIG", "INFO", "WARNING", "SEVERE", "OFF"}));

    javax.swing.GroupLayout loggingPanelLayout = new javax.swing.GroupLayout(loggingPanel);
    loggingPanel.setLayout(loggingPanelLayout);
    loggingPanelLayout.setHorizontalGroup(
        loggingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loggingPanelLayout.createSequentialGroup().addContainerGap()
                .addComponent(logginLevelLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(loggingLevelComboBox, 0, 356, Short.MAX_VALUE).addContainerGap()));
    loggingPanelLayout.setVerticalGroup(loggingPanelLayout
        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(loggingPanelLayout.createSequentialGroup().addContainerGap()
            .addGroup(loggingPanelLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(logginLevelLabel).addComponent(loggingLevelComboBox,
                    javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(249, Short.MAX_VALUE)));

    tabbedPane.addTab("Logging", loggingPanel);

    add(tabbedPane, java.awt.BorderLayout.CENTER);

    buttonsPanel.setPreferredSize(new java.awt.Dimension(454, 40));

    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cancelButtonActionPerformed(evt);
      }
    });

    okButton.setText("OK");
    okButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        okButtonActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout buttonsPanelLayout = new javax.swing.GroupLayout(buttonsPanel);
    buttonsPanel.setLayout(buttonsPanelLayout);
    buttonsPanelLayout.setHorizontalGroup(
        buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
            javax.swing.GroupLayout.Alignment.TRAILING,
            buttonsPanelLayout.createSequentialGroup().addContainerGap(298, Short.MAX_VALUE)
                .addComponent(okButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton).addContainerGap()));
    buttonsPanelLayout.setVerticalGroup(
        buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
            javax.swing.GroupLayout.Alignment.TRAILING,
            buttonsPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(buttonsPanelLayout
                    .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton).addComponent(okButton))
                .addContainerGap()));

    add(buttonsPanel, java.awt.BorderLayout.PAGE_END);
  }

  /** Read the configuration values and set them as initial value for the form fields. */
  private void readConfig() {
    loggingLevelComboBox
        .setSelectedItem(configManager.getProperty(FrameWebPlugin.CONFIG_LOGGING_LEVEL));
  }

  private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
    // Change the logging configuration.
    String loggingLevel = loggingLevelComboBox.getSelectedItem().toString();
    configManager.setProperty(FrameWebPlugin.CONFIG_LOGGING_LEVEL, loggingLevel);
    Logger.setLevel(Level.parse(loggingLevel));

    // Save the configuration.
    configManager.save();

    // Close the configuration dialog.
    containerDialog.close();
    plugin.setPluginSettingsDialogOpen(false);
  }

  private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
    // Close the configuration dialog.
    containerDialog.close();
    plugin.setPluginSettingsDialogOpen(false);
  }

  // Variables declaration - do not modify
  private javax.swing.JPanel buttonsPanel;
  private javax.swing.JButton cancelButton;
  private javax.swing.JLabel logginLevelLabel;
  private javax.swing.JComboBox<String> loggingLevelComboBox;
  private javax.swing.JPanel loggingPanel;
  private javax.swing.JButton okButton;
  private javax.swing.JTabbedPane tabbedPane;
  // End of variables declaration
}
