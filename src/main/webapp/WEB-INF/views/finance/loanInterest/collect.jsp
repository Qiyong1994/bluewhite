<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
	<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>利息汇总</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
</head>

<body>
	<div class="layui-card">
		<div class="layui-card-body">
			<div class="layui-form layui-card-header layuiadmin-card-header-auto">
				<div class="layui-form-item">
					<table>
						<tr>
							<td>借款方:</td>
							<td><input type="text" name="customerName" id="firstNames" class="layui-input" /></td>
							<td>&nbsp;&nbsp;</td>
							<td><select class="layui-input" name="selectone" id="selectone">
									<option value="expenseDate">预计付款日期</option>
									<option value="paymentDate">实际付款日期</option>
								</select></td>
							<td>&nbsp;&nbsp;</td>
							<td><input id="startTime" style="width: 300px;" name="orderTimeBegin" placeholder="请输入时间" class="layui-input laydate-icon"></td>
							<td>&nbsp;&nbsp;</td>
							<td>是否核对:
							<td><select class="form-control" name="flags">
									<option value="0">未审核</option>
									<option value="2">部分审核</option>
									<option value="1">已审核</option>
							</select></td>
							<td>&nbsp;&nbsp;</td>
							<td><button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-search">
									<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
								</button></td>
							<td style="width:130px;"></td>
							<td style="font-size: 20px;">未支付总额:</td>
							<td id="allPrice" style="color:red;font-size: 20px;"></td>
						</tr>
					</table>
				</div>
			</div>
			<table id="tableData" class="table_th_search" lay-filter="tableData"></table>
		</div>
	</div>
<script>
	layui.config({
		base: '${ctx}/static/layui-v2.4.5/'
	}).extend({
		tablePlug: 'tablePlug/tablePlug'
	}).define(
		['tablePlug', 'laydate',],
		function() {
			var $ = layui.jquery,
				layer = layui.layer,
				form = layui.form,
				table = layui.table,
				laydate = layui.laydate,
				tablePlug = layui.tablePlug,
				upload = layui.upload;
			laydate.render({ elem: '#startTime', type: 'date', range: '~', });
			getTotalAmount({flags: '0,2',});
			function getTotalAmount(post){
				$.ajax({
					url: '${ctx}/fince/totalAmount?type=10',
					data: post,
					success:function(r){
						if(r.code==0){
							$('#allPrice').text(r.data);
						}else
							$('#allPrice').html('异常');
					}
				})
			};
			tablePlug.smartReload.enable(true);
			table.render({
				elem: '#tableData',
				size: 'lg',
				height:'700px',
				url: '${ctx}/fince/getConsumption?type=10' ,
				where:{
					flags:'0,2',
				},
				request:{
					pageName: 'page' ,
					limitName: 'size' 
				},
				page: {},
				loading: true,
				cellMinWidth: 90,
				colFilterRecord: true,
				smartReloadModel: true,// 开启智能重载
				parseData: function(ret) {
					return {
						code: ret.code,
						msg: ret.message,
						count:ret.data.total,
						data: ret.data.rows
					}
				},
				cols:[[
				       { align: 'center', field: "content", 	title: "借款方",},
				       { align: 'center', field: "remark", 		title: "借款类型", },
					   { align: 'center', field: "money", 			title: "报销申请金额", },
					   { align: 'center', field: "expenseDate", 	title: "报销申请日期", },
					   { align: 'center', field: "paymentDate",     title: "实际付款日期",},
					   { align: 'center', field: "flag", 			title: "审核状态", 		templet:  function(d){if(d.flag==0){return "未审核";}if(d.flag==1){return "已审核";}if(d.flag==2){return "部分审核";}} }
					 ]],
			});

			form.on('submit(LAY-search)', function(data) {
				var field = data.field;
				var orderTime=field.orderTimeBegin.split('~');
				var orderTimeBegin="";
				var orderTimeEnd="";
				if(orderTime!=""){
				orderTimeBegin=orderTime[0]+' '+'00:00:00';
				orderTimeEnd=orderTime[1]+' '+'23:59:59';
				}
				var a="";
				var b="";
				if($("#selectone").val()=="expenseDate"){
					a="2019-05-08 00:00:00"
				}else{
					b="2019-05-08 00:00:00"
				}
				var post={
					customerName:field.customerName,
					flags:field.flags,
					orderTimeBegin:orderTimeBegin,
					orderTimeEnd:orderTimeEnd,
					expenseDate:a,
					paymentDate:b,
				}
				table.reload('tableData', {
					where: post
				});
			});
			
		}
	)
</script>
</body>

</html>