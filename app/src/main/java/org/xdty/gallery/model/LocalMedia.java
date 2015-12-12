package org.xdty.gallery.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocalMedia extends File implements Media<LocalMedia>, Comparable<File> {

    private final static String[] SCHEME = new String[]{"file"};

    private LocalMedia parent;
    private List<LocalMedia> children = new ArrayList<>();

    public LocalMedia() {
        super("/");
    }

    public LocalMedia(String path) {
        super(path);
    }

    public LocalMedia(File media) {
        super(media.getPath());
    }

    @Override
    public String getParent() {
        return "file://" + super.getParent();
    }

    @Override
    public void setParent(LocalMedia parent) {
        this.parent = parent;
    }

    @Override
    public LocalMedia parent() {
        return parent;
    }

    @Override
    public List<LocalMedia> children() {
        if (children.size() == 0) {
            File[] files = super.listFiles();
            for (File file : files) {
                LocalMedia media = new LocalMedia(file);
                media.setParent(this);
                children.add(media);
            }
            Collections.sort(children);
        }
        return Collections.unmodifiableList(children);
    }

    @Override
    public LocalMedia[] listMedia() {
        ArrayList<LocalMedia> list = new ArrayList<>();
        File[] files = super.listFiles();
        for (File file : files) {
            list.add(new LocalMedia(file));
        }
        Collections.sort(list);
        return list.toArray(new LocalMedia[list.size()]);
    }

    @Override
    public String[] scheme() {
        return SCHEME;
    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public long getLastModified() {
        return 0;
    }

    @Override
    public String getUri() {
        return "file://" + super.getPath();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this);
    }

    @Override
    public boolean isImage() {
        String name = getName().toLowerCase();
        return name.endsWith(".png") ||
                name.endsWith(".jpg") ||
                name.endsWith(".jpeg") ||
                name.endsWith(".bmp") ||
                name.endsWith(".gif");
    }

    @Override
    public LocalMedia fromUri(String uri) {
        return new LocalMedia(uri.replace("file://", ""));
    }

    @Override
    public LocalMedia auth(String domain, String directory, String username, String password) {
        return this;
    }

    @Override
    public boolean equals(Object object) {
        boolean equal = false;

        if (object != null && object instanceof Media) {
            equal = this.getPath().equals(((Media) object).getPath());
        }

        return equal;
    }
}