/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action.realtest;

import afengine.core.IAppLogic;
import afengine.core.util.XMLEngineBoot;
import albertgame.avg.AvgConfigUtil;
import org.dom4j.Document;
import org.dom4j.Element;

public class Logic implements IAppLogic {
    public static void main(String[] args) {
        XMLEngineBoot.bootEngine("test/action/realtest/realboot.xml");
    }
    @Override
    public boolean init() {
        AvgConfigUtil util=new AvgConfigUtil();
        Document doc=XMLEngineBoot.readXMLFileDocument("test/action/realtest/avgconfig.xml");
        Element root=doc.getRootElement();
        util.loadConfigs(root);
     return true;
    }
    @Override
    public boolean update(long l) {
        return true;
    }

    @Override
    public boolean shutdown() {
        return true;
    }
    
}
