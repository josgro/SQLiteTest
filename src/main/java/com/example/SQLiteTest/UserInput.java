package com.example.SQLiteTest;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Scanner;

public class UserInput {

    DataService dataService;

    @Autowired
    public UserInput(DataService dataService) {
        this.dataService = dataService;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        List<MailCourseStatus> list;

        do {
            System.out.println("Enter email and course. Type 'exit' to end the program.\n");

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.println();

            System.out.print("Course: ");
            String course = scanner.nextLine();

            System.out.println();

            MailCourseStatus mailCourseStatus = new MailCourseStatus();
            mailCourseStatus.setEmail(email);
            mailCourseStatus.setCourse(course);

            dataService.createMailCourse(mailCourseStatus);

            list = dataService.read();

            System.out.println("Database: ");
            for (MailCourseStatus mcs : list) {
                System.out.println(mcs.getEmail());
            }

        } while (!scanner.nextLine().equals("exit"));

    }
}



