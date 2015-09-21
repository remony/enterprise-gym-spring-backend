package six.team.backend.dao;

import jdk.internal.util.xml.impl.Pair;
import six.team.backend.store.AnswerStore;
import six.team.backend.store.IndexStore;
import six.team.backend.store.QuestionStore;

import java.sql.*;
import java.util.LinkedList;

/**
 * Created by Gareth on 20/09/2015.
 */
public class QuizDAO {
    public  LinkedList<QuestionStore> getQuiz(int quizid){
        Connection connection = null;
        QuestionStore question = new QuestionStore();
        AnswerStore answer = new AnswerStore();
        LinkedList<QuestionStore>  questions = new LinkedList<QuestionStore>();
        LinkedList<AnswerStore> answers = new LinkedList<AnswerStore>();


        try {
            connection = getDBConnection();
          /* // PreparedStatement ps = connection.prepareStatement("SELECT * FROM Quiz where quizid =?");
            //ps.setInt(1,quizid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if(rs != null) {
                */
                    PreparedStatement ps1 = connection.prepareStatement("SELECT * FROM Questions where question_id =?");
                    ps1.setInt(1, 1);
                    ResultSet rs1 = ps1.executeQuery();
                    while (rs1.next()) {
                        if (rs1 != null) {
                            question.setQuestion_text(rs1.getString("question_text"));
                            question.setCorrectAnswer(rs1.getInt("correct_answer_id"));
                            PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM Answers where answer_id =?");
                            ps2.setInt(1, rs1.getInt("answer_id"));
                            ResultSet rs2 = ps2.executeQuery();
                            while(rs2.next()) {
                                if (rs2 != null) {
                                    AnswerStore answer1 = new AnswerStore();
                                    answer1.setAnswer_id(rs2.getInt("answer_id"));
                                   // System.out.println(rs2.getInt("answer_id"));
                                    answer1.setAnswer_text(rs2.getString("answer_text"));
                                    answers.add(answer1);
                                    System.out.println(answers.get(rs2.getInt("answer_id") - 1).getAnswer_id());
                                }
                            }
                        }
                    }
            question.setAnswers(answers);
            questions.add(question);

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
