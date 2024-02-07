let tableBody = $("#body");

//save the customer information
$(`#save-customer`).click(function () {
    saveCustomer();
});

function saveCustomer() {
    let id = $("#customer-gmail");
    let address = $("#customer-address");
    let name = $("#customer-name");
    let salary = $("#customer-tp");

    const customerObj = {
        id:id,
        name:name,
        address:address,
        salary:salary
    };

    const jsonObj = JSON.stringify(customerObj);

    $.ajax({
        url: "http://localhost:8080/app/customers",
        method: "POST",
        data: jsonObj,
        contentType: "application/json",
        success: function (resp, textStatus, jqxhr) {
            console.log("success: ", resp);
            console.log("success: ", textStatus);
            console.log("success: ", jqxhr);
         
            if (jqxhr.status == 201)
                alert(jqxhr.responseText);
        },
        error: function (jqxhr, textStatus, error) {
            console.log("error: ", jqxhr);
            console.log("error: ", textStatus);
            console.log("error: ", error);
        }
    })

    getAll();
    clearCustomerInputFields();
}

$('#updateCustomer').on('click', function () {
    updateCustomer();
});