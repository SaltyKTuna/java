/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI;

import DAO.DichVuDAO;
import DAO.HoaDonChiTietDAO;
import DAO.HoaDonDAO;
import DAO.MayDAO;
import Entity.HoaDon;
import Entity.HoaDonChiTiet;
import Entity.May;
import Utils.MessageBox;
import java.awt.Frame;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL
 */
public class QuanLyMayPanel extends javax.swing.JPanel {

    private MainForm main;
    MayDAO dao = new MayDAO();
    String keyword = null;
    public String tenMay = null;
    private JPanel pnlCon;
    HoaDonDAO hddao = new HoaDonDAO();
    private int MHD;
    DichVuDAO dvdao = new DichVuDAO();
    HoaDonChiTietDAO ctdao = new HoaDonChiTietDAO();
    DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Creates new form QuanLyMayPanel
     */
    public QuanLyMayPanel() {
        initComponents();
//        FillTable();
        initTrangThai();
    }

//    void FillTable() {
//        DefaultTableModel model = (DefaultTableModel) tblMay.getModel();
//        model.setRowCount(0);
//        try {
//            List<May> list = dao.SelectAll();
//            for (May may : list) {
//                Object[] row = {
//                    may.getMaMay(),
//                    may.getTenMay(),
//                    may.isTrangThai() ? "Mở" : "Tắt"};
//                model.addRow(row);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
    void fillTableCTDichVu() {
        DefaultTableModel model = (DefaultTableModel) tblCTDichVu.getModel();
        model.setRowCount(0);
        try {
            List<Object[]> list = ctdao.getChiTietDV(MHD);
            if (!list.isEmpty()) {
                for (Object[] objects : list) {
                    model.addRow(objects);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    void getMay(){
//        May may = new May();
//        may.setMaMay(dao.SelectByName(tenMay).getMaMay());
//        may.setTenMay(tenMay);
//        may.setTrangThai(true);
//    }
    void setMay(May may) {
        lblTenMay.setText(may.getTenMay());
        if (may.isTrangThai() == true) {
            txtTrangThai.setText("Đang chơi");
        } else {
            txtTrangThai.setText("Tắt");
        }
        txtGia.setText(String.valueOf(may.getGiaMay()));
    }

    void edit() {
        try {
            May may = dao.SelectByName(tenMay);
            List<Integer> listMaHD = hddao.selectMaHDChuaThanhToan(may.getMaMay());
            if (tenMay != null) {
                setMay(may);
                if (listMaHD.isEmpty()) {
                    txtHoaDon.setText("");
                } else {
                    txtHoaDon.setText(String.valueOf(listMaHD.get(0)));
                    MHD = Integer.parseInt(txtHoaDon.getText());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void insertHD() {
        HoaDon hd = new HoaDon();
        May currentMay = dao.SelectByName(lblTenMay.getText());
        hd.setMaMay(currentMay.getMaMay());
        String now = LocalDateTime.now().format(formater);
        hd.setThoiGianBD(LocalDateTime.parse(now, formater));
        hd.setThoiGianKT(null);
        hd.setThoiGianSD(0);
        hd.setTongTienDV(0);
        hd.setTongPhaiTra(0);
        hd.setTrangThai(false);
        if (currentMay.isTrangThai() == false) {
            try {
                currentMay.setTrangThai(true);
                hddao.insert(hd);
                dao.update(currentMay);
                MessageBox.alert(this, "Mở máy thành công");
                edit();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            MessageBox.alert(this, "Máy đang mở");
        }
    }

    void openThemDV() {
        if (txtHoaDon.getText().isEmpty()) {
            MessageBox.alert(this, "Chưa khởi động máy");
        } else {
            MHD = Integer.valueOf(txtHoaDon.getText());
            ThemDVDialog themDV = new ThemDVDialog((Frame) SwingUtilities.getWindowAncestor(this), true, MHD);
            themDV.setVisible(true);
        }
    }

    void TinhTien() {
        List<LocalDateTime> listTgBD = hddao.selectThoiGianBatDau(Integer.valueOf(txtHoaDon.getText()));
        LocalDateTime tgBD = listTgBD.get(0);
        LocalDateTime tgKT = LocalDateTime.now();
        Duration duration = Duration.between(tgBD, tgKT);
        double tgSD = duration.toMinutes();
        double tongTienDV = ctdao.selectTongTienDV(Integer.valueOf(txtHoaDon.getText()));

        May may = dao.SelectByName(tenMay);
        double tongPhaiTra = (tgSD * (may.getGiaMay()/60) + tongTienDV);

        HoaDon hd = hddao.SelectByID(MHD);
        String now = LocalDateTime.now().format(formater);
        hd.setThoiGianKT(LocalDateTime.parse(now, formater));
        hd.setThoiGianSD(tgSD);
        hd.setTongTienDV(tongTienDV);
        hd.setTongPhaiTra(tongPhaiTra);
        hd.setTrangThai(true);

        try {
            hddao.update(hd);
            MessageBox.alert(this, "Thanh toán thành công");
            dao.UpdateCloseByKeyword(lblTenMay.getText());
            edit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void initTrangThai() {
        List<Boolean> list = dao.SelectTrangThai();
        for (Boolean tt : list) {
            if (list.get(0) == false) {
                lblMay1.setText("Đóng");
            } else {
                lblMay1.setText("Đang chơi");
            }
            if (list.get(1) == false) {
                lblMay2.setText("Đóng");
            } else {
                lblMay2.setText("Đang chơi");
            }
            if (list.get(2) == false) {
                lblMay3.setText("Đóng");
            } else {
                lblMay3.setText("Đang chơi");
            }
            if (list.get(3) == false) {
                lblMay4.setText("Đóng");
            } else {
                lblMay4.setText("Đang chơi");
            }
            if (list.get(4) == false) {
                lblMay5.setText("Đóng");
            } else {
                lblMay5.setText("Đang chơi");
            }
            if (list.get(5) == false) {
                lblMay6.setText("Đóng");
            } else {
                lblMay6.setText("Đang chơi");
            }
            if (list.get(6) == false) {
                lblMay7.setText("Đóng");
            } else {
                lblMay7.setText("Đang chơi");
            }
            if (list.get(7) == false) {
                lblMay8.setText("Đóng");
            } else {
                lblMay8.setText("Đang chơi");
            }
            if (list.get(8) == false) {
                lblMay9.setText("Đóng");
            } else {
                lblMay9.setText("Đang chơi");
            }
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

        GroupMay = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnmay1 = new javax.swing.JButton();
        btnmay2 = new javax.swing.JButton();
        btnmay3 = new javax.swing.JButton();
        btnmay9 = new javax.swing.JButton();
        btnmay4 = new javax.swing.JButton();
        btnmay5 = new javax.swing.JButton();
        btnmay6 = new javax.swing.JButton();
        btnmay8 = new javax.swing.JButton();
        btnmay7 = new javax.swing.JButton();
        lblMay2 = new javax.swing.JLabel();
        lblMay1 = new javax.swing.JLabel();
        lblMay3 = new javax.swing.JLabel();
        lblMay4 = new javax.swing.JLabel();
        lblMay5 = new javax.swing.JLabel();
        lblMay6 = new javax.swing.JLabel();
        lblMay7 = new javax.swing.JLabel();
        lblMay8 = new javax.swing.JLabel();
        lblMay9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblTenMay = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTrangThai = new javax.swing.JTextField();
        txtGia = new javax.swing.JTextField();
        btnThemDV = new javax.swing.JButton();
        btnMoMay = new javax.swing.JButton();
        btnTinhTien = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtHoaDon = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCTDichVu = new javax.swing.JTable();

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnmay1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/polypro/icon/Computer.png"))); // NOI18N
        btnmay1.setText("Máy-01");
        GroupMay.add(btnmay1);
        btnmay1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmay1ActionPerformed(evt);
            }
        });

        btnmay2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/polypro/icon/Computer.png"))); // NOI18N
        btnmay2.setText("Máy-02");
        GroupMay.add(btnmay2);
        btnmay2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmay2ActionPerformed(evt);
            }
        });

        btnmay3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/polypro/icon/Computer.png"))); // NOI18N
        btnmay3.setText("Máy-03");
        GroupMay.add(btnmay3);
        btnmay3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmay3ActionPerformed(evt);
            }
        });

        btnmay9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/polypro/icon/Computer.png"))); // NOI18N
        btnmay9.setText("Máy-09");
        GroupMay.add(btnmay9);
        btnmay9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmay9ActionPerformed(evt);
            }
        });

        btnmay4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/polypro/icon/Computer.png"))); // NOI18N
        btnmay4.setText("Máy-04");
        GroupMay.add(btnmay4);
        btnmay4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmay4ActionPerformed(evt);
            }
        });

        btnmay5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/polypro/icon/Computer.png"))); // NOI18N
        btnmay5.setText("Máy-05");
        GroupMay.add(btnmay5);
        btnmay5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmay5ActionPerformed(evt);
            }
        });

        btnmay6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/polypro/icon/Computer.png"))); // NOI18N
        btnmay6.setText("Máy-06");
        GroupMay.add(btnmay6);
        btnmay6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmay6ActionPerformed(evt);
            }
        });

        btnmay8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/polypro/icon/Computer.png"))); // NOI18N
        btnmay8.setText("Máy-08");
        GroupMay.add(btnmay8);
        btnmay8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmay8ActionPerformed(evt);
            }
        });

        btnmay7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/polypro/icon/Computer.png"))); // NOI18N
        btnmay7.setText("Máy-07");
        GroupMay.add(btnmay7);
        btnmay7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmay7ActionPerformed(evt);
            }
        });

        lblMay2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMay2.setText("Trạng thái");

        lblMay1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMay1.setText("Trạng thái");

        lblMay3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMay3.setText("Trạng thái");

        lblMay4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMay4.setText("Trạng thái");

        lblMay5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMay5.setText("Trạng thái");

        lblMay6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMay6.setText("Trạng thái");

        lblMay7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMay7.setText("Trạng thái");

        lblMay8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMay8.setText("Trạng thái");

        lblMay9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMay9.setText("Trạng thái");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnmay4)
                            .addComponent(btnmay1)
                            .addComponent(btnmay7))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnmay2)
                            .addComponent(btnmay5)
                            .addComponent(btnmay8)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(lblMay1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMay2)
                        .addGap(24, 24, 24))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(lblMay4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMay5)
                        .addGap(27, 27, 27))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(lblMay7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMay8)
                        .addGap(27, 27, 27)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnmay6)
                            .addComponent(btnmay3)
                            .addComponent(btnmay9))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMay3)
                        .addGap(51, 51, 51))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMay6)
                        .addGap(53, 53, 53))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMay9)
                        .addGap(51, 51, 51))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnmay1)
                    .addComponent(btnmay2)
                    .addComponent(btnmay3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMay2)
                    .addComponent(lblMay1)
                    .addComponent(lblMay3))
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnmay4)
                    .addComponent(btnmay5)
                    .addComponent(btnmay6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMay4)
                    .addComponent(lblMay5)
                    .addComponent(lblMay6))
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnmay9)
                    .addComponent(btnmay7)
                    .addComponent(btnmay8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMay7)
                    .addComponent(lblMay8)
                    .addComponent(lblMay9))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblTenMay.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTenMay.setText("Máy - XX");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Trạng thái:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Giá:");

        btnThemDV.setText("Thêm Dịch Vụ");
        btnThemDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemDVActionPerformed(evt);
            }
        });

        btnMoMay.setText("Mở máy");
        btnMoMay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoMayActionPerformed(evt);
            }
        });

        btnTinhTien.setText("Tính tiền");
        btnTinhTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTinhTienActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Hóa đơn:");

        txtHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtHoaDon.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnMoMay)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTinhTien))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTenMay)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                                    .addComponent(txtGia)))
                            .addComponent(btnThemDV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(59, 59, 59)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(txtHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)))))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTenMay)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThemDV)
                .addGap(36, 36, 36)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMoMay)
                    .addComponent(btnTinhTien))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Chi Tiết Dịch Vụ:");

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblCTDichVu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Dịch vụ", "Số lượng", "Tổng tiền dịch vụ"
            }
        ));
        jScrollPane2.setViewportView(tblCTDichVu);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 773, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Quản lý máy", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHoaDonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHoaDonActionPerformed

    private void btnTinhTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTinhTienActionPerformed
        // TODO add your handling code here:
        TinhTien();
        initTrangThai();
    }//GEN-LAST:event_btnTinhTienActionPerformed

    private void btnMoMayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoMayActionPerformed
        // TODO add your handling code here:
        insertHD();
        initTrangThai();
    }//GEN-LAST:event_btnMoMayActionPerformed

    private void btnThemDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemDVActionPerformed
        // TODO add your handling code here:
        openThemDV();
    }//GEN-LAST:event_btnThemDVActionPerformed

    private void btnmay7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmay7ActionPerformed
        // TODO add your handling code here:
        tenMay = btnmay7.getText();
        edit();
        fillTableCTDichVu();
    }//GEN-LAST:event_btnmay7ActionPerformed

    private void btnmay8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmay8ActionPerformed
        // TODO add your handling code here:
        tenMay = btnmay8.getText();
        edit();
        fillTableCTDichVu();
    }//GEN-LAST:event_btnmay8ActionPerformed

    private void btnmay6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmay6ActionPerformed
        // TODO add your handling code here:
        tenMay = btnmay6.getText();
        edit();
        fillTableCTDichVu();
    }//GEN-LAST:event_btnmay6ActionPerformed

    private void btnmay5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmay5ActionPerformed
        // TODO add your handling code here:
        tenMay = btnmay5.getText();
        edit();
        fillTableCTDichVu();
    }//GEN-LAST:event_btnmay5ActionPerformed

    private void btnmay4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmay4ActionPerformed
        // TODO add your handling code here:
        tenMay = btnmay4.getText();
        edit();
        fillTableCTDichVu();
    }//GEN-LAST:event_btnmay4ActionPerformed

    private void btnmay9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmay9ActionPerformed
        // TODO add your handling code here:
        tenMay = btnmay9.getText();
        edit();
        fillTableCTDichVu();
    }//GEN-LAST:event_btnmay9ActionPerformed

    private void btnmay3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmay3ActionPerformed
        // TODO add your handling code here:
        tenMay = btnmay3.getText();
        edit();
        fillTableCTDichVu();
    }//GEN-LAST:event_btnmay3ActionPerformed

    private void btnmay2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmay2ActionPerformed
        // TODO add your handling code here:
        tenMay = btnmay2.getText();
        edit();
        fillTableCTDichVu();
    }//GEN-LAST:event_btnmay2ActionPerformed

    private void btnmay1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmay1ActionPerformed
        // TODO add your handling code here:
        tenMay = btnmay1.getText();
        edit();
        fillTableCTDichVu();
    }//GEN-LAST:event_btnmay1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup GroupMay;
    private javax.swing.JButton btnMoMay;
    private javax.swing.JButton btnThemDV;
    private javax.swing.JButton btnTinhTien;
    private javax.swing.JButton btnmay1;
    private javax.swing.JButton btnmay2;
    private javax.swing.JButton btnmay3;
    private javax.swing.JButton btnmay4;
    private javax.swing.JButton btnmay5;
    private javax.swing.JButton btnmay6;
    private javax.swing.JButton btnmay7;
    private javax.swing.JButton btnmay8;
    private javax.swing.JButton btnmay9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblMay1;
    private javax.swing.JLabel lblMay2;
    private javax.swing.JLabel lblMay3;
    private javax.swing.JLabel lblMay4;
    private javax.swing.JLabel lblMay5;
    private javax.swing.JLabel lblMay6;
    private javax.swing.JLabel lblMay7;
    private javax.swing.JLabel lblMay8;
    private javax.swing.JLabel lblMay9;
    private javax.swing.JLabel lblTenMay;
    private javax.swing.JTable tblCTDichVu;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtHoaDon;
    private javax.swing.JTextField txtTrangThai;
    // End of variables declaration//GEN-END:variables
}
