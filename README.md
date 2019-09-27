## Spring-boot 开发多人在线博客平台

### Docker安装(Windows)

- 进入[官网](https://hub.docker.com/?overlay=onboarding)直接下载Docker软件(它会提示在注册账号)
- 安装Docker 

```docker
docker --version  //查看docker的版本，如果有显示表示安装成功
```

- 安装成功后，配置Docker镜像(提高下载其他工具速度，毕竟是国外的网站)
- 在Docker Settings 里Daemon里面配置Registry mirros: `https://registry.docker-cn.com`(国内的镜像文件)

### Docker 创建MySQL

- 下载MySQL,运行如下命令

  `docker pull mysql`

- 等待下载成功,创建一个零时MySQL

  ```docker
  docker run --name my-mysql -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=data -p 3306:3306 -d mysql
  
  命令说明:
  -p 3306:3306	:将主机的端口映射到容器端口(前面端口是主机端口)
  -e MYSQL_ROOT_PASSWORD=password		:初始化root用户的密码
  -e MYSQL_DATABASE=data				:创建名为data的数据库
  --name								:为docker创建的当前容器命名
  ```

  

### Flyway

- [官网](https://flywaydb.org/documentation/maven/)
- 配置maven依赖
- 配置项目的目录

```
 src
     main
       resources
         db
             migration                 
                 U1_Create_User_table.sql
                 U1_Create_Blog_table.sql
```

- 在.sql文件中存放建表SQL语句，方便项目的移植(可以直接用mvn运行建表，不用每一次移植都要**手动创建**)

- docker启动数据库之后，使用如下命令可以自动创建表结构

  `mvn flyway:migrate`

###  项目结构

- Controller层：主要对前端的请求参数验证和清洗
- Service层：主要处理业务逻辑的方法实现，依赖于DAO层对数据库的操作
- DAO层：提供访问数据库系统所需操作的接口，DAO 模式的优势就在于它实现了两次隔离：隔离了数据访问代码和业务逻辑代码，隔离了不同数据库实现。
- entity：主要用于存放固定数据类型的类
- configuration：用于存放`web`相关安全配置和`java`配置

### 自动化测试

本项目添加了自动化测试文件，使用的是**Travis CI**

- 单元测试：

  在Controller单元测试，先测试单个功能；通过mock的方式，创建假的参数传进去，检测功能是否成功实现；当假参数传入函数没有返回值时，采用when().thenReturn函数进行条件返回设置

- 集成测试：

  创建一个Get请求向目标地址发送，检测返回的Responsebody是否含有登录失败的信息

- Travis CI：需要配置.travis.yml文件，绑定`mvn verify`的周期，实现自动化测试业务功能