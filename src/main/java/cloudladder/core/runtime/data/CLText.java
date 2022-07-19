package cloudladder.core.runtime.data;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizers.ChineseWordTokenizer;
import com.kennycason.kumo.palette.LinearGradientColorPalette;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CLText extends CLData {
    private int len;

    private String sourcePath;//source file

    private String targetPath;//for save
    private List<String> paragraph = new ArrayList<String>();

    //private List<String> priority = new ArrayList<String>();

    //private List<String> comment = new ArrayList<String>();

    public CLText() {
        len = 0;
        sourcePath = null;
        targetPath = null;
        paragraph.clear();
    }

    public CLText(String sp) {
        sourcePath = sp;
        targetPath = null;
        paragraph.clear();

        String[] tmp = sp.split("\\.");

        if(tmp.length == 0){
            System.out.println("wrong file format");
        }

        if(tmp[tmp.length-1].equals("docx")){
            OPCPackage opcPackage = null;
            String content = null;
            try {
                opcPackage = POIXMLDocument.openPackage(sourcePath);
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
                input = new FileInputStream(new File(sourcePath));
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
            Path tmppath = Paths.get(sourcePath);
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
        else if(tmp[tmp.length-1].equals("pdf")){
            File file = new File(sourcePath);
            PDDocument doc = null;
            try {
                doc = Loader.loadPDF(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(doc == null){//vacuum
                len = 0;
            }
            else{
                int pages = doc.getNumberOfPages();
                String text = "";
                for(int i = 0 ; i < pages ; ++i){
                    AccessPermission ap = doc.getCurrentAccessPermission();
                    if(!ap.canExtractContent()){
                        String reminder = "\ncan't get the access of the "+String.valueOf(i)+"th pages\n";
                        System.out.println(reminder);
                        text = text+reminder;
                    }
                    PDFTextStripper stripper = null;
                    try {
                        stripper = new PDFTextStripper();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    stripper.setSortByPosition(true);
                    stripper.setStartPage(i+1);
                    stripper.setEndPage(i+1);
                    try {
                        text = text + stripper.getText(doc);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    tmp = text.split("(\\r\\n)|(\\r)|(\\n)");
                    for(int j = 0;j < tmp.length - 1;j++){
                        paragraph.add(tmp[j]);
                    }
                    text = tmp[tmp.length - 1];
                }
                paragraph.add(text);
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

    public void setSourcePath(String apath){ sourcePath = apath; }

    public void setTargetPath(String apath){ targetPath = apath; }
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

    public void createWordCloud(String path){

        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        frequencyAnalyzer.setMinWordLength(2);

        frequencyAnalyzer.setWordTokenizer(new ChineseWordTokenizer());
        List<WordFrequency> wordFrequencyList;
        this.save();
        if(targetPath != null){
            try {
                wordFrequencyList = frequencyAnalyzer.load(targetPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            if(sourcePath != null) {
                try {
                    String[] tmp = sourcePath.split("\\.");
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < tmp.length - 1; ++i) {
                        sb.append(tmp[i]);
                    }
                    sb.append(".txt");
                    wordFrequencyList = frequencyAnalyzer.load(new String(sb));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                return;
            }
        }
        Dimension dimension = new Dimension(1920,1080);
        WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        java.awt.Font font = new java.awt.Font("STSong-Light", 2, 20);
        wordCloud.setColorPalette(new LinearGradientColorPalette(Color.RED, Color.BLUE, Color.GREEN, 30, 30));
        wordCloud.setKumoFont(new KumoFont(font));
        wordCloud.setBackgroundColor(new Color(255,255,255));
        wordCloud.setBackground(new CircleBackground(255));
        wordCloud.setFontScalar(new SqrtFontScalar(12, 45));
        wordCloud.build(wordFrequencyList);
        wordCloud.writeToFile(path);
    }

    public void save(Path path){//以txt的方式存储
        BufferedWriter writer = null;

        String[] p = path.toString().split("\\.");
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<p.length - 1; ++i){
            sb.append(p[i]);
        }
        sb.append(".txt");
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

    public void save(){
        if(sourcePath == null){
            System.out.println("ERROR:CLText have nothing to save.");
        }
        else{
            if(targetPath == null){
                String[] tmp = sourcePath.split("\\.");
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i<tmp.length - 1; ++i){
                    sb.append(tmp[i]);
                }
                sb.append(".txt");
                Path p = Paths.get(new String(sb));
                this.save(p);
            }
            else{
                Path p = Paths.get(targetPath);
                this.save(p);
            }
        }
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Text[");
        sb.append(paragraph.get(0));
        for (int i = 1; i < paragraph.size(); i++) {
            sb.append(paragraph.get(i));
        }
        sb.append("]");
        return new String(sb);
    }
}
