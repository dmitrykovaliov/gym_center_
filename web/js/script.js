function fillActivityForm (idCount, countNumber, idActivity, idCommand, idSubmit, activityValue, create, update) {

    var count = document.getElementById(idCount);
    var idAct = document.getElementById(idActivity);
    var idComm = document.getElementById(idCommand);
    var idSubm = document.getElementById(idSubmit);

    if(count.value == countNumber) {
        count.value = "";
        idAct.value = "";
        idSubm.value = create;
        idComm.value = "activity_create";

    } else {
        count.focus();
        count.value = countNumber;
        idAct.value = activityValue;

        idSubm.value = update;
        idComm.value = "activity_update";
    }
}

function fillOrderForm (idOrder, idCommand, idSubmit, idClient, idActivity,
                        orderValue, clientValue, activityValue, create, update) {

    var idOrd = document.getElementById(idOrder);
    var idComm = document.getElementById(idCommand);
    var idSubm = document.getElementById(idSubmit);
    var idCl = document.getElementById(idClient);
    var idAct = document.getElementById(idActivity);

    if(idOrd.value == orderValue) {
        idOrd.value = "";
        idSubm.value = create;
        idComm.value = "order_create";
        idCl.value = "";
        idAct.value = "";
    } else {
        idOrd.focus();
        idOrd.value = orderValue;
        idSubm.value = update;
        idComm.value = "order_update";
        idCl.value = clientValue;
        idAct.value = activityValue;
    }
}

function fillOrderFormClient (idOrder, idClient, idActivity, idSubmit, feedback,
                              valueOrder, valueClient, valueActivity) {

    var idOrd = document.getElementById(idOrder);
    var idSubm = document.getElementById(idSubmit);
    var feedb = document.getElementById(feedback);

    document.getElementById(idClient).value = valueClient;
    document.getElementById(idActivity).value = valueActivity;

    if(idOrd.value == valueOrder) {
        idOrd.value = "";
        idSubm.hidden = true;
        feedb.hidden = true;
    } else {
        idOrd.value = valueOrder;
        idSubm.hidden = false;
        feedb.hidden = false;
        feedb.focus();
    }
}

function fillPrescriptionForm (idCount, countNumber, idOrder, idTrainer, idCommand, idSubmit, valueOrder,
                               valueTrainer, create, update) {

    var count = document.getElementById(idCount);
    var idOrd = document.getElementById(idOrder);
    var idTrain = document.getElementById(idTrainer);
    var idComm = document.getElementById(idCommand);
    var idSubm = document.getElementById(idSubmit);

    if(count.value == countNumber) {
        count.value = "";
        idOrd.value = "";
        idTrain.value = "";
        idSubm.value = create;
        idComm.value = "prescription_create";

    } else {
        count.focus();
        count.value = countNumber;
        idOrd.value = valueOrder;
        idTrain.value = valueTrainer;

        idSubm.value = update;
        idComm.value = "prescription_update";
    }
}

function fillPrescriptionFormTrainer (idCount, countNumber, idOrder, idCommand, idSubmit, valueOrder,
                               create, update) {

    var count = document.getElementById(idCount);
    var idOrd = document.getElementById(idOrder);
    var idComm = document.getElementById(idCommand);
    var idSubm = document.getElementById(idSubmit);

    if(count.value == countNumber) {
        count.value = "";
        idOrd.value = "";
        idSubm.value = create;
        idComm.value = "prescription_create_trainer";

    } else {
        count.focus();
        count.value = countNumber;
        idOrd.value = valueOrder;
        idSubm.value = update;
        idComm.value = "prescription_update_trainer";
    }
}

function fillPrescriptionFormClient (idCount, countValue, idOrder, idTrainer, idSubmit, clientNote, agreed,
                                     valueOrder, valueTrainer) {

    var count = document.getElementById(idCount);
    var idSubm = document.getElementById(idSubmit);
    var clNote = document.getElementById(clientNote);
    var agr = document.getElementById(agreed);

    document.getElementById(idOrder).value = valueOrder;
    document.getElementById(idTrainer).value = valueTrainer;

    if(count.value == countValue) {
        count.value = "";
        idSubm.hidden = true;
        clNote.hidden = true;
        agr.hidden = true;
    } else {
        count.value = countValue;
        idSubm.hidden = false;
        clNote.hidden = false;
        agr.hidden = false;
        clNote.focus();
    }
}

function fillFormTraining (idCount, countNumber, idTraining, idOrder, idTrainer, idCommand, idSubmit, valueTraining,
                           valueOrder, valueTrainer, create, update) {

    var count = document.getElementById(idCount);
    var idTrain = document.getElementById(idTraining);
    var idOrd = document.getElementById(idOrder);
    var idTr = document.getElementById(idTrainer);
    var idComm = document.getElementById(idCommand);
    var idSubm = document.getElementById(idSubmit);

    if(count.value == countNumber) {

        count.value = "";
        idTrain.value = "";
        idOrd.value = "";
        idTr.value = "";

        idSubm.value = create;
        idComm.value = "training_create";

    } else {
        count.focus();
        count.value = countNumber;
        idTrain.value = valueTraining;
        idOrd.value = valueOrder;
        idTr.value = valueTrainer;

        idSubm.value = update;
        idComm.value = "training_update";
    }
}

function fillTrainingFormTrainer (idCount, countNumber, idTraining, idOrder, idSubmit, trainerNote,
                                  valueTraining, valueOrder) {

    var count = document.getElementById(idCount);
    var idSubm = document.getElementById(idSubmit);
    var trNote = document.getElementById(trainerNote);

    document.getElementById(idTraining).value = valueTraining;
    document.getElementById(idOrder).value = valueOrder;

    if(count.value == countNumber) {
        count.value = "";
        idSubm.hidden = true;
        trNote.hidden = true;
    } else {
        count.value = countNumber;
        idSubm.hidden = false;
        trNote.hidden = false;
        trNote.focus();
    }
}

function fillTrainingFormClient (idCount, countNumber, idTraining, idOrder, idTrainer, idSubmit, clientNote,
                                 trainingValue, orderValue, trainerValue) {

    var count = document.getElementById(idCount);
    var idSubm = document.getElementById(idSubmit);
    var clNote = document.getElementById(clientNote);

    document.getElementById(idTraining).value = trainingValue;
    document.getElementById(idOrder).value = orderValue;
   document.getElementById(idTrainer).value = trainerValue;

    if(count.value == countNumber) {
        count.value = "";
        idSubm.hidden = true;
        clNote.hidden = true;
    } else {
        count.value = countNumber;
        idSubm.hidden = false;
        clNote.hidden = false;
        clNote.focus();
    }
}
