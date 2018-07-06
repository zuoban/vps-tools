# vps-tools
## 功能
- 下载文件
## 安装
```
git clone https://github.com/zuoban/vps-tools.git
cd vps-tools
gradle build -x test
```
## 配置
```
# application-prod.yml 配置生产环境下载文件保存路径
storage:
  location: /root/Download 
# applicaton.yml 配置登录用户名和密码, 
# password通过com.zuoban.toy.vpstools.config.WebSecurityConfig#main加密
user:
  user-name: zuoban
  password: $2a$10$V56/vX7qVqBYD3ZlwkIXhuQo8sVjbkjxbO4e9zxDR45C4Zh3U5azK 
```

## 演示
![vps-tools.gif](https://github.com/zuoban/vps-tools/blob/master/assets/img/vps-tools.gif)
