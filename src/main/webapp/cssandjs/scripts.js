function showLogin() {
    document.getElementById('registerBackground').style.display = 'none';
    document.getElementById('loginBackground').style.display = 'inline-block';
}

function showRegister() {
    document.getElementById('loginBackground').style.display = 'none';
    document.getElementById('registerBackground').style.display = 'inline-block';
}

/*
Function that hides backgrounds of containers 'login', 'register', and 'details' when clicking on the outer area.
 */
window.onclick = function(event) {
    const login = document.getElementById('loginBackground');
    const register = document.getElementById('registerBackground');
    const detailsBackground = document.getElementById('detailsBackground');
    if (event.target === login) {
        login.style.display = "none";
    } else if (event.target === register) {
        register.style.display = "none";
    } else if (event.target === detailsBackground) {
        detailsBackground.style.display = "none";
        var node = document.getElementById("details");
        node.querySelectorAll('*').forEach(n => n.remove());
    }
}

function validateRegister(form) {
    const inputs = form.getElementsByTagName('input');
    if (isNaN(parseInt(inputs[2].value)) || inputs[2].value.length < 11) {
        alert('Enter the eleven-digit phone number!');
        return false;
    }
    if (inputs[3].value !== inputs[4].value) {
        alert('Both passwords must match!');
        return false;
    }
    return true;
}

/*
Puts all the ads existing in the database on the index page using ajax request.
Hides some of the details of an ad that may be seen by clicking 'details' button.
 */
function fetchAds() {
    $.ajax({
        method: 'GET',
        url: 'http://localhost:8080/job4j_cars/fetchAds',
        dataType: 'json'
    }).done(function(data) {
        const jsonResp = JSON.parse(JSON.stringify(data));
        if (jsonResp.length === 0) {
            $('#box').append('<div class="card card-margin">No ads with cars yet :(<div/>');
            return;
        }
        for (var i in jsonResp) {
            var card = document.createElement("div");
            card.className = 'card card-margin border border-info';
            card.setAttribute("id", jsonResp[i].id);
            card.innerHTML =
                '            <img class="card-img-top" src="data:image/jpeg;base64,' + jsonResp[i].car.photo.photo + '" alt="Here might be a photo" onerror="this.onerror=null;this.src=\'img/noPhoto.jpeg\';" style="max-width: 100%; height: 15rem">\n' +
                '            <div class="card-body">\n' +
                '                <h5 class="card-title"><b>' + jsonResp[i].name + '</b></h5>\n' +
                '                <p class="card-text" hidden="true"><b>Published: </b>' + new Date(jsonResp[i].created) + '</p>\n' +
                '                <p class="card-text"><b>Car brand: </b>' + jsonResp[i].car.name + '</p>\n' +
                '                <p class="card-text"><b>Model: </b>' + jsonResp[i].car.carModel.name + '</p>\n' +
                '                <p class="card-text"><b>Year of issue: </b>' + jsonResp[i].car.yearOfIssue + '</p>\n' +
                '                <p class="card-text"><b>Mileage: </b>' + jsonResp[i].car.mileage + 'km</p>\n' +
                '                <p class="card-text"><b>Price: </b>' + jsonResp[i].price + '$</p>\n' +
                '                <p class="card-text"><b>Status: </b>' + renderStatus(jsonResp[i].seller.email, jsonResp[i].sold) + '</p>\n' +

                '                <p class="card-text" hidden="true"><b>Body type: </b>' + jsonResp[i].car.bodyType + ' <b>Steering Wheel: </b>' + jsonResp[i].car.carModel.steeringWheel + '</p>\n' +
                '                <p class="card-text" hidden="true"><b>Transmission: </b>' + jsonResp[i].car.transmission + ' <b>Car drive: </b>' + jsonResp[i].car.carModel.carDrive + '</p>\n' +
                '                <p class="card-text" hidden="true"><b>Engine: </b> ' + jsonResp[i].car.engine.type + ' <b>Volume: </b>' + jsonResp[i].car.engine.volume + 'L</p>\n' +

                '                <p class="card-text" hidden="true"><b>Description: </b>' + jsonResp[i].description + '</p>\n' +
                '                <p class="card-text" hidden="true"><b>Seller: </b>' + jsonResp[i].seller.name + '</p>\n' +
                '                <p class="card-text" hidden="true"><b>Seller\'s email: </b>' + jsonResp[i].seller.email + ' <b>Seller\s phone: </b>' + jsonResp[i].seller.phone + '</p>\n' +
                '                <button class="btn btn-outline-info details" onclick="showDetails(this)">Details</button>\n' + renderEdit(jsonResp[i].seller.email) +
                '            </div>';
            document.getElementById('box').appendChild(card);
        }
        document.getElementById('loading').remove();
    });
}

/*
Checks if the email of the concrete of an ad equals to that of the logged in user.
 */
function userRegistered(userEmail) {
    return $('#userEmail').text() === userEmail;
}

/*
Changes sale status of an an to the opposite. Uses ajax call to the server.
 */
function updateStatus(card, status) {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/job4j_cars/updateAdStatus',
        data: {
            adId: card.getAttribute('id'),
            status: status
        },
        success: function () {
            location.reload();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert('textStatus: ' + textStatus + '\n' + 'errorThrown: ' + errorThrown + '\n' + 'responseText:' + jqXHR.responseText
                + '\n' + 'jqXHR.statusText: ' + jqXHR.statusText);
        }
    });
}

/*
Renders the status of an ad. Based upon whether the given ad's email is
equal to the logged in user's email renders the interactive status sign that can be changed.
 */
function renderStatus(userEmail, sold) {
    if (userRegistered(userEmail)) {
        if (sold) {
            return '<a style="cursor: pointer" title="set ON SALE" onclick="return updateStatus(this.closest(\'.card\'), false)"><span style="color: red; display: inline">SOLD</span></a>';
        } else {
            return '<a style="cursor: pointer" title="set SOLD" onclick="return updateStatus(this.closest(\'.card\'), true)"><span style="color: green; display: inline">ON SALE</span></a>';
        }
    } else {
        if (sold) {
            return '<span style="color: red; display: inline">SOLD</span>';
        } else {
            return '<span style="color: green; display: inline">ON SALE</span>';
        }
    }
}

/*
Deletes an ad using ajax requst to the server.
 */
function deleteAd(card) {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/job4j_cars/deleteAd',
        data: {
            adId: card.getAttribute('id'),
        },
        success: function () {
            location.reload();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert('textStatus: ' + textStatus + '\n' + 'errorThrown: ' + errorThrown + '\n' + 'responseText:' + jqXHR.responseText
                + '\n' + 'jqXHR.statusText: ' + jqXHR.statusText);
        }
    });
}

/*
Places 'delete' button on an ad if the ad has been published by the authorised user.
 */
function renderEdit(userEmail) {
    if (userRegistered(userEmail)) {
        return '<button class="btn btn-outline-danger delete" onclick="deleteAd(this.closest(\'.card\'))">Delete</button>';
    } else return '';
}

/*
Shows the 'details' block filling it with the copy of an ad and showing all of the hidden properties.
Launched by pushing the 'details' button.
 */
function showDetails(cardBody) {
    var details = document.getElementById('details');
    var card = cardBody.closest('.card');
    var cardClone = card.cloneNode(true);
    cardClone.className = "container";

    details.appendChild(cardClone);
    details.querySelectorAll("p").forEach(child => child.removeAttribute("hidden"));
    cardClone.querySelectorAll("button").forEach(btn => btn.remove());
    document.getElementById('detailsBackground').style.display = 'inline-block';
}

/*
Instantly shows the photo of an ad that is about to be uploaded to the server.
Triggered by the file form field when changed.
 */
function showPhoto(input) {
    var reader = new FileReader();
    reader.onload = function(e) {
        $('#photo').attr('src', e.target.result);
    }
    reader.readAsDataURL(input.files[0]);
    $('#photo').show();
}

/*
Fetches all the cars and car models from the database to fill the select list
so that a user can choose those from existing ones.
 */
function fetchCars() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/job4j_cars/fetchCars',
        dataType: 'json',
    }).done(function (data) {
        var jsonResp = JSON.parse(JSON.stringify(data));
        var predefinedCar = $('#carHolder').val();
        if (predefinedCar !== 'null') {
            $('#car').attr('value', predefinedCar);
        }
        var predefinedCarModel = $('#carModelHolder').val();
        if (predefinedCarModel !== 'null') {
            $('#carModel').attr('value', predefinedCarModel);
        }
        for (var i in jsonResp) {
            $('#cars').append('<option value="' + jsonResp[i].name + '">');
            $('#carModels').append('<option value="' + jsonResp[i].carModel.name + '">');
        }
    }).fail(function (err) {
        alert(err);
    });
}

