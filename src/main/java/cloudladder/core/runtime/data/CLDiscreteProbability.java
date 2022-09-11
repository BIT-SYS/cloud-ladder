//package cloudladder.core.runtime.data;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//
//import java.util.ArrayList;
//import java.util.Comparator;
//
//public class CLDiscreteProbability extends CLData {
//    @AllArgsConstructor
//    private static class Entry {
//        private final double probability;
//        private final CLData item;
//    }
//
//    private ArrayList<Entry> items;
//    private boolean isSorted;
//
//    private void sort() {
//        if (this.isSorted) {
//            return;
//        }
//        this.items.sort(new Comparator<Entry>() {
//            @Override
//            public int compare(Entry o1, Entry o2) {
//                return Double.compare(o2.probability, o1.probability);
//            }
//        });
//        this.isSorted = true;
//    }
//
//    public CLDiscreteProbability() {
//        this.items = new ArrayList<>();
//    }
//
//    public void addEntry(double prob, CLData value) {
//        this.items.add(new Entry(prob, value));
//    }
//
//    public CLData getMax() {
//        this.sort();
//
//        return this.items.get(0).item;
//    }
//
//    public ArrayList<CLData> topN(int n) {
//        this.sort();
//
//        ArrayList<CLData> temp = new ArrayList<>();
//        n = Math.min(this.items.size(), n);
//        for (int i = 0; i < n; i++) {
//            temp.add(this.items.get(i).item);
//        }
//
//        return temp;
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("[");
//        for (int i = 0; i < items.size(); i++) {
//            Entry item = items.get(i);
//            sb
//                    .append(item.probability)
//                    .append("->")
//                    .append(item.item.toString())
//                    ;
//            if (i < items.size() - 1) {
//                sb.append(",");
//            }
//        }
//        sb.append("]");
//        return new String(sb);
//    }
//}
