## 1.本类、接口、枚举名称
# IDCreator ID创造器类  

## 2.本类设计目的
为全局创建唯一的ID

## 3.本类的职责概要
创建唯一的ID


## 4.本类的使用方式和生命周期
### 使用方式
如果需要获取唯一ID值，则调用createID方法。

### 生命周期
无生命周期，静态方法

## 5.本类需要实现的标准api说明
	
	static long createId()	- 获取唯一id，此方法创建的id值不可能相同

## 6.相关其他类或者标准