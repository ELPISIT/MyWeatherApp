function tes() {
    console.log("mora")
}
function getMyDate(){
    var dateVar = new Date();
    var timezone = dateVar.getTimezoneOffset()/60 * (-1);
    var search = {
        "timezone" : timezone,
        "date":dateVar
    }

    $.ajax({
        type : "POST",
        contentType : 'application/json; charset=utf-8',
        dataType : 'json',
        url : "http://localhost:8081/abc/",
        data : JSON.stringify(search),
        success : function(result) {
            console.log("SUCCESS: ", data);
        },
        error: function(e){
            console.log("ERROR: ", e);
        },
        done : function(e) {
            console.log("DONE");
        }
    });
}