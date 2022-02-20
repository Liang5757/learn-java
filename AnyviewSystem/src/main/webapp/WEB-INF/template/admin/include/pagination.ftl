
<input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}" />
<div class="row">
	<!-- 显示总记录 -->
	<div class="col-md-5 col-sm-12">
		<div class="dataTables_info" id="sample_1_info" role="status" aria-live="polite">${message("admin.page.total", page.total)}</div>
	</div>
	<div class="col-md-12 col-sm-12" style="text-align:center">
		<!-- <div class="dataTables_paginate paging_bootstrap_full_number">  -->
		<div style="margin:0 auto">
			<!-- 页数大于0时，显示分页 -->
			[#if totalPages > 0]
				<ul class="pagination" style="visibility: visible;">
					<!-- 首页 -->
					<li class="prev [#if isFirst]disabled[/#if]">
						<!-- <a href="[#if isFirst]javascript:;[#else]javascript: $.pageSkip(${firstPageNumber});[/#if]" title="First"><i class="fa fa-angle-double-left"></i></a> -->
						<a href="[#if isFirst]javascript:;[#else]javascript: $.pageSkip(${firstPageNumber});[/#if]" title="First">首页</a>
					</li>
					<!-- 上一页 -->
					<li class="prev [#if isFirst]disabled[/#if]">
						<!-- <a href="[#if hasPrevious]javascript: $.pageSkip(${previousPageNumber});[#else]javascript:;[/#if]" title="Prev"><i class="fa fa-angle-left"></i></a> -->
						<a href="[#if hasPrevious]javascript: $.pageSkip(${previousPageNumber});[#else]javascript:;[/#if]" title="Prev">上一页</a>
					</li>
					[#list segment as segmentPageNumber]
						[#if segmentPageNumber != pageNumber]
							<li>
								<a href="javascript: $.pageSkip(${segmentPageNumber});">${segmentPageNumber}</a>
							</li>
						[#else]
							<li class="active">
								<a href="javascript:;">${segmentPageNumber}</a>
							</li>
						[/#if]
					[/#list]
					<!-- 中间显示页数，记录 -->
					<!-- <li><span class="dataTables_info" id="sample_1_info" role="status" aria-live="polite" style="border:none;">
					当前${page.pageNumber}也/共${page.totalPages}页，${message("admin.page.total", page.total)}</span>
					</li> -->
					<!-- 下一页 -->
					<li class="next [#if isLast]disabled[/#if]">
						<!-- <a href="[#if hasNext]javascript: $.pageSkip(${nextPageNumber});[#else]javascript:;[/#if]" title="Next"><i class="fa fa-angle-right"></i></a> -->
						<a href="[#if hasNext]javascript: $.pageSkip(${nextPageNumber});[#else]javascript:;[/#if]" title="Next">下一页</a>
					</li>
					<!-- 尾页 -->
					<li class="next [#if isLast]disabled[/#if]">
						<!-- <a href="[#if isLast]javascript:;[#else]javascript: $.pageSkip(${lastPageNumber});[/#if]" title="Last"><i class="fa fa-angle-double-right"></i></a> -->
						<a href="[#if isLast]javascript:;[#else]javascript: $.pageSkip(${lastPageNumber});[/#if]" title="Last">尾页</a>
					</li>
				</ul>
			[/#if]
		</div>
	</div>
</div>

<!-- <input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}" /> -->
