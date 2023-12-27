/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author DELL
 */
public class LogoGenerate {
//    public static Image getAppIcon(){
//        URL url = LogoGenerate.class.getResource("/icon/fpt.png");
//        return new ImageIcon(url).getImage();
//    }
    
    public static boolean save(File src){
        File dst = new File ("src\\logo", src.getName());
        if(!dst.getParentFile().exists()){
            dst.getParentFile().mkdirs();//tao thu muc logo neu khong ton tai
        }
        try{
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    
    public static ImageIcon read(String fileName){
        File path = new File("src\\logo", fileName);
        return new ImageIcon(path.getAbsolutePath());
    }
    
    
}

