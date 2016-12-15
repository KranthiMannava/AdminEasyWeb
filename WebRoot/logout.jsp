<%@ page session="true"%>


User '<%=request.getRemoteUser()%>' has been logged out.


<% session.invalidate(); %>


<br/><br/>

<meta http-equiv="Refresh" content= "0; URL=./admin.html"/>
