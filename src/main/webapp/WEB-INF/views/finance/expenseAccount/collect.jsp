<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
	<!--<![endif]-->

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>财务审核</title>
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	</head>

	<body>
		<section id="main-wrapper" class="theme-default">
			<%@include file="../../decorator/leftbar.jsp"%> 
			<section id="main-content" class="animated fadeInUp">
				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">财务审核</h3>
								<div class="actions pull-right">
									<i class="fa fa-expand"></i>
									<i class="fa fa-chevron-down"></i>
								</div>
							</div>
							<div class="panel-body">
								<div class="layui-form layui-card-header layuiadmin-card-header-auto">
									<div class="layui-form-item">
										<table>
											<tr>
												<td>报销人:</td>
												<td>
													<input type="text" name="username" id="firstNames" class="form-control search-query name" />
												</td>
												<td>&nbsp&nbsp</td>
												<td>报销内容:</td>
												<td>
													<input type="text" name="content" class="form-control search-query" />
												</td>
												<td>&nbsp&nbsp</td>
												<td>
													<select class="form-control"  id="selectone">
														<option  value="">请选择</option>
														<option name="expenseDate" value="2018-10-08 00:00:00">报销申请日期</option>
														<option name="paymentDate" value="2018-10-08 00:00:00">付款日期</option>
													</select>
												</td>
												<td>&nbsp&nbsp</td>
												<td>开始:</td>
												<td>
													<input id="startTime" name="orderTimeBegin" placeholder="请输入开始时间" class="form-control laydate-icon">
												</td>
												<td>&nbsp&nbsp</td>
												<td>结束:</td>
												<td>
													<input id="endTime" name="orderTimeEnd" placeholder="请输入结束时间" class="form-control laydate-icon">
												</td>
												<td>&nbsp&nbsp</td>
												<td>需要支付总额:
													<td>
														<input type="text" id="allPrice" class="form-control search-query" />
													</td>
													<td>&nbsp&nbsp</td>
													<td>
														<div class="layui-inline">
															<button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-search">
														<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
													</button>
														</div>
													</td>
											</tr>
										</table>
									</div>
								</div>
								<table id="tableData" class="table_th_search" lay-filter="tableData"></table>
							</div>

						</div>
					</div>
				</div>
			</section>
		</section>
		</section>

		<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
		<script>
			layui.config({
				base: '${ctx}/static/layui-v2.4.5/'
			}).extend({
				tablePlug: 'tablePlug/tablePlug'
			}).define(
				['tablePlug', 'laydate', 'element'],
				function() {
					var $ = layui.jquery,
						layer = layui.layer //弹层
						,
						form = layui.form //表单
						,
						table = layui.table //表格
						,
						laydate = layui.laydate //日期控件
						,
						tablePlug = layui.tablePlug //表格插件
						,
						element = layui.element;
					//全部字段
					var allField;
					//select全局变量
					var htmls = '<option value="">请选择</option>';
					var index = layer.load(1, {
						shade: [0.1, '#fff'] //0.1透明度的白色背景
					});
					
					laydate.render({
						elem: '#startTime',
						type: 'datetime',
					});
					laydate.render({
						elem: '#endTime',
						type: 'datetime',
					});
				 
					$.ajax({
						url: '${ctx}/system/user/findAllUser',
						type: "GET",
						async: false,
						beforeSend: function() {
							index;
						},
						success: function(result) {
							$(result.data).each(function(i, o) {
								htmls += '<option value=' + o.id + '>' + o.userName + '</option>'
							})
							layer.close(index);
						},
						error: function() {
							layer.msg("操作失败！", {
								icon: 2
							});
							layer.close(index);
						}
					});
					
					
				   	tablePlug.smartReload.enable(true); 
					table.render({
						elem: '#tableData',
						size: 'lg',
						url: '${ctx}/fince/getConsumption' ,
						where:{
							flag:0
						},
						request:{
							pageName: 'page' ,//页码的参数名称，默认：page
							limitName: 'size' //每页数据量的参数名，默认：limit
						},
						page: {
							limit:15
						},//开启分页
						loading: true,
						toolbar: '#toolbar', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
						/*totalRow: true //开启合计行 */
						cellMinWidth: 90,
						colFilterRecord: true,
						smartReloadModel: true,// 开启智能重载
						parseData: function(ret) {
							$('#allPrice').val(ret.data.statData.statAmount)
							return {
								code: ret.code,
								msg: ret.message,
								count:ret.data.total,
								data: ret.data.rows
							}
						},
						cols: [
							[{
								type: 'checkbox',
								align: 'center',
								fixed: 'left'
							}, {
								field: "content",
								title: "报销内容",
								align: 'center',
							}, {
								field: "userId",
								title: "报销人",
								align: 'center',
								search: true,
								edit: false,
								type: 'normal',
								templet: function(d){
									return d.user.userName;
								}
							}, {
								field: "budget",
								title: "是否预算",
								align: 'center',
								search: true,
								edit: false,
								type: 'normal',
								templet: function(d){
									if(d.budget==0){
										return "";
									}
									if(d.budget==1){
										return "预算";
									}
								}
							}, {
								field: "money",
								title: "报销申请金额",
							}, {
								field: "expenseDate",
								title: "报销申请日期",
							}, {
								field: "withholdReason",
								title: "扣款事由",
							}, {
								field: "withholdMoney",
								title: "扣款金额",
							}, {
								field: "settleAccountsMode",
								title: "结款模式",
								search: true,
								edit: false,
								templet:  function(d){
									if(d.settleAccountsMode==0){
										return "";
									}
									if(d.settleAccountsMode==1){
										return "现金";
									}
									if(d.settleAccountsMode==2){
										return "月结";
									}
								}
							}, {
								field: "paymentDate",
								title: "付款时间",
							}, {
								field: "paymentMoney",
								title: "付款金额",
							}, {
								field: "flag",
								title: "审核状态",
								templet:  function(d){
									if(d.flag==0){
										return "未审核";
									}
									if(d.flag==1){
										return "已审核";
									}
								}
							}]
						],
						done: function() {
							var tableView = this.elem.next();
							tableView.find('.layui-table-grid-down').remove();
							var totalRow = tableView.find('.layui-table-total');
							var limit = this.page ? this.page.limit : this.limit;
							layui.each(totalRow.find('td'), function(index, tdElem) {
								tdElem = $(tdElem);
								var text = tdElem.text();
								if(text && !isNaN(text)) {
									text = (parseFloat(text) / limit).toFixed(2);
									tdElem.find('div.layui-table-cell').html(text);
								}
							});
						},
						//下拉框回显赋值
						done: function(res, curr, count) {
							var tableView = this.elem.next();
							var tableElem = this.elem.next('.layui-table-view');
							layui.each(tableElem.find('select'), function(index, item) {
								var elem = $(item);
								elem.val(elem.data('value'));
							});
							form.render();
							// 初始化laydate
							layui.each(tableView.find('td[data-field="paymentDate"]'), function(index, tdElem) {
								tdElem.onclick = function(event) {
									layui.stope(event)
								};
										})
									},
								});

				
					


					//监听搜索
					form.on('submit(LAY-search)', function(data) {
						var field = data.field;
						console.log(field)
						$.ajax({
							url: "${ctx}/fince/getConsumption",
							type: "get",
							data: field,
							dataType: "json",
							success: function(result) {
								table.reload('tableData', {
									where: field
								});
							}
						});
					});
					

				}
			)
		</script>
	</body>

</html>