/**
 * Created by mrbjoern on 3/4/15.
 */
$(document).ready(function() {
    $.ajax({
        url: "http://192.168.0.109:8080/api/report/temperature"
    }).then(function(data) {
        $('.tempDate').replaceWith(data.timestamp);
        $('.tempDegrees').replaceWith(data.temperature);
    });
});