/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author DELL
 */
public class HoaDonChiTiet {
    private int MaCT;
    private String MaHD;
    private String MaDV;
    private int SoLuong;
    private double TongTienDV;

    public HoaDonChiTiet() {
    }
    
    
    public HoaDonChiTiet(int MaCT, String MaHD, String MaDV, int SoLuong, double TongTienDV) {
        this.MaCT = MaCT;
        this.MaHD = MaHD;
        this.MaDV = MaDV;
        this.SoLuong = SoLuong;
        this.TongTienDV = TongTienDV;
    }

    public int getMaCT() {
        return MaCT;
    }

    public void setMaCT(int MaCT) {
        this.MaCT = MaCT;
    }

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String MaHD) {
        this.MaHD = MaHD;
    }

    public String getMaDV() {
        return MaDV;
    }

    public void setMaDV(String MaDV) {
        this.MaDV = MaDV;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public double getTongTienDV() {
        return TongTienDV;
    }

    public void setTongTienDV(double TongTienDV) {
        this.TongTienDV = TongTienDV;
    }
    
    
}
