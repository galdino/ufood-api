function getAllRestaurants() {
    $.ajax({
        url: "http://localhost:8080/restaurants",
        type: "get",

        success: function(response) {
            $("#content").text(JSON.stringify(response));
        }
    });
}

$("#button").click(getAllRestaurants);