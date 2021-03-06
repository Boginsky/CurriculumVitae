-- SQL Manager for PostgreSQL 6.3.0.54659
-- ---------------------------------------
-- Хост         : localhost
-- База данных  : resume
-- Версия       : PostgreSQL 13.3, compiled by Visual C++ build 1914, 64-bit



SET search_path = public, pg_catalog;
DROP INDEX IF EXISTS public.skill_idx;
DROP INDEX IF EXISTS public.practic_idx1;
DROP INDEX IF EXISTS public.practic_idx;
DROP INDEX IF EXISTS public.language_idx;
DROP INDEX IF EXISTS public.education_idx1;
DROP INDEX IF EXISTS public.education_idx;
DROP INDEX IF EXISTS public.course_idx1;
DROP INDEX IF EXISTS public.course_idx;
DROP SEQUENCE IF EXISTS public.skill_seq;
DROP TABLE IF EXISTS public.skill;
DROP TABLE IF EXISTS public.skill_category;
DROP SEQUENCE IF EXISTS public.profile_seq;
DROP SEQUENCE IF EXISTS public.practic_seq;
DROP TABLE IF EXISTS public.practic;
DROP SEQUENCE IF EXISTS public.language_seq;
DROP TABLE IF EXISTS public.language;
DROP SEQUENCE IF EXISTS public.education_seq;
DROP TABLE IF EXISTS public.education;
DROP SEQUENCE IF EXISTS public.course_seq;
DROP TABLE IF EXISTS public.course;
DROP TABLE IF EXISTS public.profile;
SET check_function_bodies = false;
--
-- Structure for table course (OID = 16851) : 
--
CREATE TABLE public.course (
    id bigint NOT NULL,
    id_profile bigint NOT NULL,
    name varchar(60) NOT NULL,
    school varchar(60) NOT NULL,
    finish_date date
)
WITH (oids = false);
--
-- Definition for sequence course_seq (OID = 16854) : 
--
CREATE SEQUENCE public.course_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
--
-- Structure for table education (OID = 16856) : 
--
CREATE TABLE public.education (
    id bigint NOT NULL,
    id_profile bigint NOT NULL,
    summary varchar(100) NOT NULL,
    begin_year integer NOT NULL,
    finish_year integer,
    university text NOT NULL,
    faculty varchar(255) NOT NULL
)
WITH (oids = false);
--
-- Definition for sequence education_seq (OID = 16862) : 
--
CREATE SEQUENCE public.education_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
--
-- Structure for table language (OID = 16869) : 
--
CREATE TABLE public.language (
    id bigint NOT NULL,
    id_profile bigint NOT NULL,
    name varchar(30) NOT NULL,
    level varchar(18) NOT NULL,
    type varchar(7) DEFAULT 0 NOT NULL
)
WITH (oids = false);
--
-- Definition for sequence language_seq (OID = 16873) : 
--
CREATE SEQUENCE public.language_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
--
-- Structure for table practic (OID = 16878) : 
--
CREATE TABLE public.practic (
    id bigint NOT NULL,
    id_profile bigint NOT NULL,
    "position" varchar(100) NOT NULL,
    company varchar(100) NOT NULL,
    begin_date date NOT NULL,
    finish_date date,
    responsibilities text NOT NULL,
    demo varchar(255),
    src varchar(255)
)
WITH (oids = false);
--
-- Definition for sequence practic_seq (OID = 16884) : 
--
CREATE SEQUENCE public.practic_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
--
-- Structure for table profile (OID = 16886) : 
--
CREATE TABLE public.profile (
    id bigint NOT NULL,
    uid varchar(100) NOT NULL,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    birth_day date,
    phone varchar(20),
    email varchar(100),
    country varchar(60),
    city varchar(100),
    objective text,
    summary text,
    large_photo varchar(255),
    small_photo varchar(255),
    info text,
    password varchar(255) NOT NULL,
    completed boolean NOT NULL,
    created timestamp(0) without time zone DEFAULT now() NOT NULL,
    skype varchar(80),
    vkontakte varchar(255),
    facebook varchar(255),
    linkedin varchar(255),
    github varchar(255),
    stackoverflow varchar(255)
)
WITH (oids = false);
--
-- Definition for sequence profile_seq (OID = 16896) : 
--
CREATE SEQUENCE public.profile_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
--
-- Structure for table skill (OID = 16898) : 
--
CREATE TABLE public.skill (
    id bigint NOT NULL,
    id_profile bigint NOT NULL,
    category varchar(50) NOT NULL,
    value text NOT NULL,
    id_category bigint
)
WITH (oids = false);
--
-- Structure for table skill_category (OID = 16904) : 
--
CREATE TABLE public.skill_category (
    id bigint NOT NULL,
    category varchar(50) NOT NULL
)
WITH (oids = false);
--
-- Definition for sequence skill_seq (OID = 16907) : 
--
CREATE SEQUENCE public.skill_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
--
-- Definition for index course_idx (OID = 16942) : 
--
CREATE INDEX course_idx ON public.course USING btree (finish_date);
--
-- Definition for index course_idx1 (OID = 16943) : 
--
CREATE INDEX course_idx1 ON public.course USING btree (id_profile);
--
-- Definition for index education_idx (OID = 16944) : 
--
CREATE INDEX education_idx ON public.education USING btree (id_profile);
--
-- Definition for index education_idx1 (OID = 16945) : 
--
CREATE INDEX education_idx1 ON public.education USING btree (finish_year);
--
-- Definition for index language_idx (OID = 16947) : 
--
CREATE INDEX language_idx ON public.language USING btree (id_profile);
--
-- Definition for index practic_idx (OID = 16948) : 
--
CREATE INDEX practic_idx ON public.practic USING btree (id_profile);
--
-- Definition for index practic_idx1 (OID = 16949) : 
--
CREATE INDEX practic_idx1 ON public.practic USING btree (finish_date);
--
-- Definition for index skill_idx (OID = 16950) : 
--
CREATE INDEX skill_idx ON public.skill USING btree (id_profile);
--
-- Definition for index course_pkey (OID = 16911) : 
--
ALTER TABLE ONLY course
    ADD CONSTRAINT course_pkey
    PRIMARY KEY (id);
--
-- Definition for index education_pkey (OID = 16913) : 
--
ALTER TABLE ONLY education
    ADD CONSTRAINT education_pkey
    PRIMARY KEY (id);
--
-- Definition for index language_pkey (OID = 16917) : 
--
ALTER TABLE ONLY language
    ADD CONSTRAINT language_pkey
    PRIMARY KEY (id);
--
-- Definition for index practic_pkey (OID = 16921) : 
--
ALTER TABLE ONLY practic
    ADD CONSTRAINT practic_pkey
    PRIMARY KEY (id);
--
-- Definition for index profile_email_key (OID = 16923) : 
--
ALTER TABLE ONLY profile
    ADD CONSTRAINT profile_email_key
    UNIQUE (email);
--
-- Definition for index profile_phone_key (OID = 16925) : 
--
ALTER TABLE ONLY profile
    ADD CONSTRAINT profile_phone_key
    UNIQUE (phone);
--
-- Definition for index profile_pkey (OID = 16927) : 
--
ALTER TABLE ONLY profile
    ADD CONSTRAINT profile_pkey
    PRIMARY KEY (id);
--
-- Definition for index profile_uid_key (OID = 16933) : 
--
ALTER TABLE ONLY profile
    ADD CONSTRAINT profile_uid_key
    UNIQUE (uid);
--
-- Definition for index skill_category_category_key (OID = 16935) : 
--
ALTER TABLE ONLY skill_category
    ADD CONSTRAINT skill_category_category_key
    UNIQUE (category);
--
-- Definition for index skill_category_pkey (OID = 16937) : 
--
ALTER TABLE ONLY skill_category
    ADD CONSTRAINT skill_category_pkey
    PRIMARY KEY (id);
--
-- Definition for index skill_pkey (OID = 16939) : 
--
ALTER TABLE ONLY skill
    ADD CONSTRAINT skill_pkey
    PRIMARY KEY (id);
--
-- Definition for index course_fk (OID = 16956) : 
--
ALTER TABLE ONLY course
    ADD CONSTRAINT course_fk
    FOREIGN KEY (id_profile) REFERENCES profile(id) ON UPDATE CASCADE ON DELETE CASCADE;
--
-- Definition for index education_fk (OID = 16961) : 
--
ALTER TABLE ONLY education
    ADD CONSTRAINT education_fk
    FOREIGN KEY (id_profile) REFERENCES profile(id) ON UPDATE CASCADE ON DELETE CASCADE;
--
-- Definition for index language_fk (OID = 16971) : 
--
ALTER TABLE ONLY language
    ADD CONSTRAINT language_fk
    FOREIGN KEY (id_profile) REFERENCES profile(id) ON UPDATE CASCADE ON DELETE CASCADE;
--
-- Definition for index practic_fk (OID = 16981) : 
--
ALTER TABLE ONLY practic
    ADD CONSTRAINT practic_fk
    FOREIGN KEY (id_profile) REFERENCES profile(id) ON UPDATE CASCADE ON DELETE CASCADE;
--
-- Definition for index skill_fk (OID = 16991) : 
--
ALTER TABLE ONLY skill
    ADD CONSTRAINT skill_fk
    FOREIGN KEY (id_profile) REFERENCES profile(id) ON UPDATE CASCADE ON DELETE CASCADE;
--
-- Definition for index skill_fk1 (OID = 16996) : 
--
ALTER TABLE ONLY skill
    ADD CONSTRAINT skill_fk1
    FOREIGN KEY (id_category) REFERENCES skill_category(id) ON UPDATE CASCADE ON DELETE CASCADE;
--
-- Comments
--
COMMENT ON SCHEMA public IS 'standard public schema';
