<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html class="no-js">
<!--<![endif]-->

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>任务管理</title>
<meta name="description" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<link rel="stylesheet" href="${ctx }/static/plugins/bootstrap/css/bootstrap.min.css">
	<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
	<script src="${ctx }/static/js/layer/layer.js"></script>	
	<script src="${ctx }/static/js/laydate-icon/laydate.js"></script>
	<link rel="stylesheet" href="${ctx }/static/css/main.css">
	<script src="${ctx }/static/js/laypage/laypage.js"></script>
	<script src="${ctx }/static/plugins/bootstrap/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	
</head>

<body>
	
<div class="panel panel-default">
	<div class="panel-body">
	<!--查询开始  -->

		<table>
			<tr>
				<td>批次号:</td>
				<td><input type="text" name="number" id="number"
					placeholder="请输入批次号"
					class="form-control search-query number" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>产品名称:</td>
				<td><input type="text" name="name" id="name"
					placeholder="请输入产品名称"
					class="form-control search-query name" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>工序名称:</td>
				<td><input type="text" name="procedureName" id="procedureName"
						placeholder="请输入工序名称" class="form-control search-query name" /></td>
				<td>&nbsp;&nbsp;</td>
				<td>开始时间:</td>
				<td><input id="startTime" placeholder="请输入开始时间"
					class="form-control laydate-icon"
					onClick="laydate({elem: '#startTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
				</td>
				<td>&nbsp;&nbsp;</td>
				<td>结束时间:</td>
				<td><input id="endTime" placeholder="请输入结束时间"
					class="form-control laydate-icon"
					onClick="laydate({elem: '#endTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
				</td>
				<td>&nbsp;&nbsp;</td>
				<td><select class="form-control selectchoice hidden"><option
							value="0">裁剪工序</option></select></td>
				<td>&nbsp;&nbsp;</td>
				<td><span class="input-group-btn">
					<button type="button"
						class="btn btn-info btn-square btn-sm btn-3d searchtask">
						查找 <i class="icon-search icon-on-right bigger-110"></i>
					</button>
				</span></td>
				<td>&nbsp;&nbsp;</td>
				<td><button type="button"
					class="btn btn-default btn-danger btn-sm btn-3d attendance">一键删除</button>&nbsp;&nbsp;</td>
			</tr>
		</table>

		<h1 class="page-header"></h1>
		<table class="table table-condensed table-hover">
			<thead>
				<tr>
					<th class="center"><label> <input type="checkbox"
							class="ace checks" /> <span class="lbl"></span>
					</label></th>
					<th class="text-center">任务编号</th>
					<th class="text-center">批次号</th>
					<th class="text-center" style="width: 250px;">产品名</th>
					<th class="text-center">时间</th>
					<th class="text-center">工序</th>
					<th class="text-center">预计时间</th>
					<th class="text-center">任务价值</th>
					<th class="text-center">b工资净值</th>
					<th class="text-center">数量</th>
					<th class="text-center">完成人</th>
					<th class="text-center">操作</th>
				</tr>
			</thead>
			<tbody id="tablecontent">

			</tbody>
		</table>
		<div id="pager" class="pull-right"></div>
	</div>
</div>


	<!--隐藏框 人员信息开始  -->
	<div id="savegroup" style="display: none;">
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">人员分组详情</h4>
					</div>
					<div class="modal-body"></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>
	</div>
	<!--隐藏框 人员信息结束  -->
	</section>


	<script>
   jQuery(function($){
   	var Login = function(){
			var self = this;
			//表单jsonArray
			//初始化js
			var _cache;
			this.setCache = function(cache){
		  		_cache=cache;
		  	}
		  	this.getCache = function(){
		  		return _cache;
		  	}
		  	this.getCount = function(){
		  		return _count;
		  	}
		  	this.setCount = function(count){
		  		_count=count;
		  	}
		  	function p(s) {
				return s < 10 ? '0' + s: s;
				}
		  	var myDate = new Date();
			//获取当前年
			var year=myDate.getFullYear();
			//获取当前月
			var month=myDate.getMonth()+1;
			//获取当前日
			var date=myDate.getDate(); 
			var day = new Date(year,month,0);  
			var firstdate = year + '-' +p(month) + '-01'+' '+'00:00:00';
			var lastdate = year + '-' +p(month) + '-' + day.getDate() +' '+'23:59:59';
			$('#startTime').val(firstdate);
			$('#endTime').val(lastdate);
			 var data={
						page:1,
				  		size:13,	
				  		type:5,
						flag:$('.selectchoice').val(),
						orderTimeBegin:$("#startTime").val(),
				  		orderTimeEnd:$("#endTime").val(),
				} 
			this.init = function(){
				
				//注册绑定事件
				self.events();
				self.loadPagination(data);
			}
			//加载分页
			  this.loadPagination = function(data){
			    var index;
			    var html = '';
			    var htmlto="";
			    $.ajax({
				      url:"${ctx}/task/allTask",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			 $(result.data.rows).each(function(i,o){
		      				 var a=""
		      				 var s=o.procedureName
		      				if(o.flag==1){
		      					a="(返工)"
		      				}
		      				 if(o.taskActualTime==null){
		      					o.taskActualTime=0
		      				 }
		      				html +='<tr><td class="center reste"><label> <input type="checkbox" class="ace checkboxId" value="'+o.id+'"/><span class="lbl"></span></label></td>'
		      				+'<td class="text-center  name">'+o.id+'</td>'
		      				+'<td class="text-center  name">'+o.bacthNumber+'</td>'
		      				+'<td class="text-center name">'+o.productName+'</td>'
		      				+'<td class="text-center edit allotTime">'+o.allotTime+'</td>'
		      				+'<td class="text-center  name">'+s+a+'</td>'
		      				+'<td class="text-center  name">'+parseFloat((o.expectTime).toFixed(4))+'</td>'
		      				+'<td class="text-center  name">'+parseFloat((o.taskPrice).toFixed(4))+'</td>'
		      				+'<td class="text-center  name">'+parseFloat((o.payB).toFixed(4))+'</td>'
		      				+'<td class="text-center edit number">'+o.number+'</td>'
		      				+'<td class="text-center"><button class="btn btn-primary btn-trans btn-sm savemode" data-toggle="modal" data-target="#myModal" data-id="'+o.id+'")">查看人员</button></td>'
							+'<td class="text-center"><button class="btn btn-sm btn-info  btn-trans updateremake" data-id='+o.id+'>编辑</button> <button class="btn btn-sm btn-danger btn-trans delete" data-id='+o.id+'>删除</button></td></tr>'
							
		      			}); 
		      			self.setCount(result.data.pageNum)
				        //显示分页
					   	 laypage({
					      cont: 'pager', 
					      pages: result.data.totalPages, 
					      curr:  result.data.pageNum || 1, 
					      jump: function(obj, first){ 
					    	  if(!first){ 
					    		 
						        	var _data = {
						        			page:obj.curr,
									  		size:13,
									  		type:5,
								  			productName:$('#name').val(),
								  			bacthNumber:$('#number').val(),
								  			procedureName:$('#procedureName').val(),
								  			orderTimeBegin:$("#startTime").val(),
								  			orderTimeEnd:$("#endTime").val(), 
								  			flag:$('.selectchoice').val(),
								  	}
						        
						            self.loadPagination(_data);
							     }
					      }
					    });  
					   	layer.close(index);
					   
					   	 $("#tablecontent").html(html); 
					   	self.loadEvents();
					   	self.checkedd();
					   	self.checkeddd();
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
			}
			
			  this.checkeddd=function(){
					
					$(".rest").each(function(i,o){
							
							var rest=$(o).parent().data("status");
						
							if($(o).val()==rest){
							
								$(o).attr('checked', 'checked');;
							}
					})
					
				}
			this.loadEvents = function(){
				
				
				$('.rest').on('click',function(){
					var  del=$(this);
					var id = $(this).parent().data('id');
					var rest = $(this).val();
					
				    	  $.ajax({
								url:"${ctx}/task/getTaskActualTime",
								data:{
									ids:id,
									status:rest,
									},
								type:"GET",
								beforeSend:function(){
									index = layer.load(1, {
										  shade: [0.1,'#fff'] //0.1透明度的白色背景
										});
								},
								success:function(result){
									//选择1
									
									if(rest==0){
								
										del.parent().find(".rest").eq(1).prop("checked", false);

									}else{
										del.parent().find(".rest").eq(0).prop("checked", false);
										
									}
									layer.msg("操作成功！", {icon: 1});
									layer.close(index);
								
							
									
								},
								error:function(){
									layer.msg("操作失败！", {icon: 2});
									layer.close(index);
								}
							});
				    	  
				 
		
				});
				
				
				//修改方法
				$('.updateremake').on('click',function(){
					if($(this).text() == "编辑"){
						$(this).text("保存")
						
						$(this).parent().siblings(".edit").each(function(index) {  // 获取当前行的其他单元格

							//修改编辑单元弹出，时间选择板。代码如下：
							if(index==0){	
								$(this).html('<input type="text" id="editTime" class="input-mini form-control laydate-icon" value="'+$(this).text()+'"/>');
								document.getElementById('editTime').onclick=function(){
									laydate({
									    elem: '#editTime',
									    istime: true, format: "YYYY-MM-DD hh:mm:ss"
									  });
								}
							}else
				       			$(this).html("<input class='input-mini' type='text' style='width:60px;' value='"+$(this).text()+"'>");
							//原代码：
							//$(this).html("<input class='input-mini' type='text' value='"+$(this).text()+"'>");
				        });
					}else{
							$(this).text("编辑")
						$(this).parent().siblings(".edit").each(function() {  // 获取当前行的其他单元格

					            obj_text = $(this).find("input:text");    // 判断单元格下是否有文本框

					       
					                $(this).html(obj_text.val()); 
									
							});
							
							var postData = {
									id:$(this).data('id'),
									number:$(this).parent().parent('tr').find(".number").text(),
									allotTime:$(this).parent().parent('tr').find(".allotTime").text(),
							}
							var index;
							$.ajax({
								url:"${ctx}/task/upTask",
								data:postData,
								type:"POST",
								beforeSend:function(){
									index = layer.load(1, {
										  shade: [0.1,'#fff'] //0.1透明度的白色背景
										});
								},
								
								success:function(result){
									if(0==result.code){
									layer.msg("修改成功！", {icon: 1});
									var _data = {
						        			page:self.getCount(),
									  		size:13,
									  		type:5,
									  		productName:$('#name').val(),
								  			bacthNumber:$('#number').val(),
								  			orderTimeBegin:$("#startTime").val(),
								  			orderTimeEnd:$("#endTime").val(),
								  			flag:$('.selectchoice').val(),
								  	}
						            self.loadPagination(_data);
									layer.close(index);
									}else{
										layer.msg("修改失败！", {icon: 1});
										layer.close(index);
									}
								},error:function(){
									layer.msg("操作失败！", {icon: 2});
									layer.close(index);
								}
							});
					}
				})
				
				
				//删除
						$('.delete').on('click',function(){
							var postData = {
									ids:$(this).data('id'),
							}
							
							var index;
							 index = layer.confirm('确定删除吗', {btn: ['确定', '取消']},function(){
							$.ajax({
								url:"${ctx}/task/delete",
								data:postData,
								type:"GET",
								beforeSend:function(){
									index = layer.load(1, {
										  shade: [0.1,'#fff'] //0.1透明度的白色背景
										});
								},
								
								success:function(result){
									if(0==result.code){
									layer.msg("删除成功！", {icon: 1});
									var _data = {
						        			page:self.getCount(),
									  		size:13,
									  		type:5,
									  		productName:$('#name').val(),
								  			bacthNumber:$('#number').val(),
								  			orderTimeBegin:$("#startTime").val(),
								  			orderTimeEnd:$("#endTime").val(),
								  			flag:$('.selectchoice').val(),
								  	}
									self.loadPagination(_data)
									layer.close(index);
									}else{
										layer.msg("删除失败！", {icon: 1});
										layer.close(index);
									}
								},error:function(){
									layer.msg("操作失败！", {icon: 2});
									layer.close(index);
								}
							});
							 })
				})
				
				//人员详细显示方法
				$('.savemode').on('click',function(){
					var id=$(this).data('id')
					 var display =$("#savegroup").css("display")
					 if(display=='none'){
							$("#savegroup").css("display","block");  
						}
					 var postData={
							id:id,
					}
					 var arr=new Array();
					var html="";
					$.ajax({
						url:"${ctx}/task/taskUser",
						data:postData,
						type:"GET",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						
						success:function(result){
							$(result.data).each(function(i,o){
							html+=o.userName+"&nbsp;&nbsp;&nbsp;&nbsp;"
							})
							$('.modal-body').html(html);
							layer.close(index);
							
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					}); 
					
					
					
				})
				
				
			}
			this.checkedd=function(){
				
				$(".checks").on('click',function(){
					
                    if($(this).is(':checked')){ 
			 			$('.checkboxId').each(function(){  
                    //此处如果用attr，会出现第三次失效的情况  
                     		$(this).prop("checked",true);
			 			})
                    }else{
                    	$('.checkboxId').each(function(){ 
                    		$(this).prop("checked",false);
                    		
                    	})
                    }
                }); 
				
			}
			this.events = function(){
				$('.searchtask').on('click',function(){
					var data = {
				  			page:1,
				  			size:13,
				  			type:5,
				  			productName:$('#name').val(),
				  			bacthNumber:$('#number').val(),
				  			procedureName:$('#procedureName').val(),
				  			orderTimeBegin:$("#startTime").val(),
				  			orderTimeEnd:$("#endTime").val(), 
				  			flag:$('.selectchoice').val(),
				  	}
		            self.loadPagination(data);
				});
				
				/* 一键删除 */
				$('.attendance').on('click',function(){
					  var  that=$(this);
					  var arr=new Array()//员工id
						$(this).parent().parent().parent().parent().parent().find(".checkboxId:checked").each(function() {  
							arr.push($(this).val());   
						});
					  if(arr.length<=0){
							return layer.msg("至少选择一个！", {icon: 2});
						}
						var data={
								type:5,
								ids:arr,
						}
						var index;
						 index = layer.confirm('确定一键删除吗', {btn: ['确定', '取消']},function(){
						$.ajax({
							url:"${ctx}/task/delete",
							data:data,
				            traditional: true,
							type:"GET",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							
							success:function(result){
								if(0==result.code){
									layer.msg(result.message, {icon: 1});
									var _data = {
						        			page:self.getCount(),
									  		size:13,
									  		type:5,
									  		productName:$('#name').val(),
								  			bacthNumber:$('#number').val(),
								  			orderTimeBegin:$("#startTime").val(),
								  			orderTimeEnd:$("#endTime").val(),
								  			flag:$('.selectchoice').val(),
								  	}
									self.loadPagination(_data);
								}else{
									layer.msg(result.message, {icon: 2});
								}
								layer.close(index);
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						});
						 });
				  })
				
				  
				  /* 一键暂停 */
				/* $('.suspend').on('click',function(){
					  var  that=$(this);
					  var arr=new Array()//员工id
						$(this).parent().parent().parent().parent().parent().find(".checkboxId:checked").each(function() {  
							arr.push($(this).val());   
						});
					  
					  if(arr.length<=0){
							return layer.msg("至少选择一个！", {icon: 2});
						}
						var data={
								status:1,
								type:4,
								ids:arr,
						}
						var index;
						 index = layer.confirm('确定一键暂停吗', {btn: ['确定', '取消']},function(){
						$.ajax({
							url:"${ctx}/task/getTaskActualTime",
							data:data,
				            traditional: true,
							type:"GET",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							
							success:function(result){
								if(0==result.code){
									layer.msg(result.message, {icon: 1});
									self.loadPagination(data);
								}else{
									layer.msg(result.message, {icon: 2});
								}
								layer.close(index);
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						});
						 });
				  }) */
				
				  /* 一键结束 */
				/* $('.end').on('click',function(){
					  var  that=$(this);
					  var arr=new Array()//员工id
						$(this).parent().parent().parent().parent().parent().find(".checkboxId:checked").each(function() {  
							arr.push($(this).val());   
						});
					  
					  if(arr.length<=0){
							return layer.msg("至少选择一个！", {icon: 2});
						}
						var data={
								status:1,
								type:3,
								ids:arr,
						}
						var index;
						 index = layer.confirm('确定一键暂停吗', {btn: ['确定', '取消']},function(){
						$.ajax({
							url:"${ctx}/task/getTaskActualTime",
							data:data,
				            traditional: true,
							type:"GET",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							
							success:function(result){
								if(0==result.code){
									layer.msg(result.message, {icon: 1});
									self.loadPagination(data);
								}else{
									layer.msg(result.message, {icon: 2});
								}
								layer.close(index);
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						});
						 });
				  }) */
				  
				  /* $('.start').on('click',function(){
					  var  that=$(this);
					  var arr=new Array()//员工id
						$(this).parent().parent().parent().parent().parent().find(".checkboxId:checked").each(function() {  
							arr.push($(this).val());   
						});
					  
					  if(arr.length<=0){
							return layer.msg("至少选择一个！", {icon: 2});
						}
						var data={
								status:0,
								type:4,
								ids:arr,
								flag:$('.selectchoice').val(),
						}
						var index;
						 index = layer.confirm('确定一键开始吗', {btn: ['确定', '取消']},function(){
						$.ajax({
							url:"${ctx}/task/getTaskActualTime",
							data:data,
				            traditional: true,
							type:"GET",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							
							success:function(result){
								if(0==result.code){
									layer.msg(result.message, {icon: 1});
									self.loadPagination(data);
								}else{
									layer.msg(result.message, {icon: 2});
								}
								layer.close(index);
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						});
						 });
				  }) */
			}
   	}
   			var login = new Login();
				login.init();
			})
    
    </script>

</body>

</html>