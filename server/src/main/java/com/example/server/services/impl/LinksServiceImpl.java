package com.example.server.services.impl;

import com.example.server.models.Link;
import com.example.server.repositories.ILinksRepository;
import com.example.server.services.ILinksService;
import com.example.server.utils.dtos.CreateLinkDTO;
import org.apache.commons.validator.routines.UrlValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinksServiceImpl implements ILinksService {

    private final ILinksRepository linksRepository;

    @Autowired
    public LinksServiceImpl(ILinksRepository linksRepository) {
        this.linksRepository = linksRepository;
    }

    @Override
    public List<Link> all() {
        return linksRepository.findAll();
    }

    @Override
    public Link create(CreateLinkDTO link) {
        Link linkInfo = new Link(link.getUrl());
        if (urlValidator(link.getUrl())) {
            return linksRepository.save(linkInfo);
        }
        else {
            throw new RuntimeException("Url is invalid");
        }
    }

    public static boolean urlValidator(String url)
    {
        // Get an `UrlValidator` using default schemes
        UrlValidator defaultValidator = new UrlValidator();
        return defaultValidator.isValid(url);
    }

    @Override
    public Link findById(Long linkId) {
        return linksRepository.findById(linkId).orElseThrow(() -> new RuntimeException("link not found"));
    }
}
