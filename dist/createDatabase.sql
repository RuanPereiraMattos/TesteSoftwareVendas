drop database mydatabase;

create database if not exists mydatabase default charset utf8mb4;

use mydatabase;

create table if not exists clientes(
id int auto_increment,
nome varchar(255),
telefone varchar(255),
cpf varchar(255),
primary key(id)) default charset utf8mb4;

create table if not exists produtos(
id int auto_increment,
nome varchar(255),
descricao varchar(255),
valor float,
primary key(id)) default charset utf8mb4;

create table if not exists estoque(
id int auto_increment,
id_produto int,
quantidade int,
primary key(id),
foreign key (id_produto) references produtos (id)) default charset utf8mb4;

create table if not exists vendas(
id int auto_increment,
id_cliente int,
data_venda timestamp,
primary key(id),
foreign key(id_cliente) references clientes (id)) default charset utf8mb4;

create table if not exists metodos_pagamento(
id int auto_increment,
id_venda int,
metodo int,#tipo
vezes int,
valor float,
troco float,
primary key(id),
foreign key(id_venda) references vendas (id)) default charset utf8mb4;

create table if not exists produtos_vendidos(
id int auto_increment,
id_venda int,
id_produto int,
nome varchar(255),
descricao varchar(255),
valor float,
quantidade int,
primary key(id),
foreign key(id_venda) references vendas (id),
foreign key(id_produto) references produtos (id)) default charset utf8mb4;

insert into clientes (nome) value ("Ruan Mattos");

insert into produto (nome, descricao, valor) value ("Produto 1", "Descrição 1", 10.0);