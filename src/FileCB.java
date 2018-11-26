/*
 * @Author Triston Gregoire
 */
public class FileCB {
    public String fileName;
    int fileSize;
    public FileCB(String fileName, long fileSize) {
        setFileName(fileName);
        setFileSize(fileSize);
    }

    public FileCB() {

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }
    public void setFileSize(long fileSize){
        try {
            this.fileSize = Math.toIntExact(fileSize);
        }catch (ArithmeticException ae){
            ae.printStackTrace();
            this.fileSize = -1;
        }
    }
}
