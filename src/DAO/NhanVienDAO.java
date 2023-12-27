/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.NhanVien;
import Utils.JDBCHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author DELL
 */
public class NhanVienDAO extends DAOsup<NhanVien, String>{

    @Override
    public void insert(NhanVien entity) {
        String INSERT_SQL = "Insert into NhanVien(MaNV, MatKhau, HoTen, GioiTinh, NgaySinh, SDT, NgayDK, VaiTro, Hinh) values(?,?,?,?,?,?,?,?,?)";
        JDBCHelper.update(INSERT_SQL, entity.getMaNV(),entity.getMatKhau(),entity.getHoTen(),entity.isGioiTinh(),entity.getNgaySinh(),entity.getSDT(),entity.getNgayDK(),entity.isVaitro(), entity.getHinh());
    }

    @Override
    public void update(NhanVien entity) {
        String UPDATE_SQL = "Update NhanVien set MatKhau = ?, HoTen = ?, GioiTinh = ?, NgaySinh = ?, SDT = ?, VaiTro = ?, Hinh = ? where MaNV = ?";
        JDBCHelper.update(UPDATE_SQL, entity.getMatKhau(),entity.getHoTen(), entity.isGioiTinh(), entity.getNgaySinh(), entity.getSDT(), entity.isVaitro(), entity.getHinh(), entity.getMaNV());
    }

    @Override
    public void delete(String id) {
        String DELETE_SQL = "Delete from NhanVien where MaNV = ?";
        JDBCHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<NhanVien> SelectAll() {
        String SELECT_ALL_SQL = "Select * from NhanVien";
        return SelectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public NhanVien SelectByID(String id) {
        String SELECT_BY_ID_SQL = "Select * from NhanVien where MaNV = ?";
        List<NhanVien> list = SelectBySQL(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhanVien> SelectBySQL(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try{
            ResultSet rs = JDBCHelper.Query(sql, args);
            while (rs.next()){
                NhanVien entity = new NhanVien();
                entity.setMaNV(rs.getString("MaNV"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setHoTen(rs.getString("HoTen"));
                entity.setGioiTinh(rs.getBoolean("GioiTinh"));
                entity.setNgaySinh(rs.getDate("NgaySinh"));
                entity.setSDT(rs.getString("SDT"));
                entity.setNgayDK(rs.getDate("NgayDK"));
                entity.setVaitro(rs.getBoolean("VaiTro"));
                entity.setHinh(rs.getString("Hinh"));
                list.add(entity);
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        return list;
    }
    
}
