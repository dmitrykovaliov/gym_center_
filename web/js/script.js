function fillActivityForm (idCount, countNumber, idActivity, idCommand, idSubmit, activityValue, create, update) {

    var count = document.getElementById(idCount);
    var idAct = document.getElementById(idActivity);
    var idComm = document.getElementById(idCommand);
    var idSubm = document.getElementById(idSubmit);

    if(count.value === countNumber) {
        count.value = "";
        idAct.value = "";
        idSubm.value = create;
        idComm.value = "activity_create";

    } else {
        count.value = countNumber;
        idAct.value = activityValue;

        idSubm.value = update;
        idComm.value = "activity_update";
    }
}

function fillOrderForm (idCount, countNumber, idOrder, idCommand, idSubmit, orderValue, create, update) {

    var count = document.getElementById(idCount);
    var idOrd = document.getElementById(idOrder);
    var idComm = document.getElementById(idCommand);
    var idSubm = document.getElementById(idSubmit);

    if(count.value === countNumber) {

        count.value = "";
        idOrd.value = "";
        idSubm.value = create;
        idComm.value = "order_create";

    } else {
        count.value = countNumber;
        idOrd.value = orderValue;

        idSubm.value = update;
        idComm.value = "order_update";
    }
}

function fillOrderFormClient (idCount, countNumber, idOrder, idClient, idActivity, valueOrder,
                              valueClient, valueActivity) {

    var count = document.getElementById(idCount);

    document.getElementById(idOrder).value = valueOrder;
    document.getElementById(idClient).value = valueClient;
    document.getElementById(idActivity).value = valueActivity;

    if(count.value === countNumber) {
        count.value = "";
    } else {
        count.value = countNumber;
    }
}

function fillPrescriptionForm (idCount, countNumber, idOrder, idTrainer, idCommand, idSubmit, valueOrder,
                               valueTrainer, create, update) {

    var count = document.getElementById(idCount);
    var idOrd = document.getElementById(idOrder);
    var idTrain = document.getElementById(idTrainer);
    var idComm = document.getElementById(idCommand);
    var idSubm = document.getElementById(idSubmit);

    if(count.value === countNumber) {

        count.value = "";
        idOrd.value = "";
        idTrain.value = "";
        idSubm.value = create;
        idComm.value = "prescription_create";

    } else {
        count.value = countNumber;
        idOrd.value = valueOrder;
        idTrain.value = valueTrainer;

        idSubm.value = update;
        idComm.value = "prescription_update";
    }
}

function fillPrescriptionFormTrainer (idCount, countNumber, idOrder, idTrainer, idCommand, idSubmit, valueOrder,
                                      valueTrainer, create, update) {

    var count = document.getElementById(idCount);
    var idOrd = document.getElementById(idOrder);
    var idTrain = document.getElementById(idTrainer);
    var idComm = document.getElementById(idCommand);
    var idSubm = document.getElementById(idSubmit);

    if(count.value === countNumber) {
        count.value = "";
        idOrd.value = "";
        idTrain.value = "";
        idSubm.value = create;
        idComm.value = "prescription_create_trainer";
    } else {
        count.value = countNumber;
        idOrd.value = valueOrder;
        idTrain.value = valueTrainer;

        idSubm.value = update;
        idComm.value = "prescription_update_trainer";
    }

}

function fillPrescriptionFormClient (idCount, countValue, idOrder, idTrainer,
                                     valueOrder, valueTrainer) {

    var count = document.getElementById(idCount);

    document.getElementById(idOrder).value = valueOrder;
    document.getElementById(idTrainer).value = valueTrainer;

    if(count.value === countValue) {
        count.value = "";
    } else {
        count.value = countValue;
    }
}

function fillFormTraining (idCount, countNumber, idTraining, idCommand, idSubmit, valueTraining, create, update) {

    var count = document.getElementById(idCount);
    var idTrain = document.getElementById(idTraining);
    var idComm = document.getElementById(idCommand);
    var idSubm = document.getElementById(idSubmit);

    if(count.value === countNumber) {

        count.value = "";
        idTrain.value = "";
        idSubm.value = create;
        idComm.value = "training_create";

    } else {
        count.value = countNumber;
        idTrain.value = valueTraining;
        idSubm.value = update;
        idComm.value = "training_update";
    }
}

function fillTrainingFormTrainer (idCount, countNumber, idTraining, idOrder, valueTraining, valueOrder) {

    var count = document.getElementById(idCount);
    document.getElementById(idTraining).value = valueTraining;
    document.getElementById(idOrder).value = valueOrder;

    if(count.value === countNumber) {
        count.value = "";
    } else {
        count.value = countNumber;
    }
}

function fillTrainingFormClient (idCount, countNumber, idTraining, idOrder, idTrainer,
                                 trainingValue, orderValue, trainerValue) {

    var count = document.getElementById(idCount);

    document.getElementById(idTraining).value = trainingValue;
    document.getElementById(idOrder).value = orderValue;
   document.getElementById(idTrainer).value = trainerValue;

    if(count.value === countNumber) {
        count.value = "";
    } else {
        count.value = countNumber;
    }
}
