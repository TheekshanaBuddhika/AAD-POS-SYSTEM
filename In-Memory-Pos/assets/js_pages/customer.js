let tableBody = $("#body");

//save the customer information
$(`#save-customer`).click(function () {

    let id = $('#txt-id').val();
    let name = $('#txt-name').val();
    let address = $('#txt-address').val();


    const customerObj = {
        id:id,
        name:name,
        address:address
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
            /*if(jqxhr.status == 201)
                alert("Added customer successfully")*/
            if (jqxhr.status == 201)
                alert(jqxhr.responseText);
        },
        error: function (jqxhr, textStatus, error) {
            console.log("error: ", jqxhr);
            console.log("error: ", textStatus);
            console.log("error: ", error);
        }
    })
    ////////////////////////////////////////////////////////////////////////////////////////

    let idVal = $("#customer-gmail").val();
    if (searchCustomer(idVal.trim()) === undefined) {
        let id = $("#customer-gmail");
        let address = $("#customer-address");
        let name = $("#customer-name");
        let tp = $("#customer-tp");

        customer = {
            name: name.val(),
            id: id.val(),
            address: address.val(),
            tp: tp.val()
        }
        customerDB.push(customer);
    }else {
        alert("already exit item iD");
    }
    getAll();
    clearCustomerInputFields();
});



function searchCustomer(id) {
    return customerDB.find(function (customer) {
        return customer.id == id;
    });
}

$('#updateCustomer').on('click', function () {
    updateCustomer();
});

function updateCustomer() {
    let id = $(`#upCID`).val();
    if (searchCustomer(id) == undefined) {
        alert("No such Customer..please check the ID");
    } else {
        let consent = confirm("Do you really want to update this customer.?");
        if (consent) {
            let customer = searchCustomer(id);
            //if the customer available can we update.?
            let name = $(`#upCName`).val();
            let address = $(`#upCAddress`).val();
            let tp = $(`#upCTp`).val();

            customer.name = name;
            customer.address = address;
            customer.tp = tp;
        }
    }
    getAll();
    clearUpdateFiald();
}


$(`#getAllCustomer`).click(function () {
    getAll();
});


function getAll() {

    $(`#body`).empty();

    $.ajax({
        url : "http://localhost:8080/app/customers",
        method : "GET",
        success : function (resp) {
            console.log("Success: ", resp);
            for (const customer of resp) {
                console.log(customer.id);
                console.log(customer.name);
                console.log(customer.address);
                console.log(customer.salary);

                $(`#body`).append(`<tr>
                                <td>${customer.id}</td>
                                <td>${customer.name}</td>
                                <td>${customer.address}</td>
                                <td>${customer.salary}</td>
                                <td><button type="button" class="btn btn-primary btn-sm me-2" data-bs-toggle="modal"
                                        data-bs-target="#exampleModal2">
                                    Edit
                                </button>
                                <button class="btn btn-danger me-3 btn-sm delete">Delete</button></td>
                   
                             </tr>`);
                setEvent();
            }

        },
        error : function (error) {
            console.log("error: ", error);
        }
    })

    
}

function setEvent() {

    $(`#tblCustomer tr`).click(function () {

        var $row = $(this).closest("tr"),
            $tds = $row.find("td:nth-child(1)");
        $ts = $row.find("td:nth-child(2)");
        $tt = $row.find("td:nth-child(3)");
        $tf = $row.find("td:nth-child(4)");
        // let td_list =  $();

        $(`#upCID`).val($tds.text());
        $(`#upCName`).val($ts.text());
        $(`#upCAddress`).val($tt.text());
        $(`#upCTp`).val($tf.text());

    });

    $('.delete').click(function () {
        $(`#tblCustomer tr`).click(function () {

            var $row = $(this).closest("tr");        // Finds the closest row <tr>
            $tds = $row.find("td:nth-child(1)");

            if (searchCustomer($tds.text()) === undefined) {
                alert("No such Customer..please check the ID");
            } else {
                if (deleteFunc($tds.text())){
                    // $(this).closest("tr").remove();
                    alert("customer Deleted !");
                    getAll();
                }
            }
        });
    });


}

$('.delete').click(function () {
    $(`#tblCustomer tr`).click(function () {

        var $row = $(this).closest("tr");        // Finds the closest row <tr>
        $tds = $row.find("td:nth-child(1)");

        if (searchCustomer($tds.text()) === undefined) {
            alert("No such Customer..please check the ID");
        } else {
            if (deleteFunc($tds.text())){
                // $(this).closest("tr").remove();
                alert("customer Deleted !");
                getAll();
            }
        }
    });
});

function deleteFunc(id){
    for (let i = 0; i < customerDB.length; i++) {
        if (customerDB[i].id == id) {
            customerDB.splice(i, 1);
            return true
        }
    }
    return false;
}

$(`#tblCustomer tr`).click(function () {

    var $row = $(this).closest("tr");        // Finds the closest row <tr>
    $tds = $row.find("td:nth-child(1)");
    $ts = $row.find("td:nth-child(2)");
    $tt = $row.find("td:nth-child(3)");
    $tf = $row.find("td:nth-child(4)");
    // let td_list =  $();

    $(`#upCID`).val($tds.text());
    $(`#upCName`).val($ts.text());
    $(`#upCAddress`).val($tt.text());
    $(`#upCTp`).val($tf.text());


});

$('#txtSearch').on('keyup',function (){

    let txtVal = $('#txtSearch');

    if (txtVal.val() === ''){
        getAll();
    }

    $(`#body`).empty();
    for (let customer of customerDB) {
        if ($("#cusSearch").val() == "Customer Id") {
            if (customer.id.indexOf($("#txtSearch").val()) !== -1) {
                $("#tblCustomer > tbody").append($(`#body`).append(`<tr>
                                <td>${customer.id}</td>
                                <td>${customer.name}</td>
                                <td>${customer.address}</td>
                                <td>${customer.tp}</td>
                                <td><button type="button" class="btn btn-primary btn-sm me-2" data-bs-toggle="modal"
                                        data-bs-target="#exampleModal2">
                                    Edit
                                </button>
                                <button class="btn btn-danger me-3 btn-sm delete">Delete</button></td>
                   
                             </tr>`));
            }
        } else {
            if (customer.name.indexOf($("#txtSearch").val()) !== -1) {

                $("#tblCustomer > tbody").append($(`#body`).append(`<tr>
                                <td>${customer.id}</td>
                                <td>${customer.name}</td>
                                <td>${customer.address}</td>
                                <td>${customer.tp}</td>
                                <td><button type="button" class="btn btn-primary btn-sm me-2" data-bs-toggle="modal"
                                        data-bs-target="#exampleModal2">
                                    Edit
                                </button>
                                <button class="btn btn-danger me-3 btn-sm delete">Delete</button></td>
                   
                             </tr>`));
            }
        }
    }
});


getAll();