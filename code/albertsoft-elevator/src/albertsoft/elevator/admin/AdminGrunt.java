package albertsoft.elevator.admin;

public enum AdminGrunt {    
    LOOK,//仅仅只是查看，授权给外部的检查机关
    GENERAL,//普通管理员，仅仅只是查看、修改
    CREATOR,//创造者，可以修改，创建
}
