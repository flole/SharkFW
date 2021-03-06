package net.sharkfw.knowledgeBase.persistent.dump;

import net.sharkfw.asip.ASIPInformation;
import net.sharkfw.asip.ASIPSpace;
import net.sharkfw.knowledgeBase.SharkKBException;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by j4rvis on 2/28/17.
 */
public class DumpASIPInformation extends DumpPropertyHolder implements ASIPInformation {

    private final ASIPInformation info;

    public DumpASIPInformation(DumpSharkKB dumpSharkKB, ASIPInformation info) {
        super(dumpSharkKB, info);
        this.info = info;
    }

    @Override
    public ASIPSpace getASIPSpace() throws SharkKBException {
        return new DumpASIPSpace(kb, info.getASIPSpace());
    }

    @Override
    public long lastModified() {
        return info.lastModified();
    }

    @Override
    public long creationTime() {
        return info.creationTime();
    }

    @Override
    public void setContent(InputStream is, long len) {
        info.setContent(is, len);
        kb.persist();
    }

    @Override
    public void setContent(byte[] content) {
        info.setContent(content);
        kb.persist();
    }

    @Override
    public void setContent(String content) {
        info.setContent(content);
        kb.persist();
    }

    @Override
    public void removeContent() {
        info.removeContent();
        kb.persist();
    }

    @Override
    public void setContentType(String mimetype) {
        info.setContentType(mimetype);
        kb.persist();
    }

    @Override
    public String getContentType() {
        return info.getContentType();
    }

    @Override
    public byte[] getContentAsByte() {
        return info.getContentAsByte();
    }

    @Override
    public void streamContent(OutputStream os) {
        info.streamContent(os);
    }

    @Override
    public long getContentLength() {
        return info.getContentLength();
    }

    @Override
    public String getName() {
        return info.getName();
    }

    @Override
    public String getContentAsString() throws SharkKBException {
        return info.getContentAsString();
    }

    @Override
    public void setName(String name) throws SharkKBException {
        info.setName(name);
        kb.persist();
    }
}
