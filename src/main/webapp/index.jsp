<html>
<%pageContext.setAttribute("project", request.getContextPath());%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="${project}/js/jquery-1.7.2.js"></script>

<body>
<a href="findAll">点击进入</a>

<script>
    $(function () {
        $("#getCode").click(function () {
            let val = $("#phoneNumber").val();
            $.ajax({
                url:"${project}/getCode",
                type:"post",
                data:"phoneNumber="+val



            })
        });
        $("#btn1").click(function () {

            $.ajax({
                url:"${project}/seckill",
                data:"itemId=1001",
                success:function (data) {
                    if (data === "true") {
                        alert("秒杀成功");
                    } else {
                        alert("秒杀失败");
                        $("#btn1").attr("disabled",true)
                    }

                }



            })
        })

    })
</script>

<form action="${project}/verifyCode">
手机号码:<input name="phoneNumber" id="phoneNumber" type="text"><input id=getCode type="button" value="点击获取验证码">
验证码:<input name="code" type="text">
    <input type="submit">
</form>

<form>
    <input id="btn1" type="button" value="点击进行秒杀">


</form>




</body>
</html>
