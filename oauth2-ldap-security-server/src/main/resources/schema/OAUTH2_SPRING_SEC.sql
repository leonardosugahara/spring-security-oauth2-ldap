-- CREATE USER AND PRIVILEGES
create user OAUTH2_SPRING_SECURITY IDENTIFIED BY OAUTH2_SPRING_SECURITY;
grant all privileges to OAUTH2_SPRING_SECURITY;

-- CREATE TABLES
create table OAUTH2_SPRING_SECURITY.oauth_client_details (
  client_id VARCHAR(256) PRIMARY KEY,
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256),
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR2(4000),
  autoapprove VARCHAR(256)
);

create table OAUTH2_SPRING_SECURITY.oauth_access_token (
  token_id VARCHAR(256),
  token BLOB,
  authentication_id VARCHAR(256),
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication BLOB,
  refresh_token VARCHAR(256)
);

create table OAUTH2_SPRING_SECURITY.oauth_refresh_token (
  token_id VARCHAR(256),
  token BLOB,
  authentication BLOB
);

create table OAUTH2_SPRING_SECURITY.oauth_code (
  code VARCHAR(256), authentication BLOB
);

-- INSERT CLIENT
insert into OAUTH2_SPRING_SECURITY.oauth_client_details (client_id,client_secret,scope,authorized_grant_types,access_token_validity,refresh_token_validity) 
values ('app-trusted-client','app-secret','read,write','password,authorization_code,refresh_token,implicit',120,600); 
commit;