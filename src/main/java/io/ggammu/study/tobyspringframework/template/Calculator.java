package io.ggammu.study.tobyspringframework.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public Integer calcSum(String filePath) throws IOException {
        BufferedReaderCallBack sumCallBack = new BufferedReaderCallBack() {
            @Override
            public Integer doSomethigWithReader(BufferedReader br) throws IOException {
                Integer sum = 0;
                String line = null;
                while ((line = br.readLine()) != null) {
                    sum += Integer.valueOf(line);
                }
                return sum;
            }
        };
        return fileReadTemplate(filePath, sumCallBack);
    }

    public Integer calcMultiply(String filePath) throws IOException {
        BufferedReaderCallBack multiplyCallBack = new BufferedReaderCallBack() {
            @Override
            public Integer doSomethigWithReader(BufferedReader br) throws IOException {
                Integer multiply = 1;
                String line = null;
                while ((line = br.readLine()) != null) {
                    multiply *= Integer.valueOf(line);
                }
                return multiply;
            }
        };
        return fileReadTemplate(filePath, multiplyCallBack);
    }

    public Integer fileReadTemplate(String filePath, BufferedReaderCallBack callBack) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            return callBack.doSomethigWithReader(br);
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
