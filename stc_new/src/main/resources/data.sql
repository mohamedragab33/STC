insert into permission_groups (id, group_name) values (1, 'admins');
insert into permission_groups (id, group_name) values (2, 'users');
insert into Permission (id, USER_EMAIL, PERMISSIONS_LEVEL, GROUP_ID) values (1, 'ragab.admin@gmail.com','all', 1 );
insert into Permission (id, USER_EMAIL, PERMISSIONS_LEVEL, GROUP_ID) values (2, 'ragab.user@gmail.com','sub', 2 );