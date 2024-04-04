package week5;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(
        urlPatterns = {"/login"}
)
public class LoginServlet extends HttpServlet {
    public Connection con = null;


    public void init()throws ServletException{
        ServletContext context = getServletConfig().getServletContext();
        String drivername = context.getInitParameter("drivername");
        String url = context.getInitParameter("url");
        String username = context.getInitParameter("username");
        String password = context.getInitParameter("password");
        try{
            Class.forName(drivername);
            Connection con = DriverManager.getConnection(url,username,password);
            System.out.println("-->connection"+con);
            this.con = con;
        }catch(Exception e){
            e.printStackTrace();
        }

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try{
            String sql ="Select * from tb_user where username = ? "+"and password = ?";
            PreparedStatement stat = con.prepareStatement(sql);
            stat.setString(1,username);
            stat.setString(2,password);
            ResultSet rs = stat.executeQuery();
            PrintWriter writer = response.getWriter();

            if(rs.next()){
                writer.println("<html><body>");
                writer.println("<h1>Login Success!!!</h1>");
                writer.println("<h2>Welcome:<h2>"+username);
                writer.println("</body></html>");
            }
            else{
                writer.println("<html><body>");
                writer.println("<h1>Login Error!!!</h1>");
                writer.println("</body></html>");
            }
            writer.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
