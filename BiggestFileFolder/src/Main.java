import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ForkJoinPool;

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

        System.out.println("-----------");
        FolderSizeCalculator calculator =
                new FolderSizeCalculator(file);
        ForkJoinPool pool = new ForkJoinPool();
        long size2 = pool.invoke(calculator);
        System.out.println(humanReadable(size2, SI.YES));
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
    enum SI {
        YES(1024),
        NO(1000);

        int value;

        SI(int value) {
            this.value = value;
        }
    }

    private static String humanReadable(long bytes, SI si)
    {
        int unit = si.value;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre;
        if (si.value == 1000){
            pre = "kMGTPE".charAt(exp - 1) + "";}
        else{ pre = "kMGTPE".charAt(exp - 1) + "i";}
        if (si.value == 1024) pre = "KMGTPE".charAt(exp - 1) + "";
        else pre = "KMGTPE".charAt(exp - 1) + "i";
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

}
