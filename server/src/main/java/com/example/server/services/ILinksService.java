package com.example.server.services;

import com.example.server.models.Link;
import com.example.server.utils.dtos.CreateLinkDTO;

import java.util.List;

public interface ILinksService {

    List<Link> all();

    Link create(CreateLinkDTO link);
     Link findById(Long linkId);
}
