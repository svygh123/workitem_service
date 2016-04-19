# 项目说明

## 环境及框架
　　'dev framework : Spring 3.1.1RELEASE +  jersey 2.2 + servlet-api 3.0.1 + ehcache 2.10.1'  
　　'dev env : MyEclipse 10.5 + Maven 3.0.4'

## 运行
步骤1: http://localhost:8080/workitem_service/rest/auth  
方法: post  
postman里面还需要加2个Body参数:  
username=ndh  
password=ndn  
返回值: {"username":"ndh","token":"-196186307c3c5accec7ed0fc3e8d0a2ef98fbd242"}  
  
步骤2: http://localhost:8080/workitem_service/rest/hello/ndh  
方法: get  
postman里面还需要加1个Headers参数: 
Authorization=Basic -196186307c3c5accec7ed0fc3e8d0a2ef98fbd242  
返回值(JSON): Bad string  
返回值(Text): {'Jersey say':'ndh'}  
  
## 注意
ApplicationResourceConfig里面配置了@ApplicationPath("/rest/*"),因此在
Auth.java和HelloWorldService.java的Path不用加rest了
