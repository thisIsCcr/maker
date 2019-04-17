package com.crsm.maker;

/**
 * creat by Ccr on 2019/4/5
 **/
public class Acount {

    private int money;

    public Acount(int money) {
        this.money = money;
    }

    public synchronized void getMoney(int money) {
        // 注意这个地方必须用while循环，因为即便再存入钱也有可能比取的要少
        while (this.money < money) {
            System.out.println("取款：" + money + " 余额：" + this.money
                    + " 余额不足，正在等待存款......");
            try {
                wait();
            } catch (Exception e) {
            }
        }
        this.money = this.money - money;
        System.out.println(Thread.currentThread().getName()+"->取出：" + money + " 还剩余：" + this.money);

    }

    public synchronized void setMoney(int money) {
        try {
            Thread.sleep(10);
        } catch (Exception e) {
        }
        this.money = this.money + money;
        System.out.println(Thread.currentThread().getName()+"->新存入：" + money + " 共计：" + this.money);
        notifyAll();
    }

    public static void main(String args[]) {
        Acount Acount = new Acount(0);
        Bank b = new Bank(Acount);
        Consumer c = new Consumer(Acount);
        new Thread(c,"儿子1号").start();
        new Thread(b,"老子1号").start();
        new Thread(c,"儿子2号").start();
    }
}

// 存款类
class Bank implements Runnable {
    Acount Acount;

    public Bank(Acount Acount) {
        this.Acount = Acount;
    }

    public void run() {
        while (true) {
            int temp = (int) (Math.random() * 1000);
            Acount.setMoney(temp);
        }
    }

}

// 取款类
class Consumer implements Runnable {
    Acount Acount;

    public Consumer(Acount Acount) {
        this.Acount = Acount;
    }

    public void run() {
        while (true) {
            int temp = (int) (Math.random() * 1000);
            Acount.getMoney(temp);
        }
    }
}
