import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage;
    private int size;

    public ArrayStorage() {
        this.storage = new Resume[10000];
    }

    public ArrayStorage(int length) {
        this.storage = new Resume[length];
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void save(Resume resume) {
        if (resume.uuid != null && size < storage.length && findIndex(resume.uuid) == -1) {
            storage[size] = resume;
            size++;
        }
    }

    public void delete(String uuid) {
        int indexOfResume = findIndex(uuid);
        if (indexOfResume != -1) {
            storage[indexOfResume] = null;
            if (indexOfResume < size - 1) {
                System.arraycopy(
                        storage, indexOfResume + 1,
                        storage, indexOfResume,
                        size - indexOfResume - 1);
            }
            size--;
        }
    }

    public Resume get(String uuid) {
        int indexOfResume = findIndex(uuid);
        return indexOfResume != -1 ? storage[indexOfResume] : null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int size() {
        return size;
    }
}
