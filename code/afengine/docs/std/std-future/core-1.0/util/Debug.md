## 1.本类、接口、枚举名称
# Debug 调试类

## 2.本类设计目的
设计为运行中调试的手段。

## 3.本类的职责概要
游戏运行中对日志或一些断言的手段和方法。

## 4.本类的使用方式和生命周期
### 使用方式
调用断言方法，日志输出方法，在任何地方都可以使用

### 生命周期
无

## 5.本类需要实现的标准api说明
	
	void assert(statement,falseInfo)	- 如果处于调试模式，检查statement语句是否为true，如果不是，则-使用日志输出falseInfo，
	void assertNotNull(obj,nullIfo)	- 如果处于调试模式，检查是否为null，如果时null则输出nullInfo，

	void log(info)	- 如果处于调试模式，调用日志输出到控制台
	void log(info,filepath)	- 此方法在任何情况下都可以使用，调用日志输出添加入目标文件

## 6.相关其他类或者标准
