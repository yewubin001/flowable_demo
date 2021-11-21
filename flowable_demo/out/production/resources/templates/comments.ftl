<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>FlowDemo</title>
</head>
<#include "include/common-js.ftl"/>
<#include "include/common-css.ftl"/>
<body class="layui-main">
<#include "include/header.ftl"/>
<hr/>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
    <legend>审批历史</legend>
</fieldset>

<ul class="layui-timeline">
    <#if comments??>
        <#list comments as comment>
            <li class="layui-timeline-item">

                <i class="layui-icon layui-timeline-axis"></i>

                <div class="layui-timeline-content layui-text">

                    <div class="layui-timeline-title">${comment_index + 1}.&nbsp;${comment.processNode!}</div>
                    <table class="">
                        <tr>
                            <td>节点：</td>
                            <td>   ${comment.processNode!} </td>
                        </tr>
                        <tr>
                            <td>办理人：</td>
                            <td>  ${comment.assignee!}</td>
                        </tr>
                        <tr>
                            <td>办理时间：</td>
                            <td>${comment.processTime!} </td>

                        </tr>
                        <tr>
                            <td>办理结果：</td>
                            <td>${comment.processResult!} </td>

                        </tr>
                        <tr>
                            <td>办理意见：</td>
                            <td>${comment.processMemo!}</td>
                        </tr>
                    </table>
                </div>

            </li>
        </#list>
    </#if>
</ul>

</body>
<script>
    $(function () {

    })
</script>
</html>
