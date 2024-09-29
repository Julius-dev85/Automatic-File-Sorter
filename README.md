# File Sorting Automation

## Project Overview

This Java application monitors your **Downloads** folder and automatically moves files to specific directories based on their file types. It uses the Java `WatchService` API to continuously watch for new files and then classifies and moves them to designated directories, such as Documents, Pictures, and Videos.

## Features

- **Automatic File Sorting**: Detects when a new file is downloaded and moves it to the appropriate folder (e.g., PDF files go to the Documents folder, JPG files go to the Pictures folder).
- **Supports Various File Types**: Currently supports PDF, DOCX, TXT, JPG, PNG, and MP4. Additional file types can easily be added by modifying the mapping in the code.
- **Real-Time Monitoring**: Runs continuously in the background, monitoring the Downloads folder for new files.

## Technologies Used

- **Java**: Core programming language used to implement the file monitoring and moving functionalities.
- **WatchService API**: Java NIO (Non-blocking I/O) API for monitoring file changes in a directory.

## How It Works

1. The program watches the **Downloads** directory for new files.
2. Once a new file is detected, it checks the file's extension.
3. Based on the file extension, the program moves the file to the corresponding directory (Documents, Pictures, Videos, etc.).
4. The user can modify or add more file extensions and their target directories in the `fileTypeMapping` HashMap.

### Default File Mappings

| File Type | Destination Directory |
|-----------|-----------------------|
| `.pdf`    | Documents              |
| `.docx`   | Documents              |
| `.txt`    | Documents              |
| `.jpg`    | Pictures               |
| `.png`    | Pictures               |
| `.mp4`    | Videos                 |

## Setup and Usage

### Prerequisites

- **Java 8** or higher installed on your system.

### Running the Application

1. Clone the repository to your local machine:
   ```bash
   git clone https://github.com/yourusername/file-sorting-automation.git
