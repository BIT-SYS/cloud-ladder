package cloudladder.core.knowledge;

import cloudladder.core.knowledge.nodes.*;
import cloudladder.core.knowledge.relations.FilterRelation;
import cloudladder.core.knowledge.relations.MapperRelation;
import cloudladder.core.knowledge.relations.PredicateRelation;

import java.util.ArrayList;

public class KnowledgeGraph {
    public ArrayList<EntityNode> entities;
    public ArrayList<RelationShip> relations;

    public KnowledgeGraph() {
        this.entities = new ArrayList<>();
        this.relations = new ArrayList<>();

        this.addEntity(new ImageNode());
        this.addEntity(new AudioNode());
        this.addEntity(new StringNode());
        this.addEntity(new BoolNode());
        this.addEntity(new ObjectNode());

        this.addRelation(new PredicateRelation());
        this.addRelation(new FilterRelation());
        this.addRelation(new MapperRelation());
    }

    public void addEntity(EntityNode node) {
        this.entities.add(node);
    }

    public void addRelation(RelationShip relationShip) {
        this.relations.add(relationShip);
    }

    public ArrayList<RelationShip> matchInterface(ArrayList<Class> args, ArrayList<Class> rets) {
        ArrayList<RelationShip> results = new ArrayList<>();
        for (RelationShip r : this.relations) {
            if (r.matchInterface(args, rets)) {
                results.add(r);
            }
        }

        return results;
    }
}
