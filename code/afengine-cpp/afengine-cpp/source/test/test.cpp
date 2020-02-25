#include "../../include/core/app.h"
#include <iostream>
using namespace std;

class PartSupport1 :public AbPartSupport {
public:
	PartSupport1(string name) :AbPartSupport(name) {}

	bool initPart(){
		cout << "init PartSupport"<<this->getName() << endl;
		return true;
	}

	bool updatePart(long time) {
		cout << "update PartSupport" << this->getName() << endl;
		return true;
	}

	bool shutdownPart() {
		cout << "shutdown PartSupport" << this->getName() << endl;
		return true;
	}
};

class AppLogic1 :public IAppLogic {
public:
	bool initLogic() {
		cout << "init Logic" << endl;
		return true;
	}
	bool updateLogic(long delttime) {
		cout << "update Logic" << endl;
		return true;
	}
	bool shutdownLogic() {
		cout << "shutdown Logic" << endl;
		return true;
	}
};

class App1 :public AbApp {
public:
	App1(string name) :AbApp("app1", name) {}
protected:
	bool initApp() {
		cout << "init App" <<this->getName()<< endl;
		return true;
	}
	bool updateApp(long delttime) {
		cout << "update App" << this->getName() << endl;
		return true;
	}
	bool shutdownApp() {
		cout << "shutdown App" << this->getName() << endl;
		return true;
	}
};

int main(void){
	
	/*
		1.创建游戏逻辑实例
		2.创建游戏应用实例
		3.创建需要的一堆插件部分支持，并且填入游戏应用的插件部分管理器
		
		4.调用游戏应用实例运行方法，填入游戏逻辑，内部自行初始化
	*/

	AppLogic1 logic;
	App1 app("app");

	PartSupport1 part1("part1");
	PartSupport1 part2("part2");
	PartSupport1 part3("part3");
	
	app.getPartSupportManager().addPart(&part1, 1);
	app.getPartSupportManager().addPart(&part2, 2);
	app.getPartSupportManager().addPart(&part3, 3,false);

	app.run(&logic);

	string cmd;
	while (app.isRunning()) {
		app.update(10);
		cin >> cmd;
		if (cmd == "exit") {
			AbApp::getRunningApp()->exitAppUpdate();
		}
		cout << "update app --- " << endl;
	}
	
	app.end();

	return 0;
}