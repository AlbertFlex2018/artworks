/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albertgame.avg;

import org.dom4j.Element;


public class AvgConfigUtil{
    /*
        <avg-config>
            <stages-config>
                <stages>                    
                    <stage path=""/>
                    <stage path=""/>
                    <stage path=""/>
                </stages>
                <saved-config>
                    <stage-name value=""/>
                    <story-name value=""/>
                    <action-index value=""/>
                </saved-config>
            </stages-config>
            <datas>
                <key value=""/>
                <key value=""/>
                <key value=""/>
                ...
            </datas>
        </avg-config>
    */
    public void loadConfigs(Element root){
        Element stagesconfig=root.element("statges-config");
        Element data=root.element("datas");
    }
}
