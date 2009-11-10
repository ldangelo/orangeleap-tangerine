$(document).ready(function() {
    $("#recurring").bind("change", function() {
        if ($(this).val() == "true") {
            $("#amountTotal").val('');
            $('#projectedDate').val('');
        }
        else {
            $("#amountPerGift").val('');
            $('#startDate').val('');
            $('#endDate').val('');
            $('#frequency').val('none');
        }
    });
});
