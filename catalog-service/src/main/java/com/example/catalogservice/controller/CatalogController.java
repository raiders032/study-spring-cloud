package com.example.catalogservice.controller;

import com.example.catalogservice.entity.Catalog;
import com.example.catalogservice.service.CatalogService;
import com.example.catalogservice.vo.ResponseCatalog;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/catalog-service")
@RestController
public class CatalogController {

    private final CatalogService catalogService;
    private final Environment environment;
    private final ModelMapper modelMapper;

    @GetMapping("/health-check")
    public String status() {
        return String.format("It's Working in Catalog Service on PORT %s", environment.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> getCatalogs(){
        Iterable<Catalog> allCatalogs = catalogService.getAllCatalogs();
        List<ResponseCatalog> result = new ArrayList<>();

        allCatalogs.forEach(catalog -> result.add(modelMapper.map(catalog, ResponseCatalog.class)));
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }
}
