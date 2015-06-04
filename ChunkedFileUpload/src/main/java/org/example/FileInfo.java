package org.example;

public class FileInfo {

	private String uuid;
	
	private String name;
	
	private long uploaded;
	
	private long size;

	public FileInfo(String uuid, String name, long size, long uploaded) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.size = size;
		this.uploaded = uploaded; 
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileInfo other = (FileInfo) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	public String getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public long getSize() {
		return size;
	}

	public long getUploaded() {
		return uploaded;
	}
}
