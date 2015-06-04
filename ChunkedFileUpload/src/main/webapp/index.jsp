<html>
<head>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
	 <style>
		.ui-progressbar {
		position: relative;
		}
		.progress-label {
		position: absolute;
		left: 50%;
		top: 4px;
		font-weight: bold;
		text-shadow: 1px 1px 0 #fff;
		}
	</style>
	<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="http://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
	<script type="text/javascript">
		var ChunkedUploader = function (file, options) {
			if (!this instanceof ChunkedUploader) {
		        return new ChunkedUploader(file, options);
		    }
		 
		    this.file = file;
		 	this._register();
		 
		    this.options = $.extend({
		        url: '/fileUpload'
		    }, options);
		 
		    this.file_size = this.file.size;
		    this.chunk_size = 20* (1024 * 1024); // 20MB
		    this.range_start = 0;
		    this.range_end = this.chunk_size;
		 
		    if ('mozSlice' in this.file) {
		        this.slice_method = 'mozSlice';
		    }
		    else if ('webkitSlice' in this.file) {
		        this.slice_method = 'webkitSlice';
		    }
		    else {
		        this.slice_method = 'slice';
		    }
		 	this.percentComplete = 0;
		 	this.localUploaded = 0;
		 	this.uploaded = 0;
		 	this.upload_request = new XMLHttpRequest();
		};
		ChunkedUploader.prototype = {
			// Internal Methods __________________________________________________
 			_register: function() {
 				var self = this;
 				$.ajax({
 					url : 'fileUpload',
 					data: {
 						name: self.file.name, 
 						size: self.file.size,
 						type: self.file.type
 					},
 					success: function(data){
 						var fileInfo = JSON.parse(data);
						self.fileInfo = fileInfo;
						self.uploaded = fileInfo.uploaded;
 					},
 					async: false
 				});
 			},
    		_upload: function() {
        		var self = this,
            		chunk;
            	setTimeout(function() {
		            // Prevent range overflow
		            if (self.range_end > self.file_size) {
		                self.range_end = self.file_size;
		            }
		 
		            chunk = self.file[self.slice_method](self.range_start, self.range_end);
		            self.upload_request.open('POST', self.options.url, true);
		            //self.upload_request.overrideMimeType('application/octet-stream');
		 			self.upload_request.upload.addEventListener("progress", function(evt){
		 				self._updateProgress(evt);
		 			}, false);
		 			//self.upload_request.upload.addEventListener("load", function(evt){
		 			//	self._onChunkComplete(evt);
		 			//}, false);
		 			self.upload_request.upload.addEventListener("error", function(evt){
		 				self._onChunkError(evt);
		 			}, false); 
		 			self.upload_request.upload.addEventListener("abort", function(evt){
		 				self._onChunkError(evt);
		 			}, false); 
				  	
		 			self.upload_request.onreadystatechange=function(evt){
				  		if (self.upload_request.readyState==4 && self.upload_request.status==200){
				    		self._onChunkComplete(evt);
				    	}
				  	}
  					
  					
		 			var contentLength = self.range_end - self.range_start;
		           
		            self.upload_request.setRequestHeader('Content-Id', self.fileInfo.uuid);
		            self.upload_request.setRequestHeader('Content-Name', self.fileInfo.name);
		            self.upload_request.setRequestHeader('Content-Size', self.file_size);
		            self.upload_request.setRequestHeader('Content-Start', self.range_start);
		            self.upload_request.setRequestHeader('Content-Type', 'application/octet-stream');
		            self.upload_request.setRequestHeader('Content-Length', self.fileInfo.size);
		            self.upload_request.setRequestHeader('Content-Range', 'bytes ' + self.range_start + '-' + self.range_end + '/' + self.file_size);
		 			self.upload_request.send(chunk);
		        }, 20);
           	},
           	_updateProgress: function(evt){
           		var self = this;
			    if(evt.lengthComputable){
			    	this.localUploaded = evt.loaded;
			    	var percentComplete = 100* (self.uploaded+this.localUploaded) / self.file_size;
			    	$("#progressbar").progressbar("option", "value", Math.round(percentComplete));
			    }
           	},
           	_onUploadComplete: function(evt){
           		
           	},
			
			// Event Handlers ____________________________________________________
		    _onChunkComplete: function(evt) {
		    	var self = this;
		        // If the end range is already the same size as our file, we
		        // can assume that our last chunk has been processed and exit
		        // out of the function.
		        //console.log("complete: "+evt.loaded);
		        if (this.range_end === this.file_size) {
		            this._onUploadComplete();
		            return;
		        }
		 		self.uploaded = self.uploaded+this.localUploaded;
		        // Update our ranges
		        this.range_start = this.range_end;
		        this.range_end = this.range_start + this.chunk_size;
		 
		        // Continue as long as we aren't paused
		        if (!this.is_paused) {
		            this._upload();
		        }else{
		        }
		    },
			_onChunkError: function(){
				//this.is_paused = true;
			},
			_resume: function(){
			  	var self = this;
 				$.ajax({
 					url : 'fileUpload',
 					type: 'get',
 					data: {
 						uuid: self.fileInfo.uuid,
 						name: self.fileInfo.name, 
 						size: self.fileInfo.size,
 						type: self.fileInfo.type
 					},
 					success: function(data){
 						var fileInfo = JSON.parse(data);
						self.fileInfo = fileInfo;
						self.range_start = fileInfo.uploaded;
						self._upload();
 					},
 					async: false
 				});
			},
			// Public Methods ____________________________________________________
			start : function(){
				this._upload();
			},
			pause : function(){
				this.upload_request.abort();
				this.is_paused = true;
			},
			resume : function(){
				this.is_paused = false;
				this._resume();
			}
		};
		
		$(document).ready(function(e){
			 var progressbar = $( "#progressbar" ),
				 progressLabel = $( ".progress-label" ),
				 pauseBtn = $('#pause_btn'),
				 resumeBtn = $('#resume_btn'),
				 fileInput = $('#file_input'); 
			var fileUploader;
			fileInput.change(function(e){
				fileUploader = new ChunkedUploader(this.files[0]);
				fileUploader.start();
				pauseBtn.removeAttr('disabled');
				resumeBtn.attr('disabled','disabled');
			});
			pauseBtn.click(function(e){
				pauseBtn.attr('disabled','disabled');
				resumeBtn.removeAttr('disabled');
				fileUploader.pause();
			});
			resumeBtn.click(function(e){
				resumeBtn.attr('disabled','disabled');
				pauseBtn.removeAttr('disabled');
				fileUploader.resume();
			});
			progressbar.progressbar({
				change: function() {
					progressLabel.text( progressbar.progressbar( "value" ) + "%" );
				}
			});
		});
	</script>
	<script>
		
	</script>
</head>
<body>
<div id="container">
<h1>Test html5 slicing</h1>
	<form id="upload_form" action="/fileUpload" method="post">
	    <label for="file_input">Select Files:</label>
	    <input id="file_input" type="file" multiple>
	    <div>
	        <input id="pause_btn" type="button" value="Pause" disabled>
	        <input id="resume_btn" type="button" value="Resume" disabled>
	    </div>
	</form>
	<div id="progressbar"><div class="progress-label">Uploading...</div></div>
</div>
</body>
</html>
