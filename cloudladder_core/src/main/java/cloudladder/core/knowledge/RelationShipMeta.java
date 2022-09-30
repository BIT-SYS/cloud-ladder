package cloudladder.core.knowledge;

import cloudladder.core.knowledge.nodes.*;

import java.util.ArrayList;

public class RelationShipMeta {
    public ArrayList<String> args;
    public ArrayList<String> rets;
    public String description;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (String a: this.args) {
            sb.append(a).append(", ");
        }
//        sb.deleteCharAt(sb.length() - 1);
        sb.append(") -> (");

        for (String ret: this.rets) {
            sb.append(ret).append(", ");
        }
//        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return new String(sb);
    }

    public EntityNode stringToEntityNode(String name) {
        return switch (name) {
            case "array" -> new ArrayNode();
            case "audio" -> new AudioNode();
            case "bool" -> new BoolNode();
            case "image" -> new ImageNode();
            case "object" -> new ObjectNode();
            case "string" -> new StringNode();
            default -> new UnknownNode();
        };
    }

    public RelationShip toRelationShip() {
        RelationShip relationShip = new RelationShip(this.description);
        for (String name: this.args) {
            relationShip.addFrom(this.stringToEntityNode(name));
        }
        for (String name: this.rets) {
            relationShip.addTo(this.stringToEntityNode(name));
        }
        return relationShip;
    }
}
