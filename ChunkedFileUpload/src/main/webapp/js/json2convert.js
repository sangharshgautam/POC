var converted = "", editorAce, editorResult, viewname;

$(document).ready(function() {

	viewname = $("#viewName").val().trim();

	if (viewname == 'json-to-csv') {
		setViewTitle("JSON TO CSV Converter", true, true);
		createEditor("json", "text");
	} else if (viewname == 'jsontoxml') {
		setViewTitle("JSON TO XML VIEWER", true, true);
		createEditor("json", "xml");
	} else if (viewname == 'json-to-yaml') {
		setViewTitle("JSON TO YAML Converter", true, true);
		createEditor("json", "yaml");
	} else if (viewname == 'jsonvalidate') {
		createEditor("json");
		setViewTitle("JSON Validator", true, true);
	}

});

function jsonTocsv() {
	$.ajax({
		type : "post",
		url : globalurl + "convter",
		data : {
			type : 'jsonTosql',
			data : editorAce.getValue()
		},
		success : function(response) {
			editorResult.setValue(response);
		},
		error : function(e) {
			openErrorDialog("Failed to Convert into CSV");
		}
	});
}

function json2xmlConvert() {
	try {
		editorResult.getSession().setMode("ace/mode/xml");
		var obj = editorAce.getValue();

		if (obj.trim().length > 0) {
			var xotree = new XML.ObjTree();
			var xml = xotree.writeXML(JSON.parse(obj));
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
			editorResult.setValue(vkbeautify.xml(xml));
			setOutputMsg("JSON TO XML");
		}
	} catch (e) {
		editorResult.setValue("Error in json data");
	}
}

function JsonToYAML() {
	editorResult.getSession().setMode("ace/mode/yaml");
	var oldformat = editorAce.getValue();

	if (oldformat.trim().length > 0) {
		try {

			data = json2yaml(oldformat.trim());

			editorResult.setValue(data);

			setOutputMsg("JSON TO YAML");
		} catch (e) {
			var errorData = "";

			errorData = errorData + "Error : " + e['message'];
			errorData = errorData + "\n";
			errorData = errorData + "Line : " + e['parsedLine'] + "  "
					+ e['snippet'];

			editorResult.setValue(errorData);
			setOutputMsg("Invalid JSON");
		}
	}
}

function validateJSON() {
	if (validate(editorAce.getValue().trim()) != " "
			&& editorAce.getValue().trim().length > 0) {
		var data = editorAce.getValue();
		if (data != null && data != "" && data.trim().length > 0) {
			try {
				if (jsonlint.parse(data)) {
					$("#hResult").show();
					$("#hResult").removeClass();
					$("#hResult").addClass("success");
					$("#editor").css({
						'border' : '1px solid #C6D880'
					});
					$("#hResult").text("Valid JSON");
					var oldformat = editorAce.getValue();
					;
					if (oldformat.trim().length > 0) {
						var newformat = vkbeautify.json(oldformat);
						editorAce.setValue(newformat);
						editorAce.clearSelection();
					}
				}
			} catch (e) {
				$("#hResult").show();
				$("#editor").css({
					'border' : '1px solid #FBC2C4'
				});
				$("#hResult").removeClass();
				$("#hResult").addClass("error");
				$("#hResult").text(e);
			}
		}
	} else {
		$("#editor").css({
			'border' : '1px solid #BCBDBA'
		});
		$("#hResult").hide();
	}
}

function validate(arg) {
	if (arg == undefined || arg == null || arg == "") {

		return "";
	} else {
		return arg;
	}
}
function clearJSON() {
	editorAce.setValue("");
	$("#hResult").hide();
}

function setToEditor(data) {

	editorAce.setValue(vkbeautify.json(data));
	if (viewname == 'json-to-csv') {
		jsonTocsv();
	} else if (viewname == 'jsontoxml') {
		json2xmlConvert();
	} else if (viewname == 'json-to-yaml') {
		JsonToYAML();
	} else if (viewname == 'jsonvalidate') {
		validateJSON();
	}

}
