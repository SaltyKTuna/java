/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.May;
import Utils.JDBCHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author DELL
 */
public class MayDAO extends DAOsup<May, String> {
    
    final String INSERT_SQL = "insert into May(MaMay, TenMay, TrangThai, GiaMay, MaNV) values (?,?,?,?,?)";
    final String UPDATE_SQL = "update May set TenMay = ?, TrangThai = ?, GiaMay=?, MaNV=? where MaMay = ?";
    final String DELETE_SQL = "delete from May where MaMay = ?";
    final String SELECT_ALL_SQL = "select * from May";
    final String SELECT_BY_ID_SQL = "select * from May where MaMay = ?";
    
    @Override
    public void insert(May entity) {
        JDBCHelper.update(INSERT_SQL, entity.getMaMay(),entity.getTenMay(),entity.isTrangThai(),entity.getGiaMay(),entity.getMaNV());
    }
    
    @Override
    public void update(May entity) {
        JDBCHelper.update(UPDATE_SQL, entity.getTenMay(), entity.isTrangThai(), entity.getGiaMay(), entity.getMaNV(), entity.getMaMay());
    }
    
    @Override
    public void delete(String id) {
        JDBCHelper.update(DELETE_SQL, id);
    }
    
    @Override
    public List<May> SelectAll() {
        return SelectBySQL(SELECT_ALL_SQL);
    }
    
    public void UpdateOpenByKeyword(String keyword){
        String SQL = "Update May set TrangThai = 1 where TenMay = ?";
        JDBCHelper.update(SQL, keyword);
    }
    
    public void UpdateCloseByKeyword(String keyword){
        String SQL = "Update May set TrangThai = 0 where TenMay = ?";
        JDBCHelper.update(SQL, keyword);
    }
    
    @Override
    public May SelectByID(String id) {
        List<May> list = SelectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public May SelectByName(String name){
        String SQL = "Select * from May where TenMay = ?";
        List<May> list = SelectBySQL(SQL, name);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
    
    @Override
    public List<May> SelectBySQL(String sql, Object... args) {
        List<May> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.Query(sql, args);
            while (rs.next()) {
                May entity = new May();
                entity.setMaMay(rs.getString("MaMay"));
                entity.setTenMay(rs.getString("TenMay"));
                entity.setTrangThai(rs.getBoolean("TrangThai"));
                entity.setGiaMay(rs.getDouble("GiaMay"));
                entity.setMaNV(rs.getString("MaNV"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
        
    }
    
    public List<Boolean> SelectTrangThai(){
        String SQL = "Select TrangThai from May";
        List<Boolean> list = new ArrayList<>();
        try{
            ResultSet rs = JDBCHelper.Query(SQL);
            while(rs.next()){
                list.add(rs.getBoolean("TrangThai"));
            }
            rs.getStatement().getConnection().close();
            return list;
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    
    
    
     
}
