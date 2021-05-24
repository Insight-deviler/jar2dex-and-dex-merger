package sar.id.dynamic.d8;

import  java.io.File;
 import java.io.IOException;
 import  java.io.InputStream;
import java.lang.InterruptedException;
 import java.lang.Object;
 import java.lang.Process;
 import  java.lang.Runtime;
 import  java.lang.String;
 import java.lang.StringBuilder;
 import  java.util.Scanner;
 

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class RuntimeExecution {
    private String errStream = "";
    private String inStream = "";
    private Process process;
    private StringBuilder sb;

    public RuntimeExecution() {
        StringBuilder stringBuilder;
        this.sb = stringBuilder = new StringBuilder();
    }

    public void destroyProcess() {
        if (this.process != null) {
            this.process.destroy();
        }
    }

    public void exec(String string) throws IOException {
        this.process = Runtime.getRuntime().exec(string);
        this.setLogStream(this.process.getErrorStream(), this.errStream);
        this.setLogStream(this.process.getInputStream(), this.inStream);
    }

    public void exec(String string, String[] arrstring) throws IOException {
        this.process = Runtime.getRuntime().exec(string, arrstring);
        this.setLogStream(this.process.getErrorStream(), this.errStream);
        this.setLogStream(this.process.getInputStream(), this.inStream);
    }

    public void exec(String string, String[] arrstring, File file) throws IOException {
        this.process = Runtime.getRuntime().exec(string, arrstring, file);
        this.setLogStream(this.process.getErrorStream(), this.errStream);
        this.setLogStream(this.process.getInputStream(), this.inStream);
    }

    public void exec(String[] arrstring) throws IOException {
        this.process = Runtime.getRuntime().exec(arrstring);
        this.setLogStream(this.process.getErrorStream(), this.errStream);
        this.setLogStream(this.process.getInputStream(), this.inStream);
    }

    public void exec(String[] arrstring, String[] arrstring2) throws IOException {
        this.process = Runtime.getRuntime().exec(arrstring, arrstring2);
        this.setLogStream(this.process.getErrorStream(), this.errStream);
        this.setLogStream(this.process.getInputStream(), this.inStream);
    }

    public void exec(String[] arrstring, String[] arrstring2, File file) throws IOException {
        this.process = Runtime.getRuntime().exec(arrstring, arrstring2, file);
        this.setLogStream(this.process.getErrorStream(), this.errStream);
        this.setLogStream(this.process.getInputStream(), this.inStream);
    }

    public String getErrStream() {
        return this.errStream;
    }

    public String getOutStream() {
        return this.inStream;
    }

    public int getStatusProcess() {
        try {
            int n = this.process.waitFor();
            return n;
        }
        catch (InterruptedException interruptedException) {
            return -1;
        }
    }

    public void setLogStream(InputStream inputStream, String string) {
        StringBuilder stringBuilder;
        this.sb = stringBuilder = new StringBuilder();
        Scanner scanner = new Scanner(inputStream);
        do {
            if (!scanner.hasNext()) {
                this.sb.toString();
                return;
            }
            this.sb.append(scanner.next());
        } while (true);
    }
}
