package com.example.server.service;

import com.example.server.entity.BanList;

public interface BanService {
    String add(String token);

    String delete(String token);

    BanList find(String token);
}
