# MetabaseRceTools
CVE-2023-38646 Metabase RCE 工具



# CVE-2023-38646 RCE 图形化利用工具

## 验证模块

- 输入指定网址即可检测未授权Token

![image-20231011112444404](C:\Users\22927\AppData\Roaming\Typora\typora-user-images\image-20231011112444404.png)

## 命令执行

- 该模块首先需要执行验证模块获取token才可以使用
- JarLocation：metabase.jar的位置，默认当前目录

![image-20231011112525296](C:\Users\22927\AppData\Roaming\Typora\typora-user-images\image-20231011112525296.png)

## 内存马注入

- 目前仅写了cmd和godzilla模式，通过x-client-data控制
- x-client-data:cmd 给cmd请求头写命令即可
- x-client-data:godzilla 直接连接哥斯拉，默认密码pass

![image-20231011112542389](C:\Users\22927\AppData\Roaming\Typora\typora-user-images\image-20231011112542389.png)



![image-20231011112958066](C:\Users\22927\AppData\Roaming\Typora\typora-user-images\image-20231011112958066.png)

