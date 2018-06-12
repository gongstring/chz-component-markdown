# JAVA版Markdown文档解析生成器

如何优雅的写文档？

##  1、使用方法

>   修改配置文件

application.yml文件中docDir属性，是指文档以及配置文件的目录地址，其中包括properties目录下面的菜单路由配置以及图片路径。


>   运行方式

该工程使用JDK1.8+SpringBoot，无需数据库，只有菜单配置和markdown文档编写两部分；另外使用了MDTool工具类，并且做了扩展MDToolExt（支持多编码集）。

命令行执行：
```
    gradle bootRun
```

服务器运行：
```
    nohup java -jar /opt/server/app_wiki/chz-component-markdown-1.0-SNAPSHOT.jar &
    
    
    备注：可以通过在命令中添加docDir参数的方式，直接指定运行路径
```


>   编写Markdown文档

    可以将自己已经编写好的Markdown文档放到服务器的任何位置，只需要在菜单配置文件中添加路径。前端将会自动生成标准样式的页面。

>   修改菜单文件
    
    {docDir}/properties/wiki_dev.xml   开发环境菜单配置文件
    
    {docDir}/properties/wiki_demo.xml   演示环境菜单配置文件
    

>   菜单文件示例

```
    <?xml version="1.0" encoding="UTF-8"?>
    <menus name="远程医疗协同平台文档接口V0.1（开发版）">
        <group name="开发流程">
            <menu id="overview" name="概述" path="/oauth2/dev/main.md" />
            <menu id="prepare" name="开发前准备" path="/oauth2/dev/userinfo.md" />
        </group>
        <group name="开发文档">
            <menu id="hospital" name="医院信息">
                <menu id="hospitalAll" name="1.全量查询" absolute="false" path="/oauth2/dev/hospital/all.md" />
                <menu id="hospitalAdd" name="2.新增" path="/oauth2/dev/hospital/add.md" />
                <menu id="hospitalEdit" name="3.修改" path="/oauth2/dev/hospital/edit.md" />
                <menu id="hospitalDel" name="4.删除" path="/oauth2/dev/hospital/del.md" />
                <menu id="hospitalListening" name="5.数据变更监听" path="/oauth2/dev/hospital/listening.md" />
            </menu>
            <menu id="org" name="科室信息">
                <menu id="orgAll" name="1.全量查询" absolute="false" path="/oauth2/dev/org/all.md" />
                <menu id="orgAdd" name="2.新增" path="/oauth2/dev/org/add.md" />
                <menu id="orgEdit" name="3.修改" path="/oauth2/dev/org/edit.md" />
                <menu id="orgDel" name="4.删除" path="/oauth2/dev/org/del.md" />
                <menu id="orgListening" name="5.数据变更监听" path="/oauth2/dev/org/listening.md" />
            </menu>
            <menu id="person" name="医生信息">
                <menu id="personAll" name="1.全量查询" absolute="false" path="/oauth2/dev/person/all.md" />
                <menu id="personAdd" name="2.新增" path="/oauth2/dev/person/add.md" />
                <menu id="personEdit" name="3.修改" path="/oauth2/dev/person/edit.md" />
                <menu id="personDel" name="4.删除" path="/oauth2/dev/person/del.md" />
                <menu id="personListening" name="5.数据变更监听" path="/oauth2/dev/person/listening.md" />
            </menu>
        </group>
    </menus>
    
```
>   menus 节点属性说明：

```
    name:       文档名称
```

>   group   分组节点

```
    name    分组名称
```

>   group--menu
```
    id :                菜单唯一ID（全局唯一ID）
    name:               菜单标题名称
    absolute:           文档是否直接读取服务器上的绝对路径
    path:               菜单路径（如果absolute为true，则此路径为绝对路径，如果absolute为false，则此路径为相对于menus节点中{docDir}的相对路径）
    group--menu--menu:  子菜单
```

>   图片文件路径

```
    rootPath目录中的image目录
```

##  2、缓存更新

修改了xml配置文件后，如果需要更新菜单，需要通过先访问如下地址清空缓存：

    http://127.0.0.1:8894/clear

##  3、功能描述

>   首页访问地址：

```
    开发环境：http://127.0.0.1:8894/wiki/dev/index.html
    演示环境：http://127.0.0.1:8894/wiki/demo/index.html
```

>   功能演示地址：[http://wiki.cxkh.17vote.com/wiki/dev/index.html](http://wiki.cxkh.17vote.com/wiki/dev/index.html)

##  4、版本说明

>   V1.0

```
    第一个版本功能，开源上传
```

>   V1.1

```
    修改LocalCacheUtil工具类，处理线程不安全等BUG；
```


##   备注

>   负责人：gongstring

>   联系方式：gongstring@foxmail.com

>   微信：gongstring