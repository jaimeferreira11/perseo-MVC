-- Run this with psql -U YourUser -f create_database.sql
CREATE USER "perseo" WITH ENCRYPTED PASSWORD 'perseo';
ALTER ROLE "perseo" WITH createdb;
CREATE database "perseo";

