package org.spbstu.gorchatovra;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Parser {
    @Option(name = "-u")
    private File u;
    @Option(name = "-out")
    private String out;

    @Argument
    private String arguments;
    public static void main(String[] args) {
        new Parser().run(args);
    }

    private void run(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            if ((arguments == null && out != null) || (u != null && out != null)) {
                System.err.println("Ошибка ввода аргументов!");
                System.err.println("tar [опции...] аргументы...");
                System.err.println("\nПример: tar -u \"filename.txt\" \n tar \"file1.txt file2.txt\" -out output.txt");
                throw new IllegalArgumentException("");
            }
            if (out != null) {
                new Delimiter().tar(arguments.split(" "), out);
            } else {
                if((u!=null && !Objects.requireNonNull(u).exists()) ){
                    throw new IOException("Папки или файла не существует");
                }

                new Delimiter().tar(u);
            }

        } catch (CmdLineException | IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
