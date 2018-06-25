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
    <title>批次管理</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
   <link rel="stylesheet" href="${ctx }/static/sfm/css/bootstrap-datetimepicker.min.css">
   
</head>

<body>
    <section id="main-wrapper" class="theme-default">
        
        <%@include file="../../decorator/leftbar.jsp"%> 
        
        <!--main content start-->
        
           <section id="main-content" class="animated fadeInUp">
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title">批次管理</h3>
                                <div class="actions pull-right">
                                    <i class="fa fa-expand"></i>
                                    <i class="fa fa-chevron-down"></i>
                                </div>
                            </div>
        <div class="row" style="height: 30px; margin:15px 0 10px">
			<div class="col-xs-8 col-sm-8  col-md-9">
				<form class="form-search" >
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<div class="input-group"> 
								<table><tr><td>批次号:</td><td><input type="text" name="number" id="number" placeholder="请输入批次号" class="form-control search-query number" /></td>
									<td>&nbsp&nbsp</td>
								<td>产品名称:</td><td><input type="text" name="name" id="name" placeholder="请输入产品名称" class="form-control search-query name" /></td>
									<td>&nbsp&nbsp</td>
								<td>开始时间:</td>
								<td>
								<input id="startTime" placeholder="请输入开始时间" class="form-control laydate-icon"
             					onClick="laydate({elem: '#startTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"> 
								</td>
									<td>&nbsp&nbsp</td>
				<td>结束时间:</td>
				<td>
					<input id="endTime" placeholder="请输入结束时间" class="form-control laydate-icon"
             onClick="laydate({elem: '#endTime', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
								</td>
								</tr></table> 
								<span class="input-group-btn">
									<button type="button" class="btn btn-info btn-square btn-sm btn-3d searchtask">
											查&nbsp找
									</button>
								</span>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<span class="input-group-btn">
									<button type="button" id="addprocedure" class="btn btn-success btn-sm btn-3d pull-right">一键接收</button>
								</span>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
                            <div class="panel-body">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                        	<th class="text-center">批次号</th>
                                            <th class="text-center">时间</th>
                                            <th class="text-center">产品名</th>
                                            <th class="text-center">数量</th>
                                            <th class="text-center">预计生产单价</th>
                                            <th class="text-center">外发价格</th>
                                            <th class="text-center">任务价值</th>
                                            <th class="text-center">地区差价</th>
                                            <th class="text-center">备注</th>
                                            <th class="text-center">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tablecontent">
                                        
                                    </tbody>
                                </table>
                                <div id="pager" class="pull-right">
                                
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </section>
        <!--隐藏框 工序分配开始  -->
        <div id="addDictDivType" style="display: none;">
			<div class=" col-xs-12  col-sm-12  col-md-12 ">
				<div class="space-10"></div>
				<div style="height: 30px"></div>
				<form class="form-horizontal addDictDivTypeForm">
					<div class="row col-xs-12  col-sm-12  col-md-12 ">
		
                 
						<div class="form-group">
                           <label class="col-sm-3 col-md-2 control-label">任务数量:</label>
                              <div class="col-sm-3 col-md-3">
                                  <input type="text" class="form-control sumnumber">
                              </div>
                               <div >
                            <label class="col-sm-2 col-md-2 control-label" >预计完成时间:</label>
                                <div class="col-sm-3 col-md-3">
                                  <input type="text"   placeholder="非返工任务不填写"  class="form-control sumtime">
                                </div>
                                </div>
                    	</div>
                    	
                    	
                 		 <div class="form-group">
                               <label class="col-sm-2 control-label">任务分配:</label>
                                 <div class="col-sm-3">
                                          <input id="Time" placeholder="时间可不填" class="form-control laydate-icon"
           									onClick="laydate({elem: '#Time', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                                      </div>
                              <label class="col-sm-2 control-label">加绩工序:</label>
                              <div class="col-sm-3 workingtw">
                              </div> 
                		 </div>
                    	
                    	
                    	<div class="form-group">
                           <label class="col-sm-2 control-label">选择工序:</label>
                              <div class="col-sm-2 working">
                              </div>
                              <div class="col-sm-2 checkworking"></div>
                            <label class="col-sm-1 control-label">完成人:</label>
                                <div class="col-sm-2 complete">
                                  <input type="text" class="form-control">
                                </div>
                                 <div class="col-sm-2 select"></div>
                    	</div>
                 </div>
				</div>

				</form>
</div>
</div>
<!--隐藏框 工序分配结束  -->





<!--隐藏框 工序分配2开始  -->
        <div id="addDictDivTypetw" style="display: none;">
			<div class=" col-xs-12  col-sm-12  col-md-12 ">
				<div class="space-10"></div>
				<div style="height: 30px"></div>
				<form class="form-horizontal addDictDivTypeFormtw">
					<div class="row col-xs-12  col-sm-12  col-md-12 " id="tabs">

                                     	
                    <div class="form-group">
                           <label class="col-sm-2 control-label">加绩工序:</label>
                            <div class="col-sm-3 workingth">
                            </div>
                           <label class="col-sm-2 control-label">选择工序:</label>
                              <div class="col-sm-3 workingtw">
                              </div>
                              <div class="col-sm-2 checkworkingtw"></div>
                    	</div>
                    	
                    <div class="form-group">
							<label class="col-sm-2 control-label">任务时间:</label>
                                    <div class="col-sm-3">
                                        <input id="Timet" placeholder="时间可不填" class="form-control laydate-icon"
         										onClick="laydate({elem: '#Timet', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                                    </div>
                            <label class="col-sm-2 control-label" >预计时间:</label>
                                <div class="col-sm-3">
                                  <input type="text" placeholder="非返工任务不填写" class="form-control sumtimetw">
                                </div>
                 	</div>
                    	
                    <div class="form-group" >
                           <label class="col-sm-3 col-md-2 control-label">开始时间:</label>
                              <div class="col-sm-2 col-md-2">
                                  <input id="Timetstr"  class="form-control laydate-icon"
             					onClick="laydate({elem: '#Timetstr', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                              </div>
                               <div >
                            <label class="col-sm-1 col-md-1 control-label" >结束时间:</label>
                                <div class="col-sm-2 col-md-2">
                                  <input id="Timetend"  class="form-control laydate-icon"
             					onClick="laydate({elem: '#Timetend', istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                                </div>
                                </div>
                            <label class="col-sm-1 control-label">完成人:</label>
                                <div class="col-sm-2 completetw">
                                  <input type="text" class="form-control">
                                </div>
                               <div class="col-sm-1 selecttw"></div>
                                  <div class="col-sm-2 col-md-1"><input type="button" class="btn btn-sm  btn-success" id="save" value="新增"></input></div>
                    	</div>
                 		
                 </div>
				</div>

				</form>
</div>
<!--隐藏框 工序分配2结束  -->



<!--隐藏框已完成的批次开始  -->
        <div id="addworking" style="display: none;">
			<div class="panel-body">
 <div class="form-group">
  </div> 
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                        	<th class="text-center">批次号</th>
                                            <th class="text-center">时间</th>
                                            <th class="text-center">产品名</th>
                                            <th class="text-center">数量</th>
                                            <th class="text-center">待接收数量</th>
                                            <th class="text-center">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tableworking">
                                    </tbody>
                                </table>
                                 <div id="pagerr" class="pull-right">
                            </div>
</div>
<!--隐藏框 已完成的批次结束  -->


    </section>
    
   
   
   <script src="${ctx }/static/js/vendor/jquery-3.3.1.min.js"></script>
    <script src="${ctx }/static/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="${ctx }/static/plugins/navgoco/jquery.navgoco.min.js"></script>
    <script src="${ctx }/static/plugins/switchery/switchery.min.js"></script>
    <script src="${ctx }/static/plugins/pace/pace.min.js"></script>
    <script src="${ctx }/static/plugins/fullscreen/jquery.fullscreen-min.js"></script>
    <script src="${ctx }/static/js/src/app.js"></script>
     <script src="${ctx }/static/js/laypage/laypage.js"></script> 
    <script src="${ctx }/static/plugins/dataTables/js/jquery.dataTables.js"></script>
    <script src="${ctx }/static/plugins/dataTables/js/dataTables.bootstrap.js"></script>
    <script src="${ctx }/static/js/laydate-icon/laydate.js"></script>
    <script src="${ctx }/static/sfm/js/bootstrap-datetimepicker.min.js"></script>
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
			 var data={
						page:1,
				  		size:13,	
				  		type:2,
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
			    $.ajax({
				      url:"${ctx}/bacth/allBacth",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			  
		      			 $(result.data.rows).each(function(i,o){
		      				 
		      				 html +='<tr>'
		      				+'<td class="text-center  bacthNumber">'+o.bacthNumber+'</td>'
		      				+'<td class="text-center  allotTime">'+o.allotTime+'</td>'
		      				+'<td class="text-center  name">'+o.product.name+'</td>'
		      				+'<td class="text-center edit number">'+o.number+'</td>'
		      				+'<td class="text-center  bacthDepartmentPrice">'+o.bacthDepartmentPrice+'</td>'
		      				+'<td class="text-center  bacthHairPrice">'+o.bacthHairPrice+'</td>'
		      				+'<td class="text-center  sumTaskPrice">'+ parseFloat((o.sumTaskPrice*1).toFixed(3))+'</td>'
		      				+'<td class="text-center  regionalPrice">'+parseFloat((o.regionalPrice*1).toFixed(3))+'</td>'
		      				+'<td class="text-center edit remarks">'+o.remarks+'</td>'
							+'<td class="text-center"><button class="btn btn-sm btn-primary btn-trans addDict" data-id='+o.id+' data-proid='+o.product.id+' data-bacthnumber='+o.bacthNumber+' data-proname='+o.product.name+'>分配</button> <button class="btn btn-sm btn-primary btn-trans addDicttw" data-id='+o.id+' data-proid='+o.product.id+' data-bacthnumber='+o.bacthNumber+' data-proname='+o.product.name+' data-number='+o.number+'>分配2</button> <button class="btn btn-sm btn-info  btn-trans updateremake" data-id='+o.id+'>编辑</button> <button class="btn btn-sm btn-danger btn-trans delete" data-id='+o.id+'>删除</button></td></tr>' 
							
		      			}); 
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
									  		type:2,
								  			name:$('#name').val(),
								  			bacthNumber:$('#number').val(),
								  			orderTimeBegin:$("#startTime").val(),
								  			orderTimeEnd:$("#endTime").val(),
								  	}
						        
						            self.loadPagination(_data);
							     }
					      }
					    });  
					   	layer.close(index);
					   	 $("#tablecontent").html(html); 
					   	self.loadEvents();
					   
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
			}
			
			this.loadEvents = function(){
				//删除
				$('.delete').on('click',function(){
					var postData = {
							id:$(this).data('id'),
					}
					
					var index;
					 index = layer.confirm('确定删除吗', {btn: ['确定', '取消']},function(){
					$.ajax({
						url:"${ctx}/bacth/deleteBacth",
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
							self.loadPagination(data)
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
				//修改方法
				$('.updateremake').on('click',function(){
					if($(this).text() == "编辑"){
						$(this).text("保存")
						
						$(this).parent().siblings(".edit").each(function() {  // 获取当前行的其他单元格

				            $(this).html("<input class='input-mini' type='text' value='"+$(this).text()+"'>");
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
									remarks:$(this).parent().parent('tr').find(".remarks").text(),
							}
							
							var index;
							$.ajax({
								url:"${ctx}/bacth/addBacth",
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
				
				//分配1
				$('.addDict').on('click',function(){
					var that=$(this)
					var productId=$(this).data('proid')
					var productName=$(this).data('proname')
					var bacthId=$(this).data('id')
					var bacthNumber=$(this).data('bacthnumber')
					var _index
					var index
					var postData
					//工序遍历  
				    var indextwo;
				    
				    var htmlth = '';
				    var htmlfr = '';
				 	
				    //遍历工序类型
				    var getdata={type:"productFristPack",}
	      			$.ajax({
					      url:"${ctx}/basedata/list",
					      data:getdata,
					      type:"GET",
			      		  success: function (result) {
			      			  $(result.data).each(function(k,j){
			      				htmlfr +='<option value="'+j.id+'">'+j.name+'</option>'
			      			  });  
			      			$('.working').html("<select class='form-control selectchang'><option value="+0+">请选择</option><option value="+""+">全部</option>"+htmlfr+"</select>")
							//改变事件
			      			$(".selectchang").change(function(){
			      				var htmlfv="";
			      				var	id=$(this).val()
			      				if(id==109 || id==""){
			      					$('#dis').css("display","block")
			      				}else{
			      					$('#dis').css("display","none")
			      				}
								   var data={
										   productId:productId,
										   type:2,
										   bacthId:bacthId,
										   procedureTypeId:id,
								   }
			      				//查询各个工序的名称
								   $.ajax({
										url:"${ctx}/production/typeToProcedure",
										data:data,
										type:"GET",
										beforeSend:function(){
											index = layer.load(1, {
												  shade: [0.1,'#fff'] //0.1透明度的白色背景
												});
										},
										
										success:function(result){
											$(result.data).each(function(i,o){
												htmlfv +='<div class="input-group"><input type="checkbox" class="checkWork" value="'+o.id+'" data-residualnumber="'+o.residualNumber+'">'+o.name+' 剩余:'+o.residualNumber+'</input></div>'
											})
											var s="<div class='input-group'><input type='checkbox' class='checkWorkAll'>全选</input></div>"
											$('.checkworking').html(s+htmlfv);
											$(".checkWorkAll").on('click',function(){
							                    if($(this).is(':checked')){ 
										 			$('.checkWork').each(function(){  
							                    //此处如果用attr，会出现第三次失效的情况  
							                     		$(this).prop("checked",true);
										 			})
							                    }else{
							                    	$('.checkWork').each(function(){ 
							                    		$(this).prop("checked",false);
							                    		
							                    	})
							                    }
							                });
											layer.close(index);
										},error:function(){
											layer.msg("操作失败！", {icon: 2});
											layer.close(index);
										}
									});
							 })
					      }
					  });
					var data={
							type:2
					}
					//遍历人名组别
				    $.ajax({
					      url:"${ctx}/production/getGroup",
					      data:data,
					      type:"GET",
			      		  success: function (result) {
			      			  $(result.data).each(function(k,j){
			      				htmlth +='<option value="'+j.id+'">'+j.name+'</option>'
			      			  });  
			      			 $('.complete').html("<select class='form-control selectcomplete'><option value="+0+">请选择</option><option value="+""+">全部</option>"+htmlth+"</select>") 
							//改变事件
			      			 $(".selectcomplete").change(function(){
			      				var htmltwo = "";
			      				var	id=$(this).val()
								   var data={
										  id:id,
										  type:2,
								   }
			      				$.ajax({
									url:"${ctx}/production/allGroup",
									data:data,
									type:"GET",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									
									success:function(result){
										$(result.data).each(function(i,o){
										
										$(o.users).each(function(i,o){
											htmltwo +='<div class="input-group"><input type="checkbox" class="stuCheckBox" value="'+o.id+'" data-username="'+o.userName+'">'+o.userName+'</input></div>'
										})
										})
										var s="<div class='input-group'><input type='checkbox' class='checkall'>全选</input></div>"
										$('.select').html(s+htmltwo)
										$(".checkall").on('click',function(){
							                    if($(this).is(':checked')){ 
										 			$('.stuCheckBox').each(function(){  
							                    //此处如果用attr，会出现第三次失效的情况  
							                     		$(this).prop("checked",true);
										 			})
							                    }else{
							                    	$('.stuCheckBox').each(function(){ 
							                    		$(this).prop("checked",false);
							                    		
							                    	})
							                    }
							                });
										layer.close(index);
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});
							 }) 
					      }
					  });
				    
					//遍历杂工加绩比值
					var html=""
					$.ajax({
						url:"${ctx}/task/taskPerformance",
						type:"GET",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						
						success:function(result){
							$(result.data).each(function(i,o){
							html+='<option value="'+o.number+'" data-name="'+o.name+'">'+o.name+'</option>'
							})
							$('.workingtw').html("<select class='form-control selectchangtw'><option value='0'>请选择</option>"+html+"</select>");
							layer.close(index);
							
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					});
				    
					var postData
					var dicDiv=$('#addDictDivType');
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['60%', '60%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:productName,
						  content: dicDiv,
						  btn: ['确定', '取消'],
						  yes:function(index, layero){
							  var values=new Array()
							  var numberr=new Array()
								$(".checkWork:checked").each(function() {   
									values.push($(this).val());
									numberr.push($(this).data('residualnumber'));
								}); 
							  var arr=new Array()
							  
								$(".stuCheckBox:checked").each(function() {   
								    arr.push($(this).val());   
								}); 
							  var username=new Array()
							  $(".stuCheckBox:checked").each(function() {   
								  username.push($(this).data('username'));   
								});
							  if(values.length<=0){
									return layer.msg("至少选择一个工序！", {icon: 2});
								}
								if(arr.length<=0){
									return layer.msg("至少选择一个员工！", {icon: 2});
								}
								number=$(".sumnumber").val();
								for (var i = 0; i < numberr.length; i++) {
									if(numberr[i]-number<0){
										return layer.msg("有工序剩余数量不足！", {icon: 2});
									}
								}
								expectTime=$(".sumtime").val();
								var performanceNumber=$(".selectchangtw").val();
								
								var performance=$(".selectchangtw option:selected").text();
								console.log(performance)
								if(performance=="请选择请选择"){
									performance="";
								}
								var postData = {
										type:2,
										bacthId:that.data("id"),
										procedureIds:values,
										userIds:arr,
										number:number,
										userNames:username,
										performance:performance,
										performanceNumber:performanceNumber,
										productName:productName,
										expectTime:expectTime,
										bacthNumber:bacthNumber,
										allotTime:$('#Time').val(),
								}
							    $.ajax({
									url:"${ctx}/task/addTask",
									data:postData,
						            traditional: true,
									type:"post",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									
									success:function(result){
										if(0==result.code){
										  $('.addDictDivTypeForm')[0].reset(); 
										$('.checkworking').text("");
										  $('.select').text("");
											layer.msg("添加成功！", {icon: 1});
											
											
										}else{
											layer.msg("添加失败", {icon: 2});
										}
										
										layer.close(index);
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});  
							},
						   end:function(){
							  $('.addDictDivTypeForm')[0].reset(); 
							  $("#addDictDivType").hide();
						
							
						  } 
					});
					
					
				})
				
				
				
				
				
				
				
				
				
				
				//分配2
				$('.addDicttw').on('click',function(){
					var that=$(this)
					var productId=$(this).data('proid')
					var productName=$(this).data('proname')
					var bacthId=$(this).data('id')
					var bacthNumber=$(this).data('bacthnumber')
					var number=$(this).data('number')
					var _index
					var index
					var postData
					//工序遍历  
				    var indextwo;
				    
				    var htmlth = '';
				    var htmlfr = '';
				 	
				    //遍历工序类型
				    var getdata={type:"productFristQuality",}
	      			$.ajax({
					      url:"${ctx}/basedata/list",
					      data:getdata,
					      type:"GET",
			      		  success: function (result) {
			      			  $(result.data).each(function(k,j){
			      				htmlfr +='<option value="'+j.id+'">'+j.name+'</option>'
			      			  });  
			      			$('.workingtw').html("<select class='form-control selectchangtt'><option value="+0+">请选择</option><option value="+""+">全部</option>"+htmlfr+"</select>")
							//改变事件
			      			$(".selectchangtt").change(function(){
			      				var htmlfv="";
			      				var	id=$(this).val()
			      				if(id==109 || id==""){
			      					$('#diss').css("display","block")
			      				}else{
			      					$('#diss').css("display","none")
			      				}
								   var data={
										   productId:productId,
										   type:2,
										   bacthId:bacthId,
										   procedureTypeId:id,
								   }
			      				//查询各个工序的名称
								   $.ajax({
										url:"${ctx}/production/typeToProcedure",
										data:data,
										type:"GET",
										beforeSend:function(){
											index = layer.load(1, {
												  shade: [0.1,'#fff'] //0.1透明度的白色背景
												});
										},
										
										success:function(result){
											$(result.data).each(function(i,o){
												htmlfv +='<div class="input-group"><input type="checkbox" class="checkWorks" value="'+o.id+'" data-residualnumber="'+o.residualNumber+'">'+o.name+' 剩余:'+o.residualNumber+'</input></div>'
											})
											var s="<div class='input-group'><input type='checkbox' class='checkWorkAlls'>全选</input></div>"
											$('.checkworkingtw').html(s+htmlfv);
											$(".checkWorkAlls").on('click',function(){
							                    if($(this).is(':checked')){ 
										 			$('.checkWorks').each(function(){  
							                    //此处如果用attr，会出现第三次失效的情况  
							                     		$(this).prop("checked",true);
										 			})
							                    }else{
							                    	$('.checkWorks').each(function(){ 
							                    		$(this).prop("checked",false);
							                    		
							                    	})
							                    }
							                });
											layer.close(index);
										},error:function(){
											layer.msg("操作失败！", {icon: 2});
											layer.close(index);
										}
									});
							 })
					      }
					  });
					var data={
							type:2
					}
					//遍历人名组别
				    $.ajax({
					      url:"${ctx}/production/getGroup",
					      data:data,
					      type:"GET",
			      		  success: function (result) {
			      			  $(result.data).each(function(k,j){
			      				htmlth +='<option value="'+j.id+'">'+j.name+'</option>'
			      			  });  
			      			 $('.completetw').html("<select class='form-control selectcompletet'><option value="+0+">请选择</option><option value="+""+">全部</option>"+htmlth+"</select>") 
							//改变事件
			      			 $(".selectcompletet").change(function(){
			      				var htmltwo = "";
			      				var	id=$(this).val()
								   var data={
										  id:id
								   }
			      				$.ajax({
									url:"${ctx}/production/allGroup",
									data:data,
									type:"GET",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									
									success:function(result){
										$(result.data).each(function(i,o){
										
										$(o.users).each(function(i,o){
											htmltwo +='<div class="input-group"><input type="checkbox" class="stuCheckBoxt" value="'+o.id+'" data-username="'+o.userName+'">'+o.userName+'</input></div>'
										})
										})
										var s="<div class='input-group'><input type='checkbox' class='checkalls'>全选</input></div>"
										$('.selecttw').html(s+htmltwo)
										$(".checkalls").on('click',function(){
							                    if($(this).is(':checked')){ 
										 			$('.stuCheckBoxt').each(function(){  
							                    //此处如果用attr，会出现第三次失效的情况  
							                     		$(this).prop("checked",true);
										 			})
							                    }else{
							                    	$('.stuCheckBoxt').each(function(){ 
							                    		$(this).prop("checked",false);
							                    		
							                    	})
							                    }
							                });
										layer.close(index);
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});
							 }) 
					      }
					  });
				    
					//遍历杂工加绩比值
					var html=""
					$.ajax({
						url:"${ctx}/task/taskPerformance",
						type:"GET",
						beforeSend:function(){
							index = layer.load(1, {
								  shade: [0.1,'#fff'] //0.1透明度的白色背景
								});
						},
						
						success:function(result){
							$(result.data).each(function(i,o){
							html+='<option value="'+o.number+'" data-name="'+o.name+'">'+o.name+'</option>'
							})
							$('.workingth').html("<select class='form-control selectchangtwt'><option value='0'>请选择</option>"+html+"</select>");
							layer.close(index);
							
						},error:function(){
							layer.msg("操作失败！", {icon: 2});
							layer.close(index);
						}
					});
					var time;
					var timeover;
					var ss;
					var times=new Array();
					var values=new Array();
					var roleidArray = new Array();
					var str1;
					var i = -1;
					$('#save').on('click',function(){
					var trHtml="";
						i++;
						time=$("#Timetstr").val();
						timeover=$("#Timetend").val();
						var dt1 = new Date(Date.parse(time));
						var dt2 = new Date(Date.parse(timeover));
						ss=(dt2-dt1)/60000
						var arr=new Array()
						var username=new Array()
						if(dt1=="Invalid Date"){
							return layer.msg("开始时间不能为空！", {icon: 2});
						}
						if(dt2=="Invalid Date"){
							return layer.msg("结束时间不能为空！", {icon: 2});
						}
						if(dt2-dt1<=0){
							return layer.msg("结束时间不能小于开始时间！", {icon: 2});
						}
						$(".stuCheckBoxt:checked").each(function() {   
						    arr.push($(this).val()); 
						    username.push($(this).data('username'))
						}); 
						if(arr.length==0){
							 return layer.msg("领取人不能为空", {icon: 2});
						}
						
						  times.push(ss);
						  roleidArray.push(arr)
						  str1=roleidArray.join(".")
						 
						 trHtml ='<div class="form-group" data-id="'+i+'">'
	                           +'<label class="col-sm-3 col-md-2 control-label">开始时间:</label>'
                        	   +'<div class="col-sm-2 col-md-2">'
                               +'<input  class="form-control laydate-icon" value="'+time+'" onClick="laydate({elem: "#Timetstr", istime: true, format: "YYYY-MM-DD hh:mm:ss"})">'
                               +'</div>'
                               +'<div>'
                               +'<label class="col-sm-1 col-md-1 control-label" >结束时间:</label>'
                               +'<div class="col-sm-2 col-md-2">'
                               +'<input value="'+timeover+'"  class="form-control laydate-icon" onClick="laydate({elem: "#Timetend", istime: true, format: "YYYY-MM-DD hh:mm:ss"})">'
                               +'</div>'
                               +'</div>'
                               +'<label class="col-sm-1 control-label">完成人:</label>'
                               +'<div class="col-sm-2 completetw">'
                               +'<input type="text" value="'+username+'" class="form-control">'
                               +'</div>'
                               +'<div class="col-sm-1 "></div>'
                               +'<div class="col-sm-2 col-md-1"><input type="button" class="btn btn-sm btn-success del" id="'+i+'" value="删除"></input></div></div>'
                              
                               $("#tabs").append(trHtml); 
                            	 
	                       $('.del').on('click',function(){
                            	   var va=$(this).parent().parent().data('id');
                            	   times.splice(va,1,"delete");
                            		roleidArray.splice(va,1,["delete"]);
                            	   str1=roleidArray.join(".");
                            	   $(this).parent().parent().hide();
                            	   return layer.msg("删除成功", {icon: 1});
                               })
						return layer.msg("添加成功", {icon: 1});
					})
					var postData
					var dicDiv=$('#addDictDivTypetw');
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['60%', '60%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:productName,
						  content: dicDiv,
						  btn: ['确定', '取消'],
						  yes:function(index, layero){
							  var values=new Array()
							  var numberr=new Array()
							
								$(".checkWorks:checked").each(function() {   
									values.push($(this).val());
									numberr.push($(this).data('residualnumber'));
								}); 
							  var arr=new Array()
							  
								$(".stuCheckBoxt:checked").each(function() {   
								    arr.push($(this).val());   
								}); 
							  var username=new Array()
							  $(".stuCheckBoxt:checked").each(function() {   
								  username.push($(this).data('username'));   
								});
							  if(values.length<=0){
									return layer.msg("至少选择一个工序！", {icon: 2});
								}
								if(arr.length<=0){
									return layer.msg("至少选择一个员工！", {icon: 2});
								}
								 for (var i = 0; i < numberr.length; i++) {
									if(numberr[i]-number<0){
										return layer.msg("有工序剩余数量不足！", {icon: 2});
									}
								} 
								expectTime=$(".sumtimetw").val();
								var performanceNumber=$(".selectchangtwt").val();
								
								var performance=$(".selectchangtwt option:selected").text();
								if(performance=="请选择"){
									performance="";
								}
								var postData = {
										type:2,
										times:times,
										users:str1,
										bacthId:that.data("id"),
										procedureIds:values,
										number:number,
										performance:performance,
										performanceNumber:performanceNumber,
										productName:productName,
										expectTime:expectTime,
										bacthNumber:bacthNumber,
										allotTime:$('#Timet').val(),
								}
							    $.ajax({
									url:"${ctx}/task/addTaskTwo",
									data:postData,
						            traditional: true,
									type:"post",
									beforeSend:function(){
										index = layer.load(1, {
											  shade: [0.1,'#fff'] //0.1透明度的白色背景
											});
									},
									
									success:function(result){
										if(0==result.code){
										  $('.addDictDivTypeFormtw')[0].reset(); 
										$('.checkworkingtw').text("");
										  $('.selecttw').text("");
											layer.msg("添加成功！", {icon: 1});
											
											
										}else{
											layer.msg("添加失败", {icon: 2});
										}
										
										layer.close(index);
									},error:function(){
										layer.msg("操作失败！", {icon: 2});
										layer.close(index);
									}
								});  
							},
						   end:function(){
							  $('.addDictDivTypeFormtw')[0].reset(); 
							  $("#addDictDivTypetw").hide();
						
							
						  } 
					});
					
					
				})
				
				
				
				
				
				
				
				
				
			}
			this.loadworking=function(){
				var data={
						page:1,
				  		size:13,	
				  		status:1,
				  		receive:0,
				}
				var index;
			    var html = '';
			    $.ajax({
				      url:"${ctx}/bacth/allBacth",
				      data:data,
				      type:"GET",
				      beforeSend:function(){
					 	  index = layer.load(1, {
						  shade: [0.1,'#fff'] //0.1透明度的白色背景
						  });
					  }, 
		      		  success: function (result) {
		      			  
		      			 $(result.data.rows).each(function(i,o){
		      				 var a=o.number
		      				 html +='<tr>'
		      				+'<td class="text-center  bacthNumber">'+o.bacthNumber+'</td>'
		      				+'<td class="text-center  allotTime">'+o.allotTime+'</td>'
		      				+'<td class="text-center  name">'+o.product.name+'</td>'
		      				+'<td class="text-center edit number">'+o.number+'</td>'
		      				+'<td class="text-center edit numberfr"><input class="work"  value="'+a+'"></input></td>'
							+'<td class="text-center"><button class="btn btn-sm btn-primary btn-trans receive" data-id='+o.id+' data-proid='+o.product.id+' data-bacthnumber='+o.bacthNumber+' data-proname='+o.product.name+'>接收</button> </td></tr>' 
							
		      			}); 
				        //显示分页
					   	  laypage({
					      cont: 'pagerr', 
					      pages: result.data.totalPages, 
					      curr:  result.data.pageNum || 1, 
					      jump: function(obj, first){ 
					    	  if(!first){ 
					    		 
						        	var _data = {
						        			page:obj.curr,
									  		size:13,
									  		type:2,
								  			name:$('#name').val(),
								  			bacthNumber:$('#number').val(),
								  			orderTimeBegin:$("#startTime").val(),
								  			orderTimeEnd:$("#endTime").val(),
								  	}
						        
						            self.loadPagination(_data);
							     }
					      }
					    });  
					   	layer.close(index);
					   	 $("#tableworking").html(html); 
					   	self.loadEventsth();
					   
				      },error:function(){
							layer.msg("加载失败！", {icon: 2});
							layer.close(index);
					  }
				  });
			}
			this.loadEventsth=function(){
				 $('.receive').on('click',function(){
					console.log($('.work').val())
				}) 
			}
			this.events = function(){
				
				//查询
				$('.searchtask').on('click',function(){
					var data = {
				  			page:1,
				  			size:13,
				  			type:2,
				  			name:$('#name').val(),
				  			bacthNumber:$('#number').val(),
				  			 orderTimeBegin:$("#startTime").val(),
				  			orderTimeEnd:$("#endTime").val(), 
				  	}
		            self.loadPagination(data);
				});
				
				
				//触发工序弹框 加载内容方法
				$('#addprocedure').on('click',function(){
					var _index
					var productId=$(this).data('id')
					var name=$(this).data('name')
					var dicDiv=$('#addworking');
					  //打开隐藏框
					_index = layer.open({
						  type: 1,
						  skin: 'layui-layer-rim', //加上边框
						  area: ['60%', '60%'], 
						  btnAlign: 'c',//宽高
						  maxmin: true,
						  title:name,
						  content: dicDiv,
						  
						  yes:function(index, layero){
							 
							},
						  end:function(){
							  $('#addworking').hide();
							  data={
									page:1,
								  	size:13,	
								  	type:2,
								  	name:$('#name').val(),
						  			number:$('#number').val(),
							  }
							self.loadPagination(data);
						  }
					});
					
					self.loadworking(); 
					
					
				})
				
				
				
				
			}
   	}
   			var login = new Login();
				login.init();
			})
    
    </script>
       
</body>

</html>