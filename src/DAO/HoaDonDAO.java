/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.HoaDon;
import Utils.JDBCHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;

/**
 *
 * @author DELL
 */
public class HoaDonDAO extends DAOsup<HoaDon, Integer> {

    final String INSERT_SQL = "insert into HoaDon(MaMay, ThoiGianBatDau, ThoiGianKetThuc, ThoiGianSD, TongTienDV, TongPhaiTra, TrangThai) values (?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "update HoaDon set ThoiGianKetThuc = ?, ThoiGianSD = ?, TongTienDV=?, TongPhaiTra=?, TrangThai=? where MaHD = ?";
    final String DELETE_SQL = "delete from HoaDon where MaHD = ?";
    final String SELECT_ALL_SQL = "select * from HoaDon";
    final String SELECT_BY_ID_SQL = "select * from HoaDon where MaHD = ?";

    @Override
    public void insert(HoaDon entity) {
        JDBCHelper.update(INSERT_SQL, entity.getMaMay(), entity.getThoiGianBD(), entity.getThoiGianKT(), entity.getThoiGianSD(), entity.getTongTienDV(), entity.getTongPhaiTra(), entity.isTrangThai());
    }

    @Override
    public void update(HoaDon entity) {
        java.sql.Timestamp timestampKT = java.sql.Timestamp.valueOf(entity.getThoiGianKT());
        JDBCHelper.update(UPDATE_SQL, timestampKT, entity.getThoiGianSD(), entity.getTongTienDV(), entity.getTongPhaiTra(), entity.isTrangThai(), entity.getMaHD());
    }

    @Override
    public void delete(Integer id) {
        JDBCHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<HoaDon> SelectAll() {
        return SelectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public HoaDon SelectByID(Integer id) {
        List<HoaDon> list = SelectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

//    public HoaDon selectHDChuaThanhToan(String maMay){
//        String SQL = "Select * from HoaDon where MaMay = ? and TrangThai = 0";
//        List<HoaDon> list = SelectBySQL(SQL, maMay);
//        if(list.isEmpty()){
//            return null;
//        }
//        return list.get(0);
//    }
    @Override
    public List<HoaDon> SelectBySQL(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.Query(sql, args);
            while (rs.next()) {
                HoaDon entity = new HoaDon();
                entity.setMaHD(rs.getInt("MaHD"));
                entity.setMaMay(rs.getString("MaMay"));
                java.sql.Timestamp sqlTimeStamp = rs.getTimestamp("ThoiGianBatDau");
                entity.setThoiGianBD(sqlTimeStamp.toLocalDateTime());
                java.sql.Timestamp sqlTimeStampKT = null;
                if (rs.getTimestamp("ThoiGianKetThuc") != null) {
                    sqlTimeStampKT = rs.getTimestamp("ThoiGianKetThuc");
                    entity.setThoiGianKT(sqlTimeStampKT.toLocalDateTime());
                } else {
//                    entity.setThoiGianKT(LocalDateTime.of(0, Month.MARCH, 1, 0, 0));
                }
                entity.setThoiGianSD(rs.getDouble("ThoiGianSD"));
                entity.setTongTienDV(rs.getDouble("TongTienDv"));
                entity.setTongPhaiTra(rs.getDouble("TongPhaitra"));
                entity.setTrangThai(rs.getBoolean("TrangThai"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private List<Object[]> getListofArray(String sql, String[] cols, Object... args) {
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = JDBCHelper.Query(sql, args);
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    public List<Object[]> getThongKeHoaDon(Date Ngay) {
//        String SQL = "{Call sp_ThongKeDoanhThu(?)}";
//        String[] cols = {"MaHD", "MaMay", "ThoiGianSD", "TenMay", "TongTienDV", "TongPhaiTra", "Ngay", "TrangThai"};
//        return getListofArray(SQL, cols, Ngay);
//    }
    public List<Integer> selectYear() {
        String SQL = "Select DATEPART(YEAR, ThoiGianKetThuc) as Nam from HoaDon";
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.Query(SQL);
            while (rs.next()) {
                int year = rs.getInt("Nam");
                list.add(year);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<HoaDon> selectByDK(Integer nam, Integer thang, Integer ngay) {
        String SQL = "Select * from HoaDon where DatePart(Year, ThoiGianKetThuc) = ? and DatePart(Month, ThoiGianKetThuc) = ? and DatePart(Day, ThoiGianKetThuc) = ?";
        return SelectBySQL(SQL, nam, thang, ngay);
    }

    public List<HoaDon> selectByYear(Integer nam) {
        String SQL = "Select * from HoaDon where DatePart(Year, ThoiGianKetThuc) = ?";
        return SelectBySQL(SQL, nam);
    }

    public List<HoaDon> selectByMonth(Integer thang) {
        String SQL = "Select * from HoaDon where DatePart(Month, ThoiGianKetThuc) = ?";
        return SelectBySQL(SQL, thang);
    }

    public List<HoaDon> selectByDay(Integer ngay) {
        String SQL = "Select * from HoaDon where DatePart(Day, ThoiGianKetThuc) = ?";
        return SelectBySQL(SQL, ngay);
    }

    public List<HoaDon> selectBetweenDay(Integer ngayBD, Integer ngayKT) {
        String SQL = "Select * from HoaDon where DatePart(Day, ThoiGianKetThuc) between ? and ?";
        return SelectBySQL(SQL, ngayBD, ngayKT);
    }

    public List<Integer> selectMaHDChuaThanhToan(String maMay) {
        String SQL = "Select MaHD from HoaDon where MaMay = ? and TrangThai = 0";
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.Query(SQL, maMay);
            while (rs.next()) {
                int MaHD = rs.getInt("MaHD");
                list.add(MaHD);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<LocalDateTime> selectThoiGianBatDau(Integer maHD) {
        String SQL = "Select ThoiGianBatDau from HoaDon where MaHD = ?";
        List<LocalDateTime> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.Query(SQL, maHD);
            while (rs.next()) {
                java.sql.Timestamp timestampBD = rs.getTimestamp("ThoiGianBatDau");
                LocalDateTime batdau = timestampBD.toLocalDateTime();
                list.add(batdau);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void ThanhToan(LocalDateTime tgKT, double tgSD, double TongTienDV, double TongPhaiTra, boolean trangThai, Integer maHD) {
        JDBCHelper.update(UPDATE_SQL, tgKT, tgSD, TongTienDV, TongPhaiTra, trangThai, maHD);
    }
    
}
