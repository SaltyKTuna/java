/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.DichVu;
import Utils.JDBCHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author DELL
 */
public class DichVuDAO extends DAOsup<DichVu, String>{

    @Override
    public void insert(DichVu entity) {
        String Insert_SQL = "insert into DichVu(MaDV, TenDV, GiaTien, MaNV, HinhAnh) values(?,?,?,?,?)";
        JDBCHelper.update(Insert_SQL, entity.getMaDV(),entity.getTenDV(),entity.getGiaTien(), entity.getMaNV(),entity.getHinhAnh());
    }

    @Override
    public void update(DichVu entity) {
        String Update_SQL = "Update DichVu set TenDV = ?, GiaTien = ?, MaNV = ?, HinhAnh = ? where MaDV = ?";
        JDBCHelper.update(Update_SQL, entity.getTenDV(),entity.getGiaTien(),entity.getMaNV(),entity.getHinhAnh(),entity.getMaDV());
    }

    @Override
    public void delete(String id) {
        String Delete_SQL = "Delete from DichVu where MaDV = ?";
        JDBCHelper.update(Delete_SQL, id);
    }

    @Override
    public List<DichVu> SelectAll() {
        String SELECT_ALL_SQL = "Select * from DichVu";
        return SelectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public DichVu SelectByID(String id) {
        String SELECT_BY_ID_SQL = "Select * from DichVu where MaDV = ?";
        List<DichVu> list = SelectBySQL(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
    
    public DichVu SelectByName(String name){
        String SELECT_BY_NAME_SQL = "Select * from DichVu where TenDV = ?";
        List<DichVu> list = SelectBySQL(SELECT_BY_NAME_SQL, name);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
    

    @Override
    public List<DichVu> SelectBySQL(String sql, Object... args) {
        List<DichVu> list = new ArrayList<>();
        try{
            ResultSet rs = JDBCHelper.Query(sql, args);
            while(rs.next()){
                DichVu entity = new DichVu();
                entity.setMaDV(rs.getString("MaDV"));
                entity.setTenDV(rs.getString("TenDV"));
                entity.setGiaTien(rs.getDouble("GiaTien"));
                entity.setMaNV(rs.getString("MaNV"));
                entity.setHinhAnh(rs.getString("HinhAnh"));
                list.add(entity);
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public List<String> selectDichVu(){
        String SQL = "Select TenDV from DichVu order by TenDV desc";
        List<String> list = new ArrayList<>();
        try{
            ResultSet rs = JDBCHelper.Query(SQL);
            while(rs.next()){
                String tenDV = rs.getString("TenDV");
                list.add(tenDV);
            }
            rs.getStatement().getConnection().close();
            return  list;
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    
    
    
    
    
}
