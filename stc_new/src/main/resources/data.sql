insert into permission_groups (id, group_name) values (1, 'admins');
insert into permission_groups (id, group_name) values (2, 'users');
insert into permissions (id, USER_EMAIL, PERMISSIONS_LEVEL, GROUP_ID) values (1, 'admin@digi.com','all', 1 );
insert into permissions (id, USER_EMAIL, PERMISSIONS_LEVEL, GROUP_ID) values (2, 'user@digi.com','sub', 2 );