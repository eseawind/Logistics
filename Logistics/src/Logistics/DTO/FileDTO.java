package Logistics.DTO;

public class FileDTO {
	private String fileID;
	private String fileName;
	private String directory;
	private String backupDirectory;
	public String getFileID() {
		return fileID;
	}
	public void setFileID(String fileID) {
		this.fileID = fileID;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	public String getBackupDirectory() {
		return backupDirectory;
	}
	public void setBackupDirectory(String backupDirectory) {
		this.backupDirectory = backupDirectory;
	}
	
}
