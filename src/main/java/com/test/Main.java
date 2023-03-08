package com.test;

import com.test.entity.Book;
import com.test.entity.Record;
import com.test.entity.Student;
import com.test.mapper.TestMapper;
import lombok.extern.java.Log;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.LogManager;

@Log
public class Main {
    private static SqlSessionFactory factory;
    private static final LogManager logManager = LogManager.getLogManager();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        logManager.readConfiguration(Resources.getResourceAsStream("logging.properties"));
        System.out.println("欢迎使用图书管理系统");
        sys:
        while (true) {
            System.out.println("1.录入学生信息");
            System.out.println("2.录入书籍信息");
            System.out.println("3.录入借阅信息");
            System.out.println("4.查询学生信息列表");
            System.out.println("5.查询图书信息列表");
            System.out.println("6.查询借阅信息列表");
            System.out.println("7.退出");
            switch (scanner.nextInt()) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    addBook();
                    break;
                case 3:
                    addRecord();
                    break;
                case 4:
                    getStudents();
                    break;
                case 5:
                    getBooks();
                    break;
                case 6:
                    getRecords();
                    break;
                case 7:
                    System.out.println("系统已退出");
                    break sys;
            }
        }

        scanner.close();

    }

    private static int addStudent() {
        int res = 0;
        try (SqlSession session = factory.openSession(true)) {
            scanner.nextLine();
            TestMapper mapper = session.getMapper(TestMapper.class);
            Student student = new Student();
            System.out.println("请输入学生姓名:");
            String name;
            while (true) {
                name = scanner.nextLine();
                if (name.length() < 10) {
                    student.setName(name);
                    break;
                } else {
                    System.out.println("学生名字过长，名字长度应小于10");
                }
            }

            System.out.println("请输入学生性别:");
            String sex;
            while (true) {
                sex = scanner.nextLine();
                if (sex.equals("男") || sex.equals(("女"))) {
                    student.setSex(sex);
                    break;
                } else {
                    System.out.println("非法字符，请输入\"男\"或\"女\"");
                }
            }

            res = mapper.addStudent(student);
            System.out.println("插入中，请稍等..");
            if (res == 1)
                System.out.println("学生插入成功");
            else
                System.out.println("学生插入失败，请重试");

        }
        return res;
    }

    private static int addBook() {
        int res = 0;
        try (SqlSession session = factory.openSession(true)) {
            scanner.nextLine();
            TestMapper mapper = session.getMapper(TestMapper.class);
            Book book = new Book();
            System.out.println("请输入书名:");
            String name;
            while (true) {
                name = scanner.nextLine();
                if (name.length() < 10) {
                    book.setName(name);
                    break;
                } else {
                    System.out.println("书名过长，书名长度应小于10");
                }
            }

            System.out.println("请输入作者:");
            String author;
            while (true) {
                author = scanner.nextLine();
                if (author.length() < 10) {
                    book.setAuthor(author);
                    break;
                } else {
                    System.out.println("作者名字过长，名字长度应小于10");
                }
            }

            System.out.println("请输入价格:");
            double price;
            while (true) {
                price = scanner.nextDouble();
                if (price < Double.MAX_VALUE) {
                    book.setPrice(price);
                    break;
                } else {
                    System.out.println("价格数值错误，超过上限");
                }
            }

            res = mapper.addBook(book);
            System.out.println("插入中，请稍等..");
            if (res == 1)
                System.out.println("书籍插入成功");
            else
                System.out.println("书籍插入失败，请重试");

        }
        return res;
    }

    private static int addRecord() {
        int res = 0;
        try (SqlSession session = factory.openSession(true)) {
            scanner.nextLine();
            TestMapper mapper = session.getMapper(TestMapper.class);

            System.out.println("请输入学生sid:");
            int sid;
            List<Integer> Sids = mapper.getSid();
            while (true) {
                sid = scanner.nextInt();
                if (Sids.contains(sid)) {
                    break;
                } else {
                    System.out.println("学生sid不存在");
                }
            }

            System.out.println("请输入书籍bid:");
            int bid;
            List<Integer> Bids = mapper.getBid();
            while (true) {
                bid = scanner.nextInt();
                if (Bids.contains(bid)) {
                    break;
                } else {
                    System.out.println("书籍Bid不存在");
                }
            }

            res = mapper.addRecordBySidAndTid(sid, bid);
            System.out.println("插入中，请稍等..");
            if (res == 1)
                System.out.println("记录插入成功");
            else
                System.out.println("记录插入失败，请重试");
        }
        return res;
    }

    private static void getStudents() {
        try (SqlSession session = factory.openSession(true)) {
            scanner.nextLine();
            TestMapper mapper = session.getMapper(TestMapper.class);
            List<Student> list = mapper.getStudents();
            System.out.printf("%6s%20s%20s\n", "sid", "name", "sex");
            for (Student student : list) {
                System.out.printf("%6d%20s%20s\n", student.getSid(), student.getName(), student.getSex());
            }
        }
    }

    private static void getBooks() {
        try (SqlSession session = factory.openSession(true)) {
            scanner.nextLine();
            TestMapper mapper = session.getMapper(TestMapper.class);
            List<Book> list = mapper.getBooks();
            System.out.printf("%6s%20s%20s%20s\n", "sid", "name", "author", "price");
            for (Book book : list) {
                System.out.printf("%6d%20s%20s%20.2f\n", book.getBid(), book.getName(), book.getAuthor(), book.getPrice());
            }
        }
    }

    private static void getRecords() {
        try (SqlSession session = factory.openSession(true)) {
            scanner.nextLine();
            TestMapper mapper = session.getMapper(TestMapper.class);
            List<Record> list = mapper.getRecords();
            System.out.printf("%6s%20s%20s\t\t\t\t%6s%20s%20s%20s\n", "sid", "name", "sex", "sid", "name", "author", "price");
            for (Record record : list) {
                System.out.printf("%6d%20s%20s\t\t\t\t%6d%20s%20s%20.2f\n", record.getStudent().getSid(),
                        record.getStudent().getName(), record.getStudent().getSex(), record.getBook().getBid(),
                        record.getBook().getName(), record.getBook().getAuthor(), record.getBook().getPrice());
            }
        }
    }
}
