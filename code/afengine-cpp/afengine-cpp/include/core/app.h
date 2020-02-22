#pragma once

#include <string>
#include <map>
using std::string;
using std::map;

class IPartSupport;
class PartSupportManager;
class IAppLogic;
class App;

//class implements App for console running.
class ServiceApp;

class IPartSupport {
public:
	virtual string getName() = 0;
	
	virtual bool initPart()=0;
	virtual bool updatePart(long time)=0;
	virtual bool shutdownPart()=0;
};

class PartSupportManager {
private:
	map<string, IPartSupport*> partMap;
	map<string, int>	      namePriorityMap;
	map<int, IPartSupport*>  priorityMap;
public:
	PartSupportManager() {}
	
	bool addPart(IPartSupport* IPartSupport);
	bool addPart(IPartSupport* IPartSupport, int order);
	bool hasPart(string partname);
	bool removePart(string partname);
	IPartSupport* getPart(string partname);

	int getPartOrder(string partname);
	bool changePartOrder(string partname, int newOrder);
	
	bool initAllParts();
	bool updateAllParts(long deltime);
	bool shutdownAllParts();
};