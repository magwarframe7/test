import java.nio.file.*;
import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Comparator;

public class test {

    public static void main(String[] args) {
        String directoryPath = "/path/to/your/directory";

        try {
            Path latestSubdirectory = getLatestSubdirectory(directoryPath);
            System.out.println("Latest subdirectory: " + latestSubdirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Path getLatestSubdirectory(String directoryPath) throws IOException {
        Path directory = Paths.get(directoryPath);

        // List all subdirectories
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, Files::isDirectory)) {
            return stream
                    .map(subdirectory -> new Pair(subdirectory, getLastModifiedTime(subdirectory)))
                    .max(Comparator.comparing(Pair::getModifiedTime))
                    .map(Pair::getPath)
                    .orElse(null);
        }
    }

    private static FileTime getLastModifiedTime(Path path) {
        try {
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
            return attrs.lastModifiedTime();
        } catch (IOException e) {
            throw new RuntimeException("Error getting last modified time", e);
        }
    }

    // Helper class to hold a pair of Path and FileTime
    private static class Pair {
        private final Path path;
        private final FileTime modifiedTime;

        public Pair(Path path, FileTime modifiedTime) {
            this.path = path;
            this.modifiedTime = modifiedTime;
        }

        public Path getPath() {
            return path;
        }

        public FileTime getModifiedTime() {
            return modifiedTime;
        }
    }
}
