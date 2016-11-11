<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div style="z-index: 280;position: fixed;right: 0px;left: 0px;top: 0px;bottom: 0px;display: none;"
		id="cover" onclick="cancelComment()">&nbsp;</div>
	<div class="mui-input-group"
		style="position: fixed;bottom:0px;z-index: 300;width: 100%;display:none;" id="commentForm">
		<textarea rows="5" placeholder="评论内容" style="border-bottom: 1px solid #dddddd;" id="commentText"></textarea>
		<div class="mui-button-row">
			<button class="mui-btn mui-btn-primary" onclick="submitComment();" style="width: 45%">确认</button>
			&nbsp;&nbsp;
			<button class="mui-btn mui-btn-primary" style="width: 45%" onclick="cancelComment()">取消</button>
		</div>
		<input type="hidden" id="commentPostId" />
	</div>