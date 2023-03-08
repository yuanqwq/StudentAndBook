package com.test.mapper;

import com.test.entity.Book;
import com.test.entity.Record;
import com.test.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TestMapper {
    @Select("select * from student where sid=#{sid}")
    Student getStudentBySid(int sid);

    @Select("select * from student")
    List<Student> getStudents();
    @Select("select sid from student")
    List<Integer> getSid();

    @Select("select * from book where bid=#{bid}")
    Book getBookByBid(int bid);

    @Select("select * from book")
    List<Book> getBooks();
    @Select("select bid from book")
    List<Integer> getBid();

    @Insert("insert into student(name, sex) values(#{student.name},#{student.sex})")
    int addStudent(@Param("student") Student student);

    @Insert("insert into book(name, author, price) values(#{book.name},#{book.author},#{book.price})")
    int addBook(@Param("book") Book book);

    @Insert("insert into borrow values(#{sid},#{tid})")
    int addRecordBySidAndTid(@Param("sid") int sid, @Param("tid") int tid);

    @Results({
            @Result(id = true, column = "bid", property = "bid"),
            @Result(column = "name", property = "name"),
            @Result(column = "price", property = "price")
    })
    @Select("select * from book inner join borrow on book.bid=borrow.bid where sid=#{sid}")
    List<Book> getBooksBySid(int sid);

    @Results({
            @Result(column = "sid",property = "student",one =
                @One(select = "getStudentBySid")
            ),
            @Result(column = "bid",property = "book",one =
                @One(select = "getBookByBid")
            )
    })
    @Select("select * from student inner join borrow on student.sid=borrow.sid inner join book on borrow.bid=book.bid")
    List<Record> getRecords();
}
