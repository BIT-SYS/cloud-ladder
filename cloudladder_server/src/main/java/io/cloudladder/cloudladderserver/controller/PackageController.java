package io.cloudladder.cloudladderserver.controller;

import cloudladder.core.knowledge.RelationShipMeta;
import io.cloudladder.cloudladderserver.config.PackageConfig;
import io.cloudladder.cloudladderserver.knowledge.KnowledgeGraphBuilder;
import org.checkerframework.checker.units.qual.K;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

@RestController
@RequestMapping("/package")
public class PackageController {
    @Autowired
    private PackageConfig packageConfig;

    @GetMapping("/hello")
    public String hello() {
        return "hello from package controller";
    }

    @GetMapping("/{packageName}/{version}")
    public ResponseEntity<Resource> getPackage(@PathVariable String packageName, @PathVariable String version) throws IOException {
        if (version == null || version.isEmpty()) {
            version = "latest";
        }

        String filename = packageName + "-" + version + ".jar";
        System.out.println(filename);

        Path path = Path.of(this.packageConfig.getDir()).resolve(filename);
        File file = path.toFile();
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=" + filename)
                .body(new ByteArrayResource(Files.readAllBytes(path)));
    }

    @GetMapping("/relation-meta")
    public ArrayList<RelationShipMeta> getRelationMeta() {
        KnowledgeGraphBuilder builder = new KnowledgeGraphBuilder(this.packageConfig.getDir());
        ArrayList<RelationShipMeta> meta = builder.getAllRelationShipMeta();

        return meta;
    }
}
