<div class="form-group">
    <div class="row">
        <div class="col-sm-9">
            <label class="col-sm-2 control-label">用户权限<span class="required"> * </span></label>

            <div class="col-sm-10">
            [#list roots as root]
                <div class="checkbox-inline i-checks">
                    <input type="checkbox" class="i-checks" name="ids"
                           value="${root.id}">${root.name}
                </div>
                <br>

                [#list root.children as child]
                    <div class="checkbox-inline i-checks" style="margin-left:30px;">
                        <input type="checkbox" class="i-checks" name="ids"
                               value="${child.id}">${child.name}
                    </div>
                    [#list child.children as btn]
                        [#if btn_index==0]
                            <br>
                        [/#if]
                        <div class="checkbox-inline i-checks" [#if btn_index==0]style="margin-left:60px;" [/#if]>
                            <input type="checkbox" class="i-checks" name="ids" value="${btn.id}">${btn.name}
                        </div>
                        [#if !btn_has_next] <br>[/#if]
                    [/#list]
                [/#list]
                <br>
            [/#list]
            </div>
        </div>
    </div>
</div>