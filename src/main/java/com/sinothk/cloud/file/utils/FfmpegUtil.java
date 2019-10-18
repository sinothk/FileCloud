package com.sinothk.cloud.file.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FfmpegUtil {

    /**
     * java调用FFmpeg截取视频做封面入口
     *
     * @param videoPath  视频路径
     * @param ffmpegPath FFmpeg安装路径（Windows使用）
     * @param coverPath  封面存储路径
     */
    public static void processImgMain(String videoPath, String ffmpegPath, String coverPath) {
//        if(ProjectConfig.ENV.equals(ProjectConfig.PROJECT_DEVLELOP)){//此处判断是否Windows，自己更改一下呗
//            processImg(videoPath,ffmpegPath,coverPath);
//        }else {
//            String command = "/monchickey/ffmpeg/bin/./ffmpeg -ss 00:00:00 -i " + videoPath
//                    + " -f image2 -y " + coverPath;
//            processImgCmd(command);
//        }
    }

    /**
     * 截取视频封面 java  Windows版本
     *
     * @param videoPath  视频路径
     * @param ffmpegPath ffmpeg.exe存放路径  截取工具
     * @param imgPath    封面存放路径
     * @return
     */
    public static boolean processImg(String videoPath, String ffmpegPath, String imgPath) {
        File file = new File(videoPath);
        if (!file.exists()) {
            System.err.println("路径[" + videoPath + "]对应的视频文件不存在!");
            return false;
        }
        List<String> commands = new ArrayList<String>();
        commands.add(ffmpegPath);
        commands.add("-i");
        commands.add(videoPath);
        commands.add("-y");
        commands.add("-f");
        commands.add("image2");
        commands.add("-ss");
        commands.add("0");//这个参数是设置截取视频多少秒时的画面
        //commands.add("-t");
        //commands.add("0.001");
        commands.add("-s");
        commands.add("700x525");
        commands.add(imgPath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            builder.start();
            System.out.println("截取成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * FFmpeg截取视频封面Linux版本
     *
     * @param command 执行cmd命令
     */
    public static void processImgCmd(String command) {
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(command);
            InputStream stderr = proc.getErrorStream();
            InputStreamReader isr = new InputStreamReader(stderr);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            System.out.println("<ERROR>");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("</ERROR>");
            int exitVal = proc.waitFor();
            System.out.println("Process exitValue: " + exitVal);
        } catch (Throwable t) {
            System.out.println(t);
            t.printStackTrace();
        }
    }
}