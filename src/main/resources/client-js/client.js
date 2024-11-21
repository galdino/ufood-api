function find() {
    $.ajax({
        url: "http://localhost:8080/payment-methods",
        type: "get",

        success: function(response) {
            writeTable(response);
        }
    });
}

function writeTable(paymentMethods) {
    $("#pm-table tbody tr").remove();

    $.each(paymentMethods, function(i, paymentMethod) {
        var line = $("<tr>");

        line.append(
            $("<td>").text(paymentMethod.id),
            $("<td>").text(paymentMethod.description)
        );

        line.appendTo("#pm-table");
    });
}


$("#btn-find").click(find);