#include "../../../include/core/app.h"

bool PartSupportManager::hasPart(string partname) {
	return this->partMap.count(partname) != 0;
}

bool PartSupportManager::removePart(string partname) {

	int count = this->partMap.count(partname);
	if (count == 0)return false;
	
	int order = this->namePriorityMap.at(partname);
	this->partMap.erase(partname);
	this->namePriorityMap.erase(partname);
	this->needUpdateMap.erase(order);

	return true;
}

bool PartSupportManager::addPart(AbPartSupport* partsupport, int order){
	return addPart(partsupport, order, true);
}
bool PartSupportManager::addPart(AbPartSupport* partsupport, int order, bool needUpdate) {

	if (partsupport == nullptr)return false;

	int count = this->partMap.count(partsupport->getName());
	if (count == 0) {
		return false;
	}

	this->partMap.insert(
		std::pair<string, AbPartSupport*>(partsupport->getName(), partsupport));
	this->namePriorityMap.insert(
		std::pair<string, int>(partsupport->getName(), order));
	if (needUpdate) {
		this->needUpdateMap.insert(
			std::pair<int, AbPartSupport*>(order, partsupport));
	}

	return true;
}

AbPartSupport* PartSupportManager::getPart(string partname) {

	int count = this->partMap.count(partname);
	if (count == 0)return nullptr;

	return this->partMap.at(partname);
}

int PartSupportManager::getPartOrder(string partname) {
	int count = this->namePriorityMap.count(partname);
	if (count == 0)return -1;

	return this->namePriorityMap.at(partname);
}

bool PartSupportManager::changePartOrder(string partname, int newOrder) {
	int count = this->partMap.count(partname);
	if (count == 0)return false;

	int order = this->namePriorityMap.at(partname);
	AbPartSupport* part = this->partMap.at(partname);
	this->namePriorityMap.erase(partname);
	this->namePriorityMap.insert(
		std::pair<string, int>(partname, newOrder));

	int count2 = this->needUpdateMap.count(order);
	if (count2 != 0) {
		this->needUpdateMap.erase(order);
		this->needUpdateMap.insert(
			std::pair<int, AbPartSupport*>(newOrder, part));
	}

	return true;
}

bool PartSupportManager::initAllParts() {
	map<int, AbPartSupport*>::iterator iter;
	bool result=true;
	for (iter = this->needUpdateMap.begin(); 
		iter != this->needUpdateMap.end(); ++iter) {
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
	map<int, AbPartSupport*>::iterator iter;
	bool result = true;
	for (iter = this->needUpdateMap.begin();
		iter != this->needUpdateMap.end(); ++iter) {
		if (iter->second->shutdownPart() == false) {
			//do something
			result = false;
			continue;
		}
	}

	return result;
}
