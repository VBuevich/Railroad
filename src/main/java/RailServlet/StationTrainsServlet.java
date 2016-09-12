package RailServlet;

import Railroad.RailroadDao;
import Railroad.Schedule;
import Service.MessageBean;
import Service.ServiceBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by vbuevich on 08.09.2016.
 */
@WebServlet(name = "findYourTrainServlet")
public class StationTrainsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
         throws ServletException, IOException {

        HttpSession session = req.getSession();
        ServiceBean bean = ServiceBean.get(session);
        MessageBean message = MessageBean.get(session);

        String stationName = req.getParameter("stationName");
        List<Schedule> scheduleList = RailroadDao.trainList(stationName);

        if (scheduleList == null || scheduleList.size() == 0) {
            bean.setScheduleList(null);
            message.setErrorMessage("Error: no departing trains for this station");
        }
        else {
            bean.setScheduleList(scheduleList);
            message.setErrorMessage(null);
        }

        resp.sendRedirect(req.getContextPath() + "/schedule.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        ServiceBean bean = ServiceBean.get(session);
        MessageBean message = MessageBean.get(session);

        String stationName = req.getParameter("stationName");
        List<Schedule> scheduleList = RailroadDao.trainList(stationName);

        if (scheduleList == null || scheduleList.size() == 0) {
            bean.setScheduleList(null);
            message.setErrorMessage("Error: no departing trains for this station");
        }
        else {
            bean.setScheduleList(scheduleList);
            message.setErrorMessage(null);
        }

        resp.sendRedirect(req.getContextPath() + "/schedule.jsp");
    }

}