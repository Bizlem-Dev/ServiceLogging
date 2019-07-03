<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="org.json.JSONArray,org.json.JSONObject;import com.userPanel.service.impl.ReadXlsImpl;import com.mongodb.DB;"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>MDM Service</title>
	<link rel="stylesheet" href="mdmscript/bootstrap.min.css" >
	<link href="mdmscript/fSelect.css" rel="stylesheet">
	<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.3/jquery.min.js"></script>
    <script src="mdmscript/fSelect.js"></script>
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<style type="text/css">
	table {
		width: 100%;
	}

.glyphicon
{
    font-size: 12px;
}

td {
 padding:2px;   
}
/* layout.css Style */
.upload-drop-zone {
  height: 200px;
  border-width: 2px;
  margin-bottom: 20px;
}

/* skin.css Style*/
.upload-drop-zone {
  color: #ccc;
  border-style: dashed;
  border-color: #ccc;
  line-height: 200px;
  text-align: center;
}
.upload-drop-zone.drop {
  color: #222;
  border-color: #222;
} 
.nav-tabs > li.active > a, .nav-tabs > li.active > a:focus {
    color: #fff !important;
    background-color: #4dbdbd !important;
}

/*view search bar css*/
.responstable {
  margin: 1em 0;
  width: 100%;
  overflow: hidden;
  background: #FFF;
  color: #024457;
  border-radius: 10px;
  border: 1px solid #4dbdbd;
}
.responstable tr {
  border: 1px solid #D9E4E6;
}
.responstable tr:nth-child(odd) {
  background-color: #EAF3F3;
}
.responstable th {
  display: none;
  border: 1px solid #FFF;
  background-color: #4dbdbd;
  color: #FFF;
  padding: 1em;
}
.responstable th:first-child {
  display: table-cell;
  text-align: center;
}
.responstable th:nth-child(2) {
  display: table-cell;
}
.responstable th:nth-child(2) span {
  display: none;
}
.responstable th:nth-child(2):after {
  content: attr(data-th);
}
@media (min-width: 480px) {
  .responstable th:nth-child(2) span {
    display: block;
  }
  .responstable th:nth-child(2):after {
    display: none;
  }
}
.responstable td {
  display: block;
  word-wrap: break-word;
  max-width: 7em;
}
.responstable td:first-child {
  display: table-cell;
  text-align: center;
  border-right: 1px solid #D9E4E6;
}
@media (min-width: 480px) {
  .responstable td {
    border: 1px solid #D9E4E6;
  }
}
.responstable th, .responstable td {
  text-align: center;
  margin: .5em 1em;
}
@media (min-width: 480px) {
  .responstable th, .responstable td {
    display: table-cell;
    padding: 1em;
  }
}



.displayNone {
    display: none;
}


.search {
  width: 35%;
  position: relative;
}
.search:before {
  position: absolute;
  top: 0;
  right: 0;
  width: 40px;
  height: 40px;
  line-height: 40px;
  font-family: 'FontAwesome';
  content: '\f002';
  background: #4dbdbd;
  text-align: center;
  color: #fff;
  border-radius: 5px;
  -webkit-font-smoothing: subpixel-antialiased;
  font-smooth: always;
}

.searchTerm {
  -moz-box-sizing: border-box;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  width: 100%;
  border: 5px solid #4dbdbd;
  padding: 5px;
  height: 40px;
  border-radius: 5px;
  outline: none;
}

.searchButton {
  position: absolute;
  top: 0;
  right: 0;
  width: 40px;
  height: 40px;
  opacity: 0;
  cursor: pointer;
}

/*end of view css*/


</style>
<script>
function approveMatchRecordProduct(){
	var checkId = document.getElementsByName("matcheck");
	var catVal = document.getElementsByName("matcat");
	var prodVal = document.getElementsByName("matprod");
	var shortDescVal = document.getElementsByName("matshortdesc");
	var imageUrl = document.getElementsByName("matimage");
	var prodName = document.getElementsByName("matprodname");
	var modelNumber = document.getElementsByName("matmodelnumber");
	var manufactVal = document.getElementsByName("matmanu");
	var prodId = document.getElementsByName("matprodid");
	for(var i=0;i<checkId.length;i++){
		if(checkId[i].checked){
        	//alert(checkId[i].value);
			  var apprmatch =  'pos='+checkId[i].value+'&category='+catVal[i].value+'&prodcode='+prodVal[i].value+
			  '&prodshortdesc='+shortDescVal[i].value+'&prodimage='+imageUrl[i].value+'&prodid='+prodId[i].value+'&prodname='+prodName[i].value+'&prodmodelnumb='+modelNumber[i].value
			  +'&manufacturer='+manufactVal[i].value+'&serviceId='+$("#serviceId").val()+'&userId='+$("#userId").val();
			  console.log(apprmatch);
			  $.ajax({
			         type: 'GET',
			         url: '<%=request.getContextPath()%>/userPanel?type=approvematchproduct',

								data : apprmatch,
								dataType : 'html',
								async: false,
								success : function(data) {
									//alert(data);
									//window.close();
									console.log("approveMatchRecord Product=="+data);
									
									//location.reload();
								}
							});

        }else{
        	//alert("else");
        }
		
		// reload
		var url      = window.location.href;
		var reloadurl = "";
		if(url.indexOf("tab") != -1){
			reloadurl = url;
		}else{
			reloadurl = url + "&tab=4";	
		}
		 
		// alert(url);
		 location.replace(reloadurl);
	}
}

function rejectMatchRecordProduct(){
	var checkId = document.getElementsByName("matcheck");
	var catVal = document.getElementsByName("matcat");
	var prodVal = document.getElementsByName("matprod");
	var shortDescVal = document.getElementsByName("matshortdesc");
	var imageUrl = document.getElementsByName("matimage");
	var prodName = document.getElementsByName("matprodname");
	var modelNumber = document.getElementsByName("matmodelnumber");
	var manufactVal = document.getElementsByName("matmanu");
	var prodId = document.getElementsByName("matprodid");
	for(var i=0;i<checkId.length;i++){
		if(checkId[i].checked){
        	//alert(checkId[i].value);
			  var apprmatch =  'pos='+checkId[i].value+'&category='+catVal[i].value+'&prodcode='+prodVal[i].value+
			  '&prodshortdesc='+shortDescVal[i].value+'&prodimage='+imageUrl[i].value+'&prodid='+prodId[i].value+'&prodname='+prodName[i].value+'&prodmodelnumb='+modelNumber[i].value
			  +'&manufacturer='+manufactVal[i].value+'&serviceId='+$("#serviceId").val()+'&userId='+$("#userId").val();
			  console.log(apprmatch);
			  $.ajax({
			         type: 'GET',
			         url: '<%=request.getContextPath()%>/userPanel?type=rejectmatchproduct',

								data : apprmatch,
								dataType : 'html',
								async: false,
								success : function(data) {
									//alert(data);
									//window.close();
									console.log("rejectMatchRecord Product=="+data);
									
									//location.reload();
								}
							});

        }else{
        	//alert("else");
        }
		
		// reload
		var url      = window.location.href;
		var reloadurl = "";
		if(url.indexOf("tab") != -1){
			reloadurl = url;
		}else{
			reloadurl = url + "&tab=4";	
		}
		 
		// alert(url);
		 location.replace(reloadurl);
	}
}

function savePartRecord(id){
	document.getElementsByName("partmatprodid")[id].value = "";
	document.getElementsByName("partmatmanu")[id].value = "";
	document.getElementsByName("partmatmodelnumber")[id].value ="";
	document.getElementsByName("partmatprodname")[id].value = "";
	document.getElementsByName("partmatimage")[id].value = "";
	var checkId = document.getElementsByName("radioPartI");
	var imageUrl = document.getElementsByName("partmatimageI");
	var prodName = document.getElementsByName("partmatprodnameI");
	var modelNumber = document.getElementsByName("partmatmodelnumberI");
	var manufactVal = document.getElementsByName("partmatmanuI");
	var prodId = document.getElementsByName("partmatprodidI");
	var image = "";
	var prodname = "";
	var modelnumber = "";
	var manuf = "";
	var prodid = "";
	for(var i=0;i<checkId.length;i++){
		if(checkId[i].checked){
			if(image != ""){
				image = image +"::;;"+ imageUrl[i].value;
			}else{
				image = imageUrl[i].value;	
			}
			if(prodname != ""){
				prodname = prodname + "::;;" +prodName[i].value;
			}else{
				prodname = prodName[i].value;	
			}
			if(modelnumber != ""){
				modelnumber = modelnumber + "::;;" + modelNumber[i].value;
			}else{
				modelnumber = modelNumber[i].value;	
			}
			if(manuf != ""){
				 manuf = manuf + "::;;" + manufactVal[i].value;
			}else{
				manuf = manufactVal[i].value;	
			}
			if(prodid != ""){
				 prodid = prodid + "::;;"+ prodId[i].value;
			}else{
				prodid = prodId[i].value;	
			}
			
		}
		
		document.getElementsByName("partmatcheck")[id].checked = "true";
		document.getElementsByName("partmatprodid")[id].value = prodid;
		document.getElementsByName("partmatmanu")[id].value = manuf;
		document.getElementsByName("partmatmodelnumber")[id].value = modelnumber;
		document.getElementsByName("partmatprodname")[id].value = prodname;
		document.getElementsByName("partmatimage")[id].value = image;
		approvePartMatchRecordProduct();
	}
}

function approvePartMatchRecordProduct(){
	var checkId = document.getElementsByName("partmatcheck");
	var catVal = document.getElementsByName("partmatcat");
	var prodVal = document.getElementsByName("partmatprod");
	var shortDescVal = document.getElementsByName("partmatshortdesc");
	var imageUrl = document.getElementsByName("partmatimage");
	var prodName = document.getElementsByName("partmatprodname");
	var modelNumber = document.getElementsByName("partmatmodelnumber");
	var manufactVal = document.getElementsByName("partmatmanu");
	var prodId = document.getElementsByName("partmatprodid");
	for(var i=0;i<checkId.length;i++){
		if(checkId[i].checked){
        	//alert(checkId[i].value);
			  var apprmatch =  'pos='+checkId[i].value+'&category='+catVal[i].value+'&prodcode='+prodVal[i].value+
			  '&prodshortdesc='+shortDescVal[i].value+'&prodimage='+imageUrl[i].value+'&prodid='+prodId[i].value+'&prodname='+prodName[i].value+'&prodmodelnumb='+modelNumber[i].value
			  +'&manufacturer='+manufactVal[i].value+'&serviceId='+$("#serviceId").val()+'&userId='+$("#userId").val();
			  console.log(apprmatch);
			  $.ajax({
			         type: 'GET',
			         url: '<%=request.getContextPath()%>/userPanel?type=approvematchproduct',

								data : apprmatch,
								dataType : 'html',
								async: false,
								success : function(data) {
									//alert(data);
									//window.close();
									console.log("approveMatchRecord Product=="+data);
									
									//location.reload();
								}
							});

        }else{
        	//alert("else");
        }
		
		// reload
		var url      = window.location.href;
		var reloadurl = "";
		if(url.indexOf("tab") != -1){
			reloadurl = url;
		}else{
			reloadurl = url + "&tab=4";	
		}
		 
		// alert(url);
		 location.replace(reloadurl);
	}
}

function unmatchedRerun(){
	var checkId = document.getElementsByName("unmatcheck");
	var catVal = document.getElementsByName("unmatcat");
	var prodVal = document.getElementsByName("unmatprod");
	var shortDescVal = document.getElementsByName("unmatshortdesc");
	var addtnM = document.getElementsByName("unmatchedmetadata");
	for(var i=0;i<checkId.length;i++){
		if(checkId[i].checked){
        	//alert(checkId[i].value);
			  var rerunmatch =  'pos='+checkId[i].value+'&category='+catVal[i].value+'&prodcode='+prodVal[i].value+
			  '&prodshortdesc='+shortDescVal[i].value+'&addnmetdata='+addtnM[i].value+'&serviceId='+$("#serviceId").val()+'&userId='+$("#userId").val();
			  console.log(rerunmatch);
			  $.ajax({
			         type: 'GET',
			         url: '<%=request.getContextPath()%>/userPanel?type=rerunproduct',

								data : rerunmatch,
								dataType : 'html',
								async: false,
								success : function(data) {
									//alert(data);
									//window.close();
									console.log("unmatchedRerun Product=="+data);
									
									//location.reload();
								}
							});

        }else{
        	//alert("else");
        }
		
		// reload
		var url      = window.location.href;
		var reloadurl = "";
		if(url.indexOf("tab") != -1){
			reloadurl = url;
		}else{
			reloadurl = url + "&tab=4";	
		}
		 
		// alert(url);
		 location.replace(reloadurl);
	}

}

function partmatchedRerun(){
	var checkId = document.getElementsByName("partmatcheck");
	var catVal = document.getElementsByName("partmatcat");
	var prodVal = document.getElementsByName("partmatprod");
	var shortDescVal = document.getElementsByName("partmatshortdesc");
	var addtnM = document.getElementsByName("partmatchedmetadata");
	for(var i=0;i<checkId.length;i++){
		if(checkId[i].checked){
        	//alert(checkId[i].value);
			  var rerunmatch =  'pos='+checkId[i].value+'&category='+catVal[i].value+'&prodcode='+prodVal[i].value+
			  '&prodshortdesc='+shortDescVal[i].value+'&addnmetdata='+addtnM[i].value+'&serviceId='+$("#serviceId").val()+'&userId='+$("#userId").val();
			  console.log(rerunmatch);
			  $.ajax({
			         type: 'GET',
			         url: '<%=request.getContextPath()%>/userPanel?type=rerunproduct',

								data : rerunmatch,
								dataType : 'html',
								async: false,
								success : function(data) {
									//alert(data);
									//window.close();
									console.log("partmatchedRerun Product=="+data);
									
									//location.reload();
								}
							});

        }else{
        	//alert("else");
        }
		
		// reload
		var url      = window.location.href;
		var reloadurl = "";
		if(url.indexOf("tab") != -1){
			reloadurl = url;
		}else{
			reloadurl = url + "&tab=4";	
		}
		 
		// alert(url);
		 location.replace(reloadurl);
	}

}

function approveMatchRecordAddr(){
	var checkId = document.getElementsByName("mataddrcheck");
	var streetaddress = document.getElementsByName("matstaddr");
	var country = document.getElementsByName("matcntaddr");
	var region = document.getElementsByName("matregaddr");
	var locality = document.getElementsByName("matlocaddr");
	var postalcode = document.getElementsByName("matpcaddr");
	var addrId = document.getElementsByName("mataddrid");
	for(var i=0;i<checkId.length;i++){
		if(checkId[i].checked){
        	//alert(checkId[i].value);
			  var apprmatch =  'pos='+checkId[i].value+'&streetaddress='+streetaddress[i].value+'&country='+country[i].value+
			  '&region='+region[i].value+'&locality='+locality[i].value+'&postalcode='+postalcode[i].value+'&addrId='+addrId[i].value+'&serviceId='+$("#serviceId").val()+'&userId='+$("#userId").val();
			  console.log(apprmatch);
			  $.ajax({
			         type: 'GET',
			         url: '<%=request.getContextPath()%>/userPanel?type=approvematchaddress',

								data : apprmatch,
								dataType : 'html',
								async: false,
								success : function(data) {
									//alert(data);
									//window.close();
									console.log("approveMatchRecord Product=="+data);
									
									//location.reload();
								}
							});

        }else{
        	//alert("else");
        }
		
		// reload
		var url      = window.location.href;
		var reloadurl = "";
		if(url.indexOf("tab") != -1){
			reloadurl = url;
		}else{
			reloadurl = url + "&tab=4";	
		}
		 
		// alert(url);
		 location.replace(reloadurl);
	}
}

function rejectMatchRecordAddr(){
	var checkId = document.getElementsByName("mataddrcheck");
	var streetaddress = document.getElementsByName("matstaddr");
	var country = document.getElementsByName("matcntaddr");
	var region = document.getElementsByName("matregaddr");
	var locality = document.getElementsByName("matlocaddr");
	var postalcode = document.getElementsByName("matpcaddr");
	var addrId = document.getElementsByName("mataddrid");
	for(var i=0;i<checkId.length;i++){
		if(checkId[i].checked){
        	//alert(checkId[i].value);
			  var apprmatch =  'pos='+checkId[i].value+'&streetaddress='+streetaddress[i].value+'&country='+country[i].value+
			  '&region='+region[i].value+'&locality='+locality[i].value+'&postalcode='+postalcode[i].value+'&addrId='+addrId[i].value+'&serviceId='+$("#serviceId").val()+'&userId='+$("#userId").val();
			  console.log(apprmatch);
			  $.ajax({
			         type: 'GET',
			         url: '<%=request.getContextPath()%>/userPanel?type=rejectmatchaddress',

								data : apprmatch,
								dataType : 'html',
								async: false,
								success : function(data) {
									//alert(data);
									//window.close();
									console.log("rejectMatchRecord Product=="+data);
									
									//location.reload();
								}
							});

        }else{
        	//alert("else");
        }
		
		// reload
		var url      = window.location.href;
		var reloadurl = "";
		if(url.indexOf("tab") != -1){
			reloadurl = url;
		}else{
			reloadurl = url + "&tab=4";	
		}
		 
		// alert(url);
		 location.replace(reloadurl);
	}
}

function savePartRecordAddr(id){
	document.getElementsByName("partmatregaddr")[id].value = "";
	document.getElementsByName("partmatlocaddr")[id].value = "";
	document.getElementsByName("partmatpcaddr")[id].value ="";
	document.getElementsByName("partmataddrid")[id].value = "";
	var checkId = document.getElementsByName("radioPartIAddr");
	var regId = document.getElementsByName("partmatregaddrI");
	var locId = document.getElementsByName("partmatlocaddrI");
	var postalId = document.getElementsByName("partmatpcaddrI");
	var addrId = document.getElementsByName("partmataddridI");
	var image = "";
	var prodname = "";
	var modelnumber = "";
	var prodid = "";
	for(var i=0;i<checkId.length;i++){
		if(checkId[i].checked){
			if(image != ""){
				image = image +"::;;"+ regId[i].value;
			}else{
				image = regId[i].value;	
			}
			if(prodname != ""){
				prodname = prodname + "::;;" +locId[i].value;
			}else{
				prodname = locId[i].value;	
			}
			if(modelnumber != ""){
				modelnumber = modelnumber + "::;;" + postalId[i].value;
			}else{
				modelnumber = postalId[i].value;	
			}
			
			if(prodid != ""){
				 prodid = prodid + "::;;"+ addrId[i].value;
			}else{
				prodid = addrId[i].value;	
			}
			
		}
		
		document.getElementsByName("partmataddrcheck")[id].checked = "true";
		document.getElementsByName("partmatregaddr")[id].value = image;
	document.getElementsByName("partmatlocaddr")[id].value = prodname;
	document.getElementsByName("partmatpcaddr")[id].value =modelnumber;
	document.getElementsByName("partmataddrid")[id].value = prodid;
	
		approvePartMatchRecordAddr();
	}
}

function approvePartMatchRecordAddr(){
	var checkId = document.getElementsByName("partmataddrcheck");
	var streetaddress = document.getElementsByName("partmatstaddr");
	var country = document.getElementsByName("partmatcntaddr");
	var region = document.getElementsByName("partmatregaddr");
	var locality = document.getElementsByName("partmatlocaddr");
	var postalcode = document.getElementsByName("partmatpcaddr");
	var addrId = document.getElementsByName("partmataddrid");
	for(var i=0;i<checkId.length;i++){
		if(checkId[i].checked){
        	//alert(checkId[i].value);
			  var apprmatch =  'pos='+checkId[i].value+'&streetaddress='+streetaddress[i].value+'&country='+country[i].value+
			  '&region='+region[i].value+'&locality='+locality[i].value+'&postalcode='+postalcode[i].value+'&addrId='+addrId[i].value+'&serviceId='+$("#serviceId").val()+'&userId='+$("#userId").val();
			  console.log(apprmatch);
			  $.ajax({
			         type: 'GET',
			         url: '<%=request.getContextPath()%>/userPanel?type=approvematchaddress',

								data : apprmatch,
								dataType : 'html',
								async: false,
								success : function(data) {
									//alert(data);
									//window.close();
									console.log("approveMatchRecord Product=="+data);
									
									//location.reload();
								}
							});

        }else{
        	//alert("else");
        }
		
		// reload
		var url      = window.location.href;
		var reloadurl = "";
		if(url.indexOf("tab") != -1){
			reloadurl = url;
		}else{
			reloadurl = url + "&tab=4";	
		}
		 
		// alert(url);
		 location.replace(reloadurl);
	}
}

function unmatchedRerunAddr(){
	var checkId = document.getElementsByName("unmataddrcheck");
	var streetaddress = document.getElementsByName("unmatstaddr");
	var country = document.getElementsByName("unmatcntaddr");
	var addtnM = document.getElementsByName("unmatchedmetadataAddr");
	for(var i=0;i<checkId.length;i++){
		if(checkId[i].checked){
        	//alert(checkId[i].value);
			  var rerunmatch =  'pos='+checkId[i].value+'&streetaddress='+streetaddress[i].value+'&country='+country[i].value+'&addnmetdata='+addtnM[i].value+'&serviceId='+$("#serviceId").val()+'&userId='+$("#userId").val();
			  console.log(rerunmatch);
			  $.ajax({
			         type: 'GET',
			         url: '<%=request.getContextPath()%>/userPanel?type=rerunaddress',

								data : rerunmatch,
								dataType : 'html',
								async: false,
								success : function(data) {
									//alert(data);
									//window.close();
									console.log("unmatchedRerunAddr Product=="+data);
									
									//location.reload();
								}
							});

        }else{
        	//alert("else");
        }
		
		// reload
		var url      = window.location.href;
		var reloadurl = "";
		if(url.indexOf("tab") != -1){
			reloadurl = url;
		}else{
			reloadurl = url + "&tab=4";	
		}
		 
		// alert(url);
		 location.replace(reloadurl);
	}

}

function partmatchedRerunAddr(){
	var checkId = document.getElementsByName("partmataddrcheck");
	var streetaddress = document.getElementsByName("partmatstaddr");
	var country = document.getElementsByName("partmatcntaddr");
	var addtnM = document.getElementsByName("partmatchedmetadataaddr");
	for(var i=0;i<checkId.length;i++){
		if(checkId[i].checked){
        	//alert(checkId[i].value);
			  var rerunmatch =  'pos='+checkId[i].value+'&streetaddress='+streetaddress[i].value+'&country='+country[i].value+'&addnmetdata='+addtnM[i].value+'&serviceId='+$("#serviceId").val()+'&userId='+$("#userId").val();
			  console.log(rerunmatch);
			  $.ajax({
			         type: 'GET',
			         url: '<%=request.getContextPath()%>/userPanel?type=rerunproduct',

								data : rerunmatch,
								dataType : 'html',
								async: false,
								success : function(data) {
									//alert(data);
									//window.close();
									console.log("partmatchedRerun Product=="+data);
									
									//location.reload();
								}
							});

        }else{
        	//alert("else");
        }
		
		// reload
		var url      = window.location.href;
		var reloadurl = "";
		if(url.indexOf("tab") != -1){
			reloadurl = url;
		}else{
			reloadurl = url + "&tab=4";	
		}
		 
		// alert(url);
		 location.replace(reloadurl);
	}

}


function approveMatchRecordVendor(){
	var checkId = document.getElementsByName("matvendorcheck");
	var vendorname = document.getElementsByName("matvendorname");
	var streetaddress = document.getElementsByName("matstaddrvendor");
	var country = document.getElementsByName("matcntvendor");
	var region = document.getElementsByName("matregaddrvendor");
	var locality = document.getElementsByName("matlocaddrvendor");
	var postalcode = document.getElementsByName("matpcaddrvendor");
	var website = document.getElementsByName("matvendorsite");
	var vendorId = document.getElementsByName("matvendorid");
	for(var i=0;i<checkId.length;i++){
		if(checkId[i].checked){
        	//alert(checkId[i].value);
			  var apprmatch =  'pos='+checkId[i].value+'&streetaddress='+streetaddress[i].value+'&country='+country[i].value+
			  '&region='+region[i].value+'&locality='+locality[i].value+'&postalcode='+postalcode[i].value+'&vendorname='+vendorname[i].value+'&website='+website[i].value+'&vendorId='+vendorId[i].value+'&serviceId='+$("#serviceId").val()+'&userId='+$("#userId").val();
			  console.log(apprmatch);
			  $.ajax({
			         type: 'GET',
			         url: '<%=request.getContextPath()%>/userPanel?type=approvematchvendor',

								data : apprmatch,
								dataType : 'html',
								async: false,
								success : function(data) {
									//alert(data);
									//window.close();
									console.log("approveMatchRecordVendor Product=="+data);
									
									//location.reload();
								}
							});

        }else{
        	//alert("else");
        }
		
		// reload
		var url      = window.location.href;
		var reloadurl = "";
		if(url.indexOf("tab") != -1){
			reloadurl = url;
		}else{
			reloadurl = url + "&tab=4";	
		}
		 
		// alert(url);
		 location.replace(reloadurl);
	}
}

function rejectMatchRecordVendor(){
	var checkId = document.getElementsByName("matvendorcheck");
	var vendorname = document.getElementsByName("matvendorname");
	var streetaddress = document.getElementsByName("matstaddrvendor");
	var country = document.getElementsByName("matcntvendor");
	var region = document.getElementsByName("matregaddrvendor");
	var locality = document.getElementsByName("matlocaddrvendor");
	var postalcode = document.getElementsByName("matpcaddrvendor");
	var website = document.getElementsByName("matvendorsite");
	var vendorId = document.getElementsByName("matvendorid");
	for(var i=0;i<checkId.length;i++){
		if(checkId[i].checked){
        	//alert(checkId[i].value);
			  var apprmatch =  'pos='+checkId[i].value+'&streetaddress='+streetaddress[i].value+'&country='+country[i].value+
			  '&region='+region[i].value+'&locality='+locality[i].value+'&postalcode='+postalcode[i].value+'&vendorname='+vendorname[i].value+'&website='+website[i].value+'&vendorId='+vendorId[i].value+'&serviceId='+$("#serviceId").val()+'&userId='+$("#userId").val();
			  
			  console.log(apprmatch);
			  $.ajax({
			         type: 'GET',
			         url: '<%=request.getContextPath()%>/userPanel?type=rejectmatchvendor',

								data : apprmatch,
								dataType : 'html',
								async: false,
								success : function(data) {
									//alert(data);
									//window.close();
									console.log("rejectMatchRecord Product=="+data);
									
									//location.reload();
								}
							});

        }else{
        	//alert("else");
        }
		
		// reload
		var url      = window.location.href;
		var reloadurl = "";
		if(url.indexOf("tab") != -1){
			reloadurl = url;
		}else{
			reloadurl = url + "&tab=4";	
		}
		 
		// alert(url);
		 location.replace(reloadurl);
	}
}

function savePartRecordVendor(id){
	document.getElementsByName("partmatstaddrvendor")[id].value = "";
	document.getElementsByName("partmatregaddrvendor")[id].value = "";
	document.getElementsByName("partmatlocaddrvendor")[id].value = "";
	document.getElementsByName("partmatpcaddrvendor")[id].value = "";
	document.getElementsByName("partmatvendorsite")[id].value = "";
	document.getElementsByName("partmatvendorid")[id].value = "";
	var checkId = document.getElementsByName("radioPartIVendor");
	var stAdrId = document.getElementsByName("partmatstaddrvendorI");
	var regId = document.getElementsByName("partmatregaddrvendorI");
	var locId = document.getElementsByName("partmatlocaddrvendorI");
	var postalId = document.getElementsByName("partmatpcaddrvendorI");
	var siteId = document.getElementsByName("partmatvendorsiteI");
	var vendorId = document.getElementsByName("partmatvendoridI");
	var stAdr = "";
	var regAdr = "";
	var locAdr = "";
	var posC = "";
	var site = "";
	var vendorCode = "";
	for(var i=0;i<checkId.length;i++){
		if(checkId[i].checked){
			if(stAdr != ""){
				stAdr = stAdr +"::;;"+ stAdrId[i].value;
			}else{
				stAdr = stAdrId[i].value;	
			}
			if(regAdr != ""){
				regAdr = regAdr + "::;;" +regId[i].value;
			}else{
				regAdr = regId[i].value;	
			}
			if(locAdr != ""){
				locAdr = locAdr + "::;;" + locId[i].value;
			}else{
				locAdr = locId[i].value;	
			}
			if(posC != ""){
				posC = posC + "::;;" + postalId[i].value;
			}else{
				posC = postalId[i].value;	
			}
			if(site != ""){
				site = site + "::;;" + siteId[i].value;
			}else{
				site = siteId[i].value;	
			}
			if(vendorCode != ""){
				 vendorCode = vendorCode + "::;;"+ vendorId[i].value;
			}else{
				vendorCode = vendorId[i].value;	
			}
			
		}
		
		document.getElementsByName("partmatvendorcheck")[id].checked = "true";
		document.getElementsByName("partmatregaddrvendor")[id].value = regAdr;
	    document.getElementsByName("partmatlocaddrvendor")[id].value = locAdr;
	    document.getElementsByName("partmatpcaddrvendor")[id].value =posC;
	    document.getElementsByName("partmatvendorid")[id].value = vendorCode;
		document.getElementsByName("partmatstaddrvendor")[id].value = stAdr;
	    document.getElementsByName("partmatvendorsite")[id].value = site;
	
		approvePartMatchRecordVendor();
	}
}

function approvePartMatchRecordVendor(){
	var checkId = document.getElementsByName("partmatvendorcheck");
	var vendorname = document.getElementsByName("partmatvendorname");
	var streetaddress = document.getElementsByName("partmatstaddrvendor");
	var country = document.getElementsByName("partmatcntaddrvendor");
	var region = document.getElementsByName("partmatregaddrvendor");
	var locality = document.getElementsByName("partmatlocaddrvendor");
	var postalcode = document.getElementsByName("partmatpcaddrvendor");
	var website = document.getElementsByName("partmatvendorsite");
	var vendorId = document.getElementsByName("partmatvendorid");
	for(var i=0;i<checkId.length;i++){
		if(checkId[i].checked){
        	//alert(checkId[i].value);
			  var apprmatch =  'pos='+checkId[i].value+'&streetaddress='+streetaddress[i].value+'&country='+country[i].value+
			  '&region='+region[i].value+'&locality='+locality[i].value+'&postalcode='+postalcode[i].value+'&vendorname='+vendorname[i].value+
			  '&website='+website[i].value+'&vendorId='+vendorId[i].value+'&serviceId='+$("#serviceId").val()+'&userId='+$("#userId").val();
			  console.log(apprmatch);
			  $.ajax({
			         type: 'GET',
			         url: '<%=request.getContextPath()%>/userPanel?type=approvematchvendor',

								data : apprmatch,
								dataType : 'html',
								async: false,
								success : function(data) {
									//alert(data);
									//window.close();
									console.log("approveMatchRecord Product=="+data);
									
									//location.reload();
								}
							});

        }else{
        	//alert("else");
        }
		
		// reload
		var url      = window.location.href;
		var reloadurl = "";
		if(url.indexOf("tab") != -1){
			reloadurl = url;
		}else{
			reloadurl = url + "&tab=4";	
		}
		 
		// alert(url);
		 location.replace(reloadurl);
	}
}

function unmatchedRerunVendor(){
	var checkId = document.getElementsByName("unmatvendorcheck");
	var vendorname = document.getElementsByName("unmatvendorname");
	var country = document.getElementsByName("unmatcntaddrvendor");
	var addtnM = document.getElementsByName("unmatchedmetadataVendor");
	for(var i=0;i<checkId.length;i++){
		if(checkId[i].checked){
        	//alert(checkId[i].value);
			  var rerunmatch =  'pos='+checkId[i].value+'&vendorname='+vendorname[i].value+'&country='+country[i].value+'&addnmetdata='+addtnM[i].value+'&serviceId='+$("#serviceId").val()+'&userId='+$("#userId").val();
			  console.log(rerunmatch);
			  $.ajax({
			         type: 'GET',
			         url: '<%=request.getContextPath()%>/userPanel?type=rerunvendor',

								data : rerunmatch,
								dataType : 'html',
								async: false,
								success : function(data) {
									//alert(data);
									//window.close();
									console.log("unmatchedRerunAddr Product=="+data);
									
									//location.reload();
								}
							});

        }else{
        	//alert("else");
        }
		
		// reload
		var url      = window.location.href;
		var reloadurl = "";
		if(url.indexOf("tab") != -1){
			reloadurl = url;
		}else{
			reloadurl = url + "&tab=4";	
		}
		 
		// alert(url);
		 location.replace(reloadurl);
	}

}

function partmatchedRerunVendor(){
	var checkId = document.getElementsByName("partmatvendorcheck");
	var vendorname = document.getElementsByName("partmatvendorname");
	var country = document.getElementsByName("partmatcntaddrvendor");
	var addtnM = document.getElementsByName("partmatchedmetadatavendor");
	for(var i=0;i<checkId.length;i++){
		if(checkId[i].checked){
        	//alert(checkId[i].value);
			  var rerunmatch =  'pos='+checkId[i].value+'&vendorname='+vendorname[i].value+'&country='+country[i].value+'&addnmetdata='+addtnM[i].value+'&serviceId='+$("#serviceId").val()+'&userId='+$("#userId").val();
			  console.log(rerunmatch);
			  $.ajax({
			         type: 'GET',
			         url: '<%=request.getContextPath()%>/userPanel?type=rerunvendor',

								data : rerunmatch,
								dataType : 'html',
								async: false,
								success : function(data) {
									//alert(data);
									//window.close();
									console.log("partmatchedRerun Product=="+data);
									
									//location.reload();
								}
							});

        }else{
        	//alert("else");
        }
		
		// reload
		var url      = window.location.href;
		var reloadurl = "";
		if(url.indexOf("tab") != -1){
			reloadurl = url;
		}else{
			reloadurl = url + "&tab=4";	
		}
		 
		// alert(url);
		 location.replace(reloadurl);
	}

}


</script>
<body>
<header>
<input type="hidden" name="serviceId" value="<%=request.getParameter("serviceId")%>" id="serviceId" />
<input type="hidden" name="userId" value="<%=request.getRemoteUser()%>" id="userId" />
	<div class="container">
		<div class="row">
			
			<div class="bs-example bs-example-tabs"  style="height:500px; display: flex; flex-direction: column;border-radius: 15px: ">
      <ul id="myTab" class="nav nav-tabs" style="flex: 0 0 auto">
        <!-- <li id="basicli" class="active"><a href="#mdmservice" data-toggle="tab"> MDM Service</a></li>
        <li id="uploadli" class=""><a href="#upload" data-toggle="tab"> Upload</a></li>
        <li id="assignli" class=""><a href="#assignsmes" data-toggle="tab"> Assign SMEs</a></li> -->
        <li id="processli" class=""><a href="#process" data-toggle="tab"> Process</a></li>
        <li id="viewli" class=""><a href="#view" data-toggle="tab">View</a></li>

      </ul>
      <%
					String totalR = "";
					String productCount = "0";
					String addrCount = "0";
					String cvCount = "0";
					String vendorItemCount = "0";
					ReadXlsImpl objReadXlsImpl = null;
					DB db = null;
					try{
					 objReadXlsImpl = new ReadXlsImpl();
					//out.println(request.getParameter("serviceId"));
					db = objReadXlsImpl.getMongoDb();
					//out.println(db);
					totalR = objReadXlsImpl.getSMEUserProcessLength(db,request.getParameter("serviceId"),request.getRemoteUser());
					String[] processlength = totalR.split(",");
					 productCount = processlength[0];
					 addrCount = processlength[1];
					 cvCount = processlength[2];
					vendorItemCount = processlength[3];
					
					}catch(Exception e){
						//out.println("message "+e.getMessage());
					}
					%>
			<div class="tab-content">
							
			<!--Start of process tab-->
			<div id="process" class='tab-page tab-pane fade in'>
				<div class="container">
					<div class="row">
						<h2 class="text-center">Process</h2>
						<div class="bs-example bs-example-tabs"  style="height:500px; display: flex; flex-direction: column;border-radius: 15px: ">
					      <ul id="myTab" class="nav nav-tabs" style="flex: 0 0 auto">
					      	<li class="active"><a href="#product" data-toggle="tab"> Product(<%=productCount %>)</a></li>
					         <li class=""><a href="#address" data-toggle="tab"> Address(<%=addrCount %>)</a></li>
					        <li class=""><a href="#vendor" data-toggle="tab"> Vendor(<%=cvCount %>)</a></li>
					       <li class=""><a href="#customer" data-toggle="tab"> VendorItem(<%=vendorItemCount %>)</a></li>
					      </ul>
					    <div class="tab-content">
<!-- tab content start here and start of 1st tab-->
							 <div id="address" class='tab-page tab-pane fade in'>
							<div class="table-responsive col-xs-4 col-md-offset-4 col-xs-offset-1">
							 		<table class="table-bordered table-hover table-striped ">
							 			<tr>
							 				<th class="text-center" colspan="2">Summary</th>
							 				
							 			</tr>
							 			<tbody>
							 			<%
							 			int matchedRecAddr = 0;
							 			int unmatchedRecAddr = 0;
							 			int partmatchedRecAddr = 0;
							 			ReadXlsImpl objReadXlsImplAddr = new ReadXlsImpl();
										DB dbAddr = objReadXlsImplAddr.getMongoDb();
							 			int approvedCountAddr = objReadXlsImplAddr.getAddressApprovedLength(dbAddr,request.getParameter("serviceId"),request.getRemoteUser());
							 String jsonAddrData =(String) request.getAttribute("addressData");
						//	 out.print(jsonAddrData);
									JSONObject addrReslen = new JSONObject(jsonAddrData);
									if(!addrReslen.isNull("matchedinfo")){
										matchedRecAddr = addrReslen.getJSONArray("matchedinfo").length();
									}
									if(!addrReslen.isNull("unmatchedinfo")){
										unmatchedRecAddr = addrReslen.getJSONArray("unmatchedinfo").length();
									}
									if(!addrReslen.isNull("partmatchedinfo")){
										partmatchedRecAddr = addrReslen.getJSONArray("partmatchedinfo").length();
									}
									int totalProdAddr = approvedCountAddr + matchedRecAddr + unmatchedRecAddr + partmatchedRecAddr; 
									%>
							 				<tr>
							 					<td>Approved</td>
							 					<td><%=approvedCountAddr %></td>
							 				</tr>
							 				<tr>
							 					<td>Matched</td>
							 					<td><%=matchedRecAddr %></td>
							 				</tr>
							 				<tr>
							 					<td>Partially Matched</td>
							 					<td><%=partmatchedRecAddr %></td>
							 				</tr>
							 				<tr>
							 					<td>Unmatched</td>
							 					<td><%=unmatchedRecAddr %></td>
							 				</tr>
							 				<tr>
							 					<td>Total</td>
							 					<td><%=totalProdAddr %></td>
							 				</tr>
							 			</tbody>
							 		</table>
							 	</div><!--end of table div -->
							 	<div class="clearfix"></div>
							 <hr>
							<%
							// String jsonProdRes =(String) request.getAttribute("addressData");
							//	out.print(jsonProdRes);
									JSONObject addrJson = new JSONObject(jsonAddrData);
								if(!addrJson.toString().equals("{}")){%>

<!-- new tabs start here  -->
				<div class="container">
					<div class="row">
						
						<div class="bs-example bs-example-tabs"  style="height:500px; display: flex; flex-direction: column;border-radius: 15px: ">
					      <ul id="myTabA" class="nav nav-tabs" style="flex: 0 0 auto">
							<%if(!addrJson.isNull("matchedinfo")){%>
					        <li class="active"><a href="#Amatched" data-toggle="tab"> Address > Matched records</a></li>
							<%}%>
							<%if(addrJson.isNull("matchedinfo")  && !addrJson.isNull("unmatchedinfo")){%>
					        <li class="active"><a href="#Aunmatched" data-toggle="tab"> Address > Unmatched records</a></li>
							<%}else if(!addrJson.isNull("unmatchedinfo")){%>
							<li class=""><a href="#Aunmatched" data-toggle="tab"> Address > Unmatched records</a></li>
							<%}%>
							<%if(addrJson.isNull("matchedinfo")  && addrJson.isNull("unmatchedinfo") && !addrJson.isNull("partmatchedinfo")) {%>
					        <li class="active"><a href="#Apartially" data-toggle="tab"> Address > PartiallyMatched records</a></li>
							<%}else if(!addrJson.isNull("partmatchedinfo")){%>
					        <li class=""><a href="#Apartially" data-toggle="tab"> Address > PartiallyMatched records</a></li>
							<%}%>
					      </ul>
					    <div class="tab-content">
<!-- tab content start here and start of 1st tab-->
							 <%if(!addrJson.isNull("matchedinfo")){
							 JSONArray addrarray = addrJson.getJSONArray("matchedinfo");
							//	out.print(comparray);
									if(addrarray.length() > 0){
							 %>
							 <div id="Amatched" class='tab-page tab-pane fade in active'>
							 	 <h4 class="text-center">Matched Records</h4>
							 <div style="height: 300px;overflow: auto;">
							 	<table id="mytable4A" class="table table-bordred table-striped table-fixed">
					                   
					                   <thead>
					                   
					                   <th><input type="checkbox" id="checkall4A" /></th>
					                   <th>Street Address</th>
					                    <th>Country</th>
					                     <th>Region</th>
					                     <th>Locality</th>
					                     <th>Postal Code</th>
					                   </thead>
								    
								<tbody>
								   <% for(int k=0;k<addrarray.length();k++){%>
								    <tr>
								    <td><input type="hidden" value="<%=addrarray.getJSONObject(k).getString("addrid") %>" name="mataddrid"/><input type="checkbox" name="mataddrcheck" class="checkthis" value="<%=addrarray.getJSONObject(k).getString("addrpos") %>" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;" readOnly class="form-control" type="text" value="<%=addrarray.getJSONObject(k).getString("streetaddress") %>" name="matstaddr" /></td>
									<td><input style="background: rgba(0,0,0,0);border: none;" readOnly class="form-control" type="text" value="<%=addrarray.getJSONObject(k).getString("country") %>" name="matcntaddr" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=addrarray.getJSONObject(k).getString("region") %>" name="matregaddr" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=addrarray.getJSONObject(k).getString("locality") %>" name="matlocaddr" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=addrarray.getJSONObject(k).getString("postalcode") %>" name="matpcaddr" /></td>
								    </tr>
								<%} %>
								   </tbody>

								        
								</table>
							 </div> <!-- table responsive div end here -->
							 <div class="row text-center">	
								<input type="button" class="btn btn-info" onclick="approveMatchRecordAddr()" value="Approve" />
								<input type="button" class="btn btn-info" onclick="rejectMatchRecordAddr()" value="Reject" />
				             </div>
							 
							 </div>
							 <%
										}else{%>
										<h4 class="text-center">Matched Records not found</h4>	
									<%	}
									}%>
							

							 <%if(addrJson.isNull("matchedinfo")  && !addrJson.isNull("unmatchedinfo")){%>
							 <div id="Aunmatched" class='tab-page tab-pane fade in active'>
							 <%}else if(!addrJson.isNull("unmatchedinfo")){%>
							  <div id="Aunmatched" class='tab-page tab-pane fade in'>
							<%}%>
							<%if(!addrJson.isNull("unmatchedinfo")){
							JSONArray addrarrayUn = addrJson.getJSONArray("unmatchedinfo");
										//	out.print(comparray);
												if(addrarrayUn.length() > 0){
									 %>
							 <h4 class="text-center">Unmatched Records</h4>
							 <div style="height: 300px;overflow: auto;">
							 	<table id="mytable5A" class="table table-bordred table-striped table-fixed">
					                   
					                   <thead>
					                   
					                   <th><input type="checkbox" id="checkall5A" /></th>
					                   <th>Street Address</th>
					                    <th>Country</th>
					                     <th>Additional Metadata</th>
					                   </thead>
								    <tbody>
								     <% for(int k=0;k<addrarrayUn.length();k++){%>
								    <tr>
								    <td><input type="checkbox" name="unmataddrcheck" class="checkthis" value="<%=addrarrayUn.getJSONObject(k).getString("addrpos") %>" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;" readOnly class="form-control" type="text" value="<%=addrarrayUn.getJSONObject(k).getString("streetaddress") %>" name="unmatstaddr" /></td>
									<td><input style="background: rgba(0,0,0,0);border: none;" readOnly class="form-control" type="text" value="<%=addrarrayUn.getJSONObject(k).getString("country") %>" name="unmatcntaddr" /></td>
								    <%if(addrarrayUn.getJSONObject(k).isNull("additionalmetadata")) { %>
								    <td><input class="form-control" type="text" value="" name="unmatchedmetadataAddr" placeholder="enter comma separated value"/></td>
								    <%} else{ %>
								    <td><input class="form-control" type="text" value="<%=addrarrayUn.getJSONObject(k).getString("additionalmetadata") %>" name="unmatchedmetadataAddr" placeholder="enter comma separated value"/></td>
								    <%} %>
								    </tr>
								    <%} %>
								    
							 </tbody>
								        
							</table>
							 </div> <!-- table responsive div of unmatched end here -->
							 <div class="row text-center">	
								<input type="button" class="btn btn-info" value="RERUN" onclick="unmatchedRerunAddr()" />
								
				             </div>
				             <%}
									else{%>
										 <h4 class="text-center">No Unmatched Records</h4>
									
									<%}%>

							 </div>
							<%} %>								
							<%if(addrJson.isNull("matchedinfo")  && addrJson.isNull("unmatchedinfo") && !addrJson.isNull("partmatchedinfo")) {%>
					        <div id="Apartially" class='tab-page tab-pane fade in active'>
							<%}else if(!addrJson.isNull("partmatchedinfo")){%>
					        <div id="Apartially" class='tab-page tab-pane fade in'>
							<%}%>	
							 <%if(!addrJson.isNull("partmatchedinfo")){
							 	JSONArray addrarrayPat = addrJson.getJSONArray("partmatchedinfo");
										//	out.print(comparray);
												if(addrarrayPat.length() > 0){
									 %>
							 <h4 class="text-center">Partially Matched Records</h4>
														 <div style="height: 300px;overflow: auto;">
							 	<table id="mytable6A" class="table table-bordred table-striped table-fixed">
					                   
					                   <thead>
					                   
					                   <th><input type="checkbox" id="checkall6A" /></th>
					                   <th>Street Address</th>
					                    <th>Country</th>
					                     <th>Additional Metadata</th>
					                     <th>Region</th>
					                     <th>Locality</th>
					                     <th>Postal Code</th>
					                       <th></th>
					                   </thead>
								    <tbody>
								    
								   <% for(int k=0;k<addrarrayPat.length();k++){%>
								    <tr>
								    <td><input type="checkbox" name="partmataddrcheck" class="checkthis" value="<%=addrarrayPat.getJSONObject(k).getString("addrpos") %>" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;" readOnly class="form-control" type="text" value="<%=addrarrayPat.getJSONObject(k).getString("streetaddress") %>" name="partmatstaddr" /></td>
									<td><input style="background: rgba(0,0,0,0);border: none;" readOnly class="form-control" type="text" value="<%=addrarrayPat.getJSONObject(k).getString("country") %>" name="partmatcntaddr" /></td>
								    <%if(addrarrayPat.getJSONObject(k).isNull("additionalmetadata")) { %>
								    <td><input class="form-control" type="text" value="" name="partmatchedmetadataaddr"  placeholder="enter comma separated value"/></td>
								    <%}else{ %>
								    <td><input class="form-control" type="text" value="<%=addrarrayPat.getJSONObject(k).getString("additionalmetadata") %>" name="partmatchedmetadataaddr"  placeholder="enter comma separated value"/></td>
								    <%} %>
								    <%
								    if(!addrarrayPat.getJSONObject(k).isNull("dataarray")){
								    JSONArray arrayPar1 =  addrarrayPat.getJSONObject(k).getJSONArray("dataarray"); %>
								    
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=arrayPar1.getJSONObject(0).getString("region") %>" name="partmatregaddr" /><input type="hidden" value="<%=arrayPar1.getJSONObject(0).getString("addrid") %>" name="partmataddrid"/></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=arrayPar1.getJSONObject(0).getString("locality") %>" name="partmatlocaddr" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=arrayPar1.getJSONObject(0).getString("postalcode") %>" name="partmatpcaddr" /></td>
								    
								    
								    <%}else{%>
								    	<td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="" name="partmatregaddr" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="" name="partmatlocaddr" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="" name="partmatpcaddr" /></td>
									    
								    <%}%>
								    
								    <td>  <button type="button" class="btn btn-info " data-toggle="modal" data-target="#partMatchedInfoAddr<%=k%>">+</button>
								    <!--modal right popup -->
						        <div class="modal  fade" id="partMatchedInfoAddr<%=k%>" role="dialog">
							    <div class="modal-dialog">
							    
							      <!-- Modal content-->
							      <div class="modal-content">
							        <div class="modal-header">
							          <button type="button" class="close" data-dismiss="modal">&times;</button>
							          
							        </div>
							        <div  class="modal-body">
							          <table id="partDataTableAddr<%=k %>" class="table table-bordred table-striped table-fixed">
					                   
					                   <thead>
					                   
					                   <th>#</th>
					                   
					                     <th>Region</th>
					                     <th>Locality</th>
					                     <th>Postal Code</th>
					                       <th></th>
					                   </thead>
								    <tbody>
								    <%
								    
								    JSONArray arrayParAddr =  addrarrayPat.getJSONObject(k).getJSONArray("dataarray");
								    //out.print(arrayPar.length)
								    //out.print(arrayPar.getJSONObject(0).getString("modelnumber"));
								    %>
								    <%for(int inner = 0;inner<arrayParAddr.length();inner++){ %>
								    <tr>
								    <!-- <input type="radio" name="radioPartI" value="<%=inner %>" class="checkthis" /> -->
								    <td><input type="checkbox" name="radioPartIAddr" value="<%=inner %>" class="checkthis" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=arrayParAddr.getJSONObject(inner).getString("region") %>" name="partmatregaddrI" /><input type="hidden" value="<%=arrayParAddr.getJSONObject(inner).getString("addrid") %>" name="partmataddridI"/></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=arrayParAddr.getJSONObject(inner).getString("locality") %>" name="partmatlocaddrI" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=arrayParAddr.getJSONObject(inner).getString("postalcode") %>" name="partmatpcaddrI" /></td>
									</tr>
									<%} %>
									
								 </tbody>
								        
								</table>
							        </div>
							        <div class="modal-footer">
							        <button type="button" class="btn btn-info" onclick="savePartRecordAddr('<%=k %>')">Approve</button>
							          <button type="button" class="btn btn-info" data-dismiss="modal">Close</button>
							        </div>
							      </div>
							      
							    </div>
							  </div><!--end of plus wala modal-->
								    
								    </td>
								    </tr>
								    
								    								<%} %>
								   </tbody>
								        
								</table>
								
																
								
							 </div> <!-- table responsive div end here -->
							 <div class="row text-center">	
								<input type="button" class="btn btn-info" onclick="approvePartMatchRecordAddr()" value="Approve" />
								<input type="button" class="btn btn-info" value="RERUN" onclick="partmatchedRerunAddr()" />
				             </div>
				             <%
										}else{%>
										<h4 class="text-center">Partially Matched Records not found</h4>	
									<%	}
									%>
							 </div>
							 <%} %>
						</div>
					  </div>
					</div>
				</div>
				<%}else{%>
					<h4 class="text-center">No Records Found</h4>
				<%}%>
							
							 </div>
	<!--Product tab content starts here -->
							<div id="product" class='tab-page tab-pane fade in active'>
							 	<div class="table-responsive col-xs-4 col-md-offset-4 col-xs-offset-1">
							 		<table class="table-bordered table-hover table-striped ">
							 			<tr>
							 				<th class="text-center" colspan="2">Summary</th>
							 				
							 			</tr>
							 			<tbody>
							 			<%
							 			int matchedRec = 0;
							 			int unmatchedRec = 0;
							 			int partmatchedRec = 0;
							 			ReadXlsImpl objReadXlsImplPA = new ReadXlsImpl();
										DB dbPA = objReadXlsImplPA.getMongoDb();
							 			int approvedCount = objReadXlsImplPA.getProductApprovedLength(dbPA,request.getParameter("serviceId"),request.getRemoteUser());
							 String jsonProdRLen =(String) request.getAttribute("productData");
									JSONObject prodReslen = new JSONObject(jsonProdRLen);
									if(!prodReslen.isNull("matchedinfo")){
										matchedRec = prodReslen.getJSONArray("matchedinfo").length();
									}
									if(!prodReslen.isNull("unmatchedinfo")){
										unmatchedRec = prodReslen.getJSONArray("unmatchedinfo").length();
									}
									if(!prodReslen.isNull("partmatchedinfo")){
										partmatchedRec = prodReslen.getJSONArray("partmatchedinfo").length();
									}
									int totalProd = approvedCount + matchedRec + unmatchedRec + partmatchedRec; 
									%>
							 				<tr>
							 					<td>Approved</td>
							 					<td><%=approvedCount %></td>
							 				</tr>
							 				<tr>
							 					<td>Matched</td>
							 					<td><%=matchedRec %></td>
							 				</tr>
							 				<tr>
							 					<td>Partially Matched</td>
							 					<td><%=partmatchedRec %></td>
							 				</tr>
							 				<tr>
							 					<td>Unmatched</td>
							 					<td><%=unmatchedRec %></td>
							 				</tr>
							 				<tr>
							 					<td>Total</td>
							 					<td><%=totalProd %></td>
							 				</tr>
							 			</tbody>
							 		</table>
							 	</div><!--end of table div -->
							 	<div class="clearfix"></div>
							 <hr>
							<%
							 String jsonProdRes =(String) request.getAttribute("productData");
							//	out.print(jsonProdRes);
									JSONObject prodJson = new JSONObject(jsonProdRes);
								if(!prodJson.toString().equals("{}")){%>

<!-- new tabs start here  -->
				<div class="container">
					<div class="row">
						
						<div class="bs-example bs-example-tabs"  style="height:500px; display: flex; flex-direction: column;border-radius: 15px: ">
					      <ul id="myTab" class="nav nav-tabs" style="flex: 0 0 auto">
							<%if(!prodJson.isNull("matchedinfo")){%>
					        <li class="active"><a href="#Pmatched" data-toggle="tab"> Product > Matched records</a></li>
							<%}%>
							<%if(prodJson.isNull("matchedinfo")  && !prodJson.isNull("unmatchedinfo")){%>
					        <li class="active"><a href="#Punmatched" data-toggle="tab"> Product > Unmatched records</a></li>
							<%}else if(!prodJson.isNull("unmatchedinfo")){%>
							<li class=""><a href="#Punmatched" data-toggle="tab"> Product > Unmatched records</a></li>
							<%}%>
							<%if(prodJson.isNull("matchedinfo")  && prodJson.isNull("unmatchedinfo") && !prodJson.isNull("partmatchedinfo")) {%>
					        <li class="active"><a href="#Ppartially" data-toggle="tab"> Product > PartiallyMatched records</a></li>
							<%}else if(!prodJson.isNull("partmatchedinfo")){%>
					        <li class=""><a href="#Ppartially" data-toggle="tab"> Product > PartiallyMatched records</a></li>
							<%}%>
					      </ul>
					    <div class="tab-content">
<!-- tab content start here and start of 1st tab-->
							 <%if(!prodJson.isNull("matchedinfo")){
							 JSONArray prodarray = prodJson.getJSONArray("matchedinfo");
							//	out.print(comparray);
									if(prodarray.length() > 0){
							 %>
							 <div id="Pmatched" class='tab-page tab-pane fade in active'>
							 	 <h4 class="text-center">Matched Records</h4>
							 <div style="height: 300px;overflow: auto;">
							 	<table id="mytable4" class="table table-bordred table-striped table-fixed">
					                   
					                   <thead>
					                   
					                   <th><input type="checkbox" id="checkall4" /></th>
					                   <th>Category</th>
					                    <th>Product</th>
					                     <th>ShortDesc</th>
					                     <th>Image</th>
					                     <th>ProductName</th>
					                      <th>Model Number</th>
					                      
					                       <th>Manufacturer</th>
					                   </thead>
								    
								<tbody>
								   <% for(int k=0;k<prodarray.length();k++){%>
								    <tr>
								    <td><input type="hidden" value="http://35.236.154.164:8082/portal/servlet/service/productselection.brief?pid=<%=prodarray.getJSONObject(k).getString("prodid") %>" name="matprodid"/><input type="checkbox" name="matcheck" class="checkthis" value="<%=prodarray.getJSONObject(k).getString("prodpos") %>" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;" readOnly class="form-control" type="text" value="<%=prodarray.getJSONObject(k).getString("category") %>" name="matcat" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;" readOnly class="form-control" type="text" value="<%=prodarray.getJSONObject(k).getString("id") %>" name="matprod" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;" readOnly class="form-control" type="text" value="<%=prodarray.getJSONObject(k).getString("shortdesc") %>" name="matshortdesc" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=prodarray.getJSONObject(k).getString("image") %>" name="matimage" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=prodarray.getJSONObject(k).getString("productname") %>" name="matprodname" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=prodarray.getJSONObject(k).getString("modelnumber") %>" name="matmodelnumber" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=prodarray.getJSONObject(k).getString("companyname") %>" name="matmanu" /></td>
								    </tr>
								<%} %>
								   </tbody>

								        
								</table>
							 </div> <!-- table responsive div end here -->
							 <div class="row text-center">	
								<input type="button" class="btn btn-info" onclick="approveMatchRecordProduct()" value="Approve" />
								<input type="button" class="btn btn-info" onclick="rejectMatchRecordProduct()" value="Reject" />
				             </div>
							 
							 </div>
							 <%
										}else{%>
										<h4 class="text-center">Matched Records not found</h4>	
									<%	}
									}%>
							

							 <%if(prodJson.isNull("matchedinfo")  && !prodJson.isNull("unmatchedinfo")){%>
							 <div id="Punmatched" class='tab-page tab-pane fade in active'>
							 <%}else if(!prodJson.isNull("unmatchedinfo")){%>
							  <div id="Punmatched" class='tab-page tab-pane fade in'>
							<%}%>
							<%if(!prodJson.isNull("unmatchedinfo")){
							JSONArray prodarrayUn = prodJson.getJSONArray("unmatchedinfo");
										//	out.print(comparray);
												if(prodarrayUn.length() > 0){
									 %>
							 <h4 class="text-center">Unmatched Records</h4>
							 <div style="height: 300px;overflow: auto;">
							 	<table id="mytable5" class="table table-bordred table-striped table-fixed">
					                   
					                   <thead>
					                   
					                   <th><input type="checkbox" id="checkall5" /></th>
					                   <th>Category</th>
					                    <th>Product Cd</th>
					                     <th>ShortDesc</th>
					                     <th>Additional Metadata</th>
					                   </thead>
								    <tbody>
								     <% for(int k=0;k<prodarrayUn.length();k++){%>
								    <tr>
								    <td><input type="checkbox" name="unmatcheck" class="checkthis" value="<%=prodarrayUn.getJSONObject(k).getString("prodpos") %>" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;" readOnly class="form-control" type="text" value="<%=prodarrayUn.getJSONObject(k).getString("category") %>" name="unmatcat" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;" readOnly class="form-control" type="text" value="<%=prodarrayUn.getJSONObject(k).getString("id") %>" name="unmatprod" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;" readOnly class="form-control" type="text" value="<%=prodarrayUn.getJSONObject(k).getString("shortdesc") %>" name="unmatshortdesc" /></td>
								    <%if(prodarrayUn.getJSONObject(k).isNull("additionalmetadata")) { %>
								    <td><input class="form-control" type="text" value="" name="unmatchedmetadata" placeholder="enter comma separated value"/></td>
								    <%} else{ %>
								    <td><input class="form-control" type="text" value="<%=prodarrayUn.getJSONObject(k).getString("additionalmetadata") %>" name="unmatchedmetadata" placeholder="enter comma separated value"/></td>
								    <%} %>
								    </tr>
								    <%} %>
								    
							 </tbody>
								        
							</table>
							 </div> <!-- table responsive div of unmatched end here -->
							 <div class="row text-center">	
								<input type="button" class="btn btn-info" value="RERUN" onclick="unmatchedRerun()" />
								
				             </div>
				             <%}
									else{%>
										 <h4 class="text-center">No Unmatched Records</h4>
									
									<%}%>

							 </div>
							<%} %>								
							<%if(prodJson.isNull("matchedinfo")  && prodJson.isNull("unmatchedinfo") && !prodJson.isNull("partmatchedinfo")) {%>
					        <div id="Ppartially" class='tab-page tab-pane fade in active'>
							<%}else if(!prodJson.isNull("partmatchedinfo")){%>
					        <div id="Ppartially" class='tab-page tab-pane fade in'>
							<%}%>	
							 <%if(!prodJson.isNull("partmatchedinfo")){
							 	JSONArray prodarrayPat = prodJson.getJSONArray("partmatchedinfo");
										//	out.print(comparray);
												if(prodarrayPat.length() > 0){
									 %>
							 <h4 class="text-center">Partially Matched Records</h4>
														 <div style="height: 300px;overflow: auto;">
							 	<table id="mytable6" class="table table-bordred table-striped table-fixed">
					                   
					                   <thead>
					                   
					                   <th><input type="checkbox" id="checkall6" /></th>
					                   <th>Category</th>
					                    <th>Product</th>
					                     <th>ShortDesc</th>
					                     <th>Additional Metadata</th>
					                     <th>Image</th>
					                     <th>ProductName</th>
					                      <th>Model Number</th>
					                      
					                       <th>Manufacturer</th>
					                       <th></th>
					                   </thead>
								    <tbody>
								    
								   <% for(int k=0;k<prodarrayPat.length();k++){%>
								    <tr>
								    <td><input type="checkbox" name="partmatcheck" class="checkthis" value="<%=prodarrayPat.getJSONObject(k).getString("prodpos") %>" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none" readOnly class="form-control" type="text" value="<%=prodarrayPat.getJSONObject(k).getString("category") %>" name="partmatcat" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none" readOnly class="form-control" type="text" value="<%=prodarrayPat.getJSONObject(k).getString("id") %>" name="partmatprod" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none" readOnly class="form-control" type="text" value="<%=prodarrayPat.getJSONObject(k).getString("shortdesc") %>" name="partmatshortdesc" /></td>
								    <%if(prodarrayPat.getJSONObject(k).isNull("additionalmetadata")) { %>
								    <td><input class="form-control" type="text" value="" name="partmatchedmetadata"  placeholder="enter comma separated value"/></td>
								    <%}else{ %>
								    <td><input class="form-control" type="text" value="<%=prodarrayPat.getJSONObject(k).getString("additionalmetadata") %>" name="partmatchedmetadata"  placeholder="enter comma separated value"/></td>
								    <%} %>
								    <%
								    if(!prodarrayPat.getJSONObject(k).isNull("dataarray")){
								    JSONArray arrayPar1 =  prodarrayPat.getJSONObject(k).getJSONArray("dataarray"); %>
								    
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly id="imageId<%=k %>" class="form-control" type="text" value="<%=arrayPar1.getJSONObject(0).getString("image") %>" name="partmatimage" /><input type="hidden" value="http://35.236.154.164:8082/portal/servlet/service/productselection.brief?pid=<%=arrayPar1.getJSONObject(0).getString("prodid") %>" id="prodId<%=k %>" name="partmatprodid"/></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly id="prodNameId<%=k %>" class="form-control" type="text" value="<%=arrayPar1.getJSONObject(0).getString("productname") %>" name="partmatprodname" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly id="prodModelId<%=k %>" class="form-control" type="text" value="<%=arrayPar1.getJSONObject(0).getString("modelnumber") %>" name="partmatmodelnumber" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly id="compNameId<%=k %>" class="form-control" type="text" value="<%=arrayPar1.getJSONObject(0).getString("companyname") %>" name="partmatmanu" /></td>
								    
								    
								    <%}else{%>
								    	<td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly id="imageId<%=k %>" class="form-control" type="text" value="" name="partmatimage" /><input type="hidden" value="http://35.236.154.164:8082/portal/servlet/service/productselection.brief?pid=" id="prodId<%=k %>" name="partmatprodid"/></td>
									    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly id="prodNameId<%=k %>" class="form-control" type="text" value="" name="partmatprodname" /></td>
									    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly id="prodModelId<%=k %>" class="form-control" type="text" value="" name="partmatmodelnumber" /></td>
									    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly id="compNameId<%=k %>" class="form-control" type="text" value="" name="partmatmanu" /></td>
									    
								    <%}%>
								    
								    <td>  <button type="button" class="btn btn-info " data-toggle="modal" data-target="#partMatchedInfo<%=k%>">+</button>
								    <!--modal right popup -->
						        <div class="modal  fade" id="partMatchedInfo<%=k%>" role="dialog">
							    <div class="modal-dialog">
							    
							      <!-- Modal content-->
							      <div class="modal-content">
							        <div class="modal-header">
							          <button type="button" class="close" data-dismiss="modal">&times;</button>
							          
							        </div>
							        <div  class="modal-body">
							          <table id="partDataTable<%=k %>" class="table table-bordred table-striped table-fixed">
					                   
					                   <thead>
					                   
					                   <th>#</th>
					                   
					                     <th>Image</th>
					                     <th>ProductName</th>
					                      <th>Model Number</th>
					                      
					                       <th>Manufacturer</th>
					                       <th></th>
					                   </thead>
								    <tbody>
								    <%
								    
								    JSONArray arrayPar =  prodarrayPat.getJSONObject(k).getJSONArray("dataarray");
								    //out.print(arrayPar.length)
								    //out.print(arrayPar.getJSONObject(0).getString("modelnumber"));
								    %>
								    <%for(int inner = 0;inner<arrayPar.length();inner++){ %>
								    <tr>
								    <!-- <input type="radio" name="radioPartI" value="<%=inner %>" class="checkthis" /> -->
								    <td><input type="checkbox" name="radioPartI" value="<%=inner %>" class="checkthis" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none" readOnly class="form-control" type="text" value="<%=arrayPar.getJSONObject(inner).getString("image") %>" name="partmatimageI" /><input type="hidden" value="http://35.236.154.164:8082/portal/servlet/service/productselection.brief?pid=<%=arrayPar.getJSONObject(inner).getString("prodid") %>" name="partmatprodidI"/></td>
								    <td><input style="background: rgba(0,0,0,0);border: none" readOnly class="form-control" type="text" value="<%=arrayPar.getJSONObject(inner).getString("productname") %>" name="partmatprodnameI" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none" readOnly class="form-control" type="text" value="<%=arrayPar.getJSONObject(inner).getString("modelnumber") %>" name="partmatmodelnumberI" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none" readOnly class="form-control" type="text" value="<%=arrayPar.getJSONObject(inner).getString("companyname") %>" name="partmatmanuI" /></td>
									</tr>
									<%} %>
									
								 </tbody>
								        
								</table>
							        </div>
							        <div class="modal-footer">
							        <button type="button" class="btn btn-info" onclick="savePartRecord('<%=k %>')">Approve</button>
							          <button type="button" class="btn btn-info" data-dismiss="modal">Close</button>
							        </div>
							      </div>
							      
							    </div>
							  </div><!--end of plus wala modal-->
								    
								    </td>
								    </tr>
								    
								    								<%} %>
								   </tbody>
								        
								</table>
								
																
								
							 </div> <!-- table responsive div end here -->
							 <div class="row text-center">	
								<input type="button" class="btn btn-info" onclick="approvePartMatchRecordProduct()" value="Approve" />
								<input type="button" class="btn btn-info" value="RERUN" onclick="partmatchedRerun()" />
				             </div>
				             <%
										}else{%>
										<h4 class="text-center">Partially Matched Records not found</h4>	
									<%	}
									%>
							 </div>
							 <%} %>
						</div>
					  </div>
					</div>
				</div>
				<%}else{%>
					<h4 class="text-center">No Records Found</h4>
				<%}%>
							</div>
<!--VENDOR tab content starts here-->
							 <div id="vendor" class='tab-page tab-pane fade in'>
							<div class="table-responsive col-xs-4 col-md-offset-4 col-xs-offset-1">
							 		<table class="table-bordered table-hover table-striped ">
							 			<tr>
							 				<th class="text-center" colspan="2">Summary</th>
							 				
							 			</tr>
							 			<tbody>
							 			<%
							 			int matchedRecVendor = 0;
							 			int unmatchedRecVendor = 0;
							 			int partmatchedRecVendor = 0;
							 			ReadXlsImpl objReadXlsImplVendor = new ReadXlsImpl();
										DB dbVendor = objReadXlsImplVendor.getMongoDb();
							 			int approvedCountVendor = objReadXlsImplVendor.getVendorApprovedLength(dbVendor,request.getParameter("serviceId"),request.getRemoteUser());
							 String jsonVendorData =(String) request.getAttribute("vendorData");
						//	 out.print(jsonVendorData);
									JSONObject vendorReslen = new JSONObject(jsonVendorData);
									if(!vendorReslen.isNull("matchedinfo")){
										matchedRecVendor = vendorReslen.getJSONArray("matchedinfo").length();
									}
									if(!vendorReslen.isNull("unmatchedinfo")){
										unmatchedRecVendor = vendorReslen.getJSONArray("unmatchedinfo").length();
									}
									if(!vendorReslen.isNull("partmatchedinfo")){
										partmatchedRecVendor = vendorReslen.getJSONArray("partmatchedinfo").length();
									}
									int totalProdVendor = approvedCountVendor + matchedRecVendor + unmatchedRecVendor + partmatchedRecVendor; 
									%>
							 				<tr>
							 					<td>Approved</td>
							 					<td><%=approvedCountVendor %></td>
							 				</tr>
							 				<tr>
							 					<td>Matched</td>
							 					<td><%=matchedRecVendor %></td>
							 				</tr>
							 				<tr>
							 					<td>Partially Matched</td>
							 					<td><%=partmatchedRecVendor %></td>
							 				</tr>
							 				<tr>
							 					<td>Unmatched</td>
							 					<td><%=unmatchedRecVendor %></td>
							 				</tr>
							 				<tr>
							 					<td>Total</td>
							 					<td><%=totalProdVendor %></td>
							 				</tr>
							 			</tbody>
							 		</table>
							 	</div><!--end of table div -->
							 	<div class="clearfix"></div>
							 <hr>
							<%
							// String jsonProdRes =(String) request.getAttribute("vendorData");
							//	out.print(jsonProdRes);
									JSONObject vendorJson = new JSONObject(jsonVendorData);
								if(!vendorJson.toString().equals("{}")){%>

<!-- new tabs start here  -->
				<div class="container">
					<div class="row">
						
						<div class="bs-example bs-example-tabs"  style="height:500px; display: flex; flex-direction: column;border-radius: 15px: ">
					      <ul id="myTabA" class="nav nav-tabs" style="flex: 0 0 auto">
							<%if(!vendorJson.isNull("matchedinfo")){%>
					        <li class="active"><a href="#Vmatched" data-toggle="tab"> Vendor > Matched records</a></li>
							<%}%>
							<%if(vendorJson.isNull("matchedinfo")  && !vendorJson.isNull("unmatchedinfo")){%>
					        <li class="active"><a href="#Vunmatched" data-toggle="tab"> Vendor > Unmatched records</a></li>
							<%}else if(!vendorJson.isNull("unmatchedinfo")){%>
							<li class=""><a href="#Vunmatched" data-toggle="tab"> Vendor > Unmatched records</a></li>
							<%}%>
							<%if(vendorJson.isNull("matchedinfo")  && vendorJson.isNull("unmatchedinfo") && !vendorJson.isNull("partmatchedinfo")) {%>
					        <li class="active"><a href="#Vpartially" data-toggle="tab"> Vendor > PartiallyMatched records</a></li>
							<%}else if(!vendorJson.isNull("partmatchedinfo")){%>
					        <li class=""><a href="#Vpartially" data-toggle="tab"> Vendor > PartiallyMatched records</a></li>
							<%}%>
					      </ul>
					    <div class="tab-content">
<!-- tab content start here and start of 1st tab-->
							 <%if(!vendorJson.isNull("matchedinfo")){
							 JSONArray vendorarray = vendorJson.getJSONArray("matchedinfo");
							//	out.print(comparray);
									if(vendorarray.length() > 0){
							 %>
							 <div id="Vmatched" class='tab-page tab-pane fade in active'>
							 	 <h4 class="text-center">Matched Records</h4>
							 <div style="height: 300px;overflow: auto;">
							 	<table id="mytable4V" class="table table-bordred table-striped table-fixed">
					                   
					                   <thead>
					                   
					                   <th><input type="checkbox" id="checkall4V" /></th>
					                    <th>Vendor</th>  
										<th>Country</th>
										<th>Street Address</th>
					                 <th>Region</th>
					                     <th>Locality</th>
					                     <th>Postal Code</th>
										 <th>Website</th>
					                   </thead>
								    
								<tbody>
								   <% for(int k=0;k<vendorarray.length();k++){%>
								    <tr>
								    <td><input type="hidden" value="<%=vendorarray.getJSONObject(k).getString("vendorid") %>" name="matvendorid"/><input type="checkbox" name="matvendorcheck" class="checkthis" value="<%=vendorarray.getJSONObject(k).getString("vendorpos") %>" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;" readOnly class="form-control" type="text" value="<%=vendorarray.getJSONObject(k).getString("vendorname") %>" name="matvendorname" /></td>
									<td><input style="background: rgba(0,0,0,0);border: none;" readOnly class="form-control" type="text" value="<%=vendorarray.getJSONObject(k).getString("country") %>" name="matcntvendor" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=vendorarray.getJSONObject(k).getString("streetaddress") %>" name="matstaddrvendor" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=vendorarray.getJSONObject(k).getString("region") %>" name="matregaddrvendor" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=vendorarray.getJSONObject(k).getString("locality") %>" name="matlocaddrvendor" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=vendorarray.getJSONObject(k).getString("postalcode") %>" name="matpcaddrvendor" /></td>
									<td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=vendorarray.getJSONObject(k).getString("website") %>" name="matvendorsite" /></td>
								    </tr>
								<%} %>
								   </tbody>

								        
								</table>
							 </div> <!-- table responsive div end here -->
							 <div class="row text-center">	
								<input type="button" class="btn btn-info" onclick="approveMatchRecordVendor()" value="Approve" />
								<input type="button" class="btn btn-info" onclick="rejectMatchRecordVendor()" value="Reject" />
				             </div>
							 
							 </div>
							 <%
										}else{%>
										<h4 class="text-center">Matched Records not found</h4>	
									<%	}
									}%>
							

							 <%if(vendorJson.isNull("matchedinfo")  && !vendorJson.isNull("unmatchedinfo")){%>
							 <div id="Vunmatched" class='tab-page tab-pane fade in active'>
							 <%}else if(!vendorJson.isNull("unmatchedinfo")){%>
							  <div id="Vunmatched" class='tab-page tab-pane fade in'>
							<%}%>
							<%if(!vendorJson.isNull("unmatchedinfo")){
							JSONArray vendorarrayUn = vendorJson.getJSONArray("unmatchedinfo");
										//	out.print(comparray);
												if(vendorarrayUn.length() > 0){
									 %>
							 <h4 class="text-center">Unmatched Records</h4>
							 <div style="height: 300px;overflow: auto;">
							 	<table id="mytable5V" class="table table-bordred table-striped table-fixed">
					                   
					                   <thead>
					                   
					                   <th><input type="checkbox" id="checkall5V" /></th>
					                   <th>Vendor</th>  
										<th>Country</th>
										<th>Additional Metadata</th>
					                   </thead>
								    <tbody>
								     <% for(int k=0;k<vendorarrayUn.length();k++){%>
								    <tr>
								    <td><input type="checkbox" name="unmatvendorcheck" class="checkthis" value="<%=vendorarrayUn.getJSONObject(k).getString("vendorpos") %>" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;" readOnly class="form-control" type="text" value="<%=vendorarrayUn.getJSONObject(k).getString("vendorname") %>" name="unmatvendorname" /></td>
									<td><input style="background: rgba(0,0,0,0);border: none;" readOnly class="form-control" type="text" value="<%=vendorarrayUn.getJSONObject(k).getString("country") %>" name="unmatcntaddrvendor" /></td>
								    <%if(vendorarrayUn.getJSONObject(k).isNull("additionalmetadata")) { %>
								    <td><input class="form-control" type="text" value="" name="unmatchedmetadataVendor" placeholder="enter comma separated value"/></td>
								    <%} else{ %>
								    <td><input class="form-control" type="text" value="<%=vendorarrayUn.getJSONObject(k).getString("additionalmetadata") %>" name="unmatchedmetadataVendor" placeholder="enter comma separated value"/></td>
								    <%} %>
								    </tr>
								    <%} %>
								    
							 </tbody>
								        
							</table>
							 </div> <!-- table responsive div of unmatched end here -->
							 <div class="row text-center">	
								<input type="button" class="btn btn-info" value="RERUN" onclick="unmatchedRerunVendor()" />
								
				             </div>
				             <%}
									else{%>
										 <h4 class="text-center">No Unmatched Records</h4>
									
									<%}%>

							 </div>
							<%} %>								
							<%if(vendorJson.isNull("matchedinfo")  && vendorJson.isNull("unmatchedinfo") && !vendorJson.isNull("partmatchedinfo")) {%>
					        <div id="Vpartially" class='tab-page tab-pane fade in active'>
							<%}else if(!vendorJson.isNull("partmatchedinfo")){%>
					        <div id="Vpartially" class='tab-page tab-pane fade in'>
							<%}%>	
							 <%if(!vendorJson.isNull("partmatchedinfo")){
							 	JSONArray vendorarrayPat = vendorJson.getJSONArray("partmatchedinfo");
										//	out.print(comparray);
												if(vendorarrayPat.length() > 0){
									 %>
							 <h4 class="text-center">Partially Matched Records</h4>
														 <div style="height: 300px;overflow: auto;">
							 	<table id="mytable6V" class="table table-bordred table-striped table-fixed">
					                   
					                   <thead>
					                   
					                   <th><input type="checkbox" id="checkall6V" /></th>
					                    <th>Vendor</th>  
										<th>Country</th>
										<th>Street Address</th>
					                 <th>Region</th>
					                     <th>Locality</th>
					                     <th>Postal Code</th>
										 <th>Website</th>
					                       <th></th>
					                   </thead>
								    <tbody>
								    
								   <% for(int k=0;k<vendorarrayPat.length();k++){%>
								    <tr>
								    <td><input type="checkbox" name="partmatvendorcheck" class="checkthis" value="<%=vendorarrayPat.getJSONObject(k).getString("vendorpos") %>" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;" readOnly class="form-control" type="text" value="<%=vendorarrayPat.getJSONObject(k).getString("vendorname") %>" name="partmatvendorname" /></td>
									<td><input style="background: rgba(0,0,0,0);border: none;" readOnly class="form-control" type="text" value="<%=vendorarrayPat.getJSONObject(k).getString("country") %>" name="partmatcntaddrvendor" /></td>
								    <%if(vendorarrayPat.getJSONObject(k).isNull("additionalmetadata")) { %>
								    <td><input class="form-control" type="text" value="" name="partmatchedmetadatavendor"  placeholder="enter comma separated value"/></td>
								    <%}else{ %>
								    <td><input class="form-control" type="text" value="<%=vendorarrayPat.getJSONObject(k).getString("additionalmetadata") %>" name="partmatchedmetadatavendor"  placeholder="enter comma separated value"/></td>
								    <%} %>
								    <%
								    if(!vendorarrayPat.getJSONObject(k).isNull("dataarray")){
								    JSONArray arrayVendor1 =  vendorarrayPat.getJSONObject(k).getJSONArray("dataarray"); %>
								    
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=arrayVendor1.getJSONObject(0).getString("streetaddress") %>" name="partmatstaddrvendor" /></td>
									<td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=arrayVendor1.getJSONObject(0).getString("region") %>" name="partmatregaddrvendor" /><input type="hidden" value="<%=arrayVendor1.getJSONObject(0).getString("vendorid") %>" name="partmatvendorid"/></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=arrayVendor1.getJSONObject(0).getString("locality") %>" name="partmatlocaddrvendor" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=arrayVendor1.getJSONObject(0).getString("postalcode") %>" name="partmatpcaddrvendor" /></td>
									<td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=arrayVendor1.getJSONObject(0).getString("website") %>" name="partmatvendorsite" /></td>
								    
								    
								    <%}else{%>
								    	<td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="" name="partmatstaddrvendor" /></td>
									<td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="" name="partmatregaddrvendor" /><input type="hidden" value="" name="partmatvendorid"/></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="" name="partmatlocaddrvendor" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="" name="partmatpcaddrvendor" /></td>
									<td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="" name="partmatvendorsite" /></td>
									    
								    <%}%>
								    
								    <td>  <button type="button" class="btn btn-info " data-toggle="modal" data-target="#partMatchedInfoVendor<%=k%>">+</button>
								    <!--modal right popup -->
						        <div class="modal  fade" id="partMatchedInfoVendor<%=k%>" role="dialog">
							    <div class="modal-dialog">
							    
							      <!-- Modal content-->
							      <div class="modal-content">
							        <div class="modal-header">
							          <button type="button" class="close" data-dismiss="modal">&times;</button>
							          
							        </div>
							        <div  class="modal-body">
							          <table id="partDataTableVendor<%=k %>" class="table table-bordred table-striped table-fixed">
					                   
					                   <thead>
					                   
					                   <th>#</th>
					                   
					                     <th>Street Address</th>
					                 <th>Region</th>
					                     <th>Locality</th>
					                     <th>Postal Code</th>
										 <th>Website</th>
					                       <th></th>
					                   </thead>
								    <tbody>
								    <%
								    
								    JSONArray arrayParVendor =  vendorarrayPat.getJSONObject(k).getJSONArray("dataarray");
								    //out.print(arrayPar.length)
								    //out.print(arrayPar.getJSONObject(0).getString("modelnumber"));
								    %>
								    <%for(int inner = 0;inner<arrayParVendor.length();inner++){ %>
								    <tr>
								    <!-- <input type="radio" name="radioPartI" value="<%=inner %>" class="checkthis" /> -->
								    <td><input type="checkbox" name="radioPartIVendor" value="<%=inner %>" class="checkthis" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=arrayParVendor.getJSONObject(inner).getString("streetaddress") %>" name="partmatstaddrvendorI" /></td>
									<td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=arrayParVendor.getJSONObject(inner).getString("region") %>" name="partmatregaddrvendorI" /><input type="hidden" value="<%=arrayParVendor.getJSONObject(inner).getString("vendorid") %>" name="partmatvendoridI"/></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=arrayParVendor.getJSONObject(inner).getString("locality") %>" name="partmatlocaddrvendorI" /></td>
								    <td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=arrayParVendor.getJSONObject(inner).getString("postalcode") %>" name="partmatpcaddrvendorI" /></td>
									<td><input style="background: rgba(0,0,0,0);border: none;background-color: greenyellow" readOnly class="form-control" type="text" value="<%=arrayParVendor.getJSONObject(inner).getString("website") %>" name="partmatvendorsiteI" /></td>
									</tr>
									<%} %>
									
								 </tbody>
								        
								</table>
							        </div>
							        <div class="modal-footer">
							        <button type="button" class="btn btn-info" onclick="savePartRecordVendor('<%=k %>')">Approve</button>
							          <button type="button" class="btn btn-info" data-dismiss="modal">Close</button>
							        </div>
							      </div>
							      
							    </div>
							  </div><!--end of plus wala modal-->
								    
								    </td>
								    </tr>
								    
								    								<%} %>
								   </tbody>
								        
								</table>
								
																
								
							 </div> <!-- table responsive div end here -->
							 <div class="row text-center">	
								<input type="button" class="btn btn-info" onclick="approvePartMatchRecordVendor()" value="Approve" />
								<input type="button" class="btn btn-info" value="RERUN" onclick="partmatchedRerunVendor()" />
				             </div>
				             <%
										}else{%>
										<h4 class="text-center">Partially Matched Records not found</h4>	
									<%	}
									%>
							 </div>
							 <%} %>
						</div>
					  </div>
					</div>
				</div>
				<%}else{%>
					<h4 class="text-center">No Records Found</h4>
				<%}%>
							
							 </div>

<!--CUSTOMER tab content starts here -->
							 <div id="customer" class='tab-page tab-pane fade in'>
							 	<div class="table-responsive col-xs-4 col-md-offset-4 col-xs-offset-1">
							 		<table class="table-bordered table-hover table-striped ">
							 			<tr>
							 				<th class="text-center" colspan="2">Summary</th>
							 				
							 			</tr>
							 			<tbody>
							 				<tr>
							 					<td>Approved</td>
							 					<td>200</td>
							 				</tr>
							 				<tr>
							 					<td>Matched</td>
							 					<td>300</td>
							 				</tr>
							 				<tr>
							 					<td>Partially Matched</td>
							 					<td>400</td>
							 				</tr>
							 				<tr>
							 					<td>Unmatched</td>
							 					<td>400</td>
							 				</tr>
							 				<tr>
							 					<td>Total</td>
							 					<td>1000</td>
							 				</tr>
							 			</tbody>
							 		</table>
							 	</div><!--end of table div -->
							 	<div class="clearfix"></div>
							 <hr>
							 <h4 class="text-center">Matched Records</h4>
							 <div style="height: 300px;overflow: auto;">
							 	<table id="mytable10" class="table table-bordred table-striped table-fixed">
					                   
					                   <thead>
					                   
					                   <th><input type="checkbox" id="checkall10" /></th>
					                   <th>Category</th>
					                    <th>Product</th>
					                     <th>ShortDesc</th>
					                     <th>Image</th>
					                     <th>ProductName</th>
					                      <th>Model Number</th>
					                      
					                       <th>Manufacture</th>
					                   </thead>
								    <tbody>
								    
								    <tr>
								    <td><input type="checkbox" class="checkthis" /></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    </tr>
								    
								 <tr>
								    <td><input type="checkbox" class="checkthis" /></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    </tr>
								    <tr>
								    <td><input type="checkbox" class="checkthis" /></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    </tr>
								    <tr>
								    <td><input type="checkbox" class="checkthis" /></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    </tr>
								    
								   
								    
								   
								    
								    </tbody>
								        
								</table>
							 </div> <!-- table responsive div end here -->
							 <div class="row text-center">	
								<a class="btn btn-info" href="">Approve</a>
								<a class="btn btn-info" href="">Reject</a>
				             </div>
							 <hr>
							 <h4 class="text-center">Unmatched Records</h4>
							 <div style="height: 300px;overflow: auto;">
							 	<table id="mytable11" class="table table-bordred table-striped table-fixed">
					                   
					                   <thead>
					                   
					                   <th><input type="checkbox" id="checkall11" /></th>
					                   <th>Category</th>
					                    <th>Product Cd</th>
					                     <th>ShortDesc</th>
					                     <th>Additional Metadata</th>
					                   </thead>
								    <tbody>
								    
								    <tr>
								    <td><input type="checkbox" class="checkthis" /></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    
								    </tr>
								    
								 <tr>
								    <td><input type="checkbox" class="checkthis" /></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    
								    </tr>
								    <tr>
								    <td><input type="checkbox" class="checkthis" /></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								   
								    </tr>
								    <tr>
								    <td><input type="checkbox" class="checkthis" /></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    
								    </tr>
							 </tbody>
								        
							</table>
							 </div> <!-- table responsive div of unmatched end here -->
							 <div class="row text-center">	
								<a class="btn btn-info" href="">RERUN</a>
								
				             </div>
							 <hr>
							 <h4 class="text-center">Partially Matched Records</h4>
														 <div style="height: 300px;overflow: auto;">
							 	<table id="mytable12" class="table table-bordred table-striped table-fixed">
					                   
					                   <thead>
					                   
					                   <th><input type="checkbox" id="checkall12" /></th>
					                   <th>Category</th>
					                    <th>Product</th>
					                     <th>ShortDesc</th>
					                     <th>Image</th>
					                     <th>ProductName</th>
					                      <th>Model Number</th>
					                      
					                       <th>Manufacture</th>
					                   </thead>
								    <tbody>
								    
								    <tr>
								    <td><input type="checkbox" class="checkthis" /></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    </tr>
								    
								 <tr>
								    <td><input type="checkbox" class="checkthis" /></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    </tr>
								    <tr>
								    <td><input type="checkbox" class="checkthis" /></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    </tr>
								    <tr>
								    <td><input type="checkbox" class="checkthis" /></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    <td></td>
								    </tr>
								    
								   
								    
								   
								    
								    </tbody>
								        
								</table>
							 </div> <!-- table responsive div of partially  end here -->
							 <div class="row text-center">	
								<a class="btn btn-info" href="">Approve</a>
								<a class="btn btn-info" href="">RERUN</a>
				             </div>
							 <hr>
							 </div>
					    </div>  
					    </div>
					    

					</div>
				</div>
				
			</div>
<!-- End of process tabs -->
			


			<div id="view" class='tab-page tab-pane fade in'>
				
				<h2 class="text-center">View</h2>
					<br><hr>
					
					 <center>
					       <form action="" class="search">
					         <input class="searchTerm" id="search" placeholder="Enter your search term ..." /><input class="searchButton" type="submit"  />
					       </form>
					      
					    </center>
					    <div class="container">
					        <div class="row">
					            <table id="display" class="displayNone ">
					  
					             
					              <tbody  >
					                  
					              </tbody>
					              
					              
					              
					            </table>

					        </div>
					    </div>
				
			</div>
		</div>
	</div>
</header>
<section>
	
</section>	
















<!-- <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.3/jquery.min.js"></script> -->
<script  src="mdmscript/jquery.js"></script>
<script src="mdmscript/bootstrap.min.js"></script>


	<script type="text/javascript">
		$(function()
{
    $(document).on('click', '.btn-add', function(e)
    {
        e.preventDefault();

      //  var controlForm = $(this).closest('table'),
      //      currentEntry = $(this).parents('tr:first'),
      //      newEntry = $(currentEntry.clone()).appendTo(controlForm);
	var controlForm = $(this).closest('table'),

 newEntry = $('#userbody').append('<tr class=""><td class=""><input class="form-control"  name="userid" value="" type="hidden" placeholder="id" /></td><td class=""><input class="form-control"  name="username" type="text" placeholder="Name" /></td><td><input class="form-control lg"  name="useremail" type="email" placeholder="admin@example.com" /></td><td><input class="form-control"  name="usermobile" type="number" placeholder="Mobile"></td><td> <div class="" ><label class=""><input type="radio" name="useradmin" value="yes"></label></div></td><td><button class="btn btn-success btn-add" type="button"><i class="glyphicon glyphicon-plus gs"></i></button></td></tr>');

  controlForm.find('tr:not(:last) .btn-add')
            .removeClass('btn-add').addClass('btn-remove')
            .removeClass('btn-success').addClass('btn-danger')
            .html('<span class="glyphicon glyphicon-minus gs"></span>');
      
      //  newEntry.find('input').val('');
        controlForm.find('tr:not(:last) .btn-add')
            .removeClass('btn-add').addClass('btn-remove')
            .removeClass('btn-success').addClass('btn-danger')
            .html('<span class="glyphicon glyphicon-minus gs"></span>');
    }).on('click', '.btn-remove', function(e)
    {
		$(this).parents('tr:first').remove();

		e.preventDefault();
		return false;
	});
});

	</script>
	<script>
	$('#listfiles').on('click', 'a', function(){
	    $(this).closest ('tr').remove ();
	});
	
function myFunction(){
    var x = document.getElementById("filesToUpload");
    var txt = "";
    if ('files' in x) {
        if (x.files.length == 0) {
            txt = "Select one or more files.";
        } else {
            for (var i = 0; i < x.files.length; i++) {
                // txt += "<br><strong>" + (i+1) + ". file</strong><br>";
                var file = x.files[i];
                if ('name' in file) {
                    txt =  file.name + "<br>";
                    $('#listfiles').append('<tr class="info"><td><div class="pull-left" name="list"><span><i class="fa fa-file-text" aria-hidden="true"></i></span>'+txt+'</div><div class="pull-right"><a class="btn btn-info " data-toggle="tooltip" title="Delete"><i class=" fa fa-times" aria-hidden="true"></i> </a></div></td>')

                }
                // if ('size' in file) {
                //     txt += "size: " + file.size + " bytes <br>";
                // }
            }
        }
    } 
    else {
        if (x.value == "") {
            txt += "Select one or more files.";
        } else {
            txt += "The files property is not supported by your browser!";
            txt  += "<br>The path of the selected file: " + x.value; // If the browser does not support the files property, it will return the path of the selected file instead. 
        }
    }
}

</script>
<script type="text/javascript">
// upload button add

	$(function()
{

    $(document).on('click', '.btn-add1', function(e)
    {
        e.preventDefault();
 

         var controlForm = $(this).closest('table'),
            currentEntry = $(this).parents('tr:first'),
            newEntry = $(currentEntry.clone()).appendTo(controlForm);
				// newEntry = $('#userbody1').append('<tr class=""><td class=""><input type="file" name=""></td><td><button class="btn btn-success btn-add" type="button"><i class="glyphicon glyphicon-plus gs"></i></button></td></tr>');
        newEntry.find('input').val('');
        controlForm.find('tr:not(:last) .btn-add1')
            .removeClass('btn-add1').addClass('btn-remove1')
            .removeClass('btn-success').addClass('btn-danger')
            .html('<span class="glyphicon glyphicon-minus gs"></span>');
    }).on('click', '.btn-remove1', function(e)
    {
		$(this).parents('tr:first').remove();

		e.preventDefault();
		return false;
	});


});

	$(function()
			{
			   if(<%=request.getParameter("tab")%> != null){
			       //alert("if");
			       var tab = '<%=request.getParameter("tab")%>';
			       if(tab == "1"){
			           $("#basicli").addClass("active");
			           $("#uploadli").removeClass("active");
			           $("#assignli").removeClass("active");
			           $("#processli").removeClass("active");
			           $("#viewli").removeClass("active");
			           $("#mdmservice").addClass("active in");
			           $("#upload").removeClass("active in");
			           $("#assignsmes").removeClass("active in");
			           $("#process").removeClass("active in");
			           $("#view").removeClass("active in");
			       }else if(tab == "2"){
			           $("#uploadli").addClass("active");
			           $("#basicli").removeClass("active");
			           $("#assignli").removeClass("active");
			           $("#processli").removeClass("active");
			           $("#viewli").removeClass("active");
			           $("#upload").addClass("active in");
			           $("#mdmservice").removeClass("active in");
			           $("#assignsmes").removeClass("active in");
			           $("#process").removeClass("active in");
			           $("#view").removeClass("active in");
			       }else if(tab == "3"){
			           $("#assignli").addClass("active");
			           $("#basicli").removeClass("active");
			           $("#uploadli").removeClass("active");
			           $("#processli").removeClass("active");
			           $("#viewli").removeClass("active");
			           $("#assignsmes").addClass("active in");
			           $("#mdmservice").removeClass("active in");
			           $("#upload").removeClass("active in");
			           $("#process").removeClass("active in");
			           $("#view").removeClass("active in");
			       }else if(tab == "4"){
			           $("#processli").addClass("active");
			           $("#basicli").removeClass("active");
			           $("#assignli").removeClass("active");
			           $("#uploadli").removeClass("active");
			           $("#viewli").removeClass("active");
			           $("#process").addClass("active in");
			           $("#mdmservice").removeClass("active in");
			           $("#assignsmes").removeClass("active in");
			           $("#upload").removeClass("active in");
			           $("#view").removeClass("active in");
			       }else if(tab == "5"){
			           $("#viewli").addClass("active");
			           $("#basicli").removeClass("active");
			           $("#assignli").removeClass("active");
			           $("#processli").removeClass("active");
			           $("#uploadli").removeClass("active");
			           $("#view").addClass("active in");
			           $("#mdmservice").removeClass("active in");
			           $("#assignsmes").removeClass("active in");
			           $("#process").removeClass("active in");
			           $("#upload").removeClass("active in");
			       }else{
			           $("#basicli").addClass("active");
			           $("#uploadli").removeClass("active");
			           $("#assignli").removeClass("active");
			           $("#processli").removeClass("active");
			           $("#viewli").removeClass("active");
			           $("#mdmservice").addClass("active in");
			           $("#upload").removeClass("active in");
			           $("#assignsmes").removeClass("active in");
			           $("#process").removeClass("active in");
			           $("#view").removeClass("active in");
			       }
			   } else{
			       //alert("else");
			   }
			});

</script>
<script type="text/javascript">
	
	$(document).ready(function(){
//---------------------------------//
// checkall code for adrress subtabs
//--------------------------------//

		//checkbox address matched records
		$("#mytable1 #checkall1").click(function () {
	
	        if ($("#mytable1 #checkall1").is(':checked')) {
	            $("#mytable1 input[type=checkbox]").each(function () {
	                $(this).prop("checked", true);
	            });

	        } else {
	            $("#mytable1 input[type=checkbox]").each(function () {
	                $(this).prop("checked", false);
	            });
        }
    });
		//checkbox for address Unmatched
    		$("#mytable2 #checkall2").click(function () {
	
	        if ($("#mytable2 #checkall2").is(':checked')) {
	            $("#mytable2 input[type=checkbox]").each(function () {
	                $(this).prop("checked", true);
	            });

	        } else {
	            $("#mytable2 input[type=checkbox]").each(function () {
	                $(this).prop("checked", false);
	            });
        }
    });
    	//checkbox for address Partially matched records
    		$("#mytable3 #checkall3").click(function () {
	
	        if ($("#mytable3 #checkall3").is(':checked')) {
	            $("#mytable3 input[type=checkbox]").each(function () {
	                $(this).prop("checked", true);
	            });

	        } else {
	            $("#mytable3 input[type=checkbox]").each(function () {
	                $(this).prop("checked", false);
	            });
        }
    });

//---------------------------------//
// checkall code for PRODUCT subtabs
//--------------------------------//

		//checkbox address matched records
		$("#mytable4 #checkall4").click(function () {
	
	        if ($("#mytable4 #checkall4").is(':checked')) {
	            $("#mytable4 input[type=checkbox]").each(function () {
	                $(this).prop("checked", true);
	            });

	        } else {
	            $("#mytable4 input[type=checkbox]").each(function () {
	                $(this).prop("checked", false);
	            });
        }
    });
		//checkbox for address Unmatched
    		$("#mytable5 #checkall5").click(function () {
	
	        if ($("#mytable5 #checkall5").is(':checked')) {
	            $("#mytable5 input[type=checkbox]").each(function () {
	                $(this).prop("checked", true);
	            });

	        } else {
	            $("#mytable6 input[type=checkbox]").each(function () {
	                $(this).prop("checked", false);
	            });
        }
    });
    	//checkbox for address Partially matched records
    		$("#mytable6 #checkall6").click(function () {
	
	        if ($("#mytable6 #checkall6").is(':checked')) {
	            $("#mytable6 input[type=checkbox]").each(function () {
	                $(this).prop("checked", true);
	            });

	        } else {
	            $("#mytable6 input[type=checkbox]").each(function () {
	                $(this).prop("checked", false);
	            });
        }
    });
    	
    	
    		//---------------------------------//
    		// checkall code for ADDRESS subtabs
    		//--------------------------------//

    				//checkbox address matched records
    				$("#mytable4A #checkall4A").click(function () {
    			
    			        if ($("#mytable4A #checkall4A").is(':checked')) {
    			            $("#mytable4A input[type=checkbox]").each(function () {
    			                $(this).prop("checked", true);
    			            });

    			        } else {
    			            $("#mytable4A input[type=checkbox]").each(function () {
    			                $(this).prop("checked", false);
    			            });
    		        }
    		    });
    				//checkbox for address Unmatched
    		    		$("#mytable5A #checkall5A").click(function () {
    			
    			        if ($("#mytable5A #checkall5A").is(':checked')) {
    			            $("#mytable5A input[type=checkbox]").each(function () {
    			                $(this).prop("checked", true);
    			            });

    			        } else {
    			            $("#mytable5A input[type=checkbox]").each(function () {
    			                $(this).prop("checked", false);
    			            });
    		        }
    		    });
    		    	//checkbox for address Partially matched records
    		    		$("#mytable6A #checkall6A").click(function () {
    			
    			        if ($("#mytable6A #checkall6A").is(':checked')) {
    			            $("#mytable6A input[type=checkbox]").each(function () {
    			                $(this).prop("checked", true);
    			            });

    			        } else {
    			            $("#mytable6A input[type=checkbox]").each(function () {
    			                $(this).prop("checked", false);
    			            });
    		        }
    		    });	

    		    		//---------------------------------//
    		    		// checkall code for VENDOR subtabs
    		    		//--------------------------------//

    		    				//checkbox vendor matched records
    		    				$("#mytable4V #checkall4V").click(function () {
    		    			
    		    			        if ($("#mytable4V #checkall4V").is(':checked')) {
    		    			            $("#mytable4V input[type=checkbox]").each(function () {
    		    			                $(this).prop("checked", true);
    		    			            });

    		    			        } else {
    		    			            $("#mytable4V input[type=checkbox]").each(function () {
    		    			                $(this).prop("checked", false);
    		    			            });
    		    		        }
    		    		    });
    		    				//checkbox for venor Unmatched
    		    		    		$("#mytable5V #checkall5V").click(function () {
    		    			
    		    			        if ($("#mytable5V #checkall5V").is(':checked')) {
    		    			            $("#mytable5V input[type=checkbox]").each(function () {
    		    			                $(this).prop("checked", true);
    		    			            });

    		    			        } else {
    		    			            $("#mytable5V input[type=checkbox]").each(function () {
    		    			                $(this).prop("checked", false);
    		    			            });
    		    		        }
    		    		    });
    		    		    	//checkbox for vneodr Partially matched records
    		    		    		$("#mytable6V #checkall6V").click(function () {
    		    			
    		    			        if ($("#mytable6V #checkall6V").is(':checked')) {
    		    			            $("#mytable6V input[type=checkbox]").each(function () {
    		    			                $(this).prop("checked", true);
    		    			            });

    		    			        } else {
    		    			            $("#mytable6V input[type=checkbox]").each(function () {
    		    			                $(this).prop("checked", false);
    		    			            });
    		    		        }
    		    		    });	
	
    		    	
    		    	
//---------------------------------//
// checkall code for VENDOR subtabs
//--------------------------------//

		//checkbox vendor matched records
		$("#mytable7 #checkall7").click(function () {
	
	        if ($("#mytable7 #checkall7").is(':checked')) {
	            $("#mytable7 input[type=checkbox]").each(function () {
	                $(this).prop("checked", true);
	            });

	        } else {
	            $("#mytable7 input[type=checkbox]").each(function () {
	                $(this).prop("checked", false);
	            });
        }
    });
		//checkbox for vendor Unmatched
    		$("#mytable8 #checkall8").click(function () {
	
	        if ($("#mytable8 #checkall8").is(':checked')) {
	            $("#mytable8 input[type=checkbox]").each(function () {
	                $(this).prop("checked", true);
	            });

	        } else {
	            $("#mytable8 input[type=checkbox]").each(function () {
	                $(this).prop("checked", false);
	            });
        }
    });
    	//checkbox for vendor Partially matched records
    		$("#mytable9 #checkall9").click(function () {
	
	        if ($("#mytable9 #checkall9").is(':checked')) {
	            $("#mytable9 input[type=checkbox]").each(function () {
	                $(this).prop("checked", true);
	            });

	        } else {
	            $("#mytable9 input[type=checkbox]").each(function () {
	                $(this).prop("checked", false);
	            });
        }
    });

//---------------------------------//
// checkall code for CUSTOMER subtabs
//--------------------------------//

		//checkbox address matched records
		$("#mytable10 #checkall10").click(function () {
	
	        if ($("#mytable10 #checkall10").is(':checked')) {
	            $("#mytable10 input[type=checkbox]").each(function () {
	                $(this).prop("checked", true);
	            });

	        } else {
	            $("#mytable10 input[type=checkbox]").each(function () {
	                $(this).prop("checked", false);
	            });
        }
    });
		//checkbox for address Unmatched
    		$("#mytable11 #checkall11").click(function () {
	
	        if ($("#mytable11 #checkall11").is(':checked')) {
	            $("#mytable11 input[type=checkbox]").each(function () {
	                $(this).prop("checked", true);
	            });

	        } else {
	            $("#mytable11 input[type=checkbox]").each(function () {
	                $(this).prop("checked", false);
	            });
        }
    });
    	//checkbox for address Partially matched records
    		$("#mytable12 #checkall12").click(function () {
	
	        if ($("#mytable12 #checkall12").is(':checked')) {
	            $("#mytable12 input[type=checkbox]").each(function () {
	                $(this).prop("checked", true);
	            });

	        } else {
	            $("#mytable12 input[type=checkbox]").each(function () {
	                $(this).prop("checked", false);
	            });
        }
    });


   
});

</script>
<script type="text/javascript">
	$(".btnviewrecords").click(function(e){
		e.preventDefault();
    $("#assignrecords").removeAttr("hidden");
});
	
	$('#previewpercent').on('hidden.bs.modal', function () {
		 //location.reload();
		 var url      = window.location.href; 
		 url = url + "&tab=3";
		 //alert(url);
		 location.replace(url);
		 
		});
	$('#previewcat').on('hidden.bs.modal', function () {
		 //location.reload();
		 var url      = window.location.href; 
		 url = url + "&tab=3";
		// alert(url);
		 location.replace(url);
		});
	
	
	   // script for view AJAX starts here  
	  
	$( document ).ready(function(){
	    $("#search").keyup(function (){
	        var searchval = $('#search').val();
	        
	        //alert(searchval);
	        if(searchval != ""){
	        	 var n = searchval.length;
	        	    if(n > 3){
	            document.getElementById("display").removeClass = "displayNone";   
	            document.getElementById("display").className = "responstable";
	            var vdata = 'result=' + searchval+'&serviceId='+$("#serviceId").val();
	           // alert(vdata);
	        $.ajax({
	            type: 'GET',
	            url: '<%=request.getContextPath()%>/userPanel?type=getviewdata',
	            data: vdata,
	            success: function(something){  
	                 console.log(something);
	//alert(Object.keys(something).length);
	                if(something != "{}"){
	                var returnedData = JSON.parse(something);
	                  var dataArr = returnedData.data;
	                  //alert(dataArr.length);
	                  var dataRec  = "";
	                  if(dataArr.length==0){
	                    document.getElementById('display').innerHTML = '<th colspan="4" class="text-center"><strong>No Data to Display</strong></th>';
	                  }
	                  else{

	                 for(var i = 0; i < dataArr.length; i++){

	                        dataRec = dataRec + "<tr><td>"+dataArr[i].level1+"</td><td>"+dataArr[i].level2+"</td><td>"+dataArr[i].level3+"</td><td><a href='"+dataArr[i].itemUrl+"'>"+dataArr[i].itemName+"</a></td></tr>";
	                        //$('#display').html(something);
	                  }
	                  document.getElementById('display').innerHTML ='<thead><th>Level1</th><th data-th="Full Details"><span>Level2</span></th><th>Level 3</th><th>ItemName</th></thead>'+ dataRec;
	                  
	                }
	                }else{
	                    document.getElementById('display').innerHTML = '<th colspan="4" class="text-center"><strong>No Data to Display</strong></th>';
	                }
	            }
	        });
	       }
	    }else if(searchval == ""){
	            document.getElementById('display').innerHTML = '<th colspan="4" class="text-center"><strong>No Data to Display</strong></th>';
	        }
	});

	});

	
</script>

</body>
</html>