function addIdToField (idAct, idHid, idSubm, idNumber, create, update) {

    var act = document.getElementById(idAct);
    var hid = document.getElementById(idHid);
    var subm = document.getElementById(idSubm);

    if(act.value == idNumber) {

        act.value = "";
        subm.value = create;
        hid.value = "activity_create";

    } else {
        // focusedElem.focus();

        act.value = idNumber;

        subm.value = update;
        hid.value = "activity_update";
    }

}


function changeLanguageToEn() {
   return '';
}

function changeLanguageToRu() {
    return 'ru_RU';
}