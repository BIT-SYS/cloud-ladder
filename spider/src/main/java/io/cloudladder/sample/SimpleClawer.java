package io.cloudladder.sample;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.object.*;
import cloudladder.core.runtime.CLRtFrame;
import cloudladder.core.vm.CLVM;
import cloudladder.std.CLBuiltinFuncAnnotation;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleClawer {

    @CLBuiltinFuncAnnotation(params = {"movie_num", "remark_num"}, name = "imdb_claw")
    public static CLObject __imdb_crawl__(CLRtFrame frame) throws CLRuntimeError {
        CLObject maymovie_num = frame.scope.getOwnVariable("movie_num");
        CLObject mayremark_num = frame.scope.getOwnVariable("remark_num");

        if (!(maymovie_num instanceof CLNumber movie_num)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting number, got `" + maymovie_num.getTypeIdentifier() + "`");
        }
        if (!(mayremark_num instanceof CLNumber remark_num)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting number, got `" + mayremark_num.getTypeIdentifier() + "`");
        }

        if (!movie_num.isInteger()) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting int, found double");
        }
        if (!remark_num.isInteger()) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting int, found double");
        }

        CLMapArray mapArray = new CLMapArray();

        String[] s1 = {
                "1,0114709,862",
                "2,0113497,8844",
                "3,0113228,15602",
                "4,0114885,31357",
                "5,0113041,11862",
                "6,0113277,949",
                "7,0114319,11860",
                "8,0112302,45325",
                "9,0114576,9091",
                "10,0113189,710",
                "11,0112346,9087",
                "12,0112896,12110",
                "13,0112453,21032",
                "14,0113987,10858",
                "15,0112760,1408",
                "16,0112641,524",
                "17,0114388,4584",
                "18,0113101,5",
                "19,0112281,9273",
                "20,0113845,11517"};
        String[] s2 = {
                "1,Toy Story (1995),Adventure|Animation|Children|Comedy|Fantasy",
                "2,Jumanji (1995),Adventure|Children|Fantasy",
                "3,Grumpier Old Men (1995),Comedy|Romance",
                "4,Waiting to Exhale (1995),Comedy|Drama|Romance",
                "5,Father of the Bride Part II (1995),Comedy",
                "6,Heat (1995),Action|Crime|Thriller",
                "7,Sabrina (1995),Comedy|Romance",
                "8,Tom and Huck (1995),Adventure|Children",
                "9,Sudden Death (1995),Action",
                "10,GoldenEye (1995),Action|Adventure|Thriller",
                "11,\"American President, The (1995)\",Comedy|Drama|Romance",
                "12,Dracula: Dead and Loving It (1995),Comedy|Horror",
                "13,Balto (1995),Adventure|Animation|Children",
                "14,Nixon (1995),Drama",
                "15,Cutthroat Island (1995),Action|Adventure|Romance",
                "16,Casino (1995),Crime|Drama",
                "17,Sense and Sensibility (1995),Drama|Romance",
                "18,Four Rooms (1995),Comedy",
                "19,Ace Ventura: When Nature Calls (1995),Comedy",
                "20,Money Train (1995),Action|Comedy|Crime|Drama|Thriller"
        };
        String[] s_spl1;
        String[] s_spl2;

        int i = 0;
        while (i < Math.min(s1.length, movie_num.getAsInt())) {
            CLArray list = new CLArray();
            s_spl1 = s1[i].split(",");
            s_spl2 = s2[i].split(",");
            i++;

            if (s_spl1[0].equals(s_spl2[0]) && !s_spl1[0].equals("movieId")) {
                try {
                    URL url;
                    HttpURLConnection connection;
                    url = new URL("https://www.imdb.com/title/tt" + s_spl1[1] + "/reviews?ref_=tt_urv");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

//                    BufferedReader read = new BufferedReader(
//                            new InputStreamReader(connection.getInputStream()));
                    if (connection.getResponseCode() == 200) {
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(connection.getInputStream()));
                        System.out.println("decoding...");
                        String main_regex = "<div class=\"text show-more__control\">[^<]+?</div>";
                        Pattern p = Pattern.compile(main_regex);
                        String line = null;
                        Matcher matcher = null;
                        while ((line = reader.readLine()) != null) {
                            matcher = p.matcher(line);
                            if (matcher.find()) {
                                list.addItem(new CLString(matcher.group().replace("<div class=\"text show-more__control\">", "").replace("</div>", "")));
                                if (list.items.size() >= remark_num.getAsInt()) {
                                    break;
                                }
                            }
                        }
                        mapArray.data.put(s_spl2[1], list);
                    } else {
                        System.out.println("error!");
//                        throw new CLRuntimeError(CLRuntimeErrorType.Unexpected, "parse result error");
                        // ignore this movie
                    }
                } catch (MalformedURLException e) {
                    throw new CLRuntimeError(CLRuntimeErrorType.Unexpected, "malformed url");
                } catch (IOException e) {
                    throw new CLRuntimeError(CLRuntimeErrorType.NetworkError, "open connection failed");
                }
            }
        }
        return mapArray;
    }

    public static void main(String[] args) throws CLRuntimeError {
        System.out.println("start...");

        CLVM vm = new CLVM(Paths.get("c:"));
        CLRtFrame frame = new CLRtFrame(vm, null, null, "");
        frame.scope.addVariable("movie_num", CLNumber.getNumberObject(1));
        frame.scope.addVariable("remark_num", CLNumber.getNumberObject(10));
        CLObject ret = __imdb_crawl__(frame);
        System.out.println(ret.defaultStringify());

        System.out.println("end...");
    }

    public static void Claw() throws IOException {
        List<String> Movie = new ArrayList<>();


        BufferedReader f1 = new BufferedReader(new FileReader(".\\src\\main\\resources\\links.csv"));
        BufferedReader f2 = new BufferedReader(new FileReader(".\\src\\main\\resources\\movies.csv"));
        OutputStream os = new FileOutputStream(".\\src\\main\\resources\\test.txt");

        String s1;
        String s2;
        String[] s_spl1;
        String[] s_spl2;

        while (true) {//every time find a line in f1 then find a line in f2
            Movie.clear();
            s1 = f1.readLine();
            if (s1 == null) {
                break;
            }

            s2 = f2.readLine();
            if (s2 == null) {
                break;
            }

            s_spl1 = s1.split(",");
            s_spl2 = s2.split(",");
            if (s_spl1[0].equals(s_spl2[0]) && !s_spl1[0].equals("movieId")) {
                Movie.add(s_spl2[1]);
                for (String remark : getMainByWeb(s_spl1[1])) {
                    Movie.add(remark);
                }
                for (int i = 0; i < Movie.size(); i++) {
                    os.write(Movie.get(i).getBytes(StandardCharsets.UTF_8));
                    os.write("$\n".getBytes(StandardCharsets.UTF_8));
                }
                os.write("$$\n\n".getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    public static List<String> getMainByWeb(String imdbid) throws IOException {
        URL url = new URL("https://www.imdb.com/title/tt" + imdbid + "/reviews?ref_=tt_urv");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        List<String> list = new ArrayList<String>();

        BufferedReader read = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));

        if (connection.getResponseCode() == 200) {

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            System.out.println("decoding...");

            String main_regex = "<div class=\"text show-more__control\">[^<]+?</div>";
            Pattern p = Pattern.compile(main_regex);
            String line = null;
            Matcher matcher = null;
            while ((line = reader.readLine()) != null) {
                matcher = p.matcher(line);
                if (matcher.find()) {
                    list.add(matcher.group().replace("<div class=\"text show-more__control\">", "").replace("</div>", "").replace("&#39;", "'").replace("&quot;", "\""));
                }
            }
        }
        return list;
    }
}