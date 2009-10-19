<script>
	        var cp = "<%= System.getProperty("contextPrefix") %>";
	        document.cookie = "JSESSIONID=0; path=/"+cp+"jasperserver;";
	        document.cookie = "JSESSIONID=0; path=/"+cp+"clementine;";
	        var orangeleap = "orangeleap";
	        if (cp.length > 0) orangeleap = cp.substring(0,cp.length-1);
	        document.cookie = "JSESSIONID=0; path=/"+orangeleap+";";
</script>