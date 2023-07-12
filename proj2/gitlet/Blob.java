package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Repository.BLOB_DIR;
import static gitlet.Utils.*;

public class Blob implements Serializable {
    private String contents;
    private String fileName;
    private String id;

    public Blob(String fileName, String contents) {
        this.fileName = fileName;
        this.contents = contents;
        id = sha1(this.fileName, this.contents);
    }

    public void save() {
        Blob blob = this;
        File blobFile = join(BLOB_DIR, id);
        writeObject(blobFile, blob);
    }

    public static Blob read(File blobFile) {
        Blob blob;
        blob = readObject(blobFile, Blob.class);
        return blob;
    }

    public String getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContents() {
        return contents;
    }
}
