--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

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

SET default_table_access_method = heap;

--
-- Name: my_action; Type: TABLE; Schema: public; Owner: linker
--

CREATE TABLE public.my_action (
    id integer NOT NULL,
    time_stamp time with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    link_id integer NOT NULL,
    ip_of_user character varying NOT NULL,
    date_stamp date DEFAULT CURRENT_DATE
);


ALTER TABLE public.my_action OWNER TO linker;

--
-- Name: TABLE my_action; Type: COMMENT; Schema: public; Owner: linker
--

COMMENT ON TABLE public.my_action IS 'table for actions on links that belongs to nicelink.com service';


--
-- Name: my_action_id_seq; Type: SEQUENCE; Schema: public; Owner: linker
--

CREATE SEQUENCE public.my_action_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.my_action_id_seq OWNER TO linker;

--
-- Name: my_actions_id_seq; Type: SEQUENCE; Schema: public; Owner: linker
--

CREATE SEQUENCE public.my_actions_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.my_actions_id_seq OWNER TO linker;

--
-- Name: my_actions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: linker
--

ALTER SEQUENCE public.my_actions_id_seq OWNED BY public.my_action.id;


--
-- Name: my_link; Type: TABLE; Schema: public; Owner: linker
--

CREATE TABLE public.my_link (
    id integer NOT NULL,
    orig_link character varying NOT NULL,
    nice_link character varying NOT NULL,
    owner_id integer
);


ALTER TABLE public.my_link OWNER TO linker;

--
-- Name: TABLE my_link; Type: COMMENT; Schema: public; Owner: linker
--

COMMENT ON TABLE public.my_link IS 'table for links that belongs to nicelink.com service';


--
-- Name: my_link_id_seq; Type: SEQUENCE; Schema: public; Owner: linker
--

CREATE SEQUENCE public.my_link_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.my_link_id_seq OWNER TO linker;

--
-- Name: my_link_id_seq1; Type: SEQUENCE; Schema: public; Owner: linker
--

CREATE SEQUENCE public.my_link_id_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.my_link_id_seq1 OWNER TO linker;

--
-- Name: my_link_id_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: linker
--

ALTER SEQUENCE public.my_link_id_seq1 OWNED BY public.my_link.id;


--
-- Name: my_user; Type: TABLE; Schema: public; Owner: linker
--

CREATE TABLE public.my_user (
    id integer NOT NULL,
    username character varying(200) NOT NULL,
    password character varying(200) NOT NULL,
    email character varying(254),
    role character varying NOT NULL,
    level character varying NOT NULL
);


ALTER TABLE public.my_user OWNER TO linker;

--
-- Name: TABLE my_user; Type: COMMENT; Schema: public; Owner: linker
--

COMMENT ON TABLE public.my_user IS 'table for users nicelink.com service';


--
-- Name: my_user_id_seq; Type: SEQUENCE; Schema: public; Owner: linker
--

CREATE SEQUENCE public.my_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.my_user_id_seq OWNER TO linker;

--
-- Name: my_user_id_seq1; Type: SEQUENCE; Schema: public; Owner: linker
--

CREATE SEQUENCE public.my_user_id_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.my_user_id_seq1 OWNER TO linker;

--
-- Name: my_user_id_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: linker
--

ALTER SEQUENCE public.my_user_id_seq1 OWNED BY public.my_user.id;


--
-- Name: my_action id; Type: DEFAULT; Schema: public; Owner: linker
--

ALTER TABLE ONLY public.my_action ALTER COLUMN id SET DEFAULT nextval('public.my_actions_id_seq'::regclass);


--
-- Name: my_link id; Type: DEFAULT; Schema: public; Owner: linker
--

ALTER TABLE ONLY public.my_link ALTER COLUMN id SET DEFAULT nextval('public.my_link_id_seq1'::regclass);


--
-- Name: my_user id; Type: DEFAULT; Schema: public; Owner: linker
--

ALTER TABLE ONLY public.my_user ALTER COLUMN id SET DEFAULT nextval('public.my_user_id_seq1'::regclass);


--
-- Data for Name: my_action; Type: TABLE DATA; Schema: public; Owner: linker
--

COPY public.my_action (id, time_stamp, link_id, ip_of_user, date_stamp) FROM stdin;
8	13:33:24.542327+01	6	192.168.0.94	2024-12-04
9	13:45:31.299687+01	6	192.168.0.18	2024-12-04
11	13:45:45.061184+01	8	192.168.0.18	2024-12-04
15	00:21:28.485967+01	6	192.168.0.94	2024-12-05
16	00:32:07.570498+01	9	192.168.0.94	2024-12-05
22	01:05:41.378087+01	12	192.168.0.94	2024-12-05
23	01:06:02.553387+01	12	192.168.0.94	2024-12-05
\.


--
-- Data for Name: my_link; Type: TABLE DATA; Schema: public; Owner: linker
--

COPY public.my_link (id, orig_link, nice_link, owner_id) FROM stdin;
6	https://tproger.ru/articles/cqrs-dlya-chajnikov	/nl/cqrs	3
8	https://spring.io/guides/	/nl/spring	4
9	https://www.youtube.com/	/nl/yt	3
12	https://www.office.com/	/nl/of	8
\.


--
-- Data for Name: my_user; Type: TABLE DATA; Schema: public; Owner: linker
--

COPY public.my_user (id, username, password, email, role, level) FROM stdin;
3	test	$2a$10$h6xwFGQC9AGpezTVZezjkunwB1BVrwPURqfGOAGa71F7fg8okJ4Bq	test@gmail.com	USER	1
4	test1	$2a$10$laDGAX3wwVPn.pwGoUFpUergsRrOfhSc3PHt2JOHGUhP2nsMr41p2	test1@gmail.com	USER	1
8	spring	$2a$10$Zd3GtGXgpzM7utpsg57k2.eW/PPqB.L67S6OLvPpLV8h/RqtuRDFG	spr@gmail.com	USER	1
\.


--
-- Name: my_action_id_seq; Type: SEQUENCE SET; Schema: public; Owner: linker
--

SELECT pg_catalog.setval('public.my_action_id_seq', 1, false);


--
-- Name: my_actions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: linker
--

SELECT pg_catalog.setval('public.my_actions_id_seq', 23, true);


--
-- Name: my_link_id_seq; Type: SEQUENCE SET; Schema: public; Owner: linker
--

SELECT pg_catalog.setval('public.my_link_id_seq', 1, false);


--
-- Name: my_link_id_seq1; Type: SEQUENCE SET; Schema: public; Owner: linker
--

SELECT pg_catalog.setval('public.my_link_id_seq1', 12, true);


--
-- Name: my_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: linker
--

SELECT pg_catalog.setval('public.my_user_id_seq', 1, true);


--
-- Name: my_user_id_seq1; Type: SEQUENCE SET; Schema: public; Owner: linker
--

SELECT pg_catalog.setval('public.my_user_id_seq1', 8, true);


--
-- Name: my_action my_actions_pkey; Type: CONSTRAINT; Schema: public; Owner: linker
--

ALTER TABLE ONLY public.my_action
    ADD CONSTRAINT my_actions_pkey PRIMARY KEY (id);


--
-- Name: my_link my_link_pkey; Type: CONSTRAINT; Schema: public; Owner: linker
--

ALTER TABLE ONLY public.my_link
    ADD CONSTRAINT my_link_pkey PRIMARY KEY (id);


--
-- Name: my_user my_user_pkey; Type: CONSTRAINT; Schema: public; Owner: linker
--

ALTER TABLE ONLY public.my_user
    ADD CONSTRAINT my_user_pkey PRIMARY KEY (id);


--
-- Name: my_action fk_action_link; Type: FK CONSTRAINT; Schema: public; Owner: linker
--

ALTER TABLE ONLY public.my_action
    ADD CONSTRAINT fk_action_link FOREIGN KEY (link_id) REFERENCES public.my_link(id) ON DELETE CASCADE;


--
-- Name: my_link fk_link_user; Type: FK CONSTRAINT; Schema: public; Owner: linker
--

ALTER TABLE ONLY public.my_link
    ADD CONSTRAINT fk_link_user FOREIGN KEY (owner_id) REFERENCES public.my_user(id) ON DELETE CASCADE;


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: pg_database_owner
--

GRANT USAGE ON SCHEMA public TO linker;


--
-- Name: SEQUENCE my_action_id_seq; Type: ACL; Schema: public; Owner: linker
--

GRANT SELECT,USAGE ON SEQUENCE public.my_action_id_seq TO linker;


--
-- Name: SEQUENCE my_link_id_seq; Type: ACL; Schema: public; Owner: linker
--

GRANT SELECT,USAGE ON SEQUENCE public.my_link_id_seq TO linker;


--
-- Name: SEQUENCE my_user_id_seq; Type: ACL; Schema: public; Owner: linker
--

GRANT SELECT,USAGE ON SEQUENCE public.my_user_id_seq TO linker;


--
-- Name: DEFAULT PRIVILEGES FOR TABLES; Type: DEFAULT ACL; Schema: public; Owner: linker
--

ALTER DEFAULT PRIVILEGES FOR ROLE linker IN SCHEMA public GRANT SELECT ON TABLES TO linker;


--
-- PostgreSQL database dump complete
--

