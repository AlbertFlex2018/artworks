## arch for avg game.
## based on afengine-v0.1.

# /avg/stage
# /avg/story/action
# /avg/ui
# /avg/config
# /avg/logic
# /avg/render

========================
## classes for archi
========================
## stage arch /avg/stage
StoryStage,Story,Player,IStoryAction,IXMLStoryActionBoot,StageFileHelp,

## action arch /avg/story/action
WordAction,BackAction,BgmAction,PlayerAction,LinkAction,CGAction,AttributeAction,

## ui arch /avg/ui
AvgUiCenter,

## config arch /avg/config
AvgConfig,ConfigFileHelp,

## logic arch /avg/logic
AvgMainLogic,...

## render arch /avg/render
WordDialogRender,
  
========================  
### use for all classes.  
========================  
## stage arch  
保存关于故事剧情和舞台的相关内容
StoryStage:  
一个故事舞台保存所有相关的故事剧情Story,并提供一个接口获取目前的Story，  
一个故事舞台保存所有相关的人物Player,并提供接口获取相关Player  
api:  
  add-contains-find-removeStory,  
  add-contains-find-removePlayer,  
  
Story:  
一个故事剧情类保存了相关的一系列动作  
api:  
  add-contains-find-removeStoryAction,  

Player:
人物保存人物的名称，立身图映射，
  
IStoryAction:  
一个剧情动作接口可以改变关于剧情，关于人物，和关于故事舞台  
api:  
  action(actionname)  

IXMLStoryActionBoot
一个剧情动作引导接口从xml文件导入动作  
api:
  bootAction(element);
  
### action arch
实现了一些内置的剧情动作
WordAction,BackAction,BgmAction,PlayerAction,LinkAction,CGAction,SelectAction,
  
  
### ui arch
保存了界面的相关操作
AvgUiCenter
界面中心保存了游戏操作的face,包括主界面时候的face和游戏界面的face,包括弹出选项的face
api:
  add-contains-find-removeUIFace,

### config arch  
保存了avg游戏的状态  
AvgConfig  
配置类全局保存了一系列故事舞台StoryStage  
此外还全局保存了目前故事舞台、故事剧情、剧情动作的索引，背景音乐的状态，对白的状态，人物显示的状态，自定义数据 
api:  】‘
{}
  add-contains-find-remove:StoryStageMap
  set-get:stageIndex,storyIndex,actionIndex  
  get/setbgm:midiId
  set-get:word
  add-contains-find-remove:PlayerMap
  add-contains-get-remove:attributes

ConfigFileHelp
提供了从xml文件读取故事舞台、剧情、动作和一些其他相关的状态的能力
  bootConfig(element);
  loadoutConfig(element,avgconfig);

### logic arch /avg/logic
保存了游戏的逻辑
AvgMainLogic
  
### render arch /avg/render
保存了对话框的自定义的渲染器
WordDialogRender,

======================
## default option
======================
window:600X800
title:albertgame-avg