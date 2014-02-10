create table User (
  id integer primary key,
  login varchar(255),
  passwordHash varchar(32)
);

alter table User add constraint login_unique unique(login);

create unique index login_index on User(login);