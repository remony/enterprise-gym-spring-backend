package six.team.backend.dao;

import jdk.internal.util.xml.impl.Pair;
import six.team.backend.store.AnswerStore;
import six.team.backend.store.IndexStore;
import six.team.backend.store.QuestionStore;
import six.team.backend.store.QuizStore;

import java.sql.*;
import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Gareth on 20/09/2015.
 */
public class QuizDAO {
    public  LinkedList<QuestionStore> getQuiz(String quizid){
        Connection connection = null;
        QuestionStore question = new QuestionStore();
        LinkedList<QuestionStore>  questions = new LinkedList<QuestionStore>();
        LinkedList<AnswerStore> answers = new LinkedList<AnswerStore>();


        try {
            connection = getDBConnection();
            PreparedStatement ps1 = connection.prepareStatement("SELECT * FROM Question where quiz_id =?");
            ps1.setString(1, quizid);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                if (rs1 != null) {
                    int count =0;
                    answers = new LinkedList<AnswerStore>();
                    QuestionStore question1 = new QuestionStore();
                    question1.setQuestion_text(rs1.getString("question_text"));
                    PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM Answer where question_id =?");
                    ps2.setString(1, rs1.getString("question_id"));
                    ResultSet rs2 = ps2.executeQuery();
                    while (rs2.next()) {
                        if (rs2 != null) {
                            AnswerStore answer1 = new AnswerStore();
                            answer1.setAnswer_id(rs2.getString("answer_id"));
                            if(rs2.getString("answer_id").equals(rs1.getString("correct_answer_id"))){
                                question1.setCorrectAnswer(Integer.toString(count));
                            }
                            answer1.setAnswer_text(rs2.getString("answer_text"));
                            answers.add(answer1);
                            System.out.println(rs2.getString("question_id"));
                            question1.setAnswers(answers);
                            count++;
                        }
                    }
                    questions.add(question1);
                }
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                //failed to close connection
                System.err.println(e.getMessage());
            }
        }
        return questions;
    }

    public  LinkedList<QuestionStore> createQuiz(LinkedList<QuestionStore> questions, String quizTitle){
        Connection connection = null;
        try {
            connection = getDBConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Quiz (quiz_id,quiz_title) values (?,?)");
            String randomQuizId = UUID.randomUUID().toString();
            randomQuizId = randomQuizId.replaceAll("-", "");
            ps.setString(1,randomQuizId);
            ps.setString(2, quizTitle);
            int result2 =ps.executeUpdate();
           for(int i=0; i<questions.size(); i++) {
               PreparedStatement ps1 = connection.prepareStatement("INSERT INTO Question (question_id,quiz_id,question_text,correct_answer_id) values(?,?,?,?)");
               ps1.setString(1, questions.get(i).getQuestion_id());
               ps1.setString(2, randomQuizId);
               ps1.setString(3, questions.get(i).getQuestion_text());
               ps1.setString(4, questions.get(i).getCorrectAnswer());
               int result = ps1.executeUpdate();
               LinkedList<AnswerStore> answers = new LinkedList<AnswerStore>();
               answers = questions.get(i).getAnswers();
               for (int r = 0; r <answers.size(); r++) {
                   PreparedStatement ps2 = connection.prepareStatement("INSERT INTO Answer (answer_id,question_id,answer_text) values(?,?,?)");
                   ps2.setString(1, answers.get(r).getAnswer_id());
                   ps2.setString(2, questions.get(i).getQuestion_id());
                   ps2.setString(3, answers.get(r).getAnswer_text());
                   int result3 = ps2.executeUpdate();
                   System.out.println(result3);
               }
           }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                //failed to close connection
                System.err.println(e.getMessage());
            }
        }
        return questions;
    }

    public  LinkedList<QuizStore> getAllQuizzes(){
        Connection connection = null;
        LinkedList<QuizStore>  quizzes= new LinkedList<QuizStore>();
            connection = getDBConnection();
          try{
              PreparedStatement ps1 = connection.prepareStatement("SELECT * FROM Quiz");
              ResultSet rs1 = ps1.executeQuery();
              while (rs1.next()) {
                if (rs1 != null) {
                    QuizStore quiz = new QuizStore();
                    quiz.setQuiz_id(rs1.getString("quiz_id"));
                    quiz.setQuiz_title(rs1.getString("quiz_title"));
                    quizzes.add(quiz);
                }
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                //failed to close connection
                System.err.println(e.getMessage());
            }
        }
        return quizzes;
    }

    private static Connection getDBConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String db = "jdbc:mysql://46.101.32.73:3306/enterprisegym";
            connection = DriverManager.getConnection(db, "admin", "admin");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
