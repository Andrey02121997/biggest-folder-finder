import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        String folderPath = "c:/users/agrab/desktop/games";
        File file = new File(folderPath);
        Path path = Paths.get(folderPath);

        long size = visitor(path);

        System.out.println("Standart: " + getFolderSize(file));
        System.out.println("Stream: " + getFolderSizeStream(path));

        //From Homework 9.1

        System.out.println("FileVisitor: " + size);


    }

    public static Long getFolderSize(File file) {
        if (file.isFile())
        {
            return file.length();
        }
        long sum = 0;
        File[] folder = file.listFiles();
        assert folder != null;
        for (File files : folder) {
            sum += getFolderSize(files);
        }
        return sum;
    }

    public static long getFolderSizeStream(Path path) throws IOException {
        return Files.walk(path)
                .filter(paths -> paths.toFile().isFile())
                .mapToLong(paths -> paths.toFile().length())
                .sum();
    }

    private static long visitor(Path path) throws IOException {
        FileVisitor fileVisitor = new FileVisitor();
        Files.walkFileTree(path, fileVisitor);
        return fileVisitor.getFilesCount();
    }
}
