## The afengine is based from core,and some plugs.  
## core contains the app,window,and util for the games  
## plugs contain the big quantity components,and parts for game.  
  
## the architure for afengine,is following.  
  
# /core  
# /core/window  
# /core/util  
  
# /part/uiinput  
# /part/message  
# /part/scene  
# /part/sound  
  
# /component/action  
# /component/render  
# /component/behavior  
  
============================  
## arch for afengine classes.  
============================  
## the core arch - /core  
IAppLogic,AbApp,WindowApp,ServiceApp,AppState  
AbPartSupport,AppPartsManager  
  
## the core window arch - /core/window  
IColor,IFont,ITexture,IGraphicsCreate,IGraphicsState,IGraphicsDraw,  
IGraphicsTech,IWindowAdjushHandler,IDrawStrategy  
RenderStratey  
  
## the core util arch -  /core/util  
Debug,IDCreator,TextCenter,Transform,Vector  
AbProcess,ProcessManager,ProcessState  
XMLEngineBoot,IXMLPartBoot,IXMLAppTypeBoot  
  
============================  
## the part input arch - /part/uiinput  
InputAdapter,InputServlet,  
UIActor,UIFace,UIInputCenter,UIDrawStrategy,UIInputRoute,UIInputPart,UIInputFileHelp,XMLUIInputPartBoot,  
UIButtonBase,UITextButton,UIImageButton,UITextLabel,UIImageLabel,UIToggle,UIInputLine,UIPane,UIButtonList
  
## the part message arch - /part/message  
AbMessageRoute,Message,MessageCenter,MessagePart,XMLMessagePartBoot  
IMessageHandler,BigMessageHandler,MessageHandlerRoute,  
  
## the part scene arch - /part/scene  
Actor,ActorComponent,IComponentFactory,ActorMessageRoute  
Scene,AbSceneLoader,SceneCenter,ScenePart,SceneFileHelp,XMLScenePartBoot,  
  
## the part sound arch - /part/sound  
Sound,SoundFilter,LoopingByteInputStream,FilterSequence,Filter3d,EchoFilter,ThreadPool  
MidiPlayer,SoundPlayer,SoundCenter,SoundPart,XMLSoundPartBoot  
  
==============================  
## the component action arch - /component/action  
ActionComponent,AbAction,SpriteAction,TimedAction,ActionComponentFactory  
  
## the component render arch - /component/render  
RenderComponent,TextRenderComponent,TextureRenderComponent,RenderComponentFactory  
GraphicsTech_Java2D,SceneDrawStrategy,  
  
## the component behavior arch - /component/behavior  
ActionBehavior,BehaviorBeanComponent,BehaviorBeanComponentFactory  