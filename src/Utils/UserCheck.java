/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import Entity.NhanVien;

/**
 *
 * @author DELL
 */
public class UserCheck {
    public static NhanVien user = null;
    public static void clear(){
        UserCheck.user = null;
    }
    
    public static boolean isLogin(){
        return UserCheck.user != null;
    }
    
    public static boolean isQuanLy(){
        return UserCheck.isLogin() && user.isVaitro();
    }
}
