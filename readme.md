# WordLadder 网页展示

## 页面展示

该网页已上线，直接访问bilibili.cqdulux.cn即可看到效果

## 项目架构

简要介绍项目中的重要文件

- wordladder.js

  WordLadder类的实现，字典的读取，Express框架下的http服务

- dictionary.json

  为方便读取，将原有的txt字典转化为json格式文件

- private.css

  自定义CSS存放

- wordshow.js

  WordLadder页面展示重要组件

- index.html

  主页面，包含ajax和页面动态调整的JS

### 项目特点

1. 使用Express作后端，前端使用jQuery库，前后端分离
2. 页面输入值存在validate的行为
3. ajax使得页面部分刷新
4. 页面展示效果炫酷