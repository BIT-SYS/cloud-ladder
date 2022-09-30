package io.cloudladder.cloudladderserver;


import io.cloudladder.cloudladderserver.knowledge.KnowledgeGraphBuilder;

public class MainTest {
    public static void main(String[] args) {
        KnowledgeGraphBuilder builder = new KnowledgeGraphBuilder("F:\\upload");
        builder.getAllRelationShipMeta();
    }
}
