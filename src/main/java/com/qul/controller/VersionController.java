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
        InputStream inputStream = classLoader.getResourceAsStream("version.txt");
        StringBuilder stringBuilder=new StringBuilder();
        float start=2.0f;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
            String s1 = br.readLine();
            if (!s.equals(s1)){
                start+=0.1f;
                File file=new File("src/main/resources/version.txt");
                FileOutputStream fileOutputStream=new FileOutputStream(file);
                fileOutputStream.write(s.getBytes("utf-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stringBuilder.append(start).append("+").append(s);
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
