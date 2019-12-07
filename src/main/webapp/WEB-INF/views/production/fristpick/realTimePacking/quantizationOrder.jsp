<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${ctx }/static/layui-v2.4.5/layui/css/layui.css" media="all">
	<script src="${ctx}/static/layui-v2.4.5/layui/layui.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>量化单</title>
</head>
<body>
<div class="layui-card">
	<div class="layui-card-body">
		<table class="layui-form">
			<tr>
				<td>产品名:</td>
				<td><input type="text" name="productName" class="layui-input"></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><button type="button" class="layui-btn layui-btn-sm" lay-submit lay-filter="search">搜索</button></td>
			</tr>
		</table>
		<table id="tableData" lay-filter="tableData"></table>
	</div>
</div>
</body>
<script type="text/html" id="printPackTpl">
<div style="padding:20px;">
	<table border="1" style="margin: auto;width: 80%;text-align:center;">
		<tr>
       	 	<td>包装组贴包人</td>
        	<td>编号</td>
	    </tr>
		<tr>
	        <td>{{ d.user?d.user.userName:'---' }}</td>
	        <td>{{ d.number}}</td>
	    </tr>
		<tr>
	        <td>产品名</td>
	        <td>当件内装数量</td>
	    </tr>
		{{# layui.each(d.packingChilds,function(index,item){  }}
		<tr>
	        <td>{{ item.product.name}}</td>
	        <td>{{ item.count}}</td>
	    </tr>
	    {{# }) }}
		<tr>
	        <td></td>
	        <td>已出货</td>
	    </tr>
	</table>
</div>
<hr>
</script>
<script>
layui.config({
	base : '${ctx}/static/layui-v2.4.5/'
}).extend({
	mytable : 'layui/myModules/mytable' ,
}).define(
	['mytable','laydate'],
	function(){
		var $ = layui.jquery
		, layer = layui.layer 				
		, form = layui.form			 		
		, table = layui.table 
		, laydate = layui.laydate
		, myutil = layui.myutil
		, laytpl = layui.laytpl
		, mytable = layui.mytable;
		myutil.config.ctx = '${ctx}';
		myutil.clickTr({
			noClick:'tableData',
		});
		var allUoloadOrder = myutil.getDataSync({ url: '${ctx}/temporaryPack/findPagesUnderGoods?size=99999', });
		var allMaterials = myutil.getDataSync({ url:'${ctx}/basedata/list?type=packagingMaterials', });
		var allUser ='';
		var tableDataNoTrans = [];
		mytable.render({
			elem:'#tableData',
			url:'${ctx}/temporaryPack/findPagesQuantitative',
			toolbar:[
				'<span class="layui-btn layui-btn-sm layui-btn-" lay-event="add">新增数据</span>',
				'<span class="layui-btn layui-btn-sm layui-btn-" lay-event="update">修改数据</span>',
				'<span class="layui-btn layui-btn-sm layui-btn-" lay-event="audit">审核</span>',
				'<span class="layui-btn layui-btn-sm layui-btn-" lay-event="print">打印</span>',
			].join(' '),
			curd:{
				btn:[4],
				otherBtn:function(obj){
					if(obj.event=='add'){
						addEdit('add',{});
					}else if(obj.event=='update'){
						var check = layui.table.checkStatus('tableData').data;
						if(check.length!=1)
							return myutil.emsg('只能选择一条数据编辑');
						var i = 0;
						for(;i<tableDataNoTrans.length;i++){
							if(tableDataNoTrans[i].id==check[0].id)
								break;
						}
						addEdit('update',tableDataNoTrans[i]);
					}else if(obj.event=='audit'){
						myutil.deleTableIds({
							 table:'tableData', 
							 text:'请选择信息|是否确认发货？',
							 url:'/temporaryPack/auditQuantitative',
						})
					}else if(obj.event=='print'){
						printOrder();
					}
				},
			},
			autoUpdate:{
				deleUrl:'/temporaryPack/deleteQuantitative',
			},
			parseData:function(ret){
				if(ret.code==0){
					var data = [],d = ret.data.rows;
					tableDataNoTrans = d;
					for(var i=0,len=d.length;i<len;i++){
						var child = d[i].quantitativeChilds;
						if(!child)
							continue;
						for(var j=0,l=child.length;j<l;j++){
							data.push({
								id: d[i].id,
								quantitativeNumber: d[i].quantitativeNumber,
								time: d[i].time,
								product: child[j].product,
								sumPackageNumber: child[j].sumPackageNumber,
								singleNumber: child[j].singleNumber,
								underGoods: child[j].underGoods,
							})
						}
					}
					console.log(data)
					return {  msg:ret.message,  code:ret.code , data: data, count:ret.data.total }; 
				}
				else
					return {  msg:ret.message,  code:ret.code , data:[], count:0 }; 
			},
			cols:[[
			       { type:'checkbox',},
			       { title:'量化编号',   field:'quantitativeNumber',	},
			       { title:'包装时间',   field:'time',   },
			       { title:'产品名',   field:'underGoods_product_name', 	},
			       { title:'总包数',   field:'sumPackageNumber',	},
			       { title:'单包个数',   field:'singleNumber',	},
			       ]],
	       done:function(){
				merge('underGoods_product_name');
				merge('quantitativeNumber');
				merge('time');
				merge('0');
				function merge(field){
					var rowspan = 1,mainCols=0;
					var cache = table.cache['tableData'];
					var allCol = $('#tableData').next().find('td[data-field="'+field+'"]');
					layui.each(allCol,function(index,item){
						if(index!=0){
							var thisData = cache[index],lastData = index!=0?cache[index-1]:{id:-1};
							if(!thisData)
								return;
							if(thisData.id!=lastData.id){
								$(allCol[mainCols]).attr('rowspan',rowspan)
								mainCols = index;
								rowspan = 1;
							}else{	//与上一列相同
								rowspan++;
								$(item).css('display','none')
							}
						}
					})
					$(allCol[mainCols]).attr('rowspan',rowspan)
				}
			}
		})
		function printOrder(){	
			var choosed=layui.table.checkStatus('tableData').data;
			if(choosed.length<1)
				return myutil.emsg('请选择打印信息');
			var tpl = $('#printPackTpl').html(), html='<div id="printDiv">';
			layui.each(choosed,function(index,item){
				laytpl(tpl).render(item,function(h){ html += h; })
			})
			layer.open({
				title: '打印',
				area: ['80%','80%'],
				offset: '100px', 
				content: html+'</div>',
				btnAlign: 'c',
				btn: ['打印','取消'],
				shadeClose: true,
				yes: function(){
					printpage('printDiv');
					var ids = [];
					myutil.deleteAjax({
						ids: ids.join(),
						url:'/temporaryPack/printQuantitative',
						success:function(){
							table.reload('tableData');
						}
					})
				}
			})
		}
		function printpage(myDiv){    
			var printHtml = document.getElementById(myDiv).innerHTML;
			var wind = window.open("",'newwindow', 'height=800, width=1500, top=100, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');
			wind.document.body.innerHTML = printHtml;
			wind.print();
			return false; 
		}  
		
		function addEdit(type,data){
			var title = '新增量化单';
			
			if(data.id){
				title = '修改量化单';
			}
			var addEditWin = layer.open({
				type:1,
				area:['90%','80%'],
				title: title,
				content: [
					'<div style="padding:10px;">',
						'<div>',
							'<table class="layui-form">',
								'<tr>',
									'<td>量化时间：</td>',
									'<td>',
										'<input class="layui-input" id="addEditTime" value="2019-12-06 17:55:50">',
									'</td>',
									'<td>&nbsp;&nbsp;贴包人：</td>',
									'<td>',
										'<select id="packPeopleSelect" lay-search><option value="">请选择</option></select>',
									'</td>',
								'</tr>',
							'</table>',
						'</div>',
						'<div style="float:left;width:68%;">',
							'<table id="addTable" lay-filter="addTable"></table>',
						'</div>',
						'<div style="float:right;width:30%;">',
							'<table id="addMaterTable" lay-filter="addMaterTable"></table>',
						'</div>',
					'</div>',
				].join(' '),
				success: function(){
					var addTable = [],addMate = [];
					laydate.render({
						elem:'#addEditTime',value: new Date(),type:'datetime',
					})
					$('#packPeopleSelect').append(allUser);
					if(data.id){
						addTable = data.quantitativeChilds;
						$('#packPeopleSelect').val(data.user?data.user.id:'');
						$('#addEditTime').val(data.time);
					}
					mytable.render({
						elem: '#addTable',
						data: addTable,
						size:'lg',
						curd:{
							saveFun: function(d){
								console.log(d)
								var url = '/temporaryPack/saveQuantitative';
								if(data.id)
									url= '';
								var time = $('#addEditTime').val();
								var userId = $('#packPeopleSelect').val();
								if(!time)
									return myutil.emsg('量化单时间不能为空！');
								if(!userId)
									return myutil.emsg('请选择贴包人！');
								myutil.saveAjax({
									url: url,
									data:{
										time: time,
										userId: userId,
										child: JSON.stringify(d),
									},
									success:function(){
										layer.close(addEditWin);
										table.reload('tableData');
									}
								})
							},
							addTemp:{
								underGoodsId: allUoloadOrder[0]?allUoloadOrder[0].id:"",
								sumPackageNumber: 0,
								singleNumber: 0,
								number: 0,
							},
						},
						autoUpdate:{
							field: { underGoods_id:'underGoodsId', },
						},
						verify:{
							count:['sumPackageNumber','singleNumber','number'],
						},
						cols:[[
							{ type:'checkbox',},
							{ title:'下货单~批次号~剩余数量', field:'underGoods_id', type:'select',
								select:{data: allUoloadOrder, name:['product_name','bacthNumber','number'],} },
							{ title:'总包数',   field:'sumPackageNumber',	},
					        { title:'单包个数',   field:'singleNumber',	},
					        { title:'总数量',   field:'number',	},
						]],
					})
					mytable.render({
						elem: '#addMaterTable',
						data: addMate,
						size:'lg',
						curd:{
							btn:[1,2,4],
							addTemp:{
								underGoodsId: allMaterials[0]?allMaterials[0].id:"",
								number: 0,
							},
						},
						autoUpdate:{
							field: { underGoods_id:'underGoodsId', },
						},
						verify:{
							count:['number'],
						},
						cols:[[
							{ type:'checkbox',},
							{ title:'材料', field:'underGoods_id', type:'select',select:{data: allMaterials, } },
							{ title:'数量',   field:'number',	},
						]],
					})
					form.render();
				}
			})
		}
		myutil.getData({
			url: myutil.config.ctx+'/system/user/findUserList?foreigns=0&isAdmin=false&orgNameIds=55&quit=0',
			done: function(data){
				layui.each(data,function(index,item){
					allUser += '<option value="'+item.id+'">'+item.userName+'</option>';
				})
			}
		})
		form.on('submit(search)',function(obj){
			table.reload('tableData',{
				where: obj.field,
			})
		}) 
	}//end define function
)//endedefine
</script>
</html>