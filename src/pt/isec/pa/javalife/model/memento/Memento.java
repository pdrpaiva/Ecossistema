package pt.isec.pa.javalife.model.memento;

import java.io.*;

public class Memento implements IMemento {
    private final byte[] snapshot;

    public Memento(Object obj) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(obj);
            snapshot = baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create memento", e);
        }
    }

    @Override
    public Object getSnapshot() {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(snapshot);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to get snapshot", e);
        }
    }
}
