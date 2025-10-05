import java.util.ArrayList;

public class StudentProcessor 
{

    public static double calculateAverageMarks(ArrayList<Student> studentsList) 
    {
        int totalMarks = 0;
        for (Student st : studentsList) 
        {
            totalMarks += st.getStudentMarks();
        }
        return (double) totalMarks / studentsList.size();
    }

    public static int findHighestMarks(ArrayList<Student> studentsList) 
    {
        int highest = studentsList.get(0).getStudentMarks();
        for (Student st : studentsList) {
            if (st.getStudentMarks() > highest) 
            {
                highest = st.getStudentMarks();
            }
        }
        return highest;
    }

    public static int findLowestMarks(ArrayList<Student> studentsList) 
    {
        int lowest = studentsList.get(0).getStudentMarks();
        for (Student st : studentsList) 
        {
            if (st.getStudentMarks() < lowest) 
            {
                lowest = st.getStudentMarks();
            }
        }
        return lowest;
    }
}
