#include "../../../include/core/app.h"

//Class define for PartSupportManger
bool PartSupportManager::hasPart(string partname) {
	return this->partMap.count(partname) != 0;
}

bool PartSupportManager::removePart(string partname) {

	if (this->partMap.find(partname) == this->partMap.end())
		return false;

	int order = this->namePriorityMap.at(partname);
	this->partMap.erase(partname);
	this->namePriorityMap.erase(partname);
	this->needUpdateMap.erase(order);
	this->priorityMap.erase(order);

	return true;
}

bool PartSupportManager::addPart(AbPartSupport* partsupport, int order){
	return addPart(partsupport, order, true);
}
bool PartSupportManager::addPart(AbPartSupport* partsupport, int order, bool needUpdate) {

	if (partsupport == nullptr)return false;

	int count = this->partMap.count(partsupport->getName());
	if (count!=0)
		return false;


	this->partMap.insert(
		std::pair<string, AbPartSupport*>(partsupport->getName(), partsupport));
	this->namePriorityMap.insert(
		std::pair<string, int>(partsupport->getName(), order));
	this->priorityMap.insert(
		std::pair<int, AbPartSupport*>(order, partsupport));
	if (needUpdate) {
		this->needUpdateMap.insert(
			std::pair<int, AbPartSupport*>(order, partsupport));
	}

	return true;
}

AbPartSupport* PartSupportManager::getPart(string partname) {

	if (this->partMap.find(partname) == this->partMap.end())
		return nullptr;


	return this->partMap.at(partname);
}

int PartSupportManager::getPartOrder(string partname) {
	if (this->partMap.find(partname) == this->partMap.end())
		return -1;


	return this->namePriorityMap.at(partname);
}

bool PartSupportManager::changePartOrder(string partname, int newOrder) {
	if (this->partMap.find(partname) == this->partMap.end())
		return false;


	int order = this->namePriorityMap.at(partname);
	AbPartSupport* part = this->partMap.at(partname);
	this->namePriorityMap.erase(partname);
	this->namePriorityMap.insert(
		std::pair<string, int>(partname, newOrder));

	this->priorityMap.erase(order);
	this->priorityMap.insert(
		std::pair<int, AbPartSupport*>(newOrder, part));

	if (this->needUpdateMap.find(order) != this->needUpdateMap.end()) {
		this->needUpdateMap.erase(order);
		this->needUpdateMap.insert(
			std::pair<int, AbPartSupport*>(newOrder, part));
	}

	return true;
}

bool PartSupportManager::initAllParts() {
	map<int, AbPartSupport*>::iterator iter;
	bool result=true;
	for (iter = this->priorityMap.begin(); 
		iter != this->priorityMap.end(); ++iter) {
		if (iter->second->initPart() == false) {
			//do something
			result = false;
			continue;
		}

	}

	return result;
}

bool PartSupportManager::updateAllParts(long deltime) {
	map<int, AbPartSupport*>::iterator iter;
	bool result = true;
	for (iter = this->needUpdateMap.begin();
		iter != this->needUpdateMap.end(); ++iter) {
		if (iter->second->updatePart(deltime) == false) {
			//do something
			result = false;
			continue;
		}
	}

	return result;
}
bool PartSupportManager::shutdownAllParts() {
	map<int, AbPartSupport*>::reverse_iterator iter;
	bool result = true;
	for (iter = this->priorityMap.rbegin();
		iter != this->priorityMap.rend(); ++iter) {
		if (iter->second->shutdownPart() == false) {
			//do something
			result = false;
			continue;
		}
	}

	return result;
}


//class define for AbApp
void AbApp::setValue(string key, string value) {

	this->attributes.erase(key);
	this->attributes.insert(std::pair<string, string>(key, value));
}

string AbApp::getValue(string key) {
	bool has = this->attributes.find(key) != this->attributes.end();
	if (has)
		return this->attributes[key];
	else {
		this->attributes.insert(std::pair<string, string>(key, ""));
		return "";
	}
}

void AbApp::run(IAppLogic* logic){
	if (this->instanceApp != nullptr)
		return;

	this->_logic = logic;		
	AbApp::instanceApp = this;
		
	initApp();
	this->getPartSupportManager().initAllParts();

	if (_logic!=nullptr) {
		_logic->initLogic();
	}
}

void AbApp::update(long delttime) {

	this->getPartSupportManager().updateAllParts(delttime);

	if (_logic != nullptr) {
		_logic->updateLogic(delttime);
	}

	this->updateApp(delttime);
}
void AbApp::end() {

	if (_logic != nullptr) {
		_logic->shutdownLogic();
	}

	this->getPartSupportManager().shutdownAllParts();
	shutdownApp();
}
void AbApp::exitAppUpdate() {
	this->_running = false;
}

AbApp* AbApp::instanceApp = nullptr;

AbApp* AbApp::getRunningApp() {
	return AbApp::instanceApp;
}
