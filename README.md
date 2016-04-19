# 项目说明

## 1. 实现功能
     实现WebApp与服务器接口交互获取令牌及校验

## 2. 环境及框架
     **dev framework :** Spring 3.1.1RELEASE +  jersey 2.2 + servlet-api 3.0.1 + ehcache 2.10.1
     **dev env :** MyEclipse 10.5 + Maven 3.0.4

## 3. Postman运行
     步骤1:  
     url: http://localhost:8080/workitem_service/rest/auth  
     方法: post  
     Body参数: username=ndh , password=ndn  
     返回值: {"username":"ndh","token":"-196186307c3c5accec7ed0fc3e8d0a2ef98fbd242"}  
     jQuery代码:
```JavaScript
var settings = {
  "async": true,
  "crossDomain": true,
  "url": "http://localhost:8080/workitem_service/rest/auth",
  "method": "POST",
  "headers": {
    "cache-control": "no-cache",
    "postman-token": "e22671c2-56b1-2c77-4d0f-3729bf128b2b",
    "content-type": "application/x-www-form-urlencoded"
  },
  "data": {
    "username": "ndh",
    "password": "ndh"
  }
};
$.ajax(settings).done(function (response) {
  console.log(response);
});
```  
     步骤2:  
     url: http://localhost:8080/workitem_service/rest/hello/ndh  
     方法: get  
     Headers参数: Authorization=Basic -196186307c3c5accec7ed0fc3e8d0a2ef98fbd242  
     返回值(JSON): Bad string , 选择Text后返回值(Text): {'Jersey say':'ndh'}  
     jQuery代码:
```javascript
var settings = {
  "async": true,
  "crossDomain": true,
  "url": "http://localhost:8080/workitem_service/rest/hello/ndh",
  "method": "GET",
  "headers": {
    "authorization": "Basic -196186307c3c5accec7ed0fc3e8d0a2ef98fbd242",
    "cache-control": "no-cache",
    "postman-token": "34a09f95-4c3c-ba39-c21d-3fc00f89128d"
  }
};
$.ajax(settings).done(function (response) {
  console.log(response);
});
```
## 4. 注意
     ApplicationResourceConfig里面配置了@ApplicationPath("/rest/*"),因此在Auth.java和HelloWorldService.java的Path不用加rest了
