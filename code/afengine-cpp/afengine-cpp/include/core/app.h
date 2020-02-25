#pragma once

#include <string>
#include <map>
#include <list>

using std::list;
using std::string;
using std::map;

class AbPartSupport;
class PartSupportManager;
class IAppLogic;
class AbApp;

//class implements App for console running.
class ServiceApp;

class AbPartSupport {
private:
	string _name;
public:
	AbPartSupport(string name) :_name(name) {}
	string getName() { return _name; }
	
	virtual bool initPart()=0;
	virtual bool updatePart(long deltime)=0;
	virtual bool shutdownPart()=0;
};

class PartSupportManager {
private:
	map<string, AbPartSupport*> partMap;
	map<string, int>	      namePriorityMap;
	map<int, AbPartSupport*>  needUpdateMap;
	map<int, AbPartSupport*>   priorityMap;
public:
	PartSupportManager(){}
	
	bool addPart(AbPartSupport* partsupport, int order);
	bool addPart(AbPartSupport* partsupport, int order, bool needUpdate);
	bool hasPart(string partname);
	bool removePart(string partname);
	AbPartSupport* getPart(string partname);

	int getPartOrder(string partname);
	bool changePartOrder(string partname, int newOrder);
	
	bool initAllParts();
	bool updateAllParts(long deltime);
	bool shutdownAllParts();
};

class IAppLogic {
public:
	virtual bool initLogic()=0;
	virtual bool updateLogic(long deltime)=0;
	virtual bool shutdownLogic()=0;
};

class AbApp {
private:
	static AbApp* instanceApp;

	map<string, string> attributes;
	PartSupportManager manager;
	string				_name;
	string				_type;
	IAppLogic*			_logic;
	bool						_running;
	
public:
	AbApp(string type, string name) :_name(name), _type(type), _running(true), _logic(nullptr){}
	const string& getType() { return _type; }
	const string& getName() { return _name; }
	PartSupportManager& getPartSupportManager() { return this->manager; }

	string getValue(string key);
	void   setValue(string key, string value);
	bool   isRunning() { return _running; }

	void   run(IAppLogic* logic);
	void   update(long time);
	void   end();
	void   exitAppUpdate();

protected:
	//need implements
	virtual bool initApp() = 0;
	virtual bool updateApp(long delttime) = 0;
	virtual bool shutdownApp() = 0;
public:
	static AbApp* getRunningApp();
};