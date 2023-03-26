package org.spbstu.gorchatovra;

import org.kohsuke.args4j.CmdLineException;

import java.io.*;


public class Delimiter {

    public void tar(File u) throws IOException {
        FileReader fr = new FileReader(u);
        BufferedReader reader = new BufferedReader(fr);
        String buf = reader.readLine();
        FileWriter writer;
        if(buf.matches("([A-Za-z0-9-_.]+/?)+ [0-9]+")) {
            while (buf != null) {
                String[] data = buf.trim().split(" ");
                String name = data[0];
                int size = Integer.parseInt(data[1]);
                File f = new File(name);
                if (f.createNewFile()) {
                    StringBuilder builder = new StringBuilder();
                    writer = new FileWriter(f);
                    for (int i = 0; i < size; i++) {
                        builder.append(reader.readLine()).append("\n");
                    }
                    writer.write(builder.toString());
                    writer.close();
                } else {
                    System.out.println("Файл уже существует");
                }
                buf = reader.readLine();
            }
        }else{
            reader.close();
            throw new IOException("Неверные данные , нужен другой файл!");
        }
        reader.close();
    }

    public void tar(String[] arguments, String out) throws IOException, CmdLineException {
        File f = new File(out);
        FileWriter writer;
        StringBuilder builder;
        if (f.createNewFile()) {
            writer = new FileWriter(f);
            for (String argument : arguments) {
                if(new File(argument).exists()) {
                    FileReader fr = new FileReader(argument);
                    BufferedReader reader = new BufferedReader(fr);
                    builder = new StringBuilder();
                    int countLines = 0;
                    String temp;
                    while ((temp = reader.readLine()) != null) {
                        builder.append(temp).append("\n");
                        countLines++;
                    }
                    writer.write(argument + " " + countLines + "\n");
                    writer.write(builder.toString());
                }else{
                    System.out.println("Неверный аргумент "+ argument + "\n пример: \"text1.txt text2.txt\" -out text3.txt");
                }
            }

            writer.close();
        } else {
            throw new IOException("Не возможно создать новый файл");
        }
    }
}
