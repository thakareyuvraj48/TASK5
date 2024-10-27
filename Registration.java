import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// Course class to store course details
class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private int enrolled;
    private String schedule;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.enrolled = 0;
        this.schedule = schedule;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnrolled() {
        return enrolled;
    }

    public String getSchedule() {
        return schedule;
    }

    public boolean registerStudent() {
        if (enrolled < capacity) {
            enrolled++;
            return true;
        }
        return false;
    }

    public boolean dropStudent() {
        if (enrolled > 0) {
            enrolled--;
            return true;
        }
        return false;
    }

    public boolean hasSpace() {
        return enrolled < capacity;
    }

    @Override
    public String toString() {
        return "Course Code: " + courseCode + ", Title: " + title + ", Description: " + description +
               ", Capacity: " + enrolled + "/" + capacity + ", Schedule: " + schedule;
    }
}

// Student class to store student details
class Student {
    private String studentID;
    private String name;
    private ArrayList<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(Course course) {
        registeredCourses.add(course);
    }

    public void dropCourse(Course course) {
        registeredCourses.remove(course);
    }

    @Override
    public String toString() {
        return "Student ID: " + studentID + ", Name: " + name + ", Registered Courses: " + registeredCourses.size();
    }
}

// Main class for Course Registration System
public class CourseRegistrationSystem {
    private static HashMap<String, Course> courseDatabase = new HashMap<>();
    private static HashMap<String, Student> studentDatabase = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeCourses();
        initializeStudents();

        while (true) {
            System.out.println("\n--- Course Registration System ---");
            System.out.println("1. View available courses");
            System.out.println("2. Register for a course");
            System.out.println("3. Drop a course");
            System.out.println("4. View registered courses");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    viewCourses();
                    break;
                case 2:
                    registerCourse();
                    break;
                case 3:
                    dropCourse();
                    break;
                case 4:
                    viewRegisteredCourses();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void initializeCourses() {
        courseDatabase.put("CS101", new Course("CS101", "Introduction to Computer Science", "Basics of CS", 30, "Mon/Wed 10:00-11:30"));
        courseDatabase.put("MATH101", new Course("MATH101", "Calculus I", "Introduction to Calculus", 25, "Tue/Thu 9:00-10:30"));
        courseDatabase.put("PHYS101", new Course("PHYS101", "Physics I", "Mechanics", 20, "Mon/Wed 12:00-13:30"));
    }

    private static void initializeStudents() {
        studentDatabase.put("S001", new Student("S001", "Alice"));
        studentDatabase.put("S002", new Student("S002", "Bob"));
    }

    private static void viewCourses() {
        System.out.println("\nAvailable Courses:");
        for (Course course : courseDatabase.values()) {
            System.out.println(course);
        }
    }

    private static void registerCourse() {
        System.out.print("\nEnter Student ID: ");
        String studentID = scanner.nextLine();
        Student student = studentDatabase.get(studentID);

        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.print("Enter Course Code to register: ");
        String courseCode = scanner.nextLine();
        Course course = courseDatabase.get(courseCode);

        if (course == null) {
            System.out.println("Course not found!");
            return;
        }

        if (course.hasSpace()) {
            if (course.registerStudent()) {
                student.registerCourse(course);
                System.out.println("Successfully registered for " + course.getTitle());
            }
        } else {
            System.out.println("Course is full.");
        }
    }

    private static void dropCourse() {
        System.out.print("\nEnter Student ID: ");
        String studentID = scanner.nextLine();
        Student student = studentDatabase.get(studentID);

        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.print("Enter Course Code to drop: ");
        String courseCode = scanner.nextLine();
        Course course = courseDatabase.get(courseCode);

        if (course == null || !student.getRegisteredCourses().contains(course)) {
            System.out.println("You are not registered for this course!");
            return;
        }

        if (course.dropStudent()) {
            student.dropCourse(course);
            System.out.println("Successfully dropped " + course.getTitle());
        }
    }

    private static void viewRegisteredCourses() {
        System.out.print("\nEnter Student ID: ");
        String studentID = scanner.nextLine();
        Student student = studentDatabase.get(studentID);

        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.println("\nRegistered Courses for " + student.getName() + ":");
        for (Course course : student.getRegisteredCourses()) {
            System.out.println(course);
        }
    }
}