package ru.javawebinar.basejava;

public class MainDeadLock {
    public static void main(String[] args) {
        IntegerContainer ic1 = new IntegerContainer(0);
        IntegerContainer ic2 = new IntegerContainer(0);
        var task1 = new Runnable() {
            @Override
            public void run() {
                synchronized (ic1) {
                    for (var idx = 0; idx < 1000000; idx++) {
                        ic1.increase(idx);
                    }
                    synchronized (ic2) {
                        ic2.increase(ic1.getNumber());
                    }
                }
            }
        };
        var task2 = new Runnable() {
            @Override
            public void run() {
                synchronized (ic2) {
                    synchronized (ic1) {
                        ic2.swap(ic1);
                    }
                }
            }
        };
        new Thread(task1).start();
        new Thread(task2).start();
    }

    static class IntegerContainer {
        private int number;

        IntegerContainer(int number) {
            this.number = number;
        }

        void save(int data) {
            this.number = data;
        }

        void increase(int number) {
            this.number += number;
        }

        int getNumber() {
            return number;
        }

        void swap(IntegerContainer data) {
            int tmp = getNumber();
            save(data.getNumber());
            data.save(tmp);
        }
    }
}
