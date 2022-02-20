
<input type="hidden" id="pageNumber" name="pageNumber" value="[#if page.pageNumber=null]0[#else]${page.pageNumber}[/#if]" />
<div class="row">
	<div class="col-md-5 col-sm-12">
		<div class="dataTables_info" id="sample_1_info" role="status" aria-live="polite">${message("admin.page.total", page.total)}</div>
	</div>
	<div class="col-md-7 col-sm-12">
		<div class="dataTables_paginate paging_bootstrap_full_number">
			[#if totalPages > 1]
				<ul class="pagination" style="visibility: visible;">
					<li class="prev [#if isFirst]disabled[/#if]">
						<a href="[#if isFirst]javascript:;[#else]javascript: $.pageSkipDialog(${firstPageNumber});[/#if]" title="First"><i class="fa fa-angle-double-left"></i></a>
					</li>
					<li class="prev [#if isFirst]disabled[/#if]">
						<a href="[#if hasPrevious]javascript: $.pageSkipDialog(${previousPageNumber});[#else]javascript:;[/#if]" title="Prev"><i class="fa fa-angle-left"></i></a>
					</li>
					[#list segment as segmentPageNumber]
						[#if segmentPageNumber != pageNumber]
							<li>
								<a href="javascript: $.pageSkipDialog(${segmentPageNumber});">${segmentPageNumber}</a>
							</li>
						[#else]
							<li class="active">
								<a href="javascript:;">${segmentPageNumber}</a>
							</li>
						[/#if]
					[/#list]
					<li class="next [#if isLast]disabled[/#if]">
						<a href="[#if hasNext]javascript: $.pageSkipDialog(${nextPageNumber});[#else]javascript:;[/#if]" title="Next"><i class="fa fa-angle-right"></i></a>
					</li>
					<li class="next [#if isLast]disabled[/#if]">
						<a href="[#if isLast]javascript:;[#else]javascript: $.pageSkipDialog(${lastPageNumber});[/#if]" title="Last"><i class="fa fa-angle-double-right"></i></a>
					</li>
				</ul>
			[/#if]
		</div>
	</div>
</div>

<input type="hidden" id="pageNumber" name="pageNumber" value="[#if page.pageNumber=null]0[#else]${page.pageNumber}[/#if]" />
