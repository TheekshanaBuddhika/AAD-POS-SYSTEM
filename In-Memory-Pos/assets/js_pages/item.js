
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
            getAll();
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
                    getAll();
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

    for (const item of itemDB) {
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
    }
    setEvent();
}

//Bind EDIT And Delete events
function setEvent() {

    $(`#tblItem tr`).click(function () {

        var $row = $(this).closest("tr");
        $tds = $row.find("td:nth-child(1)");
        $ts = $row.find("td:nth-child(2)");
        $tt = $row.find("td:nth-child(3)");
        $tf = $row.find("td:nth-child(4)");
        // let td_list =  $();

        $(`#upItemId`).val($tds.text());
        $(`#upItemdesc`).val($ts.text());
        $(`#upUnitPrice`).val($tt.text());
        $(`#upQty`).val($tf.text());

    });

    $('.deleteItem').click(function () {
        console.log("delete");
        $(`#tblItem tr`).click(function () {

            var $row = $(this).closest("tr");        // Finds the closest row <tr>
            $tds = $row.find("td:nth-child(1)");

            if (searchItem($tds.text()) === undefined) {
                alert("No such Item..please check the ID");
            } else {
                if (deleteItem($tds.text())) {
                    getAllItem();
                    alert("Item Deleted !");
                }
            }
        });
    });
}

function deleteItem(id) {
    for (let i = 0; i < itemDB.length; i++) {
        if (itemDB[i].code == id) {
            itemDB.splice(i, 1);
            return true
        }
    }
    return false;
}

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