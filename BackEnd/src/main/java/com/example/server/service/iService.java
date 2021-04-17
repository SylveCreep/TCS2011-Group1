package com.example.server.service;

import antlr.collections.List;

public  interface iService<T,U> {
    T Save(U u);

    List findAll();

   // T findOneById(String id);

   // T findOneByCode(String code);

    T delete(U u);
}
