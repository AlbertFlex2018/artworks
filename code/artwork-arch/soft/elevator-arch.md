## elevator is first project of albert soft.it's written with javafx within jdk1.8 platform,via NetBeans IDE 11.2  
## while this project is not use network to work,instead use local storage.  
## following is the arch  

using console interface.  

======================
## arch for elevator
======================

# /admin  
# /control
# /cmd  

======================  
## all classes for arch  
======================  
   
## admin op - /admin  
Admin,AdminGrunt,AdminCenter  
  
## control op - /control  
Elevator,ElevatorState,ElevatorRunStrategy,  
Building,BuildingCenter,XMLFileHelp,CmdInterface,RunClass,     

## cmd op - /cmd
AdminCmd,BuildingCmd,ElevatorCmd,SystemCmd,