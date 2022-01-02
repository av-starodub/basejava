import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage;
    private int size;

    public ArrayStorage() {
        this.storage = new Resume[10000];
        this.size = 0;
    }

    public ArrayStorage(int length) {
        this.storage = new Resume[length];
        this.size = 0;
    }

    private int find(String uuid) {
        for (int i = 0; i < this.size; i += 1) {
            if (uuid.equals(this.storage[i].uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void save(Resume resume) {
        if (find(resume.uuid) == -1) {
            try {
                this.storage[this.size] = resume;
                this.size += 1;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.printf(
                        "%s. %s\n", e.getMessage(), "This storage is full."
                );
            }
        }
    }

    public void delete(String uuid) {
        int indexOfResume = find(uuid);
        if (indexOfResume != -1) {
            this.storage[indexOfResume] = null;
            //noinspection ManualArrayCopy
            for (int i = indexOfResume; i < this.size; i += 1) {
                this.storage[i] = this.storage[i + 1];
            }
            this.size -= 1;
        }
    }

    public Resume get(String uuid) {
        int indexOfResume = find(uuid);
        return indexOfResume != -1 ? this.storage[indexOfResume] : null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(this.storage, 0, this.size);
    }

    public void clear() {
        Arrays.fill(this.storage, 0, this.size, null);
        this.size = 0;
    }

    public int size() {
        return this.size;
    }
}
