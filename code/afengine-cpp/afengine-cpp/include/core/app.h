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
class App;

//class implements App for console running.
class ServiceApp;

class AbPartSupport {
private:
	string _name;
public:
	AbPartSupport(string name) :_name(name) {}
	string getName() { return _name; }
	
	virtual bool initPart()=0;
	virtual bool updatePart(long time)=0;
	virtual bool shutdownPart()=0;
};

class PartSupportManager {
private:
	map<string, AbPartSupport*> partMap;
	map<string, int>	      namePriorityMap;
	map<int, AbPartSupport*>  needUpdateMap;
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