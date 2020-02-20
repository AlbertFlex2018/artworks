/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albertsoft.elevator.control.cmd;

import albertsoft.elevator.control.CmdInterface;
import albertsoft.elevator.control.XMLFileHelp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author Administrator
 */
public class SystemCmd implements CmdInterface{
    //system read [filepath]
    //system save [filepath]
    @Override
    public String cmd(String[] cmds) {
        if(cmds[1].equals("read")){
            SAXReader reader=new SAXReader();
            Document doc;
            try{
               doc=reader.read(new File(cmds[2]));
               Element root=doc.getRootElement();
               if(!root.getName().equals("Config")){
                   System.err.println("file "+cmds[2]+" is not a elevator system config.");
                   return "failed";
               }
               XMLFileHelp help=new XMLFileHelp();
               help.readSystemFromXML(root);
            }catch(Exception e){
                e.printStackTrace();
            }
        }else if(cmds[1].equals("save")){
            File file = new File(cmds[2]);
            Document doc=null;
            if(!file.exists()){
                doc=DocumentHelper.createDocument();
                doc.addElement("Config");
            }else{
                SAXReader reader =new SAXReader();
                try{
                    doc = reader.read(file);            
                    System.out.println("root : "+doc.getRootElement().getName());
                }catch(Exception e){
                    e.printStackTrace();
                }                
            }
            Element root=doc.getRootElement();
            XMLFileHelp help = new XMLFileHelp();
            help.writeSystemToXML(root);  
            FileOutputStream out =null;
            try {
                out = new FileOutputStream(cmds[2]);
                OutputFormat format=OutputFormat.createPrettyPrint();   //漂亮格式：有空格换行
                format.setEncoding("UTF-8");
                XMLWriter writer=new XMLWriter(out,format);
                //2.写出Document对象
                writer.write(doc);
                //3.关闭流
                writer.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SystemCmd.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(SystemCmd.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SystemCmd.class.getName()).log(Level.SEVERE, null, ex);
            }finally {
                try {
                    out.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            
        }else;
        return "system end.";
    }
    
    @Override
    public String getName() {
        return "system";
    }    
}
