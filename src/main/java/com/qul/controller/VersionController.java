package com.qul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import java.io.*;

@Controller
public class VersionController {

    @RequestMapping("/version")
    public String get(){
        return "index";
    }

    @RequestMapping(value = "/getversion",method = RequestMethod.GET)
    @ResponseBody
    public String versionInformation() {
        String s = readGitProperties().split("\n")[2].split("=")[1];
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("git.commit.id.abbrev.txt");
        InputStream inputStream1 = classLoader.getResourceAsStream("version.txt");
        StringBuilder stringBuilder=new StringBuilder();
        String s2=null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
            String s1 = br.readLine();
            BufferedReader br1 = new BufferedReader(new InputStreamReader(inputStream1,"utf-8"));
            s2=br1.readLine();
            if (!s.equals(s1)){
                double v = Double.valueOf(s2) + 0.1;
                File file2=new File("src/main/resources/version.txt");
                if (file2.exists()){
                    file2.createNewFile();
                }
                FileOutputStream fileOutputStream2=new FileOutputStream(file2);
                fileOutputStream2.write((v+"").getBytes("utf-8"));
                File file=new File("src/main/resources/git.commit.id.abbrev.txt");
                if (file.exists()){
                    file.createNewFile();
                }
                FileOutputStream fileOutputStream=new FileOutputStream(file);
                fileOutputStream.write(s.getBytes("utf-8"));
                stringBuilder.append(v).append("+").append(s);
            } else {
                stringBuilder.append(s2).append("+").append(s);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private String readGitProperties() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("git.properties");
        try {
            return readFromInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return "Version information could not be retrieved";
        }
    }
    private static String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"utf-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
