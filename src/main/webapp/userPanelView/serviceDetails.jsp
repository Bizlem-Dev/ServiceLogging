<%@page import="java.util.ResourceBundle"%>
<%@page import="com.jindal.auction.domain.xsd.CustomerServiceStatus"%>
<%@page
	import="java.util.Arrays,java.util.ArrayList,java.util.Iterator,java.util.List,java.text.SimpleDateFormat,java.util.Date"%>
<%ResourceBundle bundle=ResourceBundle.getBundle("serverConfig"); %>
<html>
<head>

<link rel="stylesheet" href="css/form.css" />
<script src="js/jquery-1.9.1.js"></script>

<script>
    <%String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());%>
    <%String bannerService = bundle.getString("bannerServices");
    String[] bannerListArray = bannerService.split(",");
    ArrayList<String> bannerList = new ArrayList<String>(
            Arrays.asList(bannerListArray));
    %>
	function openPopUp(val,productCode) {
		 var selectedValues= window.showModalDialog('<%=request.getContextPath()%>/userPanel?type=callAuto&val='
                 + val + "&productCode=" + productCode,'Assigned User', 'dialogHeight=400px;dialogWidth=400px');
		window.location.reload();
		/*$('.popup_box').load("<%=request.getContextPath()%>/userPanel?type=callAuto&val="
						+ val + "&productCode=" + productCode);*/
	}
	
	function openAccountPopUp(val,productCode) {
		 var selectedValues= window.showModalDialog('<%=request.getContextPath()%>/userPanel?type=createAccount&val='
                + val + "&productCode=" + productCode,'Assigned User', 'dialogHeight=400px;dialogWidth=400px');
		 window.location.reload();
	}
	function openSmsEmailPopUp(productCode)
	{
		 var selectedValues= window.showModalDialog('<%=request.getContextPath()%>/userPanel?type=assignSmsEmail&&productCode=' + productCode,'Assigned User', 'dialogHeight=400px;dialogWidth=400px');
			 window.location.reload();	
	}
	
	
	
	
	function openPaidPopUp(code,date){
		$('.popup_box').fadeIn("slow");
        $(".form-wrapper").css({ // this is just for style
            "opacity" : "0.3"
        });
		$('.popup_box').css('width','485');
		$('.popup_box').append("<iframe width='500' height='436' frameborder='0' src='<%=bundle.getString("advServerUrl")%>/ProductRegistrationPaid.php?startdate=<%=currentDate%>&enddate="+date+"&username=<%=request.getRemoteUser()%>'></iframe>");
	}
	
	function openSponsoredPopUp(code,date){
		$('.popup_box').fadeIn("slow");
        $(".form-wrapper").css({ // this is just for style
            "opacity" : "0.3"
        });
		$('.popup_box').css('width','485');
        $('.popup_box').append("<iframe width='500' height='436' frameborder='0' src='<%=bundle.getString("advServerUrl")%>/ProductRegistrationSponsored.php?startdate=<%=currentDate%>&enddate="+date+"&username=<%=request.getRemoteUser()%>'></iframe>");
    }
	
	function openBannerPopUp(code,date){
		$('.popup_box').fadeIn("slow");
        $(".form-wrapper").css({ // this is just for style
            "opacity" : "0.3"
        });		
		$('.popup_box').css('width','485');
        $('.popup_box').append("<iframe width='486' height='309' frameborder='0' src='<%=bundle.getString("advServerUrl")%>/BannerCreation.php?startdate=<%=currentDate%>&enddate="+date+"&username=<%=request.getRemoteUser()%>&zonename="+code+"'></iframe>");
    }
</script>
<script type="text/javascript">
	$(document).ready(function() {

		// When site loaded, load the Popupbox First
		//unloadPopupBox();

		$('.popupBoxClose').click(function() {
			unloadPopupBox();
		});

		

		function unloadPopupBox() { // TO Unload the Popupbox
			$('.popup_box').fadeOut("slow");
			$(".form-wrapper").css({ // this is just for style		
				"opacity" : "1"
			});
		}

		function loadPopupBox() { // To Load the Popupbox
			$('.popup_box').fadeIn("slow");
			$(".form-wrapper").css({ // this is just for style
				"opacity" : "0.3"
			});
		}
		/**********************************************************/

	});
</script>
</head>
<title>Login</title>
<body>
	<input type="hidden" name="type" value="callService" />
	<div class="form-wrapper">

		<div class="form-contanier">
			<form>
				<table border="0" cellpadding="0" cellspacing="0" width="100%"
					class="grid-table">



					<tr>
						<th>Description</th>
						<th>Product Code</th>
						<th>Quanity</th>
						<th>Consumed</th>
						<th>From Date</th>
						<th>To Date</th>
						<th>Status</th>
					</tr>

					<%
					    List<CustomerServiceStatus> list = (List<CustomerServiceStatus>) request
					            .getAttribute("serviceList");
					%>



					<%
					    for (int i = 0; i < list.size(); i++) {
					%>
					<tr>
						<td><%=list.get(i).getProductDescription()%></td>
						<td><%=list.get(i).getProductCode()%></td>
						<td><%=list.get(i).getQuantity()%>
						<%
						Date endDate = new Date();
						endDate.setDate(endDate.getDate()-1);
						
						%>
						<%
						  Date startDate = new Date(); 
						%>
						<%if(startDate.compareTo(list.get(i).getServiceStartDate()) > 0 && 
						        (endDate.compareTo(list.get(i).getServiceEndDate()) < 0 &&  
						                list.get(i).getStatus().equals("active"))){ %> 
						      <%String endServiceDate = new SimpleDateFormat("yyyy-MM-dd").format(list.get(i).getServiceEndDate());%>
						     <%if (list.get(i).getProductCode().equals("Chat")
						              || list.get(i).getProductCode().equals("VChat")) {
                             %> <a href="#" onclick="openPopUp('<%=list.get(i).getQuantity().intValue()%>','<%=list.get(i).getProductCode()%>');"
							         class="assign-btn"> <img
								        src="<%=request.getContextPath()%>/images/icon-hover_add.png"
								        alt=""></a> Assigned User
						     <%}else if(list.get(i).getProductCode().equals("PaidAdd")){%>
						        <a href="#" onclick="openPaidPopUp('<%=list.get(i).getProductCode()%>','<%=endServiceDate%>');"
                                     class="assign-btn"> <img
                                        src="<%=request.getContextPath()%>/images/icon-hover_add.png"
                                        alt=""></a> 
						     <%}else if(list.get(i).getProductCode().equals("SponsoredAdd")){%>
							     <a href="#" onclick="openSponsoredPopUp('<%=list.get(i).getQuantity().intValue()%>','<%=endServiceDate%>');"
	                                     class="assign-btn"> <img
	                                        src="<%=request.getContextPath()%>/images/icon-hover_add.png"
	                                        alt=""></a>
						     <%}else if(bannerList.contains(list.get(i).getProductCode())){%>
							     <a href="#" onclick="openBannerPopUp('<%=list.get(i).getProductCode()%>','<%=endServiceDate%>');"
	                                     class="assign-btn"> <img
	                                        src="<%=request.getContextPath()%>/images/icon-hover_add.png"
	                                        alt=""></a>
						     <%} else if(list.get(i).getProductCode().equals("webMail"))
						               {
                            %> <a href="#" onclick="openAccountPopUp('<%=list.get(i).getQuantity().intValue()%>','<%=list.get(i).getProductCode()%>');"
							         class="assign-btn"> <img
								        src="<%=request.getContextPath()%>/images/icon-hover_add.png"
								        alt=""></a> Account create
						  <%} else if(list.get(i).getProductCode().equalsIgnoreCase("SMS") || list.get(i).getProductCode().equalsIgnoreCase("email") || list.get(i).getProductCode().equalsIgnoreCase("call"))
						               {
                            %> <a href="#" onclick="openSmsEmailPopUp('<%=list.get(i).getProductCode()%>');"
							         class="assign-btn"> <img
								        src="<%=request.getContextPath()%>/images/icon-hover_add.png"
								        alt=""></a> Assigned User
						
						<%}}%></td>
						<td><%=list.get(i).getConsumptionQuantity()%></td>
						<td><%=list.get(i).getServiceStartDate()%></td>
						<td><%=list.get(i).getServiceEndDate()%></td>
						<td><%=list.get(i).getStatus()%></td>
					</tr>
					<%
					    }
					%>
				</table>
			</form>
		</div>

	</div>
	<div class="popup_box">
	   <a href="<%=request.getContextPath()%>/userPanel?type=callService" class="popupBoxClose"><img src="<%=request.getContextPath()%>/images/p.png" alt=""></a>
	</div>
</body>
</html>