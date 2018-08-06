    PRIMARY KEY,
  act_name    VARCHAR(200)            NULL
  COMMENT 'name of service',
  act_price   DECIMAL(19, 2) UNSIGNED NULL
  COMMENT 'price per training, price can be reconsidered and changed',
  act_note    VARCHAR(1500)           NULL
  COMMENT 'additional infomation about service'
)
  COMMENT 'register of services'
  ENGINE = InnoDB;

CREATE TABLE client
(
  id_client        INT AUTO_INCREMENT
    PRIMARY KEY,
  cl_name          VARCHAR(50)   NULL
  COMMENT 'name of client',
  cl_lastname      VARCHAR(50)   NULL
  COMMENT 'last name of client',
  cl_phone         VARCHAR(15)   NULL
  COMMENT 'contact phone number',
  cl_email         VARCHAR(50)   NULL
  COMMENT 'contact email',
  cl_personal_data VARCHAR(3000) NULL
  COMMENT 'different info about client',
  cl_iconpath      VARCHAR(300)  NULL,
  id_user          INT           NULL,
  CONSTRAINT client_user_id_user_fk
  UNIQUE (id_user)
)
  COMMENT 'register of clients'
  ENGINE = InnoDB;

CREATE TABLE order_
(
  id_order     INT AUTO_INCREMENT
    PRIMARY KEY,
  ord_date     DATE                         NULL
  COMMENT 'date of order',
  ord_price    DECIMAL                      NULL
  COMMENT 'used price, taken from service price on the date of the order',
  ord_discount TINYINT UNSIGNED DEFAULT '0' NULL
  COMMENT 'used discount in %, can be  0',
  ord_closure  DATE                         NULL
  COMMENT 'date when order closed, can be closed when all trainings finished and paid, probably in UI will be boolean flag',
  ord_feedback VARCHAR(5000)                NULL
  COMMENT 'the feedback of client relating gym, trainers, quality of services and so on',
  id_client    INT                          NOT NULL,
  id_activity  INT                          NOT NULL,
  CONSTRAINT order_client_id_client_fk
  FOREIGN KEY (id_client) REFERENCES client (id_client),
  CONSTRAINT order_activity_id_activity_fk
  FOREIGN KEY (id_activity) REFERENCES activity (id_activity)
)
  COMMENT 'client''s order, corresponds to specific service, if client wants one more service needed to create one more order'
  ENGINE = InnoDB;

CREATE INDEX order_client_id_client_fk
  ON order_ (id_client);

CREATE INDEX order_activity_id_activity_fk
  ON order_ (id_activity);

CREATE TABLE prescription
(
  id_order               INT              NOT NULL,
  id_trainer             INT              NOT NULL,
  pre_date               DATE             NULL
  COMMENT 'date of prescription',
  pre_weeks              TINYINT UNSIGNED NULL
  COMMENT 'weeks to be trained',
  pre_trainings_per_week TINYINT UNSIGNED NULL
  COMMENT 'trainings per week',
  pre_trainer_note       VARCHAR(3000)    NULL
  COMMENT 'all information about prescription: exercises, equipment, diet and so on',
  pre_client_note        VARCHAR(3000)    NULL
  COMMENT 'comments of client concerning prescription',
  pre_agreed             DATE             NULL
  COMMENT 'date when client confirmed that prescription agreed',
  PRIMARY KEY (id_order, id_trainer),
  CONSTRAINT prescription_order_id_order_fk
  FOREIGN KEY (id_order) REFERENCES order_ (id_order)
)
  COMMENT 'reconmmendations for client about excercises, equipment, diet and etc from trainer, one trainer can do only one prescription for order'
  ENGINE = InnoDB;

CREATE INDEX prescription_trainer_id_trainer_fk
  ON prescription (id_trainer);

CREATE TABLE trainer
(
  id_trainer       INT AUTO_INCREMENT
    PRIMARY KEY,
  tr_name          VARCHAR(50)   NULL
  COMMENT 'name of trainer',
  tr_lastname      VARCHAR(50)   NULL
  COMMENT 'lastname of trainer',
  tr_phone         VARCHAR(15)   NULL
  COMMENT 'contact phone number',
  tr_personal_data VARCHAR(3000) NULL
  COMMENT 'personal info about trainer',
  tr_iconpath      VARCHAR(300)  NULL,
  id_user          INT           NULL,
  CONSTRAINT trainer_user_id_user_fk
  UNIQUE (id_user)
)
  COMMENT 'register of trainers'
  ENGINE = InnoDB;

ALTER TABLE prescription
  ADD CONSTRAINT prescription_trainer_id_trainer_fk
FOREIGN KEY (id_trainer) REFERENCES trainer (id_trainer);

CREATE TABLE training
(
  id_training      INT AUTO_INCREMENT
    PRIMARY KEY,
  trg_date         DATE          NULL
  COMMENT 'date of training',
  trg_start_time   TIME          NULL
  COMMENT 'start time of training',
  trg_end_time     TIME          NULL
  COMMENT 'end time of training',
  trg_visited      TINYINT(1)    NULL
  COMMENT 'visited - true / unvisited - false',
  trg_client_note  VARCHAR(3000) NULL
  COMMENT 'any note about training from client side',
  trg_trainer_note VARCHAR(3000) NULL
  COMMENT 'trainer comments concerning specific training',
  id_order         INT           NOT NULL
  COMMENT 'training can be arranged only within placed order',
  id_trainer       INT           NULL
  COMMENT 'training can be without trainer, this field can be null',
  CONSTRAINT training_order_id_order_fk
  FOREIGN KEY (id_order) REFERENCES order_ (id_order),
  CONSTRAINT training_trainer_id_trainer_fk
  FOREIGN KEY (id_trainer) REFERENCES trainer (id_trainer)
)
  COMMENT 'trainings done or scheduled in frames of order'
  ENGINE = InnoDB;

CREATE INDEX training_order_id_order_fk
  ON training (id_order);

CREATE INDEX training_trainer_id_trainer_fk
  ON training (id_trainer);

CREATE TABLE user
(
  id_user     INT AUTO_INCREMENT
    PRIMARY KEY,
  us_login    VARCHAR(15) NOT NULL,
  us_password VARCHAR(64) NOT NULL,
  us_role     VARCHAR(50) NOT NULL
  COMMENT 'enum / admin, client, trainer',
  CONSTRAINT user_us_login_uindex
  UNIQUE (us_login)
)
  COMMENT 'users of system'
  ENGINE = InnoDB;

CREATE INDEX user_role_id_role_fk
  ON user (us_role);

ALTER TABLE client
  ADD CONSTRAINT client_user_id_user_fk
FOREIGN KEY (id_user) REFERENCES user (id_user);

ALTER TABLE trainer
  ADD CONSTRAINT trainer_user_id_user_fk
FOREIGN KEY (id_user) REFERENCES user (id_user);


