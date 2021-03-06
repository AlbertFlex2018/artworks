package afengine.component.render;

import afengine.core.AppState;
import afengine.core.WindowApp;
import afengine.core.util.Debug;
import afengine.core.util.TextCenter.Text;
import afengine.core.util.XMLEngineBoot;
import afengine.core.window.IColor;
import afengine.core.window.IFont;
import afengine.core.window.IGraphicsTech;
import afengine.core.window.ITexture;
import afengine.part.scene.Actor;
import afengine.part.scene.ActorComponent;
import afengine.part.scene.IComponentFactory;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.dom4j.Element;

/**
 * factory for rendercomp,default contains TextRender and TextureRender<br>
 * @see TextRenderComponent
 * @see TextureRenderComponent
 * @author Albert Flex
 */
public class RenderComponentFactory implements IComponentFactory{

    public static interface IRenderCreator{
        public String getType();
        public RenderComponent create(Element element,Map<String,String> datas);
    }

    public static final Map<String,IRenderCreator> extraCreatorMap=new HashMap<>();
        
    /*
        <Render type="" render-creator="" order="0" useroderclass=""/>
        </Render>
    */
    @Override
    public ActorComponent createComponent(Element element,Map<String,String> datas) {        
        RenderComponent comp;
        switch(element.attributeValue("type")){
            case "Text":
                comp= createText(element,datas);
                break;
            case "Texture":
                comp=createTexture(element,datas);
                break;
            case "Rect":
                comp=createRect(element,datas);
                break;
            default:
                comp=createExtra(element,datas);
                break;                
        }
        if(comp!=null){
            String order=element.attributeValue("order");
            if(order!=null){
                int orderi=Integer.parseInt(ActorComponent.getRealValue(order,datas));
                comp.setRenderOrder(orderi);
            }
        }
        String userorderclass = element.attributeValue("userorderclass");
        if (userorderclass != null) {
            userorderclass = ActorComponent.getRealValue(userorderclass, datas);
            Comparator<Actor> comparator = (Comparator) XMLEngineBoot.instanceObj(userorderclass);
            if (comparator != null) {
                RenderComponent.setComparator(comparator);
            }
        }
        return comp;            
    }   
    
    /*
        <Render type="" render-creator="" order="0"/>
        </Render>
    */    
    private RenderComponent createExtra(Element element,Map<String,String> datas){
        String type=element.attributeValue("type");
        IRenderCreator rc = extraCreatorMap.get(type);
        if(rc==null){
            String renderclass=element.attributeValue("render-creator");        
            if(renderclass!=null){
                rc = (IRenderCreator)XMLEngineBoot.instanceObj(renderclass);
                extraCreatorMap.put(type, rc);
            }            
            else{
                Debug.log("have no render-creator for this type");
                return null;
            }            
        }
        return rc.create(element, datas);
    }
    /**
     * <Render type="Text">
     *      <text></text>
     *      <font path=""></font>
     *      <size></size>
     *      <color></color>
     *      //<backcolor></backcolor>
     * </Render>
     */
    private RenderComponent createText(Element element,Map<String,String> datas){
        Text text;
        IFont font;
        IColor color=null;
        IColor backcolor=null;
        Element texte = element.element("text");
        if(texte!=null){
            text=new Text(ActorComponent.getRealValue(texte.getText(),datas));
        }
        else{
            text=new Text("DefaultText");
        }
        Element fonte = element.element("font");
        String sizes = element.elementText("size");
        int siz=Integer.parseInt(ActorComponent.getRealValue(sizes,datas));
        String path=null;
        if(fonte==null){
            font= ((IGraphicsTech)((WindowApp)AppState.getRunningApp())
                    .getGraphicsTech()).createFont("Dialog", false,
                            IFont.FontStyle.PLAIN, siz);
        }
        else if(fonte.attribute("path")!=null){
            path=ActorComponent.getRealValue(fonte.attributeValue("path"),datas);            
                font = ((IGraphicsTech)((WindowApp)AppState.getRunningApp()).
                    getGraphicsTech()).createFont(ActorComponent.getRealValue(fonte.getText(),datas), true, IFont.FontStyle.PLAIN, siz);                        
        }
        else{
            font= ((IGraphicsTech)((WindowApp)AppState.getRunningApp())
                    .getGraphicsTech()).createFont("Dialog", false,
                            IFont.FontStyle.PLAIN,siz);            
        }
        Element colore = element.element("color");
        String colors;

        if(colore==null){
           colors=IColor.GeneraColor.ORANGE.toString();
        }
        else colors = ActorComponent.getRealValue(element.elementText("color"),datas);
        
        color=((IGraphicsTech)((WindowApp)AppState.getRunningApp()).
                getGraphicsTech()).createColor(IColor.GeneraColor.valueOf(colors));
        Element backcolore = element.element("backcolor");

        if(backcolore!=null){
            backcolor=((IGraphicsTech)((WindowApp)AppState.getRunningApp()).
                getGraphicsTech()).createColor(IColor.GeneraColor.valueOf(ActorComponent.getRealValue(backcolore.getText(),datas)));
        }
        
        TextRenderComponent textcomp;
        if(backcolore!=null){
             textcomp= new TextRenderComponent(font,color,backcolor,text);            
        }
       else textcomp= new TextRenderComponent(font,color,text);
        
        return textcomp;
    }
    
    /**
     * <Render type="Rect">
     *    <shape width="" height="" arcwidth="" archeight="" fill=""/>
     *    <color value="r,g,b,a"/>
     * </Render>
     */
    private RenderComponent createRect(Element element,Map<String,String> datas){
        Element shape=element.element("shape");
        Element color=element.element("color");
        String width=ActorComponent.getRealValue(shape.attributeValue("width"), datas);
        String height=ActorComponent.getRealValue(shape.attributeValue("height"), datas);
        String arcwidth=ActorComponent.getRealValue(shape.attributeValue("arcwidth"), datas);
        String archeight=ActorComponent.getRealValue(shape.attributeValue("archeight"), datas);
        String colors=ActorComponent.getRealValue(color.attributeValue("value"), datas);
        String fill=ActorComponent.getRealValue(shape.attributeValue("fill"), datas);

        String[] colorm=colors.split(",");
        String r=colorm[0];
        String g=colorm[1];
        String b=colorm[2];
        String a=colorm[3];
        RectRenderComponent rect=new RectRenderComponent();
        IGraphicsTech tech=((WindowApp)AppState.getRunningApp()).getGraphicsTech();
        
        rect.setColor(tech.createColor(Integer.parseInt(r),Integer.parseInt(g) , Integer.parseInt(b), Integer.parseInt(a)));
        rect.setWidth(Integer.parseInt(width));
        rect.setHeight(Integer.parseInt(height));
        rect.setArcWidth(Integer.parseInt(arcwidth));
        rect.setArcHeight(Integer.parseInt(archeight));
        
        boolean f=Boolean.parseBoolean(fill);
        rect.setFill(f);
        return rect;
    }
    /**
     * <Render type="Texture">
     *      <texture>path</texture>
     *      //<cutsize x="" y="" width="" height=""/>
     * </Render>
     */
    private RenderComponent createTexture(Element element,Map<String,String> datas){
        String texturepath = ActorComponent.getRealValue(element.elementText("texture"),datas);
        Debug.log("texturepath:"+texturepath);
        ITexture texture = ((IGraphicsTech)((WindowApp)AppState.getRunningApp()).
                getGraphicsTech()).createTexture(texturepath);
        Element cut = element.element("cutsize");
        if(cut!=null){
            String x = cut.attributeValue("x");
            String y = cut.attributeValue("y");
            String width = cut.attributeValue("width");
            String height = cut.attributeValue("height");
            if(x!=null&&y!=null&&width!=null&&height!=null){
                texture=texture.getCutInstance(Integer.parseInt(ActorComponent.getRealValue(x,datas)),
                        Integer.parseInt(ActorComponent.getRealValue(height,datas)),
                        Integer.parseInt(ActorComponent.getRealValue(width,datas)),
                        Integer.parseInt(ActorComponent.getRealValue(height,datas)));
            }
        }
        return new TextureRenderComponent(texture);
    }
    
}
