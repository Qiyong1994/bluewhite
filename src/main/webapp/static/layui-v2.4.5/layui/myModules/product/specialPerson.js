/* 特急人员模块
 * 2019/10/4
 * author: cww
 * 使用方法：引入模块、渲染
 * specialPerson.render({
 * 	type:'',  //引用类型
 *  ctx:'',	  //前缀路径
 *  elem:'',  //绑定元素
 * })
 * 
 */
layui.config({
	base: 'static/layui-v2.4.5/'
}).extend({
	mytable: 'layui/myModules/mytable',
}).define(['jquery','table','form','mytable','laytpl','laydate'],function(exports){
	var $ = layui.jquery
	, table = layui.table 
	, form = layui.form
	, myutil = layui.myutil
	, laytpl = layui.laytpl
	, laydate = layui.laydate
	, mytable = layui.mytable
	, MODNAME = 'specialPerson'
		
	, specialPerson = {
			
	}
	, Class = function(){
		
	};
	
	var TPL = [
	           '<table class="layui-form">',
	             '<tr>',
	             	'<td>&nbsp;&nbsp;</td>',
	             	'<td>姓名:</td>',
	             	'<td><input type="text" name="userName" class="layui-input"></td>',
	             	'<td>&nbsp;&nbsp;</td>',
	             	'<td>分组:</td>',
	             	'<td><select name="groupId" id="searchSelect"><option value="">请选择</option></select></td>',
	             	'<td>&nbsp;&nbsp;</td>',
	             	'<td><span class="layui-btn" lay-submit lay-filter="search">搜索</span></td>',
	             '</tr>',
	           '</table>',
	           '<table id="tableData" lay-filter="tableData"></table>',
	           ].join(' ');
	
	
	Class.prototype.render = function(opt){
		var allGroup = [{id:'',name:'请选择'}];
		myutil.getDataSync({		
			url: opt.ctx+'/production/getGroup?type='+opt.type,
			success:function(data){
				for(var k in data){
					allGroup.push(data[k])
				}
			}
		});
		var status = [
		              { id:'0', name:'工作' ,},
		              { id:'1', name:'休息' ,},];
		laytpl(TPL).render({},function(h){
			$(opt.elem).append(h);
			var html = '';
			for(var key in allGroup){
				html+='<option value="'+allGroup[key].id+'">'+allGroup[key].name+'</option>'
			}
			$('#searchSelect').append(html);
			form.render();
		})
		
		mytable.render({
			elem:'#tableData',
			url: '/system/user/findTemporaryUserTimePages?type='+opt.type,
			size: 'lg',
			autoUpdate:{
				saveUrl:'/system/user/addTemporaryUser',
				field:{
					group_id:'groupId',
				},
				isReload:true,
			},
			verify:{
				notNull:['turnWorkTime','userName'],
				count:['phone','idCard','bankCard1'],
			},
			toolbar:['<span class="layui-btn layui-btn-sm" lay-event="onekey">一键添加考勤</span>',
			         '<span class="layui-btn layui-btn-sm" id="date">2019-10-04 ~ 2019-10-04</span>'].join(' '),
			curd:{
				btn:[1,2,3],
				otherBtn: function(obj){
					if(obj.event =='onekey'){
						var checked = layui.table.checkStatus('tableData').data;
						if(checked.length==0)
							return myutil.emsg('请选择相关人员');
						else{
							layer.confirm('是否确认添加？',function(){
								for(var k in checked){
									myutil.saveAjax({
										url:'/system/user/addTemporaryUserTime',
										data:{
											temporaryUserId: checked[k].id,
											groupId: checked[k].group && checked[k].group.id,
											temporarilyDates: $('#date').html(),
										}
									})
								}
							})
						}
					}
				} 
			},
			cols:[[
			       { type:'checkbox' },
			       { title:'人员姓名', 	field:'userName', edit:true, },
			       { title:'手机号',   field:'phone',  edit:true, },
			       { type:'select',  title:'工作状态',   field:'status',select:{ data:status, }	},
			       { title:'出勤时长',   field:'turnWorkTime',	edit:true,},
			       { title:'身份证',   field:'idCard',	edit:true,},
			       { title:'银行卡号',   field:'bankCard1',	edit:true,},
			       { type:'select',  title:'分组',   field:'group_id',	select:{ data: allGroup, }  },
			       ]],
			done:function(){
				laydate.render({
					elem:'#date',
					type:'date',
					range:"~"
				})
			}
		}) 
		
		form.on('submit(search)',function(obj){
			table.reload('tableData',{
				where: obj.field
			})
		}) 
	}
	specialPerson.render = function(opt){
		var s = new Class();
		myutil.config.ctx = opt.ctx;
		s.render(opt);
	}
	
	exports(MODNAME,specialPerson);
})