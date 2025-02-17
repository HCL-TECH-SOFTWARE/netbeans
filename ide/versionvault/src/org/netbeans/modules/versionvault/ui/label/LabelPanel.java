/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2008, 2016 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 */

/*
 * Copyright 2021 HCL America, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *    
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

/*
 * LabelPanel.java
 *
 * Created on March 11, 2008, 11:23 AM
 */

package org.netbeans.modules.versionvault.ui.label;

/**
 *
 * @author  tomas
 */
public class LabelPanel extends javax.swing.JPanel {

    /** Creates new form LabelPanel */
    public LabelPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLabel4 = new javax.swing.JLabel();

        jLabel1.setLabelFor(labelTextField);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(LabelPanel.class, "LabelPanel.jLabel1.text")); // NOI18N

        labelTextField.setText(org.openide.util.NbBundle.getMessage(LabelPanel.class, "LabelPanel.labelTextField.text")); // NOI18N
        labelTextField.setToolTipText(org.openide.util.NbBundle.getMessage(LabelPanel.class, "LabelPanel.labelTextField.toolTipText")); // NOI18N
        labelTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labelTextFieldActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(browseButton, org.openide.util.NbBundle.getMessage(LabelPanel.class, "LabelPanel.browseButton.text")); // NOI18N
        browseButton.setToolTipText(org.openide.util.NbBundle.getMessage(LabelPanel.class, "LabelPanel.browseButton.toolTipText")); // NOI18N
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        jLabel2.setLabelFor(versionTextField);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(LabelPanel.class, "LabelPanel.jLabel2.text")); // NOI18N

        versionTextField.setText(org.openide.util.NbBundle.getMessage(LabelPanel.class, "LabelPanel.versionTextField.text")); // NOI18N
        versionTextField.setToolTipText(org.openide.util.NbBundle.getMessage(LabelPanel.class, "LabelPanel.versionTextField.toolTipText")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(LabelPanel.class, "LabelPanel.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(recurseCheckBox, org.openide.util.NbBundle.getMessage(LabelPanel.class, "LabelPanel.recurseCheckBox.text")); // NOI18N
        recurseCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(LabelPanel.class, "LabelPanel.recurseCheckBox.toolTipText")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(replaceCheckBox, org.openide.util.NbBundle.getMessage(LabelPanel.class, "LabelPanel.replaceCheckBox.text")); // NOI18N
        replaceCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(LabelPanel.class, "LabelPanel.replaceCheckBox.toolTipText")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(followCheckBox, org.openide.util.NbBundle.getMessage(LabelPanel.class, "LabelPanel.followCheckBox.text")); // NOI18N
        followCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(LabelPanel.class, "LabelPanel.followCheckBox.toolTipText")); // NOI18N

        commentTextArea.setColumns(20);
        commentTextArea.setRows(4);
        commentTextArea.setToolTipText(org.openide.util.NbBundle.getMessage(LabelPanel.class, "LabelPanel.commentTextArea.toolTipText")); // NOI18N
        jScrollPane1.setViewportView(commentTextArea);

        recentMessagesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/modules/versionvault/resources/icons/recent_messages.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(recentMessagesButton, org.openide.util.NbBundle.getMessage(LabelPanel.class, "LabelPanel.recentMessagesButton.text")); // NOI18N
        recentMessagesButton.setToolTipText(org.openide.util.NbBundle.getMessage(LabelPanel.class, "LabelPanel.recentMessagesButton.toolTipText")); // NOI18N
        recentMessagesButton.setBorderPainted(false);
        recentMessagesButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        recentMessagesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recentMessagesButtonActionPerformed(evt);
            }
        });

        jLabel4.setLabelFor(commentTextArea);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(LabelPanel.class, "LabelPanel.jLabel4.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(recurseCheckBox)
                        .addGap(18, 18, 18)
                        .addComponent(replaceCheckBox)
                        .addGap(18, 18, 18)
                        .addComponent(followCheckBox))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(browseButton))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(versionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 457, Short.MAX_VALUE)
                        .addComponent(recentMessagesButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(browseButton)
                    .addComponent(labelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(versionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(recentMessagesButton)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(recurseCheckBox)
                    .addComponent(replaceCheckBox)
                    .addComponent(followCheckBox))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_browseButtonActionPerformed

private void labelTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labelTextFieldActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_labelTextFieldActionPerformed

private void recentMessagesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recentMessagesButtonActionPerformed
// ignore
}//GEN-LAST:event_recentMessagesButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    final javax.swing.JButton browseButton = new javax.swing.JButton();
    final javax.swing.JTextArea commentTextArea = new javax.swing.JTextArea();
    final javax.swing.JCheckBox followCheckBox = new javax.swing.JCheckBox();
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    final javax.swing.JTextField labelTextField = new javax.swing.JTextField();
    final javax.swing.JButton recentMessagesButton = new javax.swing.JButton();
    final javax.swing.JCheckBox recurseCheckBox = new javax.swing.JCheckBox();
    final javax.swing.JCheckBox replaceCheckBox = new javax.swing.JCheckBox();
    final javax.swing.JTextField versionTextField = new javax.swing.JTextField();
    // End of variables declaration//GEN-END:variables

}
