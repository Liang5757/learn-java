(function($) {
  $.fn.downloadr = function() {
  	return this.each(function() {
  	
  	function returnBrowserTest(){
				
					var dlBrowser = $.browser.browser();
					
					var dlString = '';
					
					switch(dlBrowser){
					
						case "Safari":
						
						dlString = 'right click on the icon below and choose <strong>Save Linked File As...</strong> or <strong>Download Linked File As...</strong> from the menu.';
						
						break;
						
						case "Firefox":
						
						dlString = 'right click on the icon below and choose <strong>Save Link As...</strong> from the menu.'
						
						break;
						
						case "Msie":
						
						dlString = 'right click on the icon below and choose <strong>Save Target As...</strong>.';
						
						break;
						
						default:
						
						dlString = 'right click on the icon below and choose <strong>Save Target As...</strong> from the menu.';
					}
					
					
					return dlString;
				}	
				
				var element = this;
			  
			  	$(element).addClass("download_link");
			  	
			  	var theTitle = $(element).attr('title');
			  				  	
				var theLink = $(element).attr('href');
	
			  	$(element).bind('click',function(e){
			  	
			  		e.preventDefault();

				  	var html = "";
				  	
				  	html += "<h2>下载文件'Tempfile'</h2>";
				  	html += "<p>正在下载 'Tempfile', 点击文件下载</p>";
				  	html += "<p style='text-align:center;'><a class='downloadA' href='" + theLink + "'><img src='../../resources/js/download/downloadr/download.png' alt='右击另存为' id='download_file'/></a></p>";
				  	html += "<p>如要直接打开，请点击<strong><a class='downloadA' href='" + theLink + "'> 这里</a></strong>.</p>";
				  	
				  	jQuery.facebox(html);
			  		
			  	});
			 });

  }
})(jQuery);