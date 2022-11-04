package cloudladder.core.object;

import cloudladder.core.error.CLRuntimeError;
import com.jakewharton.fliptables.FlipTableConverters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CLTable extends CLObject {
    private List<List<CLObject>> data;
    private List<String> cols;
    private HashMap<String, Integer> colMap;

    public CLTable() {
        this.data = new ArrayList<>();
        this.cols = new ArrayList<>();
        this.colMap = new HashMap<>();
    }

    @Override
    public String getTypeIdentifier() {
        return "table";
    }

    @Override
    public String defaultStringify() {
        if (this.data.size() == 0) {
            return "table(empty)";
        }

        int m = this.data.size();
        int n = this.data.get(0).size();

        String[][] table = new String[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < this.data.get(i).size(); j++) {
                table[i][j] = this.data.get(i).get(j).defaultStringify();
            }
        }

        String[] headers = new String[this.cols.size()];
        for (int i = 0; i < this.cols.size(); i++) {
            headers[i] = this.cols.get(i);
        }

        return FlipTableConverters.fromObjects(headers, table);
    }

    public void clear() {
        this.data.clear();
        this.cols.clear();
        this.colMap.clear();
    }

    public void loadCsv(String csvString) {
        this.clear();

        List<String> rows = csvString.lines().toList();
        String headerLine = rows.get(0);

        int headerIndex = 0;
        for (String s: headerLine.split(",")) {
            this.cols.add(s);
            this.colMap.put(s, headerIndex);
            headerIndex++;
        }

        for (int i = 1; i < rows.size(); i++) {
            String dataLine = rows.get(i);
            ArrayList<CLObject> row = new ArrayList<>();
            for (String dataItem: dataLine.split(",")) {
                row.add(new CLString(dataItem));
            }
            this.data.add(row);
        }
    }

    public void addRow(List<CLObject> row) {
        this.data.add(row);
    }

    public CLArray getRow(int index) throws CLRuntimeError {
        CLArray ret = new CLArray();
        for (CLObject obj: this.data.get(index)) {
            ret.addItem(obj);
        }
        return ret;
    }

    public CLObject getCol(int index) {
        CLArray ret = new CLArray();
        for (List<CLObject> datum: this.data) {
            ret.addItem(datum.get(index));
        }
        return ret;
    }

    public CLObject getCol(String name) {
        int index = this.colMap.get(name);
        return this.getCol(index);
    }
}
