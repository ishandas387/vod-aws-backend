--
-- PostgreSQL database dump
--

-- Dumped from database version 11.6
-- Dumped by pg_dump version 12.3

-- Started on 2020-06-27 23:34:38

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

--
-- TOC entry 196 (class 1259 OID 16425)
-- Name: chapters; Type: TABLE; Schema: public; Owner: postgresdev
--

CREATE TABLE public.chapters (
    chapter character varying(50) NOT NULL,
    description character varying(200),
    id integer NOT NULL,
    thumbnail character varying,
    module_id integer
);


ALTER TABLE public.chapters OWNER TO postgresdev;

--
-- TOC entry 199 (class 1259 OID 16473)
-- Name: CHAPTERS_ID_seq; Type: SEQUENCE; Schema: public; Owner: postgresdev
--

ALTER TABLE public.chapters ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."CHAPTERS_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 5000
    CACHE 1
);


--
-- TOC entry 197 (class 1259 OID 16448)
-- Name: lessons; Type: TABLE; Schema: public; Owner: postgresdev
--

CREATE TABLE public.lessons (
    lesson character varying NOT NULL,
    description character varying,
    id integer NOT NULL,
    chapter_id integer,
    thumbnail character varying,
    url character varying
);


ALTER TABLE public.lessons OWNER TO postgresdev;

--
-- TOC entry 198 (class 1259 OID 16462)
-- Name: LESSONS_ID_seq; Type: SEQUENCE; Schema: public; Owner: postgresdev
--

ALTER TABLE public.lessons ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."LESSONS_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 5000
    CACHE 1
    CYCLE
);


--
-- TOC entry 201 (class 1259 OID 16493)
-- Name: modules; Type: TABLE; Schema: public; Owner: postgresdev
--

CREATE TABLE public.modules (
    id integer NOT NULL,
    module_name character varying NOT NULL,
    description character varying
);


ALTER TABLE public.modules OWNER TO postgresdev;

--
-- TOC entry 200 (class 1259 OID 16491)
-- Name: MODULES_ID_seq; Type: SEQUENCE; Schema: public; Owner: postgresdev
--

ALTER TABLE public.modules ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public."MODULES_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 5000
    CACHE 1
);


--
-- TOC entry 203 (class 1259 OID 24670)
-- Name: customer; Type: TABLE; Schema: public; Owner: postgresdev
--

CREATE TABLE public.customer (
    id bigint NOT NULL,
    firstname character varying(255),
    lastname character varying(255)
);


ALTER TABLE public.customer OWNER TO postgresdev;

--
-- TOC entry 202 (class 1259 OID 24668)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgresdev
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgresdev;

--
-- TOC entry 3712 (class 2606 OID 16500)
-- Name: modules MODULES_pkey; Type: CONSTRAINT; Schema: public; Owner: postgresdev
--

ALTER TABLE ONLY public.modules
    ADD CONSTRAINT "MODULES_pkey" PRIMARY KEY (id);


--
-- TOC entry 3704 (class 2606 OID 16481)
-- Name: chapters chapter; Type: CONSTRAINT; Schema: public; Owner: postgresdev
--

ALTER TABLE ONLY public.chapters
    ADD CONSTRAINT chapter PRIMARY KEY (id);


--
-- TOC entry 3714 (class 2606 OID 24677)
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgresdev
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);


--
-- TOC entry 3710 (class 2606 OID 16472)
-- Name: lessons lessonid; Type: CONSTRAINT; Schema: public; Owner: postgresdev
--

ALTER TABLE ONLY public.lessons
    ADD CONSTRAINT lessonid PRIMARY KEY (id);


--
-- TOC entry 3707 (class 2606 OID 16479)
-- Name: chapters unique; Type: CONSTRAINT; Schema: public; Owner: postgresdev
--

ALTER TABLE ONLY public.chapters
    ADD CONSTRAINT "unique" UNIQUE (id);


--
-- TOC entry 3708 (class 1259 OID 16487)
-- Name: fki_chapter; Type: INDEX; Schema: public; Owner: postgresdev
--

CREATE INDEX fki_chapter ON public.lessons USING btree (chapter_id);


--
-- TOC entry 3705 (class 1259 OID 16506)
-- Name: fki_module; Type: INDEX; Schema: public; Owner: postgresdev
--

CREATE INDEX fki_module ON public.chapters USING btree (module_id);


--
-- TOC entry 3716 (class 2606 OID 16482)
-- Name: lessons chapter; Type: FK CONSTRAINT; Schema: public; Owner: postgresdev
--

ALTER TABLE ONLY public.lessons
    ADD CONSTRAINT chapter FOREIGN KEY (chapter_id) REFERENCES public.chapters(id) NOT VALID;


--
-- TOC entry 3715 (class 2606 OID 16501)
-- Name: chapters module; Type: FK CONSTRAINT; Schema: public; Owner: postgresdev
--

ALTER TABLE ONLY public.chapters
    ADD CONSTRAINT module FOREIGN KEY (module_id) REFERENCES public.modules(id) ON DELETE CASCADE NOT VALID;


--
-- TOC entry 3843 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgresdev
--

REVOKE ALL ON SCHEMA public FROM rdsadmin;
REVOKE ALL ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO postgresdev;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2020-06-27 23:34:46

--
-- PostgreSQL database dump complete
--
