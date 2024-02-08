//update the items
$('#btnUpdateItem').on('click',function (){
    let idval = $(`#upItemId`).val();
    searchItem(idval, function (exists) {
        if (exists) {
           // Continue with your existing code for updating the items
           updateItem();
        } else {
            clearUpdateTxt();
            alert("Code doesnot exists. Please choose a existing Code.");
        }
    });
});

function updateItem(){
        let consent = confirm("Do you really want to update this item.?");
        if (consent) {
            let code = $(`#upItemId`).val();
            let description = $(`#upItemdesc`).val();
            let unitPrice = $(`#upUnitPrice`).val();
            let qty = $(`#upQty`).val();

            const itemupdateObj = {
                code:code,
                description:description,
                unitPrice:unitPrice,
                qty:qty
    };

    const jsonitmupdate = JSON.stringify(itemupdateObj);
        
    $.ajax({
        url: "http://localhost:8080/app/items",
        method: "PUT",
        data: jsonitmupdate,
        contentType: "application/json",
        success: function (resp, textStatus, jqxhr) {
            console.log("success: ", resp);
            console.log("success: ", textStatus);
            console.log("success: ", jqxhr);
            getAllItem();
        },
        error: function (jqxhr, textStatus, error) {
            console.log("error: ", jqxhr);
            console.log("error: ", textStatus);
            console.log("error: ", error);
        }
    })
        clearUpdateTxt();
    }
    
}

//save the item
$('#btnSaveItem').on('click', function () {
    let id = $('#txtItemId').val();
    searchItem(id, function (exists) {
        if (exists) {
            clearItemTxt();
            alert("Code already exists. Please choose a different Code.");
        } else {
            saveItem();
        }
    });
});

function saveItem() {
    
    let code = $(`#upItemId`).val();
    let description = $(`#upItemdesc`).val();
    let unitPrice = $(`#upUnitPrice`).val();
    let qty = $(`#upQty`).val();

            const itemObj = {
                code: code,
                description: description,
                unitPrice: unitPrice,
                qty: qty
            };

            const jsonitmObj = JSON.stringify(itemObj);

            $.ajax({
                url: "http://localhost:8080/app/items",
                method: "POST",
                data: jsonitmObj,
                contentType: "application/json",
                success: function (resp, textStatus, jqxhr) {
                    console.log("success: ", resp);
                    console.log("success: ", textStatus);
                    console.log("success: ", jqxhr);
                    getAllItem();
                    if (jqxhr.status == 201)
                        alert(jqxhr.responseText);
                        
                },
                error: function (jqxhr, textStatus, error) {
                    console.log("error: ", jqxhr);
                    console.log("error: ", textStatus);
                    console.log("error: ", error);
                }
            });

          
            clearItemTxt();

}

//search for items
function searchItem(id, callback) {
    $.ajax({
        url: "http://localhost:8080/app/items",
        method: "GET",
        success: function (resp) {
            // ID exists
                for (const item of resp) {
                    if(item.code == id) {
                        callback(true);
                        return;
                    }
                }
                callback(false);
        },
        error: function (jqxhr, textStatus, error) {
            // ID does not exist
                callback(false);
                console.log("Error checking ID existence: ", jqxhr, textStatus, error);
        }
    });
}

//get the item
$('#btnGetAllItem').on('click', function () {
    getAllItem();
});

function getAllItem() {
    $('#Item-body').empty();

    $.ajax({
        url : "http://localhost:8080/app/items",
        method : "GET",
        success : function (resp) {
            console.log("Success: ", resp);
            for (const item of resp) {
                console.log(item.code);
                console.log(item.description);
                console.log(item.unitPrice);
                console.log(item.qty);

               $(`#Item-body`).append(`<tr>
                                <td>${item.code}</td>
                                <td>${item.description}</td>
                                <td>${item.unitPrice}</td>
                                <td>${item.qtyOnHand}</td>
                                <td><button type="button" class="btn btn-primary btn-sm me-2" data-bs-toggle="modal"
                                        data-bs-target="#update-model">
                                    Edit
                                </button>
                                <button class="btn btn-danger me-3 btn-sm deleteItem">Delete</button></td>
                   
                             </tr>`);
                setEvent();
            }

        },
        error : function (error) {
            console.log("error: ", error);
        }
    })
}

let eventsBound2 = false;

//Bind EDIT And Delete events
function setEvent() {

    if (!eventsBound2) {
        $('#tblItem').on('click', 'tr', function () {
            var $row = $(this).closest("tr"),
                $tds = $row.find("td:nth-child(1)"),
                $ts = $row.find("td:nth-child(2)"),
                $tt = $row.find("td:nth-child(3)"),
                $tf = $row.find("td:nth-child(4)");

                $(`#upItemId`).val($tds.text());
                $(`#upItemdesc`).val($ts.text());
                $(`#upUnitPrice`).val($tt.text());
                $(`#upQty`).val($tf.text());
        });

        $('#tblItem').on('click', '.delete', function (event) {
            event.stopPropagation(); // Prevent click event from reaching tr elements

            var $row = $(this).closest("tr"),
                $tds = $row.find("td:nth-child(1)");

            searchItem($tds.text(), function (exists) {
                if (exists) {
                    deleteItem($tds.text());
                } else {
                    alert("No such item..please check the code");
                }
            });
        });

        eventsBound2 = true;

    }
}

setEvent();

//delete item
function deleteItem(id) {
    $.ajax({
        url: "http://localhost:8080/app/items?code=" + id,
        method: "DELETE",
        success: function (resp, textStatus, jqxhr) {
            console.log("success: ", resp);
            console.log("success: ", textStatus);
            console.log("success: ", jqxhr);
            getAllItem();
        },
        error: function (jqxhr, textStatus, error) {
            console.log("error: ", jqxhr);
            console.log("error: ", textStatus);
            console.log("error: ", error);
        }
    })
}


//search for items
$('#txtSearchItem').on('keyup',function (){



    let txtVal = $('#txtSearchItem');

    if (txtVal.val() === ''){
        getAllItem();
    }
    $(`#Item-body`).empty();

    for (let item of itemDB) {
        if ($("#itemSearch").val() === "Code") {
            if (item.code.indexOf($("#txtSearchItem").val()) !== -1) {

                $("#tblItem > tbody").append($(`#Item-body`).append(`<tr>
                                <td>${item.code}</td>
                                <td>${item.description}</td>
                                <td>${item.unitPrice}</td>
                                <td>${item.qtyOnHand}</td>
                                <td><button type="button" class="btn btn-primary btn-sm me-2" data-bs-toggle="modal"
                                        data-bs-target="#update-model">
                                    Edit
                                </button>
                                <button class="btn btn-danger me-3 btn-sm delete">Delete</button></td>
                   
                             </tr>`));
            }
        } else {
            if (item.description.indexOf($("#txtSearchItem").val()) !== -1) {

                $("#tblItem > tbody").append($(`#Item-body`).append(`<tr>
                                <td>${item.code}</td>
                                <td>${item.description}</td>
                                <td>${item.unitPrice}</td>
                                <td>${item.qtyOnHand}</td>
                                <td><button type="button" class="btn btn-primary btn-sm me-2" data-bs-toggle="modal"
                                        data-bs-target="#update-model">
                                    Edit
                                </button>
                                <button class="btn btn-danger me-3 btn-sm deleteItem">Delete</button></td>
                   
                             </tr>`));
            }
        }
    }

    setEvent();
});


getAllItem();