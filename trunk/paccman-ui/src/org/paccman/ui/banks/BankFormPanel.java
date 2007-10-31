/*
 
    Copyright (C)    2005 Joao F. (joaof@sourceforge.net)
                     http://paccman.sourceforge.net 

    This program is free software; you can redistribute it and/or modify      
    it under the terms of the GNU General Public License as published by      
    the Free Software Foundation; either version 2 of the License, or         
    (at your option) any later version.                                       

    This program is distributed in the hope that it will be useful,           
    but WITHOUT ANY WARRANTY; without even the implied warranty of            
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             
    GNU General Public License for more details.                              

    You should have received a copy of the GNU General Public License         
    along with this program; if not, write to the Free Software               
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 
*/

package org.paccman.ui.banks;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.paccman.controller.BankController;
import org.paccman.paccman.Bank;
import org.paccman.ui.form.BadInputException;
import org.paccman.ui.form.PaccmanForm;

/**
 *
 * @author  joao
 */
public class BankFormPanel extends PaccmanForm {
    
    /**
     * Creates new form BankFormPanel
     */
    public BankFormPanel() {
        initComponents();
        setEditMode(false);
    }

    public void setForm(org.paccman.controller.Controller controller) {
        Bank bank = ((BankController)controller).getBank();
        
        bankNameEdt.setText(bank.getName());
	agencyEdt.setText(bank.getAgency());
        
	addressLine1Edt.setText(bank.getAddressLine1());
	addressLine2Edt.setText(bank.getAddressLine2());
	addressNumberEdt.setText(bank.getAddressNumber());
	codeEdt.setText(bank.getAddressZip());
	cityEdt.setText(bank.getAddressCity());
        countryCmb.setSelectedItem(bank.getAddressCountry());
        
	phoneEdt.setText(bank.getTelephone());
	faxEdt.setText(bank.getFax());
	setInternetAddress(bank.getInternet());
	emailEdt.setText(bank.getEmail());
        
    }

    public void setEditMode(boolean editing) {
        
        bankNameEdt.setEnabled(editing);
	agencyEdt.setEnabled(editing);
        
        addressLine1Edt.setEnabled(editing);
        addressLine2Edt.setEnabled(editing);
        addressNumberEdt.setEnabled(editing);
        codeEdt.setEnabled(editing);
        cityEdt.setEnabled(editing);
        countryCmb.setEnabled(editing);
        
        phoneEdt.setEnabled(editing);
        faxEdt.setEnabled(editing);
        internetEdt.setEnabled(editing); goBtn.setEnabled(internetEdt.getText().length() != 0);
	emailEdt.setEnabled(editing);
        
    }

    public void registerToDocumentCtrl() {
    }

    public void clearForm() {
        bankNameEdt.setText("");
	agencyEdt.setText("");
        
        addressLine1Edt.setText("");
        addressLine2Edt.setText("");
        addressNumberEdt.setText("");
        codeEdt.setText("");
        cityEdt.setText("");
        countryCmb.setSelectedItem(null);
        
        phoneEdt.setText("");
        faxEdt.setText("");
        setInternetAddress("");
	emailEdt.setText("");
    }
    
    private void setInternetAddress(String address) {
	internetEdt.setText(address == null ? "" : address); 
        goBtn.setEnabled((address != null) && (address.length() != 0));
    }

    public void getForm(org.paccman.controller.Controller controller) throws BadInputException {
        BankController bankCtrl = (BankController)controller;

        String name = bankNameEdt.getText();
        String agency = agencyEdt.getText();

        String number = addressNumberEdt.getText();
        String addressLine1 = addressLine1Edt.getText();
        String addressLine2 = addressLine2Edt.getText();
        String code = codeEdt.getText();
        String city = cityEdt.getText();
        String country = (String) countryCmb.getSelectedItem();
        
        String phone = phoneEdt.getText();
        String fax = faxEdt.getText();
        String email = emailEdt.getText();
        String internet = internetEdt.getText();
        
        bankCtrl.getBank().setName(name);
        bankCtrl.getBank().setAgency(agency);
        
        bankCtrl.getBank().setAddressNumber(number);
        bankCtrl.getBank().setAddressLine1(addressLine1);
        bankCtrl.getBank().setAddressLine2(addressLine2);
        bankCtrl.getBank().setAddressZip(code);
        bankCtrl.getBank().setAddressCity(city);
        bankCtrl.getBank().setAddressCountry(country);
        
        bankCtrl.getBank().setTelephone(phone);
        bankCtrl.getBank().setFax(fax);
        bankCtrl.getBank().setEmail(email);
        bankCtrl.getBank().setInternet(internet);
        
    }

    public org.paccman.controller.Controller getNewController() {
        return new BankController();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        nameLbl = new javax.swing.JLabel();
        bankNameEdt = new javax.swing.JTextField();
        agencyLbl = new javax.swing.JLabel();
        agencyEdt = new javax.swing.JTextField();
        addressPanel = new javax.swing.JPanel();
        numberLbl = new javax.swing.JLabel();
        streetLbl = new javax.swing.JLabel();
        addressNumberEdt = new javax.swing.JTextField();
        addressLine1Edt = new javax.swing.JTextField();
        addressLine2Edt = new javax.swing.JTextField();
        codeLbl = new javax.swing.JLabel();
        codeEdt = new javax.swing.JTextField();
        cityLbl = new javax.swing.JLabel();
        cityEdt = new javax.swing.JTextField();
        countryLbl = new javax.swing.JLabel();
        countryCmb = new org.paccman.ui.common.CountryComboBox();
        numbersPanel = new javax.swing.JPanel();
        phoneLbl = new javax.swing.JLabel();
        phoneEdt = new javax.swing.JTextField();
        faxLbl = new javax.swing.JLabel();
        faxEdt = new javax.swing.JTextField();
        internetLbl = new javax.swing.JLabel();
        internetEdt = new javax.swing.JTextField();
        goBtn = new javax.swing.JButton();
        emailLbl = new javax.swing.JLabel();
        emailEdt = new javax.swing.JTextField();

        nameLbl.setText("Name");

        agencyLbl.setText("Agency");

        addressPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Address"));
        numberLbl.setText("Number");

        streetLbl.setText("Street");

        codeLbl.setText("Code");

        cityLbl.setText("City");

        countryLbl.setText("Country");

        org.jdesktop.layout.GroupLayout addressPanelLayout = new org.jdesktop.layout.GroupLayout(addressPanel);
        addressPanel.setLayout(addressPanelLayout);
        addressPanelLayout.setHorizontalGroup(
            addressPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, addressPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(addressPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(codeLbl)
                    .add(numberLbl)
                    .add(countryLbl))
                .add(23, 23, 23)
                .add(addressPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, addressPanelLayout.createSequentialGroup()
                        .add(addressPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, addressNumberEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 78, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, codeEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 78, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(addressPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(streetLbl)
                            .add(cityLbl))
                        .add(4, 4, 4)
                        .add(addressPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(cityEdt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                            .add(addressLine2Edt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                            .add(addressLine1Edt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)))
                    .add(countryCmb, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE))
                .addContainerGap())
        );
        addressPanelLayout.setVerticalGroup(
            addressPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, addressPanelLayout.createSequentialGroup()
                .add(addressPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(numberLbl)
                    .add(addressNumberEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(streetLbl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(addressLine1Edt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(addressLine2Edt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(addressPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(codeEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(codeLbl)
                    .add(cityLbl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(cityEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(addressPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, countryCmb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, countryLbl))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        addressPanelLayout.linkSize(new java.awt.Component[] {addressLine1Edt, addressLine2Edt, addressNumberEdt, cityEdt, cityLbl, codeEdt, codeLbl, countryCmb, countryLbl, numberLbl, streetLbl}, org.jdesktop.layout.GroupLayout.VERTICAL);

        numbersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Contact"));
        phoneLbl.setText("Telephone");

        faxLbl.setText("Fax");

        internetLbl.setText("Internet");

        goBtn.setText("Go");
        goBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goBtnActionPerformed(evt);
            }
        });

        emailLbl.setText("E-mail");

        org.jdesktop.layout.GroupLayout numbersPanelLayout = new org.jdesktop.layout.GroupLayout(numbersPanel);
        numbersPanel.setLayout(numbersPanelLayout);
        numbersPanelLayout.setHorizontalGroup(
            numbersPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, numbersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(numbersPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, emailLbl)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, faxLbl)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, internetLbl)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, phoneLbl))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(numbersPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(emailEdt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                    .add(faxEdt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                    .add(phoneEdt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, numbersPanelLayout.createSequentialGroup()
                        .add(internetEdt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(goBtn)))
                .addContainerGap())
        );

        numbersPanelLayout.linkSize(new java.awt.Component[] {emailLbl, faxLbl, internetLbl, phoneLbl}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        numbersPanelLayout.setVerticalGroup(
            numbersPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, numbersPanelLayout.createSequentialGroup()
                .add(numbersPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, phoneLbl, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, phoneEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(numbersPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(faxEdt)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, faxLbl, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(numbersPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(emailLbl)
                    .add(emailEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(numbersPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(internetLbl)
                    .add(goBtn, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(internetEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        numbersPanelLayout.linkSize(new java.awt.Component[] {emailEdt, emailLbl, faxEdt, faxLbl, goBtn, internetEdt, internetLbl, phoneEdt, phoneLbl}, org.jdesktop.layout.GroupLayout.VERTICAL);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(numbersPanel)
                    .add(addressPanel)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(nameLbl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 84, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(bankNameEdt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(agencyLbl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 84, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(agencyEdt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(nameLbl, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(bankNameEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(agencyLbl)
                    .add(agencyEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(addressPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(numbersPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {agencyEdt, agencyLbl, bankNameEdt, nameLbl}, org.jdesktop.layout.GroupLayout.VERTICAL);

    }
    // </editor-fold>//GEN-END:initComponents

    private void goBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goBtnActionPerformed
        Desktop desktop;
        if ((desktop = Desktop.getDesktop()).isDesktopSupported()) {
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                // launch browser
                URI uri = null;
                try {
                    uri = new URI(internetEdt.getText());
                    desktop.browse(uri);
                }
                catch(IOException ioe) {
                    ioe.printStackTrace();
                }
                catch(URISyntaxException use) {
                    use.printStackTrace();

                }
            }
        }
    }//GEN-LAST:event_goBtnActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField addressLine1Edt;
    private javax.swing.JTextField addressLine2Edt;
    private javax.swing.JTextField addressNumberEdt;
    private javax.swing.JPanel addressPanel;
    private javax.swing.JTextField agencyEdt;
    private javax.swing.JLabel agencyLbl;
    private javax.swing.JTextField bankNameEdt;
    private javax.swing.JTextField cityEdt;
    private javax.swing.JLabel cityLbl;
    private javax.swing.JTextField codeEdt;
    private javax.swing.JLabel codeLbl;
    private org.paccman.ui.common.CountryComboBox countryCmb;
    private javax.swing.JLabel countryLbl;
    private javax.swing.JTextField emailEdt;
    private javax.swing.JLabel emailLbl;
    private javax.swing.JTextField faxEdt;
    private javax.swing.JLabel faxLbl;
    private javax.swing.JButton goBtn;
    private javax.swing.JTextField internetEdt;
    private javax.swing.JLabel internetLbl;
    private javax.swing.JLabel nameLbl;
    private javax.swing.JLabel numberLbl;
    private javax.swing.JPanel numbersPanel;
    private javax.swing.JTextField phoneEdt;
    private javax.swing.JLabel phoneLbl;
    private javax.swing.JLabel streetLbl;
    // End of variables declaration//GEN-END:variables
    
}
