/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.HoaDonChiTiet;
import Utils.JDBCHelper;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author DELL
 */
public class HoaDonChiTietDAO extends DAOsup<HoaDonChiTiet, Integer> {

    final String INSERT_SQL = "insert into HoaDonChiTiet(MaHD, MaDV, SoLuong, TongTienDV) values (?,?,?,?)";
    final String UPDATE_SQL = "update HoaDonChiTiet set MaHD = ?, MaDV = ?, SoLuong=?, TongTienDV=? where MaCT = ?";
    final String DELETE_SQL = "delete from HoaDonChiTiet where MaCT = ?";
    final String SELECT_ALL_SQL = "select * from HoaDonChiTiet";
    final String SELECT_BY_ID_SQL = "select * from May where HoaDonChiTiet = ?";

    @Override
    public void insert(HoaDonChiTiet entity) {
        JDBCHelper.update(INSERT_SQL,entity.getMaHD(), entity.getMaDV(), entity.getSoLuong(), entity.getTongTienDV());
    }

    @Override
    public void update(HoaDonChiTiet entity) {
        JDBCHelper.update(UPDATE_SQL, entity.getMaHD(), entity.getMaDV(), entity.getSoLuong(), entity.getTongTienDV(), entity.getMaCT());
    }

    @Override
    public void delete(Integer id) {
        JDBCHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<HoaDonChiTiet> SelectAll() {
        return SelectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public HoaDonChiTiet SelectByID(Integer id) {
        List<HoaDonChiTiet> list = SelectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HoaDonChiTiet> SelectBySQL(String sql, Object... args) {
        List<HoaDonChiTiet> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.Query(sql, args);
            while (rs.next()) {
                HoaDonChiTiet entity = new HoaDonChiTiet();
                entity.setMaCT(rs.getInt("MaCT"));
                entity.setMaHD(rs.getString("MaHD"));
                entity.setMaDV(rs.getString("MaDV"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                entity.setTongTienDV(rs.getDouble("TongTienDV"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;

    }
    
    public double selectTongTienDV(Integer maHD){
        double sumTongTienDV = 0;
        String SQL = "Select Sum(TongTienDV) as TongTienDV from HoaDonChiTiet where MaHD = ?";
        try{
            ResultSet rs = JDBCHelper.Query(SQL, maHD);
            if(rs.next()){
                sumTongTienDV = rs.getDouble("TongTienDV");
            }
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
        return sumTongTienDV;
    }
    
    public List<Object[]> getChiTietDV(Integer maHD){
        String SQL = "{CALL sp_chiTietDV(?)}";
        String[] cols = {"TenDV", "SoLuong", "TongTienDV"};
        return getListofArray(SQL, cols, maHD);
    }
    
    private List<Object[]> getListofArray(String sql, String[] cols, Object...args){
        try{
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = JDBCHelper.Query(sql,args);
            while (rs.next()){
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
//    public List<HoaDonChiTiet> selectByMaHD(Integer maHD){
//        String SQL = "Select * from HoaDonChiTiet where MaHD = ?";
//        List<HoaDonChiTiet> list = new ArrayList<>();
//        try{
//            ResultSet rs = JDBCHelper.Query(SQL, maHD);
//            while
//        }catch(SQLServerException ex){
//            throw new RuntimeException(ex);
//        }
//    }

}

