
<script>
function logInForum(){
	 document.getElementById("username").value = username;
	 document.getElementById("password").value = password;
	 document.getElementById("login").click();
}
//../CAT/forum/ucp.php?mode=login
</script>

<form action="../CAT/forum/ucp.php?mode=login" method="post" id = "forum">
        <input type="hidden" name="username" id="username" size="10" title="Username"/>
        <input type="hidden" name="password" id="password" size="10" title="Password"/>
		<input id="login" type="submit" style = "visibility:hidden;" name="login" value="Login" />
</form>

