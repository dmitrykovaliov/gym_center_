function addIdToFieldActivity (idAct, idHid, idSubm, idNumber, create, update) {

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

function addIdToFieldClient (idAct, idHid, idSubm, idNumber, create, update) {

    var act = document.getElementById(idAct);
    var hid = document.getElementById(idHid);
    var subm = document.getElementById(idSubm);

    if(act.value == idNumber) {

        act.value = "";
        subm.value = create;
        hid.value = "client_create";

    } else {
        // focusedElem.focus();

        act.value = idNumber;

        subm.value = update;
        hid.value = "client_update";
    }
}

function addIdToFieldTrainer (idAct, idHid, idSubm, idNumber, create, update) {

    var act = document.getElementById(idAct);
    var hid = document.getElementById(idHid);
    var subm = document.getElementById(idSubm);

    if(act.value == idNumber) {

        act.value = "";
        subm.value = create;
        hid.value = "trainer_create";

    } else {
        // focusedElem.focus();

        act.value = idNumber;

        subm.value = update;
        hid.value = "trainer_update";
    }

}

function addIdToFieldOrder (idAct, idHid, idSubm, idNumber, create, update) {

    var act = document.getElementById(idAct);
    var hid = document.getElementById(idHid);
    var subm = document.getElementById(idSubm);

    if(act.value == idNumber) {

        act.value = "";
        subm.value = create;
        hid.value = "order_create";

    } else {
        // focusedElem.focus();

        act.value = idNumber;

        subm.value = update;
        hid.value = "order_update";
    }
}

function addIdToFieldPrescription (idAct, idAct1, idHid, idSubm, idOrder, idTrainer, create, update) {

    var act = document.getElementById(idAct);
    var act1 = document.getElementById(idAct1);
    var hid = document.getElementById(idHid);
    var subm = document.getElementById(idSubm);

    if(act.value == idOrder && act1 == idTrainer) {

        act.value = "";
        act1.value = "";
        subm.value = create;
        hid.value = "prescription_create";

    } else {
        // focusedElem.focus();

        act.value = idOrder;
        act1.value = idTrainer;

        subm.value = update;
        hid.value = "prescription_update";
    }

}

function addIdToFieldTraining (idAct, idHid, idSubm, idNumber, create, update) {

    var act = document.getElementById(idAct);
    var hid = document.getElementById(idHid);
    var subm = document.getElementById(idSubm);

    if(act.value == idNumber) {

        act.value = "";
        subm.value = create;
        hid.value = "training_create";

    } else {
        // focusedElem.focus();

        act.value = idNumber;

        subm.value = update;
        hid.value = "training_update";
    }
}

function addIdToFieldUser (idAct, idHid, idSubm, idCl, idTr, idNumber, idClient, idTrainer, create, update) {

    var act = document.getElementById(idAct);
    var cl = document.getElementById(idCl);
    var tr = document.getElementById(idTr);
    var hid = document.getElementById(idHid);
    var subm = document.getElementById(idSubm);

    if(act.value == idNumber) {

        act.value = "";
        cl.value = "";
        tr.value = "";
        subm.value = create;
        hid.value = "user_create";

    } else {
        // focusedElem.focus();

        act.value = idNumber;
        cl.value = idClient;
        tr.value = idTrainer;

        subm.value = update;
        hid.value = "user_update";
    }
}

function changeLanguageToEn() {
   return '';
}

function changeLanguageToRu() {
    return 'ru_RU';
}