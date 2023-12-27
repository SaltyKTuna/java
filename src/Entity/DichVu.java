/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author DELL
 */
public class DichVu {
    private String MaDV;
    private String TenDV;
    private double GiaTien;
    private String MaNV;
    private String HinhAnh;

    public DichVu() {
    }

    public DichVu(String MaDV, String TenDV, double GiaTien, String MaNV, String HinhAnh) {
        this.MaDV = MaDV;
        this.TenDV = TenDV;
        this.GiaTien = GiaTien;
        this.MaNV = MaNV;
        this.HinhAnh = HinhAnh;
    }


    public String getMaDV() {
        return MaDV;
    }

    public void setMaDV(String MaDV) {
        this.MaDV = MaDV;
    }

    public String getTenDV() {
        return TenDV;
    }

    public void setTenDV(String TenDV) {
        this.TenDV = TenDV;
    }

    public double getGiaTien() {
        return GiaTien;
    }

    public void setGiaTien(double GiaTien) {
        this.GiaTien = GiaTien;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String HinhAnh) {
        this.HinhAnh = HinhAnh;
    }
    
    
}
