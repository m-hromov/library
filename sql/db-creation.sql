create table author
(
    book_id bigint not null
        primary key,
    name    varchar(255)
);


create table authorities
(
    authority_id bigint not null
        primary key,
    authority    varchar(255)
);

create table book
(
    book_id     bigint not null
        primary key,
    description varchar(2000),
    name        varchar(255)
);

create table book_author
(
    book_id   bigint not null
        constraint fkhwgu59n9o80xv75plf9ggj7xn
            references book,
    author_id bigint not null
        constraint fkbjqhp85wjv8vpr0beygh6jsgo
            references author
);

create table book_genre
(
    book_id bigint       not null
        constraint fk52evq6pdc5ypanf41bij5u218
            references book,
    genres  varchar(255) not null,
    primary key (book_id, genres)
);

create table user_table
(
    user_id                 bigint not null
        primary key,
    account_non_expired     boolean,
    account_non_locked      boolean,
    credentials_non_expired boolean,
    enabled                 boolean,
    password                varchar(255),
    role                    varchar(255),
    username                varchar(255)
);

create table book_user
(
    user_id bigint not null
        constraint fkitawp7h529prcnrfu9npna0du
            references user_table,
    book_id bigint not null
        constraint fkd5nhq3rdfgy9koewdex7bm7q1
            references book
);

create table user_authority
(
    user_id      bigint not null
        constraint fk83r0k35bfa4u6b61y5235ifdb
            references user_table,
    authority_id bigint not null
        constraint fkil6f39w6fgqh4gk855pstsnmy
            references authorities,
    primary key (user_id, authority_id)
);

