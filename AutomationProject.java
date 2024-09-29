import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class AutomationProject {

    // Define paths to directories
    private static final String DOWNLOADS_DIR = System.getProperty("user.home") + "/Downloads";
    private static final String DOCUMENTS_DIR = System.getProperty("user.home") + "/Documents";
    private static final String PICTURES_DIR = System.getProperty("user.home") + "/Pictures";
    private static final String VIDEOS_DIR = System.getProperty("user.home") + "/Videos";
    
    // Map file extensions to directories for sorting
    private static final Map<String, String> fileTypeMapping = new HashMap<>() {{
        put("pdf", DOCUMENTS_DIR);
        put("docx", DOCUMENTS_DIR);
        put("txt", DOCUMENTS_DIR);
        put("jpg", PICTURES_DIR);
        put("png", PICTURES_DIR);
        put("mp4", VIDEOS_DIR);
        // Add more extensions and directories as needed
    }};
    
    public static void main(String[] args) throws IOException {
        // Set up the WatchService to monitor the Downloads directory
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get(DOWNLOADS_DIR);
        
        //Register the directory with the watch service for when a new file is created
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
    
        
        // Infinite loop to run in background
        while (true) {
            WatchKey key;
            try {
                key = watchService.take();  // Wait for an event to happen
            } catch (InterruptedException e) {
                return;
            }

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                // If a new file is created
                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    Path filePath = ((WatchEvent<Path>) event).context();
                    Path fullPath = path.resolve(filePath);  // Get full path to the new file
                    System.out.println("New file detected: " + fullPath);

                    // Move the file to the correct folder based on its type
                    moveFile(fullPath);
                }
            }

            boolean valid = key.reset();  // Reset the watch key to continue watching
            if (!valid) {
                break;
            }
        }
    }

    private static void moveFile(Path filePath) {
        String fileName = filePath.getFileName().toString();
        String fileExtension = getFileExtension(fileName);

        // Find the right directory based on file extension
        String targetDir = fileTypeMapping.get(fileExtension.toLowerCase());

        if (targetDir != null) {
            Path targetPath = Paths.get(targetDir, fileName);
            try {
                Files.move(filePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Moved " + fileName + " to " + targetPath);
            } catch (IOException e) {
                System.err.println("Error moving file: " + e.getMessage());
            }
        } else {
            System.out.println("No directory mapped for file type: " + fileExtension);
        }
    }

    private static String getFileExtension(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        return (lastDot == -1) ? "" : fileName.substring(lastDot + 1); //if else statement to first see if there is a '.' in the file and then take the extenstion of the file
    }
}

