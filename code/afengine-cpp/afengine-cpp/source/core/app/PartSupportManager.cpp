#include "../../../include/core/app.h"

bool PartSupportManager::addPart(IPartSupport* partsupport) {
	if (partsupport == nullptr)return false;

	int count=this->partMap.count(partsupport->getName());
	if (count != 0) {
		return false;
	}

	this->partMap.insert(
		std::pair<string, IPartSupport*>(partsupport->getName(), partsupport));
	return true;
}

bool PartSupportManager::hasPart(string partname) {
	return this->partMap.count(partname) != 0;
}

bool PartSupportManager::removePart(string partname) {

	int count = this->partMap.count(partname);
	if (count == 0)return false;
	
	int order = this->namePriorityMap.at(partname);
	this->partMap.erase(partname);
	this->namePriorityMap.erase(partname);
	this->priorityMap.erase(order);

	return true;
}

bool PartSupportManager::addPart(IPartSupport* partsupport, int order) {
	if (partsupport == nullptr)return false;

	int count = this->partMap.count(partsupport->getName());
	if (count == 0) {
		return false;
	}

	this->partMap.insert(
		std::pair<string, IPartSupport*>(partsupport->getName(), partsupport));
	this->namePriorityMap.insert(
		std::pair<string, int>(partsupport->getName(), order));
	this->priorityMap.insert(
		std::pair<int, IPartSupport*>(order, partsupport));
	return true;
}

IPartSupport* PartSupportManager::getPart(string partname) {

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
	IPartSupport* part = this->partMap.at(partname);
	this->namePriorityMap.erase(partname);
	this->namePriorityMap.insert(
		std::pair<string, int>(partname, newOrder));
	this->priorityMap.erase(order);
	this->priorityMap.insert(
		std::pair<int, IPartSupport*>(newOrder, part));
	return true;
}

bool PartSupportManager::initAllParts() {
	map<int, IPartSupport*>::iterator iter;
	for (iter = this->priorityMap.begin(); 
		iter != this->priorityMap.end(); ++iter) {
		if (iter->second->initPart() == false)
			return false;
	}

	return true;
}

bool PartSupportManager::updateAllParts(long deltime) {
	map<int, IPartSupport*>::iterator iter;
	for (iter = this->priorityMap.begin();
		iter != this->priorityMap.end(); ++iter) {
		if (iter->second->updatePart(deltime) == false)
			return false;
	}

	return true;
}
bool PartSupportManager::shutdownAllParts() {
	map<int, IPartSupport*>::reverse_iterator iter;
	for (iter = this->priorityMap.rbegin();
		iter != this->priorityMap.rend(); ++iter) {
		if (iter->second->shutdownPart() == false)
			return false;
	}

	return true;
}
