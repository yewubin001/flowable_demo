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
    <a href="/black-list-remove/add" class="btn btn-default">新增加</a>&nbsp;&nbsp;
    <a href="/black-list-remove/clean" class="btn btn-danger">清除所有</a>
    <br/><br/>
    <h1>申请列表</h1>
    <hr/>
    <table class="layui-table">
        <thead>
        <th>描述</th>
        <th>PROC_INS_ID</th>
        <th>date</th>
        <th>编号</th>
        <th>名称</th>
        <th>前节点</th>
        <th>前节点处理人</th>
        <th>当前节点</th>
        <th>当前节点处理人</th>
        <th>当前节点开始</th>
        <th>操作</th>
        <th>图</th>
        </thead>
        <tbody id="apply_list_tbody">
        <#if apply_list??>
            <#list apply_list as apply_info>
                <tr>
                    <td>${apply_info.desc}</td>
                    <td>${apply_info.processInsId}</td>
                    <td>${apply_info.date}</td>
                    <td>${apply_info.businessKey}</td>
                    <td>${apply_info.processName}</td>
                    <td>${apply_info.previousNodeName!}</td>
                    <td>${apply_info.previousNodeAssignee!}</td>
                    <td>${apply_info.currNodeName}</td>
                    <td>${apply_info.currNodeAssignee}</td>
                    <td>${apply_info.currNodeStartDate}</td>
                    <td><a href="/black-list-remove/apply?taskId=${apply_info.taskId}&action=apply">审批</a></td>
                    <td><a target="_blank" href="/black-list-remove/diagram?processId=${apply_info.processInsId}">流程图</a></td>
                </tr>
            </#list>
        </#if>
        </tbody>
    </table>
    <br/>
    <h1>审核列表</h1>
    <hr/>
    <table class="layui-table">
        <thead>
        <th>描述</th>
        <th>PROC_INS_ID</th>
        <th>date</th>
        <th>编号</th>
        <th>名称</th>
        <th>前节点</th>
        <th>前节点处理人</th>
        <th>当前节点</th>
        <th>当前节点处理人</th>
        <th>当前节点开始</th>
        <th>操作</th>
        <th>操作</th>
        <th>图</th>
        </thead>
        <tbody id="apply_list_tbody">
        <#if audit_list??>
            <#list audit_list as info>
                <tr>
                    <td>${info.desc}</td>
                    <td>${info.processInsId}</td>
                    <td>${info.date}</td>
                    <td>${info.businessKey}</td>
                    <td>${info.processName}</td>
                    <td>${info.previousNodeName!}</td>
                    <td>${info.previousNodeAssignee!}</td>
                    <td>${info.currNodeName}</td>
                    <td>${info.currNodeAssignee}</td>
                    <td>${info.currNodeStartDate}</td>
                    <td><a href="/black-list-remove/apply?taskId=${info.taskId}">审批</a></td>
                    <td><a href="/black-list-remove/roll_back?taskId=${info.taskId}">退回</a></td>
                    <td><a target="_blank" href="/black-list-remove/diagram?processId=${info.processInsId}">流程图</a></td>
                </tr>
            </#list>
        </#if>
        </tbody>
    </table>
    <br/>
    <h1>完成列表</h1>
    <hr/>
    <table class="layui-table">
        <thead>
        <th>名称</th>
        <th>描述</th>
        <th>发起人</th>
        <th>PROC_INS_ID</th>
        <th>编号</th>
        <th>开始</th>
        <th>结束</th>
        <th>耗时（毫秒）</th>
        <th>最后节点</th>
        <th>最后审批人</th>
        <th>通知完成</th>
        <th>流程图</th>
        <th>审批记录</th>
        </thead>
        <tbody id="apply_list_tbody">
        <#if finished_list??>
            <#list finished_list as info>
                <tr>
                    <td>${info.processName}</td>
                    <td>${info.desc}</td>
                    <td>${info.starterName}</td>
                    <td>${info.processInsId}</td>
                    <td>${info.businessKey}</td>
                    <td>${info.startTime}</td>
                    <td>${info.endTime}</td>
                    <td>${info.durationInMillis}</td>
                    <td>${info.currNodeName}</td>
                    <td>${info.currNodeAssignee}</td>
                    <td>${info.handlerTrigger!}</td>
                    <td><a target="_blank" href="/black-list-remove/diagram?processId=${info.processInsId}">流程图</a></td>
                    <td><a target="_blank" href="/black-list-remove/comment?processId=${info.processInsId}">审批记录</a></td>
                </tr>
            </#list>
        </#if>
        </tbody>
    </table>

</body>
<script>
    $(function () {

    })
</script>
</html>
