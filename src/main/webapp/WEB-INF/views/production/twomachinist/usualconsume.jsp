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
<title>日常消费</title>
	<link rel="stylesheet" href="${ctx }/static/plugins/bootstrap/css/bootstrap.min.css">
	<script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
	<script src="${ctx }/static/js/laydate-icon/laydate.js"></script>  <!-- 时间插件 -->
	<script src="${ctx }/static/js/layer/layer.js"></script>
	<script src="${ctx }/static/js/laypage/laypage.js"></script> 
	<link rel="stylesheet" href="${ctx }/static/css/main.css">
<meta name="description" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />


</head>

<body>
	
<div class="col-md-12">
	<div class="panel panel-default">
		<div class="panel-body">
			<table>
				<tr>
					<td>最近人消费后勤:</td>
					<td><input type="text" name="number" id="number"
						class="form-control search-query numberth" /></td>
					<td>最近包装车间人数:</td>
					<td><input type="text" name="name" id="sum"
						class="form-control search-query name numberth" /></td>
					<td>当月房租设定:</td>
					<td><input type="text" name="name" id="rent"
						class="form-control search-query name numberth" /></td>
					<td>当月水电:</td>
					<td><input type="text" name="name" id="price"
						class="form-control search-query name numberth" /></td>
					<td>设备折旧:</td>
					<td><input type="text" name="name" id="equipment"
						class="form-control search-query name numberth" /></td>
					<td><div style="width: 15px"></div></td>
					<td><button type="button"
							class="btn btn-sm btn-info btn-3d update">修改</button></td>
				</tr>
				<tr>
					<td><div style="height: 10px"></div></td>
				</tr>
				<tr>
					<td>当月后勤餐饮保障:</td>
					<td><input type="text" name="number" id="numbertw"
						class="form-control search-query number numberth" /></td>
					<td>日消费房租:</td>
					<td><input type="text" name="name" id="sumtw"
						class="form-control search-query name numberth" /></td>
					<td>日消费水电折旧:</td>
					<td><input type="text" name="name" id="renttw"
						class="form-control search-query name numberth" /></td>
					<td>日消费餐饮后勤:</td>
					<td><input type="text" name="name" id="pricetw"
						class="form-control search-query name numberth" /></td>
					<td>日期：</td>
					<td><input id="startTime" placeholder="请输入日期"
						class="form-control laydate-icon"
						onClick="laydate({elem: '#startTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
					</td>
					<td><div style="width: 15px"></div></td>
					<td>
						<button type="button" id="addgroup"
							class="btn btn-sm btn-success btn-3d pull-right">新增</button>
					</td>
					<td><div style="width: 15px"></div></td>
								<td><button type="button"
										class="btn btn-danger  btn-sm btn-3d start">删除
									</button></td>
				</tr>
			</table>
								
			<h1 class="page-header"></h1>
			<table class="table table-hover">
				<thead>
					<tr>
						<th class="center"><label> <input type="checkbox"
								class="ace checks" /> <span class="lbl"></span>
						</label></th>
						<th class="text-center">日期</th>
						<th class="text-center">日消费房租选择</th>
						<th class="text-center">日消费水电折旧选择</th>
						<th class="text-center">日消费餐饮后勤选择</th>
					</tr>
				</thead>
				<tbody id="tablecontent">

				</tbody>

			</table>
			<div id="pager" class="pull-right"></div>
		</div>
	</div>
</div>

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
			 var data={
						page:1,
				  		size:10,	
				  		type:4,

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
			    var htmltw = '';
			    var postdata={
			    		type:4,
			    }
			    //查询日常数值
			    $.ajax({
				      url:"${ctx}/finance/getUsualConsume",
				      data:postdata,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      		    $('#number').val(result.data.peopleLogistics)
				        $('#sum').val(result.data.peopleNumber)
				        $('#rent').val(result.data.monthChummage)
				        $('#price').val(result.data.monthHydropower)
				        $('#numbertw').val(result.data.monthLogistics)
				        $('#sumtw').val(result.data.chummage)
				        $('#renttw').val(result.data.hydropower)
				        $('#pricetw').val(result.data.logistics)
				         $('#equipment').val(result.data.equipment)
					   	layer.close(index);
		      		  
					   
					   	self.loadEvents();
					   
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
			    
			    
			    //查询流水日常
			    $.ajax({
				      url:"${ctx}/finance/allUsualConsume",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			 $(result.data.rows).each(function(i,o){
		      				html +='<tr><td class="center reste"><label> <input type="checkbox" class="ace checkboxId" value="'+o.id+'"/><span class="lbl"></span></label></td>'
		      				+'<td class="text-center edit name">'+o.consumeDate+'</td>'
		      				+'<td class="text-center edit name">'+o.chummage*1+'</td>'
		      				+'<td class="text-center edit name">'+o.hydropower*1+'</td>'
		      				+'<td class="text-center edit name">'+o.logistics*1+'</td></tr>'
		      			}); 
				        //显示分页
				        self.setCount(result.data.pageNum)
					   	 laypage({
					      cont: 'pager', 
					      pages: result.data.totalPages, 
					      curr:  result.data.pageNum || 1, 
					      jump: function(obj, first){ 
					    	  if(!first){ 
					    		 
						        	var _data = {
						        			page:obj.curr,
									  		size:10,
									  		type:4,
								  	}
						        
						            self.loadPagination(_data);
							     }
					      }
					    });  
					   	layer.close(index);
					   	
					   	 $("#tablecontent").html(html); 
					   	self.loadEvents();
						self.checkedd();
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
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
			this.loadEvents = function(){
				
				
				
				
			}
			this.events = function(){
				$('.start').on('click',function(){
					  var  that=$(".table-hover");
					  var arr=new Array()//员工id
					  	that.parent().parent().parent().parent().parent().find(".checkboxId:checked").each(function() {  
							arr.push($(this).val());   
						});
					  var postData = {
								ids:arr,
						}
						
						var index;
						 index = layer.confirm('确定删除吗', {btn: ['确定', '取消']},function(){
						$.ajax({
							url:"${ctx}/finance/delete",
							data:postData,
							traditional: true,
							type:"GET",
							beforeSend:function(){
								index = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
							},
							
							success:function(result){
								if(0==result.code){
								layer.msg("删除成功！", {icon: 1});
								var data = {
					        			page:self.getCount(),
								  		size:10,
								  		type:4,
								  		
							  	}
								self.loadPagination(data)
								layer.close(index);
								}else{
									layer.msg("删除失败！", {icon: 2});
									layer.close(index);
								}
							},error:function(){
								layer.msg("操作失败！", {icon: 2});
								layer.close(index);
							}
						});
						 })
				})
				$('.update').on('click',function(){
					
							
							var postData = {
									peopleLogistics:$('#number').val(),
								    peopleNumber:$('#sum').val(),
								    monthChummage:$('#rent').val(),
								    monthHydropower:$('#price').val(),
								    type:4
							}
							
							var index;
							var index = layer.confirm('确定修改吗', {btn: ['确定', '取消']},function(){
							 $.ajax({
								url:"${ctx}/finance/usualConsume",
								data:postData,
								type:"GET",
								beforeSend:function(){
									index = layer.load(1, {
										  shade: [0.1,'#fff'] //0.1透明度的白色背景
										});
								},
								
								success:function(result){
									if(0==result.code){
									layer.msg("修改成功！", {icon: 1});
									self.loadPagination(data); 
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
							})
				})
				
				//新增
				$('#addgroup').on('click',function(){
					
					var _index
					var index
					var postData={
							consumeDate:$("#startTime").val(),
							chummage:$('#sumtw').val(),
							hydropower:$('#renttw').val(),
							logistics:$('#pricetw').val(),
							type:4,
					  }
					  $.ajax({
							url:"${ctx}/finance/addUsualConsume",
							data:postData,
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
				})
			}
   	}
   			var login = new Login();
				login.init();
			})
    
    </script>

</body>

</html>