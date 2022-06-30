package cloudladder.core.runtime.data;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CLText extends CLData {
    private int len;

    private String path;

    private List<String> paragraph = new ArrayList<String>();

    //private List<String> priority = new ArrayList<String>();

    //private List<String> comment = new ArrayList<String>();

    public CLText() {
        len = 0;
        path = null;
        paragraph.clear();
    }

    public CLText(String p) {
        path = p;
        paragraph.clear();

        String[] tmp = p.split("\\.");

        if(tmp.length == 0){
            System.out.println("wrong file format");
        }

        if(tmp[tmp.length-1].equals("docx")){
            OPCPackage opcPackage = null;
            String content = null;
            try {
                opcPackage = POIXMLDocument.openPackage(path);
                XWPFDocument xwpf = new XWPFDocument(opcPackage);
                POIXMLTextExtractor poiText = new XWPFWordExtractor(xwpf);

                content = poiText.getText();
                len = content.length();
                String[] para = content.split("\\n");
                for(String apara: para){
                    paragraph.add(apara);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(tmp[tmp.length-1].equals("doc")){
            String content=null;
            InputStream input = null;
            try {
                input = new FileInputStream(new File(path));
                WordExtractor wex = new WordExtractor(input);
                content = wex.getText();
                len = content.length();
                String[] para = content.split("\\r\\n");
                for(String apara: para){
                    paragraph.add(apara);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(tmp[tmp.length-1].equals("txt")){
            Path tmppath = Paths.get(path);
            try {
                byte[] bytes = Files.readAllBytes(tmppath);
                paragraph = Files.readAllLines(tmppath, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            len = 0;
            for(String apara: paragraph){
                len += apara.length();
            }
        }
        else{
            System.out.println("undefined file format");
        }
    }

    public String getParagraph(int index){
        if(paragraph.size()>index){
            return paragraph.get(index);
        }
        else{
            return "";
        }
    }

    public int getlen(){ return len; }

    public int getParagraphNum(){ return paragraph.size(); }

    public void setpath(String apath){ path = apath; }

    public String getContent(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < getParagraphNum(); ++i){
            sb.append(paragraph.get(i));
        }
        return new String(sb);
    }

    public void insertParagraph(int index, String aparagraph){
        if(paragraph.size()>=index){
            paragraph.add(index, aparagraph);
        }
        else{
            paragraph.add(paragraph.size(), aparagraph);
        }
    }

    public void removeParagraph(int index){
        if(paragraph.size()>index){
            paragraph.remove(index);
        }
    }

    public void save(Path path){//以txt的方式存储
        BufferedWriter writer = null;

        String[] p = path.toString().split("\\.");
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<p.length - 1; ++i){
            sb.append(p[i]);
        }
        sb.append("txt");
        File file = new File(new String(sb));
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false), "UTF-8"));
            for(int i = 0;i <paragraph.size();++i){
                writer.write(paragraph.get(i));
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Text[");
        sb.append(paragraph.get(0));
        for (int i = 1; i < paragraph.size(); i++) {
            sb.append("\n").append(paragraph.get(i));
        }
        sb.append("]");
        return new String(sb);
    }
}
