<html>
<head>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
	<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="http://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
	<script type="text/javascript" src="http://codebeautify.org/js/ObjTree.js"></script>
	
	<script type="text/javascript">

		$(document).ready(function() {
			var data = {"markers" : [
	                        {
	                            "homeTeam":"Lawrence Library",
	                            "awayTeam":"LUGip",
	                            "markerImage":"images/red.png",
	                            "information": "Linux users group meets second Wednesday of each month.",
	                            "fixture":"Wednesday 7pm",
	                            "capacity":"",
	                            "previousScore":""
	                        },
	                        {
	                            "homeTeam":"Hamilton Library",
	                            "awayTeam":"LUGip HW SIG",
	                            "markerImage":"images/white.png",
	                            "information": "Linux users can meet the first Tuesday of the month to work out harward and configuration issues.",
	                            "fixture":"Tuesday 7pm",
	                            "capacity":"",
	                            "tv":""
	                        },
	                        {
	                            "homeTeam":"Applebees",
	                            "awayTeam":"After LUPip Mtg Spot",
	                            "markerImage":"images/newcastle.png",
	                            "information": "Some of us go there after the main LUGip meeting, drink brews, and talk.",
	                            "fixture":"Wednesday whenever",
	                            "capacity":"2 to 4 pints",
	                            "tv":""
	                        }
		                ] };
			$("#xml").click(function(){
				//document.location = 'data:Application/octet-stream,' +encodeURIComponent("ABC");
				convertObjToXml(data);
			});
			
			function convertStrToXml(str){
				if (str.trim().length > 0) {
					convertObjToXml(JSON.parse(str))
				}
			}
			function convertObjToXml(obj){
				var xotree = new XML.ObjTree();
				var xml = xotree.writeXML(obj);
				xml = decodeSpecialCharacter(xml);
				var isvalidXML;
				try {
					isvalidXML = $.parseXML(xml);
				} catch (e) {
					isvalidXML = false;

				}
				if (isvalidXML == false) {
					xml = xml.substr(0, 39) + "<root>" + xml.substr(39) + "</root>";
				}
				console.log(xml);
				var a = document.getElementById('xml');
				var blob = new Blob([xml]);
			    a.href = URL.createObjectURL(blob);
			}
			function decodeSpecialCharacter(str) {
				return str.replace(/\&amp;/g, '&').replace(/\&gt;/g, '>').replace(/\&lt;/g,
						'<').replace(/\&quot;/g, '"');
			}
			
			$("#csv").click(function(){
				var csv = JsonObj2CSV(data.markers);
				console.log(csv);
				var a = document.getElementById('csv');
				var blob = new Blob([csv]);
			    a.href = URL.createObjectURL(blob);
			});
			function JSON2CSV(objArray) {
			    var array = typeof objArray != 'object' ? JSON.parse(objArray) : objArray;
			    return JsonObj2CSV(array);		    
			}
			function JsonObj2CSV(jsonArray){
				var str = '';
			    var line = '';

			    if ($("#labels").is(':checked')) {
			        var head = jsonArray[0];
			        if ($("#quote").is(':checked')) {
			            for (var index in jsonArray[0]) {
			                var value = index + "";
			                line += '"' + value.replace(/"/g, '""') + '",';
			            }
			        } else {
			            for (var index in jsonArray[0]) {
			                line += index + ',';
			            }
			        }

			        line = line.slice(0, -1);
			        str += line + '\r\n';
			    }

			    for (var i = 0; i < jsonArray.length; i++) {
			        var line = '';

			        if ($("#quote").is(':checked')) {
			            for (var index in jsonArray[i]) {
			                var value = jsonArray[i][index] + "";
			                line += '"' + value.replace(/"/g, '""') + '",';
			            }
			        } else {
			            for (var index in jsonArray[i]) {
			                line += jsonArray[i][index] + ',';
			            }
			        }

			        line = line.slice(0, -1);
			        str += line + '\r\n';
			    }
			    return str;
			}
		});
	</script>
</head>
<body>
<h1>JSON --> XML/OXF</h1>
	<a id='xml' download='Download.xml' type='text/csv'>Download XML</a>
	
	<a id='csv' download='Download.csv' type='text/csv'>Download CSV</a>
</body>
</html>
