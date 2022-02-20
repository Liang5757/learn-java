$(document).ready(function () {
    $('span.bar-btn').click(function () {
        $('ul.bar-list').toggle('fast');
    });
    var pagestyle = function() {
        var iframe = $("#workspace");
        var h = $(window).height() - iframe.offset().top;
        var w = $(window).width() - iframe.offset().left;
        if(h < 300) h = 300;
        if(w < 973) w = 973;
        iframe.height(h);
        iframe.width(w);
    }
    pagestyle();
    $(window).resize(pagestyle);
    $('#mainMenu>ul').first().css('display','block');
    var first_sub_menu = $('#mainMenu>ul').first().find("a").first();
    $(first_sub_menu).addClass('selected');
    //第一次进入后台时，默认定到欢迎界面
    $("#nav>ul>li").first().children("a").addClass('actived');
    $('#workspace').attr('src',$(first_sub_menu).attr("url"));
	$(".changeTheme").click(function(){
    	if($(this).attr('data-theme') == 1){
    		$.loading.init('主题切换中...');
    		jQuery.post(framework.base+"/admin/common/theme.json",{'theme':1},function(message){
    			if(message.type == 'success'){
    				setTimeout(function(){
    					window.location.reload();
    				},1500);
    			}
    		});
    	}
    });
});
 

function openItem(menu_id, sub_menu_id){
    //var menu_id = arguments[0];
    $('.actived').removeClass('actived');
    $('#'+menu_id).addClass('actived');
    $('.selected').removeClass('selected');
    $('#mainMenu ul').css('display','none');
    $('#sort_'+menu_id).css('display','block');
    if (!sub_menu_id) {

        var first_obj = $('#sort_'+menu_id+'>li>dl>dd>ol>li').first().children('a');
        $(first_obj).addClass('selected');
        $('#crumbs').html('<span>'+$('#'+menu_id+' > span').html()+'</span><span class="arrow">&nbsp;</span><span>'+$(first_obj).text()+'</span>');
        $('#workspace').attr('src',$(first_obj).attr("url"));
    }else{
        $("#" + sub_menu_id).addClass("selected");
        $('#crumbs').html('<span>'+$('#'+menu_id+' > span').html()+'</span><span class="arrow">&nbsp;</span><span>'+$("#"+sub_menu_id).text()+'</span>');
        $('#workspace').attr('src',$("#"+sub_menu_id).attr("url"));
    }
    
}
