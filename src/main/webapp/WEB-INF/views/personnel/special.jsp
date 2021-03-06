<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
<script src="${ctx }/static/layui-v2.4.5/layui/layui.js"></script>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>特急人员</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

</head>

<body>
	
<div class="layui-card">
	<div class="layui-card-body">
		<div class="layui-form">
			<div class="layui-form-item">
				<table>
					<tr>
						<td>姓名:</td>
						<td><input type="text" name="userName" id="firstNames"
							class="layui-input search-query name" /></td>
						<td>&nbsp;&nbsp;</td>
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

<form action="" id="layuiadmin-form-admin3"
		style="padding-top:10px;padding-right:10px; display:none;  text-align:">
		<div class="layui-form" lay-filter="layuiadmin-form-admin3">
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">姓名：</label>
				<div class="layui-input-inline">
					<input name="userName" style="width:190px;" lay-filter="required" i lay-search="true" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">手机号：</label>
				<div class="layui-input-inline">
					<input name="phone" style="width:190px;"  placeholder="可不填"  lay-search="true" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">身份证：</label>
				<div class="layui-input-inline">
					<input name="idCard" style="width:190px;"  placeholder="可不填" lay-search="true" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 100px;">银行卡号：</label>
				<div class="layui-input-inline">
					<input name="bankCard1" style="width:190px;"  placeholder="可不填" lay-search="true" class="layui-input">
				</div>
			</div>
		</div>
	</form>

	<script type="text/html" id="toolbar">
			<div class="layui-btn-container layui-inline">
				<span class="layui-btn layui-btn-sm" lay-event="add">新增</span>
				<span class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteSome">批量删除</span>
			</div>
		</script>

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
				 
					layer.close(index);
					tablePlug.smartReload.enable(true); 
					table.render({
						elem: '#tableData',
						size:'lg',
						url: '${ctx}/system/user/findTemporaryUserTimePages',
						request:{
							pageName: 'page' ,//页码的参数名称，默认：page
							limitName: 'size' //每页数据量的参数名，默认：limit
						},
						page:true,
						loading: true,
						toolbar: '#toolbar', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
						cellMinWidth: 90,
						parseData: function(ret) {
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
								field: "userName",
								title: "人员姓名",
								align: 'center',
								edit: 'text',
							}, {
								field: "phone",
								title: "手机号",
								align: 'center',
								edit: 'text',
							}, {
								field: "idCard",
								title: "身份证",
								align: 'center',
								edit: 'text',
							},{
								field: "bankCard1",
								title: "银行卡号",
								align: 'center',
								edit: 'text',
							}]
						],
						done: function() {
							var tableView = this.elem.next();
							tableView.find('.layui-table-grid-down').remove();
							var totalRow = tableView.find('.layui-table-total');
							var limit = this.page ? this.page.size : this.limit;
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
									},
								});

					// 监听表格中的下拉选择将数据同步到table.cache中
					form.on('select(lay_selecte)', function(data) {
						var selectElem = $(data.elem);
						var tdElem = selectElem.closest('td');
						var trElem = tdElem.closest('tr');
						var tableView = trElem.closest('.layui-table-view');
						var field = tdElem.data('field');
						table.cache[tableView.attr('lay-id')][trElem.data('index')][tdElem.data('field')] = data.value;
						var id = table.cache[tableView.attr('lay-id')][trElem.data('index')].id
						var postData = {
							id: id,
							[field]:data.value
						}
						//调用新增修改
						mainJs.fUpdate(postData);
					});
					
					//监听头工具栏事件
					table.on('toolbar(tableData)', function(obj) {
						var config = obj.config;
						var btnElem = $(this);
						var tableId = config.id;
						switch(obj.event) {
						case 'add':
							var	dicDiv=$("#layuiadmin-form-admin3");
							var index=layer.open({
								type:1,
								title:'加绩',
								area:['430px','400px'],
								btn:['确认','取消'],
								content:dicDiv,
								id: 'LAY_layuipro2' ,
								btnAlign: 'c',
							    moveType: 1, //拖拽模式，0或者1
								success : function(layero, index) {
						        	layero.addClass('layui-form');
									// 将保存按钮改变成提交按钮
									layero.find('.layui-layer-btn0').attr({
										'lay-filter' : 'addRole',
										'lay-submit' : ''
									})
						        },
								yes:function(){
									form.on('submit(addRole)', function(data) {
										if(data.field.phone!=""){
											if(!(/^1[3456789]\d{9}$/.test(data.field.phone))){ 
												return layer.msg("手机号码有误,请重新填写",{icon: 2}) 
										    }
										}
										 mainJs.fUpdate(data.field); 
										document.getElementById("layuiadmin-form-admin3").reset();
										layer.close(index);
									})
								},end:function(){ 
						        	document.getElementById("layuiadmin-form-admin3").reset();
						        	layui.form.render();
								  }
							})
							break;
							case 'deleteSome':
								// 获得当前选中的
								var checkedIds = tablePlug.tableCheck.getChecked(tableId);
								layer.confirm('您是否确定要删除选中的' + checkedIds.length + '条记录？', function() {
									var postData = {
										ids: checkedIds,
									}
									$.ajax({
										url: "${ctx}/system/user/deleteTemporaryUser",
										data: postData,
										traditional: true,
										type: "GET",
										beforeSend: function() {
											index;
										},
										success: function(result) {
											if(0 == result.code) {
												var configTemp = tablePlug.getConfig("tableData");
									            if (configTemp.page && configTemp.page.curr > 1) {
									              table.reload("tableData", {
									                page: {
									                  curr: configTemp.page.curr - 1
									                }
									              })
									            }else{
									            	table.reload("tableData", {
										                page: {
										                }
										              })
									            };
												layer.msg(result.message, {
													icon: 1,
													time:800
												});
											} else {
												layer.msg(result.message, {
													icon: 2,
													time:800
												});
											}
										},
										error: function() {
											layer.msg("操作失败！", {
												icon: 2
											});
										}
									});
									layer.close(index);
								});
								break; 
							case 'becomeFull': 
								
								break;
							case 'cleanTempData':	
									table.cleanTemp(tableId);
							break;
						}
					});

					//监听单元格编辑
					table.on('edit(tableData)', function(obj) {
						var value = obj.value ,//得到修改后的值
							data = obj.data ,//得到所在行所有键值
							field = obj.field, //得到字段
							id = data.id;
						if(field=='phone'){
							if(value!=""){
								if(!(/^1[3456789]\d{9}$/.test(value))){ 
									return layer.msg("手机号码有误,请重新填写",{icon: 2}) 
							    }
							}
						}
							var postData = {
								id:id,
								[field]:value
							}
							//调用新增修改
							mainJs.fUpdate(postData); 
					});

					//监听搜索
					form.on('submit(LAY-search)', function(data) {
						var field = data.field;
						table.reload('tableData', {
							where: field
						});
					});
					
					
					//封装ajax主方法
					var mainJs = {
					//修改							
				    fUpdate : function(data){
				    	if(data.id==""){
				    		return;
				    	}
				    	$.ajax({
							url: "${ctx}/system/user/addTemporaryUser",
							data: data,
							type: "POST",
							beforeSend: function() {
								index;
							},
							success: function(result) {
								if(0 == result.code) {
								 	 table.reload("tableData", {
						                page: {
						                }
						              })   
									layer.msg(result.message, {
										icon: 1,
										time:800
									});
								
								} else {
									layer.msg(result.message, {
										icon: 2,
										time:800
									});
								}
							},
							error: function() {
								layer.msg("操作失败！请重试", {
									icon: 2
								});
							},
						});
						layer.close(index);
				    }
					}
				}
			)
		</script>
</body>
</html>