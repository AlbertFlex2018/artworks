/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.part.scene;

import afengine.core.util.Debug;
import afengine.core.util.IDCreator;
import afengine.core.util.Transform;
import afengine.part.message.IMessageHandler;
import afengine.part.message.Message;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * a component contains for scene.<br>
 * main topic for app game.<br>
 * you can create actor,then add actor to scene,<br>
 * scene will manager actor lifespan<br>
 * @see ActorComponent
 * @see Scene
 * @author Albert Flex
 */
public class Actor implements IMessageHandler{
    
    public static Map<String,Actor> staticActorMap=new HashMap<>();    
    public static Actor findStaticActor(String name){
        return staticActorMap.get(name);
    }
    public static void addStaticActor(Actor actor){
        if(!staticActorMap.containsKey(actor.name)){
            staticActorMap.put(actor.name, actor);
            actor.isStatic=true;
        }
        else{
            Debug.log("already has actor for:"+actor.name);
        }
    }
    public static void removeStaticActor(String name){
        Actor actor = staticActorMap.get(name);
        if(actor!=null)
            actor.isStatic=false;
        
        staticActorMap.remove(name);
    }
    public static void updateStaticActor(long time){
        Iterator<Actor> actoriter=staticActorMap.values().iterator();
        while(actoriter.hasNext()){
            Actor actor = actoriter.next();
            actor.updateActor(time);
        }
    }        
    
    
    private Transform transform;
    public final long id;
    private boolean isStatic=false;  
    private String name;
    private String modPath;
    public final Map<String,String> valueMap = new HashMap<>();
    private boolean deleted=false;//remove only!

    private Actor parent;
    private final List<Actor> children = new ArrayList<>();
    //下一个层次的孩子节点的map
    private final Map<Long,Actor> childMap = new HashMap<>();
    private final Map<String, ActorComponent> componentsMap = new HashMap<>();
    
    public Actor(){
        this("actor");
    }
    public Actor(String name){
        this(name,new Transform());
    }
    public Actor(String name,Transform trans){
        this(IDCreator.createId(),name,trans);
    }
    public Actor(long id,String name,Transform transform){
        this.id=id;
        this.name=name;
        this.transform=transform;
    }

    public final void setTransform(Transform transform) {
        this.transform = transform;
    }    

    public final Transform getTransform() {
        return transform;
    }
   
    public final Map<String, ActorComponent> getComponentsMap() {
        return componentsMap;
    }

    public boolean isIsStatic() {
        return isStatic;
    }
    
    public final boolean hasComponent(String compname) {        
        return componentsMap.containsKey(compname);
    }
    
    public final void addComponent(ActorComponent comp,boolean awake){
        if(componentsMap.containsKey(comp.getComponentName())){
            Debug.log("there has one instance for type of comp."+comp.getComponentName()+"\nplease do not add more same comp again!");
            return;
        }        
        
        if(comp.getActor()!=null){
            comp.getActor().removeComponent(comp.getComponentName());
        }
        componentsMap.put(comp.getComponentName(), comp);
        comp.setActor(this);
        if(awake)
            comp.awake();
    }    
    public final void removeComponent(String compname){

        if(!componentsMap.containsKey(compname))return;        
        ActorComponent comp=componentsMap.remove(compname);        
        comp.setActor(null);        
    }
    public final ActorComponent getComponent(String compname){
        return componentsMap.get(compname);
    }
    public final void awakeAllComponents(){                
        Iterator<ActorComponent> compiter=componentsMap.values().iterator();
        while(compiter.hasNext()){
            ActorComponent comp=compiter.next();
            comp.awake();
        }
        
        Iterator<Actor> childiter=children.iterator();
        while(childiter.hasNext()){
            Actor child = childiter.next();
            child.awakeAllComponents();
        }
    }
    public final void sleepAllComponents(){
        Iterator<ActorComponent> compiter=componentsMap.values().iterator();
        while(compiter.hasNext()){
            ActorComponent comp=compiter.next();
            comp.awake();
        }        

        Iterator<Actor> childiter=children.iterator();
        while(childiter.hasNext()){
            Actor child = childiter.next();
            child.awakeAllComponents();
        }        
    }

    public String getModPath() {
        return modPath;
    }

    public void setModPath(String modPath) {
        this.modPath = modPath;
    }

    public final String getName() {
        return name;
    }

    public final Actor getParent() {
        return parent;
    }

    public final List<Actor> getChildren() {
        return children;
    }
    
    //从直属的孩子节点之中找到符合id的
    public final Actor getChild(long id){
        return childMap.get(id);
    }
    
    //循环迭代孩子实体，找到符合id的
    public final Actor findChild(long id){
        if(this.id==id)
            return this;
        
        Iterator<Actor> childiter = children.iterator();
        while(childiter.hasNext()){
            Actor child = childiter.next();
            Actor dest=child.findChild(id);
            if(dest!=null)
                return dest;
        }
        
        return null;
    }
    
    //从直属孩子节点之中查找符合name的
    public final Actor getChild(String name){
        Iterator<Actor> childiter = children.iterator();
        while(childiter.hasNext()){
            Actor child = childiter.next();            
            if(child.name.equals(name)){
                return child;
            }
        }                        
        return null;
    }
    
    //迭代循环孩子实体，找到符合name的实体
    public final Actor findChild(String name){
        if(this.name.equals(name))
            return this;
        
        Iterator<Actor> childiter = children.iterator();
        while(childiter.hasNext()){
            Actor child = childiter.next();  
            Actor dest=child.findChild(name);
            if(dest!=null){
                return dest;
            }
        }                
        
        return null;
    }    
    
    //添加到直属孩子节点之中
    public final void addChild(Actor child) {
        if(childMap.containsKey(child.id)){
            return;
        }

        children.add(child);
        if (child.parent != null){
            child.parent.removeChild(child.id);
        }
        child.parent = this;
        childMap.put(child.id, child);
    }

    //从直属孩子节点之中删除
    public final void removeChild(long actorid) {
        Actor child=childMap.remove(actorid);
        children.remove(child);
        child.parent = null;        
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final void setParent(Actor parent) {
        this.parent = parent;
    }

    public void removeThisActor(){
        deleted=true;
    }
    public double getAbsoluteX(){
        Actor parent=this.parent;
        double ax=this.transform.position.getX();
        while(parent!=null){
            ax+=parent.transform.position.getX();
            parent=parent.parent;
        }
        return ax;
    }
    public double getAbsoluteY(){
        Actor parent=this.parent;
        double ay=this.transform.position.getY();
        while(parent!=null){
            ay+=parent.transform.position.getY();
            parent=parent.parent;
        }
        return ay;
    }
    public double getAbsoluteZ(){
        Actor parent=this.parent;
        double az=this.transform.position.getZ();
        while(parent!=null){
            az+=parent.transform.position.getZ();
            parent=parent.parent;
        }
        return az;
    }

    
    //消息转交由组件应答
    @Override
    public boolean handle(Message msg){
        
        Iterator<ActorComponent> compiter = componentsMap.values().iterator();
        while(compiter.hasNext()){
            ActorComponent comp = compiter.next();
            if(comp.handle(msg))return true;
        }        
        return false;
    }
    
    List<Actor> deletedchildlist = new ArrayList<>();
    public final void updateActor(long time){
        if(deleted)
            return;

        Iterator<ActorComponent> compiter = componentsMap.values().iterator();
        while(compiter.hasNext()){
            ActorComponent comp = compiter.next();            
            if(comp.isActive()){
                comp.update(time);                
            }
        }
        
        //update child
        Iterator<Actor> childiter = children.iterator();
        while(childiter.hasNext()){
            Actor child = childiter.next();
            if(child.deleted){
                deletedchildlist.add(child);
            }
            child.updateActor(time);
        }
        
        //remove deleted actor
        childiter=deletedchildlist.iterator();
        while(childiter.hasNext()){
            Actor child = childiter.next();
            children.remove(child);
        }        
        deletedchildlist.clear();
    }    
}
