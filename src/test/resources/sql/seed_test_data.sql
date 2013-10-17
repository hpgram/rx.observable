use ecs;

insert into events values(null, CURRENT_TIMESTAMP(), "who@host.com", "host", "device.abcd123", "content.download", "com.hp.content", "1.0.0");
insert into events values(null, CURRENT_TIMESTAMP(), "who@host.com", "host", "device.abcd321", "content.download", "com.hp.content", "1.0.0");
insert into events values(null, CURRENT_TIMESTAMP(), "who@host.com", "host", "device.zxy", "content.download", "com.hp.content", "1.0.0");
insert into events values(null, CURRENT_TIMESTAMP(), "who@host.com", "host", "device.abcd123", "content.remove", "com.hp.content", "1.0.0");
insert into events values(null, CURRENT_TIMESTAMP(), "who@host.com", "host", "device.abcd123", "content.download", "com.hp.content", "1.0.1");

insert into userapps values(null, "who@host.com", "host", "device.abcd123", "com.hp.content", "1.0.0", CURRENT_TIMESTAMP());
insert into userapps values(null, "who@host.com", "host", "device.abcd123", "com.hp.content", "1.0.5", CURRENT_TIMESTAMP());
insert into userapps values(null, "who@host.com", "host", "device.abcd123", "com.hp.content", "1.0.1", CURRENT_TIMESTAMP());
insert into userapps values(null, "who@host.com", "host", "device.abcd123", "com.hp.content", "1.0.2", CURRENT_TIMESTAMP());
