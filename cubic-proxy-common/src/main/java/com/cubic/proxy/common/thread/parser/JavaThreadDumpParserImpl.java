package com.cubic.proxy.common.thread.parser;


import com.cubic.proxy.common.thread.LockInfo;
import com.cubic.proxy.common.thread.ThreadInfo;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaThreadDumpParserImpl implements  JavaThreadDumpParser {

    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");


   private static  Pattern namePattern = Pattern.compile("^\"(.*)\".*prio=[0-9]+ tid=(\\w*) nid=(\\w*)\\s\\w*");
    private static Pattern statePattern = Pattern.compile("\\s+java.lang.Thread.State: (.*)");
    private static Pattern lockWaitPattern = Pattern.compile("\\s+- parking to wait for\\s+<(.*)>\\s+\\(.*\\)");
    private static Pattern lockedPattern = Pattern.compile("\\s+- locked\\s+<(.*)>\\s+\\(.*\\)");


//    public static void main(String[] args) {
////        if (args.length > 0) {
//
//            parser.JavaThreadDumpParser javaThreadDumpParser = new JavaThreadDumpParserImpl();
//            javaThreadDumpParser.parseJavaThreadDumpFile("/Users/luqiang/Documents/github/ThreadDumpAnalyzer/src/test/resource/TestThreadDump.txt");
////        } else {
////            System.err.println("USAGE: JavaThreadDumpParserImpl <File name with path>");
////        }
//    }


    public void parseJavaThreadDumpFile(String fullpathFileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fullpathFileName))) {

            ThreadInfo threadInfo = null;
            String dateTime = null;
            String timeStamp = "";
            String tid = "";
            String nid = "";
            String state = "";
            String rawData = "";
            String humanReadableDate = "";

            String line = null;

            ArrayList<ThreadInfo> threadList = new ArrayList<>();
            ArrayList<LockInfo> lockedList = new ArrayList<>();


            boolean isFirstLine = true;
            while ((line = bufferedReader.readLine()) != null) {

                if (line.trim().length() > 0) {
                    if (isFirstLine) {
                        try {
                            dateTime = line;
                            dateTime = dateTime.replace(":","-");
                            timeStamp = Long.toString(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(line).getTime());
                            isFirstLine = false;
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else if (line.startsWith("\"")) {
                        // Start processing new thread info
                        if (threadInfo != null) {
                            threadInfo.setRawData(rawData);
                            threadList.add(threadInfo);
                            rawData = "";
                        }

                        Matcher matcher = namePattern.matcher(line);
                        if (matcher.find()) {
                            tid = matcher.group(2);
                            nid = matcher.group(3);
                            threadInfo = new ThreadInfo(matcher.group(1), tid, nid, null, null);
                        }
                    } else if (line.contains("Thread.State")) {
                        Matcher matcher = statePattern.matcher(line);
                        if (matcher.find()) {
                            threadInfo.setState(matcher.group(1).split(" ")[0]);
                        }
                    } else if (line.contains("parking to wait for")) {
                        Matcher matcher = lockWaitPattern.matcher(line);
                        if (matcher.find()) {
                            LockInfo lockInfo = new LockInfo();
                            lockInfo.setId(matcher.group(1));
                            lockInfo.setNid(nid);
                            lockInfo.setTid(tid);
                            lockInfo.setState(state);
                            lockedList.add(lockInfo);
                        }
                    } else if (line.contains("- locked")) {
                        Matcher matcher = lockedPattern.matcher(line);
                        if (matcher.find()) {
                            LockInfo lockInfo = new LockInfo();
                            lockInfo.setId(matcher.group(1));
                            lockInfo.setNid(nid);
                            lockInfo.setTid(tid);
                            lockInfo.setState(state);
                            lockInfo.setOwned(1);
                            lockedList.add(lockInfo);
                        }
                    } else {
                        rawData += line + "\n";
                    }
                }
            }

            // After file reading write information in CSV file
//            if (!threadList.isEmpty()) {
//
//                try (CSVWriter threadListWriter = new CSVWriter(new PrintWriter("ThreadList" + dateTime + ".csv"))) {
//                    threadListWriter.writeNext(new String[]{"name", "tid", "nid", "state", "rawData"});
//                    threadList.forEach(t -> {
//                        threadListWriter.writeNext(new String[]{t.getName(), t.getTid(), t.getNid(), t.getState(), t.getRawData()});
//                    });
//                    threadListWriter.flush();
//                    System.out.println("Wrote file " + System.getProperty("user.dir") + File.separator + "ThreadList" + dateTime + ".csv");
//                }
//            }
//
//            // LockInfo
//            if (!lockedList.isEmpty()) {
//                try (CSVWriter threadListWriter = new CSVWriter(new PrintWriter("LockedThreadList" + dateTime + ".csv"))) {
//                    threadListWriter.writeNext(new String[]{"id", "tid", "nid", "state", "owned"});
//                    lockedList.forEach(l -> {
//                        threadListWriter.writeNext(new String[]{l.getId(), l.getTid(), l.getNid(), l.getState(), String.valueOf(l.getOwned())});
//                    });
//                    threadListWriter.flush();
//                    System.out.println("Wrote file " + System.getProperty("user.dir") + File.separator + "LockedThreadList" + dateTime + ".csv");
//                }
//            }

        } catch (IOException e) {
            System.err.println("User dir :" + System.getProperty("user.dir"));

            e.printStackTrace();
        }

    }
}
