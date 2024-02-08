let today = new Date().toISOString().slice(0, 10);

$('#txtDate').css({
    color: 'green',
    fontWeight: '500'
});

$('#txtDate').val(today);

let finalTotal = 0;

let final = 0;


$('#btnClear').on('click', function () {
    clearAll();
    clearBill();
});

//place order button
$('#btnPlaceOrder').on('click', function () {
    let cash = parseFloat($('#txtCash').val());
    let balance = cash - final;
    $('#txtBalnce').val(balance);

    $('#btnPlaceOrder').prop("disabled", true);

    let itemList = [];

    let trList = $('#order-tbl-body > tr');

    for (const tr of trList) {
        let child = $(tr).children();
        let object = {
            oid: $('#txtOrderId').val(),
            code: $(child[0]).text(),
            qty: $(child[3]).text(),
            unitPrice: $(child[2]).text()
        }
        itemList.push(object);
    }

    orderDB.push({
        oid: $("#txtOrderId").val(),
        date: $('#txtDate').val(),
        customerID: $('#selCusId').val(),
        orderDetails: itemList
    });

    clearAll();
    $('#order-tbl-body').empty();
    loadAllOrderDetails();

});

$('#btnAddOrder').on('click', function () {

    let price = parseFloat($('#orderItemPrice').val());

    let total = price * parseInt($('#getQty').val());

    let list = $(`#order-tbl-body > tr > td:nth-child(1)`);

    for (const td of list) {
        if ($(td).text() == $('#selItemId').val()) {
            let row = $(td).parent();
            $(row).remove();
        }
    }

    $(`#order-tbl-body`).append(`<tr>
        <td>${$('#selItemId').val()}</td>
        <td>${$('#orderItemDesc').val()}</td>
        <td>${$('#orderItemPrice').val()}</td>
        <td>${$('#getQty').val()}</td>
        <td>${total}</td>
    </tr>`);


    let totalList = $(`#order-tbl-body > tr > td:nth-child(5)`);


    for (const total of totalList) {
        finalTotal += parseFloat($(total).text());
    }

    final = finalTotal;

    $('#total').text(' ' + finalTotal + '/=');

    $('#subTotal').text(finalTotal + '/=');


    $('#btnPlaceOrder').prop("disabled", false);
    $('#btnAddOrder').prop("disabled", true);
});

function loadCustomerId() {
    $.ajax({
        url: "http://localhost:8080/app/customers",
        method: "GET",
        success: function (resp) {
            console.log("Success: ", resp);
            for (const customer of resp) {
                $('#selCusId').append(`<option>${customer.id}</option>`)
            }
        },
        error: function (error) {
            console.log("Error: ", error);
        }
    });
}

$('#selCusId').on('change', function () {
    let id = $('#selCusId').val();

    $.ajax({
        url: "http://localhost:8080/app/customers",
        method: "GET",
        success: function (resp) {
            console.log("Success: ", resp);
            for (const customer of resp) {
                if (customer.id == id) {
                    $('#orderCusName').val(customer.name);
                    $('#orderCusAddres').val(customer.address);
                }
            }
            $('#selItemId').focus();
        },
        error: function (error) {
            console.log("Error: ", error);
        }
    });
    
});


function loadAllItemId() {

    $.ajax({
        url: "http://localhost:8080/app/items",
        method: "GET",
        success: function (resp) {
            console.log("Success: ", resp);
            for (const item of resp) {
                $('#selItemId').append(`<option>${item.code}</option>`)
            }
        },
        error: function (error) {
            console.log("Error: ", error);
        }
    });
}

$('#selItemId').on('change', function () {
    let code = $('#selItemId').val();

        $.ajax({
            url: "http://localhost:8080/app/items",
            method: "GET",
            success: function (resp) {
                console.log("Success: ", resp);
                for (const item of resp) {
                    if (item.code == code) {
                        $('#orderItemDesc').val(item.description);
                        $('#orderItemPrice').val(item.unitPrice);
                        $('#orderQty').val(item.qtyOnHand);
                    }
                }
                $('#getQty').focus();
                $('#btnAddOrder').prop("disabled", false);
            },
            error: function (error) {
                console.log("Error: ", error);
            }
        });
});

$('#txtDiscount').on('keyup change', function () {
    let currentDiscount = parseFloat($('#txtDiscount').val());

    let subTotal = finalTotal / 100 * currentDiscount;

    final = finalTotal - subTotal;

    $('#subTotal').text(final + '/=');

});

$('#txtDiscount').val(0);

$('#selCusId').prop('disabled',true);

loadAllItemId();

loadCustomerId();
