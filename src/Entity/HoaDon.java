/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author DELL
 */
public class HoaDon {
    private int MaHD;
    private String MaMay;
    private LocalDateTime ThoiGianBD;
    private LocalDateTime ThoiGianKT;
    private double ThoiGianSD;
    private double TongTienDV;
    private double TongPhaiTra;
    private boolean TrangThai;

    public HoaDon() {
    }

    public HoaDon(int MaHD, String MaMay, LocalDateTime ThoiGianBD, LocalDateTime ThoiGianKT, double ThoiGianSD, double TongTienDV, double TongPhaiTra, boolean TrangThai) {
        this.MaHD = MaHD;
        this.MaMay = MaMay;
        this.ThoiGianBD = ThoiGianBD;
        this.ThoiGianKT = ThoiGianKT;
        this.ThoiGianSD = ThoiGianSD;
        this.TongTienDV = TongTienDV;
        this.TongPhaiTra = TongPhaiTra;
        this.TrangThai = TrangThai;
    }

    

    public int getMaHD() {
        return MaHD;
    }

    public void setMaHD(Integer MaHD) {
        this.MaHD = MaHD;
    }

    public String getMaMay() {
        return MaMay;
    }

    public void setMaMay(String MaMay) {
        this.MaMay = MaMay;
    }

    public double getThoiGianSD() {
        return ThoiGianSD;
    }

    public void setThoiGianSD(double ThoiGianSD) {
        this.ThoiGianSD = ThoiGianSD;
    }

    public double getTongTienDV() {
        return TongTienDV;
    }

    public void setTongTienDV(double TongTienDV) {
        this.TongTienDV = TongTienDV;
    }

    public double getTongPhaiTra() {
        return TongPhaiTra;
    }

    public void setTongPhaiTra(double TongPhaiTra) {
        this.TongPhaiTra = TongPhaiTra;
    }

    public boolean isTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(boolean TrangThai) {
        this.TrangThai = TrangThai;
    }

    public LocalDateTime getThoiGianBD() {
        return ThoiGianBD;
    }

    public void setThoiGianBD(LocalDateTime ThoiGianBD) {
        this.ThoiGianBD = ThoiGianBD;
    }

    public LocalDateTime getThoiGianKT() {
        return ThoiGianKT;
    }

    public void setThoiGianKT(LocalDateTime ThoiGianKT) {
        this.ThoiGianKT = ThoiGianKT;
    }


    
    
    
    
}
