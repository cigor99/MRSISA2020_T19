function validacija() {
    $("#imeError").css('visibility', 'hidden');
    $("#prezimeError").css('visibility', 'hidden');
    $("#lozinkaError").css('visibility', 'hidden');
    $("#lozinka1Error").css('visibility', 'hidden');
    $("#drzavaError").css('visibility', 'hidden');
    $("#gradError").css('visibility', 'hidden');
    $("#adresaError").css('visibility', 'hidden');
    $("#jedBrOsigError").css('visibility', 'hidden');
    $("#telefonError").css('visibility', 'hidden');
    $("#polError").css('visibility', 'hidden');
    $("#emailError").css('visibility', 'hidden');
    $("#jmbgError").css('visibility', 'hidden');



    var regName = /^([a-zA-Z]{3,30}\s*)+$/;
    var regAdress = /^([a-zA-Z0-9 .,'-]{3,30}\s*)+$/;
    var regEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    var regJmbg = /^[0-9]{13}$/;
    var regPass = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
    var regPhone = /^[+]{1}[0-9]{11,12}$/;
    let obaveznoPolje = false;
    let uslov = false;
    if ($("#drzava").val() == "") {
        $("#drzavaError").text("Država je obavezno polje").css('visibility', 'visible').css('color', 'red');
        obaveznoPolje = true;
    }
    if ($("#grad").val() == "") {
        $("#gradError").text("Grad je obavezno polje").css('visibility', 'visible').css('color', 'red');
        obaveznoPolje = true;
    }
    if ($("#adresa").val() == "") {
        $("#adresaError").text("Adresa je obavezno polje").css('visibility', 'visible').css('color', 'red');
        obaveznoPolje = true;
    }
    if ($("#telefon").val() == "") {
        $("#telefonError").text("Telefon je obavezno polje").css('visibility', 'visible').css('color', 'red');
        obaveznoPolje = true;
    }
    if ($("#jedBrOsig").val() == "") {
        $("#jedBrOsigError").text("Jedinstveni broj osiguranja je obavezno polje").css('visibility', 'visible').css('color', 'red');
        obaveznoPolje = true;
    }
    if ($("#jmbg").val() == "") {
        $("#jmbgError").text("JMBG je obavezno polje").css('visibility', 'visible').css('color', 'red');
        obaveznoPolje = true;
    }
    if ($("#pol").val() == "") {
        $("#polError").text("Pol je obavezno polje").css('visibility', 'visible').css('color', 'red');
        obaveznoPolje = true;
    }
    if ($("#ime").val() == "") {
        $("#imeError").text("Ime je obavezno polje").css('visibility', 'visible').css('color', 'red');
        obaveznoPolje = true;
    }
    if ($("#prezime").val() == "") {
        $("#prezimeError").text("Prezime je obavezno polje").css('visibility', 'visible').css('color', 'red');
        obaveznoPolje = true;
    }
    if ($("#password").val() == "") {
        $("#lozinkaError").text("Lozinka je obavezno polje").css('visibility', 'visible').css('color', 'red');
        obaveznoPolje = true;
    }
    if ($("#password1").val() == "") {
        $("#lozinka1Error").text("Ponovljena lozinka je obavezno polje").css('visibility', 'visible').css('color', 'red');
        obaveznoPolje = true;
    }
    if (obaveznoPolje) {
        return false;
    }




    if (!regName.test($("#ime").val())) {
        $("#imeError").text("Ime sme da sadrži samo slova, minimalne dužine 3, a maksimalne 30").css('visibility', 'visible').css('color', 'red');
        uslov = true;
    }

    if (!regName.test($("#prezime").val())) {
        $("#prezimeError").text("Prezime sme da sadrži samo slova, minimalne dužine 3, a maksimalne 30").css('visibility', 'visible').css('color', 'red');
        uslov = true;
    }

    if (!regName.test($("#drzava").val())) {
        $("#drzavaError").text("Država  sme da sadrži samo slova, minimalne dužine 3, a maksimalne 30").css('visibility', 'visible').css('color', 'red');
        uslov = true;
    }

    if (!regName.test($("#grad").val())) {
        $("#gradError").text("Grad sme da sadrži samo slova, minimalne dužine 3, a maksimalne 30").css('visibility', 'visible').css('color', 'red');
        uslov = true;
    }
    if (!regAdress.test($("#adresa").val())) {
        $("#adresaError").text("Adresa sme da sadrži samo mala slova, velika slova, brojeve ,-.' minimalne dužine 3, a maksimalne 30").css('visibility', 'visible').css('color', 'red');
        uslov = true;
    }
    if ($("#pol").val() != "MUSKI" && $("#pol").val() != "ZENSKI") {
        if ($("#pol").val() != undefined) {
            $("#polError").text("Pol može biti MUSKI/ZENSKI").css('visibility', 'visible').css('color', 'red');
            uslov = true;
        }
    }
    if (!regJmbg.test($("#jedBrOsig").val())) {
        if ($("#jedBrOsig").val() != undefined) {
            $("#jedBrOsigError").text("Neispravan jedinstevni broj osiguranika").css('visibility', 'visible').css('color', 'red');
            uslov = true;
        }
    }
    if (!regPhone.test($("#telefon").val())) {
        if ($("#telefon").val() != undefined) {
            $("#telefonError").text("Neispravan broj telefona, broj mora da počne sa + i sme da sadrži 11 ili 12 cifara").css('visibility', 'visible').css('color', 'red');
            uslov = true;
        }
    }

    if (!regEmail.test($("#email").val())) {
        if ($("#email").val() != undefined) {
            $("#emailError").text("Neispravan format email-a").css('visibility', 'visible').css('color', 'red');
            uslov = true;
        }
    }

    if (!regJmbg.test($("#jmbg").val())) {
        if ($("#jmbg").val() != undefined) {
            $("#jmbgError").text("Neispravan JMBG").css('visibility', 'visible').css('color', 'red');
            uslov = true;
        }
    }

    if (!regPass.test($("#password").val())) {
        $("#lozinkaError").text("Lozinka mora da sadrži najmanje 8 karaktera, bar jedno malo slovo, bar jedno veliko slovo i bar jedan broj").css('visibility', 'visible').css('color', 'red');
        uslov = true;
    }

    if (!regPass.test($("#password1").val())) {
        $("#lozinka1Error").text("Lozinka mora da sadrži najmanje 8 karaktera, bar jedno malo slovo, bar jedno veliko slovo i bar jedan broj").css('visibility', 'visible').css('color', 'red');
        uslov = true;
    }

    if ($("#password1").val() != $("#password").val()) {
        $("#lozinkaError").text("Lozinka se ne poklapaju").css('visibility', 'visible').css('color', 'red');
        uslov = true;
    }

    if (uslov) {
        return false;
    }

    return true;
}