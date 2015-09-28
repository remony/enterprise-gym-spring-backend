package six.team.backend.dao;

import jdk.internal.util.xml.impl.Pair;
import six.team.backend.store.AnswerStore;
import six.team.backend.store.IndexStore;
import six.team.backend.store.QuestionStore;
import six.team.backend.store.QuizStore;
import six.team.backend.utils.Config;

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
            connection = Config.getDBConnection();
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

    public  LinkedList<QuestionStore> createQuiz(LinkedList<QuestionStore> questions, String quizTitle,int quizPoints, float passmark){
        Connection connection = null;
        try {
            connection = Config.getDBConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Quiz (quiz_id,quiz_title,points,passmark) values (?,?,?,?)");
            String randomQuizId = UUID.randomUUID().toString();
            randomQuizId = randomQuizId.replaceAll("-", "");
            ps.setString(1,randomQuizId);
            ps.setString(2, quizTitle);
            ps.setInt(3, quizPoints);
            ps.setFloat(4,passmark);
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
            connection = Config.getDBConnection();
          try{
              PreparedStatement ps1 = connection.prepareStatement("SELECT * FROM Quiz");
              ResultSet rs1 = ps1.executeQuery();
              while (rs1.next()) {
                if (rs1 != null) {
                    QuizStore quiz = new QuizStore();
                    quiz.setQuiz_id(rs1.getString("quiz_id"));
                    quiz.setQuiz_title(rs1.getString("quiz_title"));
                    quiz.setPassmark(rs1.getFloat("passmark"));
                    quiz.setPoints(rs1.getInt("points"));
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

    public boolean completeQuiz(int userid, String quizid, float score){
        Connection connection = null;
        boolean success = false;
        connection = Config.getDBConnection();
        try{
            PreparedStatement ps = connection.prepareStatement("Select * from Attempts where user_id =? AND quiz_id =?; ");
            ps.setInt(1,userid);
            ps.setString(2,quizid);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                PreparedStatement ps1 = connection.prepareStatement("Insert Into Attempts (user_id,quiz_id,score,no_of_attempts,completed) values(?,?,?,1,?)");
                ps1.setInt(1, userid);
                ps1.setString(2, quizid);
                ps1.setFloat(3, score);
                int completed =0;
                PreparedStatement ps2 = connection.prepareStatement("Select * from Quiz where quiz_id =?");
                ps2.setString(1, quizid);
                ResultSet rs1= ps2.executeQuery();
                while(rs1.next()){
                    if(score> rs1.getInt("pass_mark")){
                        completed = 1;
                        PointDAO pointDAO = new PointDAO();
                        pointDAO.updatePoints(userid, rs1.getInt("points"), "theory");
                    }
                }
                ps1.setInt(4,completed);
                ps1.executeUpdate();
            }else {
                while (rs.next()) {
                    PreparedStatement ps1 = connection.prepareStatement("Update Attempts set score =? , no_of_atttempts = no_of_attempts+1, completed = ? where quiz_id =? ");
                    ps1.setString(3, quizid);
                    ps1.setFloat(1, score);
                    int completed =0;
                    PreparedStatement ps2 = connection.prepareStatement("Select * from Quiz where quiz_id =?");
                    ps2.setString(1, quizid);
                    ResultSet rs1= ps2.executeQuery();
                    while(rs1.next()){
                        if(score> rs1.getInt("pass_mark")){
                            completed = 1;
                            PointDAO pointDAO = new PointDAO();
                            pointDAO.updatePoints(userid, rs1.getInt("points"), "theory");
                        }
                    }
                    ps1.setInt(2,completed);
                    ps1.executeUpdate();
                }
            }
            success = true;
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
        return success;
    }

    public QuizStore getQuizInfo(String quizid){
        Connection connection = null;
        QuizStore quiz = new QuizStore();
        connection = Config.getDBConnection();
        try{
            PreparedStatement ps1 = connection.prepareStatement("SELECT * FROM Quiz where quiz_id =?");
            ps1.setString(1,quizid);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                if (rs1 != null) {
                    quiz.setQuiz_id(rs1.getString("quiz_id"));
                    quiz.setQuiz_title(rs1.getString("quiz_title"));
                    quiz.setPassmark(rs1.getFloat("passmark"));
                    quiz.setPoints(rs1.getInt("points"));
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
        return quiz;
    }
}
