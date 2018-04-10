<html>
<head>
    <title>${docName}</title>
    <link href="/css/markdown7.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <style type="text/css">
        body{
            width:100%;
        }

        #contentDiv{
            max-width:1024px;
        }

        .group{
            margin-top:10px;
        }

        .group .title{
            background-color: #2d8cf0;
            color:#fff;
            height: 30px;
            line-height: 30px;
            padding-left: 10px;
        }

        .group ul.menus li{
            height:30px;
            line-height: 30px;
        }

        .group ul.menus li.active,.group ul.menus li.menu:hover{
            background-color: #a7d8ff;
            cursor: pointer;
            padding-left:5px;
        }

    </style>
</head>
<body style="margin: 0px;padding: 0px;">
<div style="width:100%;height: 100%">
    <div style="float: left;width:18%;height:100%;overflow:auto;background-color: #eee" id="linkDiv">
        <div style="line-height: 30px;font-size:14px;padding-left: 10px;">
            ${docName}
        </div>
        <#list groups as group>
        <div class="group">
            <div class="title">${group.name}</div>
            <ul class="menus">
                <#if group.menus??>
                    <#list group.menus as menu>
                        <#if menu.id ??>
                        <li id="${menu.id}" <#if menu.path?? && menu.id??>class="menu" </#if> >${menu.name}</li>
                            <#if menu.children??>
                                <ul>
                                <#list menu.children as child>
                                    <#if child.id??>
                                    <li id="${child.id}" <#if child.path?? && child.id??>class="menu" </#if>>${child.name}</li>
                                    </#if>
                                </#list>
                                </ul>
                            </#if>
                        </#if>
                    </#list>
                </#if>
            </ul>
        </div>
        </#list>

    </div>
    <div style="float:left;width:82%;height: 100%;overflow: auto;">
        <div id="contentDiv" style="padding-left: 10px;">
        </div>
    </div>
</div>
<script type="text/javascript">
    $().ready(function () {
        $("#linkDiv ul li.menu").bind("click",function(i){
            location.href="/wiki/${profile}/index.html#"+$(this).attr("id");
            changePage($(this).attr("id"));
        })

        var param = getParam(0);
        if(param == '')
            changePage($("#linkDiv ul li").first().attr("id"));
        else
            changePage(param);
    });

    function changePage(id){
        $("#linkDiv ul li").removeClass("active");
        $("#linkDiv ul").find("[id='"+id+"']").addClass("active");

        $.get("/markdown/${profile}/"+id,function(result){
            $("#contentDiv").html(result);
        });
    }

    /**
     * 获取参数信息
     * @param index 从0开始编起
     * @returns {string}
     */
    function getParam(index){
        var str=location.href; //取得整个地址栏
        var num=str.indexOf("#");
        if(num <= 0)
            return '';

        str=str.substr(num+1); //取得所有参数   stringvar.substr(start [, length ]
        if(str.length > 0){
            var arr=str.split("/"); //各个参数放到数组里
            if(arr.length > index)
                return arr[index];
        }
        return "";
    }
</script>
</body>
</html>
