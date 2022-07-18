package io.ggammu.study.tobyspringframework.template;

import java.io.BufferedReader;
import java.io.IOException;

public interface BufferedReaderCallback {
    Integer doSomethigWithReader(BufferedReader br) throws IOException;
}
