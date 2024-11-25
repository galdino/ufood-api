function find() {
    $.ajax({
        url: "http://localhost:8080/payment-methods",
        type: "get",

        success: function(response) {
            writeTable(response);
        }
    });
}

function remove(paymentMethod) {
    var url = "http://localhost:8080/payment-methods/" + paymentMethod.id;

    $.ajax({
        url: url,
        type: "delete",

        success: function(response) {
            find();
            alert("Payment Method removed!");
        },

        error: function(error) {
            if (error.status >= 400 && error.status <= 499) {
                var problem = JSON.parse(error.responseText);
                alert(problem.userMessage);
            } else {
                alert("Payment Method removal error!");
            }
        }
    });
}

function create() {
    var paymentMethodJson = JSON.stringify({
        "descricao": $("#desc-field").val()
    });

    console.log(paymentMethodJson);

    $.ajax({
        url: "http://localhost:8080/payment-methods",
        type: "post",
        data: paymentMethodJson,
        contentType: "application/json",

        success: function(response) {
            alert("Payment Method created!");
            find();
        },

        error: function(error) {
            if (error.status == 400) {
                var problem = JSON.parse(error.responseText);
                alert(problem.userMessage);
            } else {
                alert("Payment Method creation error!");
            }
        }
    });
}

function writeTable(paymentMethods) {
    $("#pm-table tbody tr").remove();

    $.each(paymentMethods, function(i, paymentMethod) {
        var line = $("<tr>");
        var actionLink = $("<a href='#'>")
            .text("Remove")
            .click(function(event) {
                event.preventDefault();
                remove(paymentMethod);
            });

        line.append(
            $("<td>").text(paymentMethod.id),
            $("<td>").text(paymentMethod.description),
            $("<td>").append(actionLink)
        );

        line.appendTo("#pm-table");
    });
}


$("#btn-find").click(find);
$("#btn-create").click(create);