package io.ggammu.study.tobyspringframework.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public Integer calcSum(String filePath) throws IOException {
//        BufferedReaderCallback sumCallBack = new BufferedReaderCallback() {
//            @Override
//            public Integer doSomethigWithReader(BufferedReader br) throws IOException {
//                Integer sum = 0;
//                String line = null;
//                while ((line = br.readLine()) != null) {
//                    sum += Integer.valueOf(line);
//                }
//                return sum;
//            }
//        };
//        return fileReadTemplate(filePath, sumCallBack);
        LineCallback sumCallback = new LineCallback() {
            @Override
            public Integer doSomethingWithLine(String line, Integer value) {
                return value += Integer.parseInt(line);
            }
        };
        return lineReadTemplate(filePath, sumCallback, 0);
    }

    public Integer calcMultiply(String filePath) throws IOException {
//        BufferedReaderCallback multiplyCallBack = new BufferedReaderCallback() {
//            @Override
//            public Integer doSomethigWithReader(BufferedReader br) throws IOException {
//                Integer multiply = 1;
//                String line = null;
//                while ((line = br.readLine()) != null) {
//                    multiply *= Integer.valueOf(line);
//                }
//                return multiply;
//            }
//        };
//        return fileReadTemplate(filePath, multiplyCallBack);
        LineCallback multiplyCallback = new LineCallback() {
            @Override
            public Integer doSomethingWithLine(String line, Integer value) {
                return value *= Integer.parseInt(line);
            }
        };
        return lineReadTemplate(filePath, multiplyCallback, 1);
    }

    public Integer fileReadTemplate(String filePath, BufferedReaderCallback callback) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            return callback.doSomethigWithReader(br);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public Integer lineReadTemplate(String filePath, LineCallback callback, Integer initVal) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            Integer res= initVal;
            String line = null;
            while ((line = br.readLine()) != null) {
                res = callback.doSomethingWithLine(line, res);
            }
            return res;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }
}
