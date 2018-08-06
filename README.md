# wheelmvc

wheelmvc顾名思义，就是造了一个轮子，本项目为在学习springmvc框架过程中基于自己的理解实现类似springmvc功能
参照了springmvc的思想，但实现方法肯定较springmvc有很多缺点

---

## simpleHttpservlet版本
实现最基本的Httpservlet，接收http请求，返回页面
## simpleHttpservletWithDatabase版本
在simpleHttpservlet版本基础上增加对数据库的访问
## wheelmvc版本
- 使用 Java 注解取代 XML 配置
- 基于 Servlet 3.0
- 实现了MVC、IOC、ORM

本项目实现类似springmvc功能，framework包里为框架代码，wheelProject包里为基于框架实现的测试应用

框架配置文件为wheel.properties，默认配置信息储存在ConfigConstant接口中

ClassUtil自定义类加载器以实现类加载

定义多种注解：@Controller,@Inject,@Service,@Action @Aspect

BeanHelper实现最简单的Bean容器，所有对象均为单例

IOCHelper实现@Inject的依赖注入

ControllerHelpers实现@Controller类中@Action请求地址与方法匹配

DispatcherServlet实现请求转发到框架

实现View返回jsp页面(类似于ModelAndView)和Data返回JSON数据

---

### 2018-08-06 更新内容



利用CGLib实现AOP功能，目前只实现了对整个类所有方法的代理并且只能对有注解的类有效，如@Controller,@Service

AopHelper 实现代理功能 

切面类需继承AspectProxy类并添加注解@Aspect(Controller.class)或@Aspect(Service.class)

---

