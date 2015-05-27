<html>
<head>
	<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
	<script type="text/javascript">

		$(document).ready(function() {
			function upload(filename, id, blobOrFile, count, total) {
			  var formData = new FormData();
			  formData.append("blob", blobOrFile, count, total);
			  var xhr = new XMLHttpRequest();
			  xhr.open('POST', '/fileUpload', true);
			  xhr.onload = function(e) {
				  
			  };
			  xhr.setRequestHeader("Content-Name", filename);
			  xhr.setRequestHeader("Content-Id", id);
			  xhr.setRequestHeader("Total-Chunks", total);
			  xhr.send(formData);
			}
			function makeid()
			{
			    var text = "";
			    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

			    for( var i=0; i < 5; i++ )
			        text += possible.charAt(Math.floor(Math.random() * possible.length));

			    return text;
			}
			$('#file_input').change(function(e){
				var filename = $(this).val().split('\\').pop();
				var blob = this.files[0];

				const BYTES_PER_CHUNK = 1020 * 1024; // 1MB chunk sizes.
				const SIZE = blob.size;

				var start = 0;
				var end = BYTES_PER_CHUNK;
				var count = 0;
				var total = SIZE / BYTES_PER_CHUNK;
				var id = makeid();
				while(start < SIZE) {
				    upload(filename, id, blob.slice(start, end),count, Math.ceil(total));
				    start = end;
				    count++;
					end = start + BYTES_PER_CHUNK;
				}
			});
		});
	</script>
</head>
<body>
<h1>Test html5 slicing</h1>
<form id="upload_form" action="/fileUpload" method="post">
    <label for="file_input">Select Files:</label>
    <input id="file_input" type="file" multiple>
    <div>
        <input id="submit_btn" type="submit" value="Upload" disabled>
    </div>
</form>
<ul id="file_list" style="display: none;">
    <!-- File data will be listed here -->
</ul>
</body>
</html>
