<script type="text/javascript">
    var cp = "<%= System.getProperty("contextPrefix") %>";
    document.cookie = "CASTGC=0; path=/"+cp+"cas;";
    document.cookie = "JSESSIONID=0; path=/"+cp+"cas;";
    document.cookie = "JSESSIONID=0; path=/"+cp+"jasperserver;";
    document.cookie = "JSESSIONID=0; path=/"+cp+"clementine;";
    var orangeleap = "orangeleap";
    if (cp.length > 0) {
        orangeleap = cp.substring(0,cp.length-1);
    }
    document.cookie = "JSESSIONID=0; path=/"+orangeleap+";";
</script>