/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI;

import DAO.HoaDonDAO;
import Entity.HoaDon;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 84931
 */
public class DoanhThuPanel extends javax.swing.JPanel {

    HoaDonDAO dao = new HoaDonDAO();

    /**
     * Creates new form NewJPanelDoanhthu
     */
    public DoanhThuPanel() {
        initComponents();
        init();
        fillTableThongKe();
    }

    void init() {
        fillCboNam();
        fillCboThang();
        fillCboNgay();
        fillCboNgayBD();
        fillCboNgayKT();
    }

//    void fillCboNgay(){
//        DefaultComboBoxModel model = (DefaultComboBoxModel) cboDate.getModel();
//        model.removeAllElements();
//        List<Date> list = dao.selectDate();
//        for (Date date : list) {
//            model.addElement(date);
//        }
//    }
    void fillCboNam() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboYear.getModel();
        model.removeAllElements();
        List<Integer> list = dao.selectYear();
        for (Integer year : list) {
            model.addElement(year);
        }
    }

    void fillCboThang() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboMonth.getModel();
        model.removeAllElements();
        for (int i = 0; i <= 12; i++) {
            model.addElement(i);
        }
    }

    void fillCboNgay() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboDay.getModel();
        model.removeAllElements();
        for (int i = 1; i <= 31; i++) {
            model.addElement(i);
        }
    }

    void fillCboNgayBD() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboFromDay.getModel();
        model.removeAllElements();
        for (int i = 1; i <= 31; i++) {
            model.addElement(i);
        }
    }

    void fillCboNgayKT() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboToDay.getModel();
        model.removeAllElements();
        for (int i = 1; i <= 31; i++) {
            model.addElement(i);
        }
    }

    void fillTableThongKe() {
        DefaultTableModel model = (DefaultTableModel) tblThongKe.getModel();
        model.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        try {
//        Date date = (Date) cboDate.getSelectedItem();
            List<HoaDon> list = dao.SelectAll();
            for (HoaDon hd : list) {
                String tgBDtoString = hd.getThoiGianBD().format(formatter);
                String tgKTtoString = hd.getThoiGianKT().format(formatter);
                
                Object[] row = {
                    hd.getMaHD(),
                    hd.getMaMay(),
                    tgBDtoString,
                    tgKTtoString,
                    hd.getThoiGianSD(),
                    String.format("%.1f", hd.getTongTienDV()),
                    String.format("%.1f", hd.getTongPhaiTra()),
                    hd.isTrangThai() ? "Đã thanh toán" : "Chưa thanh toán"
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void fillTableThongKeTheoNgay(Integer ngay) {
        DefaultTableModel model = (DefaultTableModel) tblThongKe.getModel();
        model.setRowCount(0);
        try {
//        Date date = (Date) cboDate.getSelectedItem();
            List<HoaDon> list = dao.selectByDay(ngay);
            for (HoaDon hd : list) {
                Object[] row = {
                    hd.getMaHD(),
                    hd.getMaMay(),
                    hd.getThoiGianBD(),
                    hd.getThoiGianKT(),
                    hd.getThoiGianSD(),
                    hd.getTongTienDV(),
                    hd.getTongPhaiTra(),
                    hd.isTrangThai() ? "Đã thanh toán" : "Chưa thanh toán"
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void fillTableThongKeTheoThang(Integer thang) {
        DefaultTableModel model = (DefaultTableModel) tblThongKe.getModel();
        model.setRowCount(0);
        try {
//        Date date = (Date) cboDate.getSelectedItem();
            List<HoaDon> list = dao.selectByMonth(thang);
            for (HoaDon hd : list) {
                Object[] row = {
                    hd.getMaHD(),
                    hd.getMaMay(),
                    hd.getThoiGianBD(),
                    hd.getThoiGianKT(),
                    hd.getThoiGianSD(),
                    hd.getTongTienDV(),
                    hd.getTongPhaiTra(),
                    hd.isTrangThai() ? "Đã thanh toán" : "Chưa thanh toán"
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void fillTableThongKeTheoNam(Integer nam) {
        DefaultTableModel model = (DefaultTableModel) tblThongKe.getModel();
        model.setRowCount(0);
        try {
//        Date date = (Date) cboDate.getSelectedItem();
            List<HoaDon> list = dao.selectByYear(nam);
            for (HoaDon hd : list) {
                Object[] row = {
                    hd.getMaHD(),
                    hd.getMaMay(),
                    hd.getThoiGianBD(),
                    hd.getThoiGianKT(),
                    hd.getThoiGianSD(),
                    hd.getTongTienDV(),
                    hd.getTongPhaiTra(),
                    hd.isTrangThai() ? "Đã thanh toán" : "Chưa thanh toán"
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void fillTableTheoDK(int Nam, int Thang, int Ngay) {
        DefaultTableModel model = (DefaultTableModel) tblThongKe.getModel();
        model.setRowCount(0);
        try {
//        Date date = (Date) cboDate.getSelectedItem();

            List<HoaDon> list = dao.selectByDK(Nam, Thang, Ngay);
            for (HoaDon hd : list) {
                Object[] row = {
                    hd.getMaHD(),
                    hd.getMaMay(),
                    hd.getThoiGianBD(),
                    hd.getThoiGianKT(),
                    hd.getThoiGianSD(),
                    hd.getTongTienDV(),
                    hd.getTongPhaiTra(),
                    hd.isTrangThai() ? "Đã thanh toán" : "Chưa thanh toán"
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void fillTableGiuaCacNgay(int ngayBD, int ngaKT) {
        DefaultTableModel model = (DefaultTableModel) tblThongKe.getModel();
        model.setRowCount(0);
        try {
//        Date date = (Date) cboDate.getSelectedItem();

            List<HoaDon> list = dao.selectBetweenDay(ngayBD, ngaKT);
            for (HoaDon hd : list) {
                Object[] row = {
                    hd.getMaHD(),
                    hd.getMaMay(),
                    hd.getThoiGianBD(),
                    hd.getThoiGianKT(),
                    hd.getThoiGianSD(),
                    hd.getTongTienDV(),
                    hd.getTongPhaiTra(),
                    hd.isTrangThai() ? "Đã thanh toán" : "Chưa thanh toán"
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThongKe = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cboYear = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cboMonth = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cboDay = new javax.swing.JComboBox<>();
        btnTimKiem = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cboFromDay = new javax.swing.JComboBox<>();
        cboToDay = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("THỐNG KÊ DOANH THU");

        tblThongKe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Mã máy", "Thời gian bắt đầu", "Thời gian kết thúc", "Thời gian sử dụng (phút)", "Tổng tiền DV", "Tổng phải trả", "Trạng thái"
            }
        ));
        jScrollPane1.setViewportView(tblThongKe);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Tìm kiếm:");

        cboYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboYearActionPerformed(evt);
            }
        });

        jLabel3.setText("Năm:");

        jLabel4.setText("Tháng:");

        cboMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMonthActionPerformed(evt);
            }
        });

        jLabel5.setText("Ngày:");

        cboDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDayActionPerformed(evt);
            }
        });

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setText("Từ ngày");

        jLabel7.setText("Đến ngày");

        jButton1.setText("Tìm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jLabel6))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(23, Short.MAX_VALUE)
                        .addComponent(cboFromDay, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel7)
                        .addGap(35, 35, 35))
                    .addComponent(cboToDay, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addComponent(jButton1)
                .addGap(16, 16, 16))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboFromDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboToDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(57, 57, 57)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboYear, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboDay, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTimKiem))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cboYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(cboMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(cboDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(230, 230, 230)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(76, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboYearActionPerformed
//        // TODO add your handling code here:
        int year = (int) cboYear.getSelectedItem();
        fillTableThongKeTheoNam(year);
    }//GEN-LAST:event_cboYearActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        int Nam = (int) cboYear.getSelectedItem();
        int Thang = (int) cboMonth.getSelectedItem();
        int Ngay = (int) cboDay.getSelectedItem();
        fillTableTheoDK(Nam, Thang, Ngay);
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void cboMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMonthActionPerformed
        // TODO add your handling code here:
        int month = (int) cboMonth.getSelectedItem();
        fillTableThongKeTheoThang(month);
    }//GEN-LAST:event_cboMonthActionPerformed

    private void cboDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDayActionPerformed
        // TODO add your handling code here:
        int day = (int) cboDay.getSelectedItem();
        fillTableThongKeTheoNgay(day);
    }//GEN-LAST:event_cboDayActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int ngayBD = (int) cboFromDay.getSelectedItem();
        int ngayKT = (int) cboToDay.getSelectedItem();
        fillTableGiuaCacNgay(ngayBD, ngayKT);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JComboBox<String> cboDay;
    private javax.swing.JComboBox<String> cboFromDay;
    private javax.swing.JComboBox<String> cboMonth;
    private javax.swing.JComboBox<String> cboToDay;
    private javax.swing.JComboBox<String> cboYear;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblThongKe;
    // End of variables declaration//GEN-END:variables
}
