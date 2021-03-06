INSERT INTO "TB_BUSINESS_PROCESS" VALUES (1);

INSERT INTO "TB_ACTION" VALUES (1,'noSecAction','NoSecurity',1);
INSERT INTO "TB_ACTION" VALUES (2,'tc2Action','TC2Leg1',1);
INSERT INTO "TB_ACTION" VALUES (3,'tc1Action','TC1Leg1',1);
INSERT INTO "TB_ACTION" VALUES (4,'tc3ActionLeg1','TC3Leg1',1);
INSERT INTO "TB_ACTION" VALUES (5,'tc3ActionLeg2','TC3Leg2',1);

INSERT INTO "TB_AGREEMENT" VALUES (1,'agreementEmpty','','',1);

INSERT INTO "TB_PARTY" VALUES (1,'http://fmstest.flame.co.za:8080/AS4','flame',NULL,NULL,1);
INSERT INTO "TB_PARTY" VALUES (2,'http://5.153.46.53:29001/AS4','ibmgw',NULL,NULL,1);
INSERT INTO "TB_PARTY" VALUES (3,'http://localhost:8892/domibus/services/msh','red_gw',NULL,NULL,1);
INSERT INTO "TB_PARTY" VALUES (4,'http://msh.holodeck-b2b.org:8080/msh','holodeck',NULL,NULL,1);
INSERT INTO "TB_PARTY" VALUES (5,'http://test.edelivery.it.nrw.de/domibus-msh','domibus_de',NULL,NULL,1);
INSERT INTO "TB_PARTY" VALUES (6,'http://localhost:8080/domibus/services/msh','blue_gw',NULL,NULL,1);
INSERT INTO "TB_PARTY" VALUES (7,'http://208.67.130.9/exchange/axwayas4','axway',NULL,NULL,1);
INSERT INTO "TB_PARTY" VALUES (8,'https://secure.gateway.eu/as4','cefgw',NULL,NULL,1);

INSERT INTO "TB_CONFIGURATION" VALUES (1,1,6);

INSERT INTO "TB_MPC" ("ID_PK","DEFAULT_MPC","IS_ENABLED", "NAME","QUALIFIED_NAME","RETENTION_DOWNLOADED","RETENTION_UNDOWNLOADED","FK_CONFIGURATION")
VALUES (1,1,1,'defaultMpc','http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/defaultMPC',3,14400,1);

INSERT INTO "TB_MESSAGE_INFO" ("ID_PK","MESSAGE_ID","REF_TO_MESSAGE_ID","TIME_STAMP") VALUES (1,'2809cef6-240f-4792-bec1-7cb300a34679@domibus.eu',NULL,'2016-02-11 12:57:19');
INSERT INTO "TB_MESSAGE_INFO" ("ID_PK","MESSAGE_ID","REF_TO_MESSAGE_ID","TIME_STAMP") VALUES (2,'78a1d578-0cc7-41fb-9f35-86a5b2769a14@domibus.eu',NULL,'2016-02-11 16:29:44');
INSERT INTO "TB_MESSAGE_INFO" ("ID_PK","MESSAGE_ID","REF_TO_MESSAGE_ID","TIME_STAMP") VALUES (3,'2bbc05d8-b603-4742-a118-137898a81de3@domibus.eu',NULL,'2016-02-11 16:30:00');
INSERT INTO "TB_MESSAGE_INFO" ("ID_PK","MESSAGE_ID","REF_TO_MESSAGE_ID","TIME_STAMP") VALUES (4,'88a1d578-0cc7-41fb-9f35-86a5b2769a14@domibus.eu',NULL,'2016-02-11 16:29:44');

INSERT INTO "TB_USER_MESSAGE" ("ID_PK","COLLABORATION_INFO_ACTION","AGREEMENT_REF_PMODE","AGREEMENT_REF_TYPE","AGREEMENT_REF_VALUE","COLL_INFO_CONVERS_ID","SERVICE_TYPE","SERVICE_VALUE","MPC","FROM_ROLE","TO_ROLE","MESSAGEINFO_ID_PK")
VALUES (1,'TC1Leg1',NULL,NULL,NULL,'7318c713-a1a7-4dc7-8497-337d40d95d39','tc1','bdx:noprocess','http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/defaultMPC','http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/initiator','http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/responder',1);

INSERT INTO "TB_USER_MESSAGE" ("ID_PK","COLLABORATION_INFO_ACTION","AGREEMENT_REF_PMODE","AGREEMENT_REF_TYPE","AGREEMENT_REF_VALUE","COLL_INFO_CONVERS_ID","SERVICE_TYPE","SERVICE_VALUE","MPC","FROM_ROLE","TO_ROLE","MESSAGEINFO_ID_PK")
VALUES (2,'TC1Leg1',NULL,NULL,NULL,'489c1e59-2f4b-4c15-b780-38fa81f1df0e','tc1','bdx:noprocess','http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/defaultMPC','http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/initiator','http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/responder',2);

INSERT INTO "TB_USER_MESSAGE" ("ID_PK","COLLABORATION_INFO_ACTION","AGREEMENT_REF_PMODE","AGREEMENT_REF_TYPE","AGREEMENT_REF_VALUE","COLL_INFO_CONVERS_ID","SERVICE_TYPE","SERVICE_VALUE","MPC","FROM_ROLE","TO_ROLE","MESSAGEINFO_ID_PK")
VALUES (3,'TC1Leg1',NULL,NULL,NULL,'9985e5cd-b898-4a7e-acd8-5fdf7a9edde7','tc1','bdx:noprocess','http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/defaultMPC','http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/initiator','http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/responder',3);

INSERT INTO "TB_USER_MESSAGE" ("ID_PK","COLLABORATION_INFO_ACTION","AGREEMENT_REF_PMODE","AGREEMENT_REF_TYPE","AGREEMENT_REF_VALUE","COLL_INFO_CONVERS_ID","SERVICE_TYPE","SERVICE_VALUE","MPC","FROM_ROLE","TO_ROLE","MESSAGEINFO_ID_PK")
VALUES (4,'TC1Leg1',NULL,NULL,NULL,'489c1e59-2f4b-4c15-b780-38fa81f1df0e','tc1','bdx:noprocess','http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/defaultMPC','http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/initiator','http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/responder',4);

INSERT INTO "TB_MESSAGING" ("ID_PK","ID","SIGNAL_MESSAGE_ID","USER_MESSAGE_ID") VALUES (1,NULL,NULL,1);
INSERT INTO "TB_MESSAGING" ("ID_PK","ID","SIGNAL_MESSAGE_ID","USER_MESSAGE_ID") VALUES (2,NULL,NULL,2);
INSERT INTO "TB_MESSAGING" ("ID_PK","ID","SIGNAL_MESSAGE_ID","USER_MESSAGE_ID") VALUES (3,NULL,NULL,3);
INSERT INTO "TB_MESSAGING" ("ID_PK","ID","SIGNAL_MESSAGE_ID","USER_MESSAGE_ID") VALUES (4,NULL,NULL,4);

INSERT INTO "TB_MESSAGE_LOG" ("ID_PK","BACKEND","DELETED","ENDPOINT","MESSAGE_ID","MESSAGE_STATUS","MESSAGE_TYPE","MPC","MSH_ROLE","NEXT_ATTEMPT","NOTIFICATION_STATUS","RECEIVED","SEND_ATTEMPTS","SEND_ATTEMPTS_MAX")
VALUES (1,NULL,NULL,NULL,'2809cef6-240f-4792-bec1-7cb300a34679@domibus.eu','RECEIVED','USER_MESSAGE','http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/defaultMPC','RECEIVING',NULL,NULL,'2016-02-11 12:57:24',0,0);

INSERT INTO "TB_MESSAGE_LOG" ("ID_PK","BACKEND","DELETED","ENDPOINT","MESSAGE_ID","MESSAGE_STATUS","MESSAGE_TYPE","MPC","MSH_ROLE","NEXT_ATTEMPT","NOTIFICATION_STATUS","RECEIVED","SEND_ATTEMPTS","SEND_ATTEMPTS_MAX")
VALUES (2,NULL,NULL,NULL,'78a1d578-0cc7-41fb-9f35-86a5b2769a14@domibus.eu','RECEIVED','USER_MESSAGE','http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/defaultMPC','RECEIVING',NULL,NULL,'2016-02-11 16:29:50',0,0);

INSERT INTO "TB_MESSAGE_LOG" ("ID_PK","BACKEND","DELETED","ENDPOINT","MESSAGE_ID","MESSAGE_STATUS","MESSAGE_TYPE","MPC","MSH_ROLE","NEXT_ATTEMPT","NOTIFICATION_STATUS","RECEIVED","SEND_ATTEMPTS","SEND_ATTEMPTS_MAX")
VALUES (3,NULL,NULL,NULL,'2bbc05d8-b603-4742-a118-137898a81de3@domibus.eu','RECEIVED','USER_MESSAGE','http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/defaultMPC','RECEIVING',NULL,NULL,'2016-02-11 16:30:00',0,0);

INSERT INTO "TB_MESSAGE_LOG" ("ID_PK","BACKEND","DELETED","ENDPOINT","MESSAGE_ID","MESSAGE_STATUS","MESSAGE_TYPE","MPC","MSH_ROLE","NEXT_ATTEMPT","NOTIFICATION_STATUS","RECEIVED","SEND_ATTEMPTS","SEND_ATTEMPTS_MAX")
VALUES (4,NULL,NULL,NULL,'88a1d578-0cc7-41fb-9f35-86a5b2769a14@domibus.eu','RECEIVED','USER_MESSAGE','http://docs.oasis-open.org/ebxml-msg/ebms/v3.0/ns/core/200704/defaultMPC','RECEIVING',NULL,NULL,'2016-02-11 16:29:50',0,0);

INSERT INTO "TB_PART_INFO" ("ID_PK","BINARY_DATA","DESCRIPTION_LANG","DESCRIPTION_VALUE","FILENAME","HREF","IN_BODY","MIME","SCHEMA_LOCATION","SCHEMA_NAMESPACE","SCHEMA_VERSION","PAYLOADINFO_ID")
VALUES (1, x'3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e3c68656c6c6f3e776f726c643c2f68656c6c6f3e',NULL,NULL,NULL,'sbdh-order','0','application/unknown',NULL,NULL,NULL,1);

INSERT INTO "TB_PART_INFO" ("ID_PK","BINARY_DATA","DESCRIPTION_LANG","DESCRIPTION_VALUE","FILENAME","HREF","IN_BODY","MIME","SCHEMA_LOCATION","SCHEMA_NAMESPACE","SCHEMA_VERSION","PAYLOADINFO_ID")
VALUES (2, x'3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e0d0a3c68656c6c6f3e776f726c643c2f68656c6c6f3e',NULL,NULL,NULL,'sbdh-order','0','application/unknown',NULL,NULL,NULL,2);

INSERT INTO "TB_PART_INFO" ("ID_PK","BINARY_DATA","DESCRIPTION_LANG","DESCRIPTION_VALUE","FILENAME","HREF","IN_BODY","MIME","SCHEMA_LOCATION","SCHEMA_NAMESPACE","SCHEMA_VERSION","PAYLOADINFO_ID")
VALUES (3, x'3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e3c68656c6c6f3e776f726c643c2f68656c6c6f3e',NULL,NULL,NULL,'sbdh-order','1','application/unknown',NULL,NULL,NULL,3);

INSERT INTO "TB_PART_INFO" ("ID_PK","BINARY_DATA","DESCRIPTION_LANG","DESCRIPTION_VALUE","FILENAME","HREF","IN_BODY","MIME","SCHEMA_LOCATION","SCHEMA_NAMESPACE","SCHEMA_VERSION","PAYLOADINFO_ID")
VALUES (4, x'3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e0d0a3c68656c6c6f3e776f726c643c2f68656c6c6f3e3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e0d0a3c68656c6c6f3e776f726c643c2f68656c6c6f3e3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e0d0a3c68656c6c6f3e776f726c643c2f68656c6c6f3e3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e0d0a3c68656c6c6f3e776f726c643c2f68656c6c6f3e3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e0d0a3c68656c6c6f3e776f726c643c2f68656c6c6f3e3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e0d0a3c68656c6c6f3e776f726c643c2f68656c6c6f3e3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e0d0a3c68656c6c6f3e776f726c643c2f68656c6c6f3e3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e0d0a3c68656c6c6f3e776f726c643c2f68656c6c6f3e3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e0d0a3c68656c6c6f3e776f726c643c2f68656c6c6f3e3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e0d0a3c68656c6c6f3e776f726c643c2f68656c6c6f3e3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e0d0a3c68656c6c6f3e776f726c643c2f68656c6c6f3e3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e0d0a3c68656c6c6f3e776f726c643c2f68656c6c6f3e3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e0d0a3c68656c6c6f3e776f726c643c2f68656c6c6f3e3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e0d0a3c68656c6c6f3e776f726c643c2f68656c6c6f3e3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e0d0a3c68656c6c6f3e776f726c643c2f68656c6c6f3e3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e0d0a3c68656c6c6f3e776f726c643c2f68656c6c6f3e3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e0d0a3c68656c6c6f3e776f726c643c2f68656c6c6f3e3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e0d0a3c68656c6c6f3e776f726c643c2f68656c6c6f3e3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e0d0a3c68656c6c6f3e776f726c643c2f68656c6c6f3e3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e0d0a3c68656c6c6f3e776f726c643c2f68656c6c6f3e3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e0d0a3c68656c6c6f3e776f726c643c2f68656c6c6f3e3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e0d0a3c68656c6c6f3e776f726c643c2f68656c6c6f3e3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e0d0a3c68656c6c6f3e776f726c643c2f68656c6c6f3e3c3f786d6c2076657273696f6e3d22312e302220656e636f64696e673d225554462d38223f3e0d0a3c68656c6c6f3e776f726c643c2f68656c6c6f3e', NULL,NULL,NULL,'sbdh-order','0','application/unknown',NULL,NULL,NULL,4);
