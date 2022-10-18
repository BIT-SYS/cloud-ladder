drop table if exists method_implementation;

create table "method_implementation"
(
    "id" serial,
    "scope" varchar,
    "name" varchar not null,
    "from" varchar not null,
    "type" varchar not null,
    "data" text not null,
    primary key ("id")
);

insert into "method_implementation"("name", "from", "type", "data") values
    ('hello', 'cloudladder', 'http', '{"url": "http://localhost:8080/hello"}')
    ;
