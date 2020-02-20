/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenetest;

import afengine.core.util.XMLEngineBoot;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 *
 * @author Admin
 */
public class XMLChineseTest{
    public static void main(String[] args) {
        Document doc=XMLEngineBoot.getXMLWritableDocument("test/scenetest/t1.xml");
        Element root=doc.getRootElement();
        String table=root.elementText("table");
        String size=root.elementText("size");
        String title=root.elementText("title");
        String content=root.elementText("content");
        System.out.println("table:"+table);
        System.out.println("size:"+size);
        System.out.println("title:"+title);
        System.out.println("content:"+content);
        print(table.toCharArray());
        print(size.toCharArray());
        print(title.toCharArray());
        print(content.toCharArray());
    }
    static void print(char[] text){
        for(char t:text){
            System.out.print(t+"\t");
        }
        System.out.println("");
    }
}
