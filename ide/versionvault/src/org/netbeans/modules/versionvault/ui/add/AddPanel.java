/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
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
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
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

package org.netbeans.modules.versionvault.ui.add;

import java.awt.BorderLayout;
import org.netbeans.modules.versionvault.ClearcaseModuleConfig;
import org.netbeans.modules.versioning.util.StringSelector;
import org.netbeans.modules.versioning.util.Utils;
import org.openide.util.NbBundle;

/**
 * Add To Clearcase customization panel.
 *
 * @author Maros Sandor
 */
class AddPanel extends javax.swing.JPanel {
    
    private AddTable addTable;

    /** Creates new form AddPanel */
    public AddPanel() {
        initComponents();
    }

    public void setAddTable(AddTable addTable) {
        this.addTable = addTable;
        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(addTable.getComponent());
        revalidate();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        bRecentMessages = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        taMessage = new javax.swing.JTextArea();
        cbSuppressCheckout = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(AddPanel.class, "AddPanel.jLabel2.text")); // NOI18N

        jLabel1.setLabelFor(taMessage);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(AddPanel.class, "AddPanel.jLabel1.text")); // NOI18N

        bRecentMessages.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/modules/versionvault/resources/icons/recent_messages.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(bRecentMessages, org.openide.util.NbBundle.getMessage(AddPanel.class, "AddPanel.bRecentMessages.text")); // NOI18N
        bRecentMessages.setToolTipText(org.openide.util.NbBundle.getMessage(AddPanel.class, "AddPanel.bRecentMessages.toolTipText")); // NOI18N
        bRecentMessages.setBorderPainted(false);
        bRecentMessages.setMargin(new java.awt.Insets(0, 0, 0, 0));
        bRecentMessages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRecentMessagesActionPerformed(evt);
            }
        });

        taMessage.setColumns(20);
        taMessage.setRows(4);
        taMessage.setToolTipText(org.openide.util.NbBundle.getMessage(AddPanel.class, "AddPanel.taMessage.toolTipText")); // NOI18N
        jScrollPane1.setViewportView(taMessage);

        org.openide.awt.Mnemonics.setLocalizedText(cbSuppressCheckout, org.openide.util.NbBundle.getMessage(AddPanel.class, "AddPanel.cbSuppressCheckout.text")); // NOI18N
        cbSuppressCheckout.setToolTipText(org.openide.util.NbBundle.getMessage(AddPanel.class, "AddPanel.cbSuppressCheckout.toolTipText")); // NOI18N
        cbSuppressCheckout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSuppressCheckoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 725, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 194, Short.MAX_VALUE)
        );

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(AddPanel.class, "AddPanel.jLabel3.text")); // NOI18N

        barPanel.setMaximumSize(new java.awt.Dimension(170, 10));
        barPanel.setPreferredSize(new java.awt.Dimension(170, 10));
        barPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout progressPanelLayout = new javax.swing.GroupLayout(progressPanel);
        progressPanel.setLayout(progressPanelLayout);
        progressPanelLayout.setHorizontalGroup(
            progressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(progressPanelLayout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(barPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
        );
        progressPanelLayout.setVerticalGroup(
            progressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 15, Short.MAX_VALUE)
            .addComponent(barPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbSuppressCheckout)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 255, Short.MAX_VALUE)
                        .addComponent(progressPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 554, Short.MAX_VALUE)
                        .addComponent(bRecentMessages))
                    .addComponent(jLabel2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1)
                    .addComponent(bRecentMessages))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbSuppressCheckout)
                        .addGap(7, 7, 7))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(progressPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
    }// </editor-fold>//GEN-END:initComponents

private void cbSuppressCheckoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSuppressCheckoutActionPerformed
    // ignore
}//GEN-LAST:event_cbSuppressCheckoutActionPerformed

private void bRecentMessagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRecentMessagesActionPerformed
    onBrowseRecentMessages();
}//GEN-LAST:event_bRecentMessagesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton bRecentMessages;
    final javax.swing.JPanel barPanel = new javax.swing.JPanel();
    javax.swing.JCheckBox cbSuppressCheckout;
    javax.swing.JLabel jLabel1;
    javax.swing.JLabel jLabel2;
    javax.swing.JLabel jLabel3;
    javax.swing.JPanel jPanel1;
    javax.swing.JScrollPane jScrollPane1;
    final javax.swing.JPanel progressPanel = new javax.swing.JPanel();
    javax.swing.JTextArea taMessage;
    // End of variables declaration//GEN-END:variables

    private void onBrowseRecentMessages() {
        String message = StringSelector.select(NbBundle.getMessage(AddPanel.class, "CTL_AddForm_RecentTitle"), 
                                               NbBundle.getMessage(AddPanel.class, "CTL_AddForm_RecentPrompt"), 
                                               Utils.getStringList(ClearcaseModuleConfig.getPreferences(), AddAction.RECENT_ADD_MESSAGES));
        if (message != null) {
            taMessage.replaceSelection(message);
        }
    }
    
}
