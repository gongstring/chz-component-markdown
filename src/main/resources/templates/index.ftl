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

        .group ul.menus li.menu:hover{
            background-color: #a7d8ff;
            cursor: pointer;
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
            changePage($(this).attr("id"));
        })
        changePage($("#linkDiv ul li").first().attr("id"));
    });

    function changePage(id){
        $.get("/markdown/${profile}/"+id,function(result){
            $("#contentDiv").html(result);
        });
    }
</script>
</body>
</html>
