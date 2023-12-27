/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author DELL
 */
public class May {
    private String MaMay;
    private String TenMay;
    private boolean TrangThai;
    private double GiaMay;
    private String MaNV;

    public May() {
    }

    public May(String MaMay, String TenMay, boolean TrangThai, double GiaMay, String MaNV, String MaDV) {
        this.MaMay = MaMay;
        this.TenMay = TenMay;
        this.TrangThai = TrangThai;
        this.GiaMay = GiaMay;
        this.MaNV = MaNV;
    }

    public String getMaMay() {
        return MaMay;
    }

    public void setMaMay(String MaMay) {
        this.MaMay = MaMay;
    }

    public String getTenMay() {
        return TenMay;
    }

    public void setTenMay(String TenMay) {
        this.TenMay = TenMay;
    }

    public boolean isTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(boolean TrangThai) {
        this.TrangThai = TrangThai;
    }


    public double getGiaMay() {
        return GiaMay;
    }

    public void setGiaMay(double GiaMay) {
        this.GiaMay = GiaMay;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    
    
}
